package de.dirent.littlehelper;

import java.io.File;
import java.util.Locale;

/**
 * @author dirk
 *
 * Finds differences between a winamp playlist and a directory listing
 */
public class MultiRenamer {

    
    public static void main( String[] args ) {
        
        String rootPath = "/home/dirk/Desktop/070730";

        String sourceSuffix = ".JPG";
        String targetSuffix = ".jpg";

        String sourcePrefix = "PICT";
        StrictDecimalFormat sourceIndexFormatter = new StrictDecimalFormat( Locale.getDefault(), "0000" );
        
        int startIndex = 13;
        int endIndex = 46;
        
        String targetPrefix = "mosel070730_";
        StrictDecimalFormat targetIndexFormatter = new StrictDecimalFormat( Locale.getDefault(), "00" );
        int targetIndex = 8;
        
        for( int i=startIndex; i<=endIndex; i++ ) {
        	
        	String sourcePath = rootPath + File.separator + 
        			sourcePrefix + sourceIndexFormatter.format(i) + sourceSuffix;
        	File source = new File( sourcePath );
        	
        	if( !source.exists() ) {
        		
        		System.out.println( "File " + sourcePath + " does not exist." );
        		continue;
        	}
        	
        	String targetPath = rootPath + File.separator + 
        			targetPrefix + targetIndexFormatter.format(targetIndex) + targetSuffix;
        	File target = new File( targetPath );
        	
        	if( !target.exists() ){
        		
        		source.renameTo( target );
        		
        	} else {
        		
        		System.out.println( "Target file already exists." );
        	}
        	
        	targetIndex++;
        }
    }
}
