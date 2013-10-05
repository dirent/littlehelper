package de.dirent.littlehelper.pages;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.util.TextStreamResponse;


@Import( stylesheet="context:/css/littlehelper.css" )
public class Index {
	
	@Property @Persist
	private Integer input;
	
	@Property
	private String inputMessage;
	
	@Component
	private Zone inputZone;
	
	@Inject
	private Request request;
	
	public Object onSuccessFromInput() {
	
		int potenz = 1;
		boolean isPowOfTwo = input == potenz;
		
		while( !isPowOfTwo  &&  potenz < input ) {
			
			potenz *= 2;
			isPowOfTwo = input == potenz;
		}
		
		if( isPowOfTwo ) {
			
			this.inputMessage = "Die Eingabe ist eine Zweiterpotenz.";
			
		} else {
			
			this.inputMessage = "Die Eingabe ist keine Zweiterpotenz.";
		}
		
		if( request.isXHR() ) return inputZone;
		
		return null;
	}
	
	
	@Inject
	private HttpServletRequest httpRequest;
	
	public Object onActionFromMyIp() {
		
		return new TextStreamResponse( "text/plain", httpRequest.getRemoteAddr() );
	}


	public Object onActionFromManifest() {
		
		return new StreamResponse() {
			
			@Override
			public void prepareResponse(Response response) {


				response.setHeader( "Expires", "0" );
			}
			
			@Override
			public InputStream getStream() throws IOException {

				String manifest = "CACHE MANIFEST\nhtml5app.html\nhtml5app.js";
				
				return new ByteArrayInputStream( manifest.getBytes( "utf-8" ) );
			}
			
			@Override
			public String getContentType() {

				return "text/cache-manifest";
			}
		};
	}
}
