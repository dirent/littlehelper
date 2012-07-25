package de.dirent.littlehelper.services;


import org.apache.tapestry5.services.ApplicationInitializer;
import org.apache.tapestry5.services.ApplicationInitializerFilter;
import org.apache.tapestry5.services.Context;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;


public class SvnKitInitializer implements ApplicationInitializerFilter {

    public void initializeApplication( Context context, 
    		ApplicationInitializer applicationInitializer ) {

		DAVRepositoryFactory.setup();
    }
}
