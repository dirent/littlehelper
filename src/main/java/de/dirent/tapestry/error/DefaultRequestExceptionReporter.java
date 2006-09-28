package de.dirent.tapestry.error;


import org.apache.commons.logging.Log;
import org.apache.tapestry.error.RequestExceptionReporterImpl;


public class DefaultRequestExceptionReporter extends RequestExceptionReporterImpl {

	private Log logger;
	

	public void setLog( Log logger ) {
		
		this.logger = logger;
		super.setLog(logger);
	}
	
    public void reportRequestException( String message, Throwable cause ) {
    	
    	if( logger != null )
    		logger.warn( "Request could be finished: " + message + " (" + cause + ")" );
    }
}
