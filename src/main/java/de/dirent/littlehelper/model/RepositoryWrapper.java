package de.dirent.littlehelper.model;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class RepositoryWrapper implements Serializable {

	private static final long serialVersionUID = 8567754306612898508L;

	private transient SVNClientManager clientManager; 

	private transient SVNRepository repository;
	private String repositoryUrl;
	private String username;
	private String password;
	
	private long revision = 1;
	
	
	public RepositoryWrapper() {
	}

	
	public void initialize( String repositoryUrl,
			String username,
			String password ) throws SVNException {
		
		this.repositoryUrl = repositoryUrl;
		this.username = username;
		this.password = password;
		
		setUpRepository();
	}
	
	protected void setUpRepository() throws SVNException {
		
		repository = SVNRepositoryFactory.create( SVNURL.parseURIDecoded( this.repositoryUrl ) );
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager( username , password );
		repository.setAuthenticationManager( authManager );
		
		clientManager = SVNClientManager.newInstance();
		clientManager.setAuthenticationManager(authManager);

		revision = 1; 
	}


	public SVNURL getRepositoryRoot() throws SVNException {
		
		if( this.repository == null ) setUpRepository();
		
		return this.repository.getRepositoryRoot(true);
	}
	
	
	public String getRepositoryUUID() throws SVNException {
		
		if( this.repository == null ) setUpRepository();
		
		return this.repository.getRepositoryUUID(true);
	}
	
	
	public long getLatestRevision() throws SVNException {
		
		if( this.repository == null ) setUpRepository();
		
		return this.repository.getLatestRevision();
	}
	
	
	public long getRevision() {
		
		return this.revision;
	}
	
	public void setRevision( long revision ) {
		
		this.revision = revision;
	}
	
	
	@SuppressWarnings( "unchecked" )
	public Collection<SVNLogEntry> log( String[] targetPaths, 
			Collection<SVNLogEntry> entries,
			long startRevision, 
			long endRevision, 
			boolean changedPath,
			boolean strictNode) throws SVNException {
		
		if( this.repository == null ) setUpRepository();
		
		return this.repository.log( targetPaths, entries, startRevision, endRevision, changedPath, strictNode );
	}
	

	public void getFile( String path, OutputStream out ) throws SVNException {
	
		if( this.repository == null ) setUpRepository();
		repository.getFile( path, revision, null, out );
	}
	
	
	public void doDiff( SVNURL diffUrl, OutputStream out ) throws SVNException {
		
		SVNDiffClient differ = clientManager.getDiffClient();
		
		differ.doDiff( diffUrl,  
				SVNRevision.create(revision-1), 
				diffUrl,
				SVNRevision.create(revision),
				SVNDepth.EMPTY,
				true,
				out );
	}
}
