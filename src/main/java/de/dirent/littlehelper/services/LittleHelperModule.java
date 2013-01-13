package de.dirent.littlehelper.services;


import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.services.ApplicationInitializerFilter;


public class LittleHelperModule {
	
    public static void contributeApplicationDefaults(
            MappedConfiguration<String, Object> configuration) {

        configuration.add( "tapestry.supported-locales", "de" );
    	
    	// enable stacktrace on default exception report
    	configuration.add( SymbolConstants.PRODUCTION_MODE, false );
    	configuration.add( SymbolConstants.COMBINE_SCRIPTS, true );    	
        configuration.add( SymbolConstants.MINIFICATION_ENABLED, true );

        configuration.add( SymbolConstants.HMAC_PASSPHRASE, "öabe$%lMMcd!" );
    }

    
    public static void contributeApplicationInitializer( OrderedConfiguration<ApplicationInitializerFilter> configuration ) {
    	
        configuration.add( "SvnKitInitializer", new SvnKitInitializer() );
    }
}
