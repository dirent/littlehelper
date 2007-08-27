package de.dirent.littlehelper;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author dirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RandomFileChooser {

	public static void main(String[] args) {
	
		if( args != null  &&  args.length == 2 ) {
			
			new RandomFileChooser( args[0], args[1] );

		} else {
			
			System.out.println( "Usage: java randomFile rootPath targetPath" );
		}
	}
	
	
	private int fileCount = 0;
	private Map<Integer, String> files = new HashMap<Integer, String>();

	
	public RandomFileChooser( String pathToRoot, String pathToTarget ) {

		File root = new File( pathToRoot );
		if( root.isDirectory()  &&  root.exists() ) {
		
			searchForFiles( root );

			System.out.println( root.getName() + " contains " + fileCount + " files." );

			for( int i=0; i<13; i++ ) {
				Math.random();
			}
			
			int index = (int) Math.round( Math.random() * (fileCount+1) );
			File sourceFile = new File( (String) files.get( new Integer( index ) ) );
			System.out.println( "The " + index + "th file is " + sourceFile.getAbsolutePath() );
			
			try {
				File targetFile = new File( pathToTarget, sourceFile.getName() );
				if( !targetFile.exists() ) {
					targetFile.createNewFile();
					FileOutputStream fo = new FileOutputStream( targetFile );
					
					FileInputStream fi = new FileInputStream( sourceFile );
					byte[] buffer = new byte[8192];
					int read = -1;
					while( (read = fi.read( buffer )) != -1 ) {
						fo.write( buffer, 0, read );	
					}
					fi.close();
					fo.close();
					
					sourceFile.delete();
					System.out.println( "File successfully moved to " + pathToTarget );
					
				} else {
					System.out.println( "Could not write target file: " + targetFile.getAbsolutePath() + " already exists." );
				}
				
			} catch( IOException io ) {
				
				System.out.println( "An IOException occured: " + io.getMessage() );
			} 
		} else {
			System.out.println( pathToRoot + " is not an existing directory." );
		} 
	}
	
	public void searchForFiles( File root ) {
		String[] children = root.list();
		
		if( children.length == 0 ) {
			System.out.println("The directory " + root.getAbsolutePath() + " is empty.");
		}
		
		for( int i=0; i<children.length; i++ ) {
			File child = new File( root, children[i] );
			if( child.isDirectory() ) {
				searchForFiles( child );
				continue;
			}
			files.put( new Integer(fileCount++), child.getAbsolutePath() );
		}
	}
}