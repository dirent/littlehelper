package de.dirent.littlehelper.services;


import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;


public class LittleHelperModule {
	
    public static void contributeApplicationDefaults(
            MappedConfiguration<String, Object> configuration) {

        configuration.add( "tapestry.supported-locales", "de" );
    	
    	// enable stacktrace on default exception report
    	configuration.add( SymbolConstants.PRODUCTION_MODE, false );
    	configuration.add( SymbolConstants.COMBINE_SCRIPTS, true );    	
        configuration.add( SymbolConstants.MINIFICATION_ENABLED, true );
    }
}
