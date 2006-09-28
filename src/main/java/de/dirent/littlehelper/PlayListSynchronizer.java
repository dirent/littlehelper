package de.dirent.littlehelper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author dirk
 *
 * Finds differences between a winamp playlist and a directory listing
 */
public class PlayListSynchronizer {

    
    public static void main( String[] args ) {
        
        long millis = System.currentTimeMillis();

        String playlistPath = null;
        
        if( args == null  ||  args.length != 1 ) {
            
            System.out.println( "Usage java PlayListSynchronizer [path to playerlist]" );
            System.exit(0);
        }

        if( args.length > 0 ) playlistPath = args[0];

        File listing = null;
        StringBuffer playList = new StringBuffer();

        try {
            
            File playlistFile = new File( playlistPath );
            listing = playlistFile.getParentFile();
            
            Reader in = new FileReader( playlistFile );
            char[] readBuffer = new char[2028];
            int read = in.read( readBuffer );
            while( read != -1 ) {
                
                char[] targetBuffer = new char[read]; 
                System.arraycopy( readBuffer, 0, targetBuffer, 0, read );
                playList.append(targetBuffer);
                
                read = in.read( readBuffer );
            }
                
        } catch( IOException io ) {
            
            System.out.println( "Could not open playlist: " + io.getMessage() );
        }
        
        //System.out.println( "Playerlist read: " + playList );
        
        String[] entries = new String[0];
        if( listing != null  &&  listing.isDirectory() ) entries = listing.list();
        
        System.out.println( "Checking " + entries.length + " files in directory." );

        for( int i=0; i<entries.length; i++ ) {
            if( playList.indexOf( entries[i]) == -1 ) {
                System.out.println( "File " + entries[i] + " kommt nicht in der Playlist vor." );
            }
        }
        
        System.out.println( "Needed " + (System.currentTimeMillis()-millis) + "ms." );
    }
}
