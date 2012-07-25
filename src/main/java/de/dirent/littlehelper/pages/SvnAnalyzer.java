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
import org.apache.tapestry5.services.Request;

import de.dirent.littlehelper.model.RepositoryWrapper;


@Import( stylesheet="context:/css/littlehelper.css" )
public class SvnAnalyzer {

	@Inject	
	private AlertManager alertManager;
	

	@Property @Persist
	private String repositoryUrl;
	
	@Property @Persist
	private String username;
	
	@Property @Persist
	private String password;
	
	@SessionState @Property
	private RepositoryWrapper repository;
	
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
		
		this.repository = new RepositoryWrapper();
		
		try {
			
			this.repository.initialize( this.repositoryUrl, 
					this.username, 
					this.password );
			
		} catch( Exception svn ) {
			
			alertManager.alert( Duration.SINGLE, Severity.ERROR, svn.getMessage() );
			svn.printStackTrace();
		}
	}
}
