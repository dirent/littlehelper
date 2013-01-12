package de.dirent.littlehelper;


import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author dirk
 *
 * Outputs ImageLinks for all files in rootPath to console 
 */
public class ImageLinkGenerator {

    
    public static void main( String[] args ) {
        
        String rootPath = "E:\\projects\\digilib-content\\privat\\urlaub\\lanzarote2006";
        String base = "privat/urlaub/lanzarote2006/";
        
        List<String> contentFiles = Arrays.asList( new File(rootPath).list() );
        Collections.sort(contentFiles);
        
        for( int i=0; i<contentFiles.size(); i++ ) {
        	Object cf = contentFiles.get(i);
        	
        	System.out.println( "<dl>" );
        	System.out.println( "<dt><a jwcid=\"@ImageLink\" contentId=\"" + base + cf + "\" target=\"_blank\" width=\"150\" border=\"0\" imageTitle=\"" + cf + "\"/></dt>" );
        	System.out.println( "<dd>Hier fehlt ein beschreibender Text.</dd>" );
        	System.out.println( "</dl>" );
        }
    }
}
