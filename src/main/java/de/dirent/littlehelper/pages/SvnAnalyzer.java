package de.dirent.littlehelper.pages;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.http.services.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;

import de.dirent.littlehelper.model.RepositoryWrapper;


@Import( stylesheet="context:/css/littlehelper.css" )
public class SvnAnalyzer {

	private static final Logger logger = LoggerFactory.getLogger( SvnAnalyzer.class );
	
	
	@Inject	
	private AlertManager alertManager;
	
	@Property @Persist
	private String repositoryUrl;
	
	@Property @Persist
	private String username;
	
	@Property @Persist
	private String password;
	
	public void onActionFromReset() {
		
		this.repositoryUrl = null;
		this.username = null;
		this.password = null;
		
		this.repository = null;
	}
	
	@SessionState
	private RepositoryWrapper repository;
	
	
	@Property @Persist
	private boolean repositoryInitialized;
	

	public String getRepositoryRoot() {
		
		if( this.repository != null ) {
			
			try {
				
				return this.repository.getRepositoryRoot().toString();
				
			} catch( Exception e ) {
				
				logger.warn( "Could not determine repository root: " + e.getMessage() );
				alertManager.alert( Duration.TRANSIENT, Severity.WARN, "Could not determine repository root: " + e.getMessage() );
			}
		}
		
		return "";
	}
	
	
	public String getRepositoryUUID() {
		
		if( this.repository != null ) {
			
			try {
				
				return this.repository.getRepositoryUUID();
				
			} catch( Exception e ) {
				
				logger.warn( "Could not determine repository uuid: " + e.getMessage() );
				alertManager.alert( Duration.TRANSIENT, Severity.WARN, "Could not determine repository uuid: " + e.getMessage() );
			}
		}
		
		return "unknown";
	}
	
	
	public long getLatestRevision() {
		
		if( this.repository != null ) {
			
			try {
				
				return this.repository.getLatestRevision();
				
			} catch( Exception e ) {
				
				logger.warn( "Could not determine latest revision: " + e.getMessage() );
				alertManager.alert( Duration.TRANSIENT, Severity.WARN, "Could not determine latest revision: " + e.getMessage() );
			}
		}
		
		return 0;
	}
	
	
	@InjectComponent
	private Zone repositoryZone;
	
	@Inject
	private Request request;
	
	public Object onSubmitFromAnalyze() {
	
		if( request.isXHR() )
			return repositoryZone.getBody();
		
		return null;
	}
	
	public void onProgressiveDisplayFromRepositoryLoader() {
		
		if( InternalUtils.isNonBlank( this.repositoryUrl ) ) {

			this.repository = new RepositoryWrapper();
			
			try {
				
				this.repository.initialize( this.repositoryUrl, 
						this.username, 
						this.password );
				
				// check connection
				this.repository.getRepositoryRoot();
				
				this.repositoryInitialized = true;
				
			} catch( Exception svn ) {
				
				alertManager.alert( Duration.TRANSIENT, Severity.WARN, "Could not connect to svn repository: " + svn.getMessage() );
				logger.error( "Could not connect to svn repository: " + svn.getMessage() );
				this.repositoryInitialized = false;
			}
			
		} else {
			
			this.repositoryInitialized = false;
		}
	}



	public long getRevision() {
		
		return this.repository.getRevision();
	}
	
	public void setRevision( long revision ) {
		
		this.repository.setRevision(revision);
	}


	@InjectComponent
	private Zone revisionZone;
	
	public Object onSubmitFromRevisionForm() {
		
		return revisionZone.getBody();
	}
	
	@Property
	private SVNLogEntry logEntry;
	
	public void onProgressiveDisplayFromRevisionLoader() {
		
		try {
			
			Collection<SVNLogEntry> logEntries = 
					repository.log( new String[] {""}, null, repository.getRevision(), repository.getRevision(), true, true );

			if( logEntries.size() == 1 ) {
				this.logEntry = logEntries.iterator().next();
			}
			
		} catch( SVNException svn ) {
			
			String message = "Could not log for revision " + repository.getRevision() + ": " + svn.getMessage();
			logger.warn( message );
			alertManager.alert( Duration.TRANSIENT, Severity.WARN, message );
		}
	}
	
	
	public boolean getRevisionOutOfRange() {
		
		return getRevision() < 1  ||  getRevision() > getLatestRevision();
	}
	

	@Property
	private SVNLogEntryPath changedPath;
	
	@SuppressWarnings("unchecked")
	public Collection<SVNLogEntryPath> getChangedPaths() {
		
		return logEntry.getChangedPaths().values();
	}
	
	public char getChangedPathType() {
		
		if( changedPath == null ) return ' ';
		return changedPath.getType();
	}
	
	public String getChangedPathPath() {
		
		if( changedPath == null ) return "";
		return changedPath.getPath();
	}
	
	public String getChangedPathCopyInfo() {
		
		return ( ( changedPath.getCopyPath( ) != null ) ? " (from "
				+ changedPath.getCopyPath( ) + " revision "
				+ changedPath.getCopyRevision( ) + ")" : "" );
	}



	@Property
	private String diffOutput;
	
	@InjectComponent
	private Zone diffZone;
	
	
	public Object onAction( String changedPathType, String changedPathPath ) {
		
		this.changedPath = new SVNLogEntryPath( changedPathPath, changedPathType.charAt(0), null, 0L );
		
		return diffZone.getBody();
	}
	
	
	public void onProgressiveDisplayFromDiffLoader( String changedPathType, String changedPathPath ) {
		
		try {

			SVNURL root = repository.getRepositoryRoot();
			if( root != null ) {
				
				try {
					
					ByteArrayOutputStream out = new ByteArrayOutputStream(512);
					SVNURL diffUrl = root.appendPath( changedPathPath, false );
					
					if( "A".equals( changedPathType ) ) {
						
						String intro = "New Entry " + changedPathPath + ":\n";
						intro += "===================================================================\n";
						
						try {
							out.write( intro.getBytes() );
						} catch( IOException io ) {}
						
						repository.getFile( changedPathPath, out );
						
					} else {
	
						repository.doDiff( diffUrl, out );
					}
					
					try {
						diffOutput = new String( out.toByteArray(), "UTF-8" );
					} catch( UnsupportedEncodingException e ) {}
	
				} catch( SVNException svne ) {
					svne.printStackTrace();
				}
			}
			
		} catch( SVNException svn ) {
			
			String message = "Could not load diff for " + changedPath + ": " + svn.getMessage();
			logger.warn( message );
			alertManager.alert( Duration.TRANSIENT, Severity.WARN, message );
		}
	}
}
