package de.dirent.littlehelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class LogSorter {

	/**
	 * @param args
	 */
	public static void main( String[] args ) throws IOException, ParseException {
		
		SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss Z", Locale.GERMAN );
		SimpleDateFormat output = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss Z '('EEE, dd. MMM yyyy')'", Locale.GERMAN );

		// parameters
		Date startWith = df.parse( "1996-01-01 00:00:00 +0000" );
		int maxcount = 25;
		
		
		Map<Integer, Date> raw = new HashMap<Integer, Date>();
		BufferedReader reader = new BufferedReader( new FileReader( new File( "C:/temp/apache.log" ) ) );

		String line = null;
		int i = 1;
		
		do {
			
			line = reader.readLine();

			if( line == null  ||  !line.startsWith( "r" ) ) continue;
			
			try {

				StringTokenizer t = new StringTokenizer( line, "|" );
				
				String revisionString = t.nextToken().trim();
				
				int revision = Integer.parseInt( revisionString.substring(1) );
				
				t.nextToken().trim();
				
				String dateString = t.nextToken().trim();
				
				int hyph = dateString.indexOf( "(" );
				if( hyph > 0 ) dateString = dateString.substring( 0, hyph-1 );
				
				Date revisionDate = df.parse( dateString );
				
				raw.put( revision, revisionDate );
				
			} catch( Throwable t ) {
				
				System.out.println( "Could not parse line " + i + ": " + t.getMessage() );
			}
			
			i++;
			
		} while( line != null );

		@SuppressWarnings("rawtypes")
		Comparator dc = new DateComparator(raw);
		@SuppressWarnings("unchecked")
		Map<Integer, Date> revisionMap = new TreeMap<Integer, Date>(dc);

		System.out.println( "Sorting " + raw.size() + " revisions..." );
		revisionMap.putAll(raw);
		
		int count = 1;
		for (Integer key : revisionMap.keySet() ) {
			
			if( startWith != null  &&  startWith.after( revisionMap.get(key ) ) ) continue;
			
            System.out.println("Revision " + key + " from "+ output.format( revisionMap.get(key) ) );
            if( ++count > maxcount ) break;
        }

		reader.close();
	}
	
	public static class DateComparator implements Comparator<Object> {

		  Map<Integer,Date> base;
		  public DateComparator(Map<Integer,Date> base) {
		      this.base = base;
		  }

		  public int compare(Object a, Object b) {

		    if( base.get(a).after( base.get(b)) ) {
		      return 1;
		    } else if( base.get(a).getTime() == base.get(b).getTime() ) {
		      return ((Integer) a).compareTo( (Integer) b );
		    } else {
		      return -1;
		    }
		  }
		}
}
