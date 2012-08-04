package de.dirent.littlehelper.pages;


import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dirent.littlehelper.model.MediawikiRevision;


@Import( stylesheet="context:/css/littlehelper.css" )
public class MediawikiScanner {


	private static final Logger logger = LoggerFactory.getLogger( MediawikiScanner.class );
	
	
	@Inject	
	private AlertManager alertManager;
	
	@Property @Persist
	private String wikiUrl;
	
	@Property @Persist
	private Integer fromRevision;
	
	@Property @Persist
	private Integer toRevision;

	
	@SetupRender
	public void init() {
	
		if( this.fromRevision == null  &&  this.toRevision == null ) onActionFromReset();
	}
	
	public void onActionFromReset() {
		
		this.wikiUrl = null;
		this.fromRevision = 1;
		this.toRevision = 1;
	}

	
	@Property @Persist
	private boolean wikiInitialized;
	
	@InjectComponent
	private Zone wikiZone;
	
	@Inject
	private Request request;
	
	public Object onSubmitFromAnalyze() {
	
		if( request.isXHR() )
			return wikiZone.getBody();
		
		return null;
	}
	
	public void onProgressiveDisplayFromWikiLoader() {
		
		this.wikiInitialized = false;
		
		if( fromRevision > toRevision ) {
			
			alertManager.alert( Duration.TRANSIENT, Severity.WARN, "Start-Revision muss kleiner oder gleich die End-Revision sein." );
			return;
		}
		
		if( InternalUtils.isNonBlank( this.wikiUrl ) ) {

			try {

				this.wiki = Jsoup.connect( this.wikiUrl ).get();
				this.analysis = new ArrayList<MediawikiRevision>();
				
				for( int i=fromRevision; i<=toRevision; i++ ) {
					
					try {
						
						String oldid = this.wikiUrl;
						if( !oldid.endsWith( "/" ) ) oldid += "/";
						oldid += "?oldid=" + i;
						
						Document revision = Jsoup.connect( oldid ).get();
						
						this.analysis.add( new MediawikiRevision( i, revision ) );
						
					} catch( Exception r ) {
						
						logger.error( "Could not read revision " + i + ": " + r.getMessage() );
					}
				}
				this.wikiInitialized = true;
				
			} catch( Exception e ) {
				
				alertManager.alert( Duration.TRANSIENT, Severity.WARN, "Could not connect to wiki: " + e.getMessage() );
				logger.error( "Could not connect to wiki: " + e.getMessage() );
				this.wikiInitialized = false;
			}
			
		}
	}
	
	
	@Property
	private Document wiki;
	
	@Property
	private List<MediawikiRevision> analysis;
	
	@Property
	private MediawikiRevision revision;
	
	
	public String getWikiTitle() {
		
		if( wiki != null ) return wiki.title();
		
		return "unknown";
	}
}
