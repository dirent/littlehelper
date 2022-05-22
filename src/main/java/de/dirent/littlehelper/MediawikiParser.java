package de.dirent.littlehelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MediawikiParser {

    public static void main(String[] args) {

        MediawikiParser parser = new MediawikiParser( "E:/csv/dewiki-20061130-pages-meta-history.xml" );

        try {

            long millis = System.currentTimeMillis();
            System.out.println( "Parsing xml..." );
            parser.parse();
            System.out.println( "Done in " + Math.round( (System.currentTimeMillis()-millis)/1000 ) + " seconds." );
            System.out.println( "Found " + parser.getPageCount() + " pages." );
            System.out.println( "Found " + parser.getRevisionCount() + " revisions." );

            Map<Long,String> revisionId2TimestampMap = parser.getRevisions();
            if( !revisionId2TimestampMap.isEmpty() ) {

                // sort map by values
                Set<Entry<Long,String>> entrySet = revisionId2TimestampMap.entrySet();
                Map<Long, String> sortedMap = 
                    entrySet.stream()
                        .sorted( Entry.comparingByValue() )
                        .collect( Collectors.toMap( Entry::getKey, Entry::getValue,
                                                    (e1, e2) -> e1, LinkedHashMap::new ) );

                // write to csv
                try {

                    System.out.println( "Writing csv..." );
                    FileWriter writer = new FileWriter("E:/csv/test.csv");

                    writer.append("id");
                    writer.append(',');
                    writer.append("Creation Date");
                    writer.append('\n');

                    Iterator<Long> it = sortedMap.keySet().iterator();
                    while( it.hasNext() ) {
                        Long revId = it.next();
                        writer.append(revId.toString());
                        writer.append(',');
                        writer.append( sortedMap.get(revId) );
                        writer.append( "\n" );
                    }

                    writer.flush();
                    writer.close();
                    System.out.println( "Done." );

                } catch( IOException io ) {
                    System.err.println("Could not create csv output: " + io.getMessage() );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public MediawikiParser( String filename ) {
        this.filename = filename;
    }

    private String filename;

    Map<Long,String> revisionId2TimestampMap = new HashMap<>();
    public Map<Long,String> getRevisions() {
        return revisionId2TimestampMap;
    }

    int pageCount = 0;

    public int getPageCount() {
        return pageCount;
    }

    boolean statePage = false;
    boolean stateTitle = false;
    String title = "";

    boolean stateRevision = false;
    int revisionCount = 0;

    public int getRevisionCount() {
        return revisionCount;
    }

    boolean stateId = false;
    Long revisionId = null;

    boolean stateTimestamp = false;
    String rawTimestamp = "";

    boolean stateContributor = false;

    public void parse() throws Exception {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setFeature( XMLConstants.FEATURE_SECURE_PROCESSING, false );
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

            public void startElement( String uri, String localName,String qName,
                        Attributes attributes) throws SAXException {

                if( "page".equals(qName) ) {
                    pageCount++;
                    statePage = true;
                }
                if( "title".equals(qName) ) {
                    stateTitle = true;
                }
                if( "revision".equals(qName) ) {
                    stateRevision = true;
                    revisionCount++;
                }
                if( "id".equals(qName) ) {
                    stateId = true;
                }
                if( "timestamp".equals(qName) ) {
                    stateTimestamp = true;
                }
                if( "contributor".equals(qName) ) {
                    stateContributor = true;
                }
            }

            public void endElement( String uri, String localName,
                String qName) throws SAXException {

                if( "page".equals(qName) ) {
                    statePage = false;
                    title = "";
                }
                if( "title".equals(qName) ) {
                    stateTitle = false;
                }
                if( "id".equals(qName) ) {
                    stateId = false;
                }
                if( "revision".equals(qName) ) {
                    if( revisionId != null ) {
                        revisionId2TimestampMap.put( revisionId, rawTimestamp.trim() + " | " + title );
                    }
                    stateRevision = false;
                    revisionId = null;
                    rawTimestamp = "";
                }
                if( stateTimestamp ) {
                    stateTimestamp = false;
                }
                if( "contributor".equals(qName) ) {
                    stateContributor = false;
                }
            }

            public void characters( char ch[], int start, int length ) throws SAXException {

                if( statePage  &&  stateTitle ) {
                	title = new String( ch, start, length );
                }
                if( stateId  &&  stateRevision  &&  !stateContributor ) {
                    try {
                        revisionId = new Long( new String( ch, start, length ).trim() );
                    } catch( NumberFormatException nfe ) {
                        System.err.println( "Unparseable revision id: " + nfe.getMessage() );
                    }
                }
                if( stateTimestamp ) {
                	rawTimestamp = new String( ch, start, length );
                }
            }
        };

        File file = new File(filename);
        InputStream is = new ProgressInputStream( new FileInputStream(file), file.length() );
        saxParser.parse( is, handler );
    }
}
