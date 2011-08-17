package de.dirent.littlehelper.pages;


import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.TextStreamResponse;


@Import( stylesheet="context:/css/littlehelper.css" )
public class Index {
	
	@Inject
	private HttpServletRequest request;
	
	public Object onActionFromMyIp() {
		
		return new TextStreamResponse( "text/plain", request.getRemoteAddr() );
	}
}
