package de.dirent.littlehelper.pages;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.util.TextStreamResponse;


@Import( stylesheet="context:/css/littlehelper.css" )
public class Index {
	
	@Inject
	private HttpServletRequest request;
	
	public Object onActionFromMyIp() {
		
		return new TextStreamResponse( "text/plain", request.getRemoteAddr() );
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
