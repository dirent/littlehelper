package de.dirent.littlehelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Sum {

	public static void main( String[] args ) throws NoSuchAlgorithmException, FileNotFoundException {
	
		MessageDigest digest = MessageDigest.getInstance("MD5");
		if( args == null  ||  args.length == 0 ) {
			
			System.out.println( "Usage: Md5Sum filename" );
			System.exit(0);
		}
		InputStream is = new FileInputStream( new File(args[0]) );
		byte[] buffer = new byte[32*4096];
		int read = 0;
		try {
			while( (read = is.read(buffer)) > 0) {
				digest.update(buffer, 0, read);
			}		
			byte[] md5sum = digest.digest();
			BigInteger bigInt = new BigInteger(1, md5sum);
			String output = bigInt.toString(16);
			System.out.println("MD5: " + output);
			
		} catch( IOException e ) {
			throw new RuntimeException("Unable to process file for MD5", e);
		} finally {
			try {
				is.close();
			} catch(IOException e) {
				throw new RuntimeException("Unable to close input stream for MD5 calculation", e);
			}
		}		
	}
}
