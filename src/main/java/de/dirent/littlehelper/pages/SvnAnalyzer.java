package de.dirent.littlehelper.pages;


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
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
