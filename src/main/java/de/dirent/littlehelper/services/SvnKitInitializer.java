package de.dirent.littlehelper.services;


import org.apache.tapestry5.http.services.ApplicationInitializer;
import org.apache.tapestry5.http.services.ApplicationInitializerFilter;
import org.apache.tapestry5.http.services.Context;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;


public class SvnKitInitializer implements ApplicationInitializerFilter {

    public void initializeApplication( Context context, 
    		ApplicationInitializer applicationInitializer ) {

		DAVRepositoryFactory.setup();
    }
}
