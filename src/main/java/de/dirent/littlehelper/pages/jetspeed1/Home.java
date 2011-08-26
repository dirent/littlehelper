package de.dirent.littlehelper.pages.jetspeed1;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.jetspeed.SearchEngine;
import org.apache.jetspeed.Util;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;


@Import( stylesheet = { "context:/css/littlehelper.css", "context:/css/jetspeed1.css" } )
public class Home {
	
    private static SearchEngine search_engines[] = {
            new SearchEngine("AltaVista", "http://www.www.altavista.com", Util.IMAGE_SEARCH_ROOT + "altavista.gif", "altavista", "pg=q&kl=XX&stype=stext&search.x=13&search.y=8&q=", "http://www.altavista.com/cgi-bin/query"),
            new SearchEngine("DejaNews", "http://www.dejanews.com", Util.IMAGE_SEARCH_ROOT + "dejanews.gif", "dejanews", "DBS=2&OP=dnquery.xp&ST=QS&QRY=", "http://www.deja.com/qs.xp"),
            new SearchEngine("Excite", "http://www.excite.com", Util.IMAGE_SEARCH_ROOT + "excite.gif", "excite", "Submit=Search&search=", "http://search.excite.com/search.gw"),
            new SearchEngine("FreshMeat", "http://www.freshmeat.net", Util.IMAGE_SEARCH_ROOT + "freshmeat.gif", "freshmeat", "query=", "http://core.freshmeat.net/search.php3"),
            new SearchEngine("HotBot", "http://www.hotbot.com", Util.IMAGE_SEARCH_ROOT + "hotbot.gif", "hotbot", "SM=MC&DV=0&LG=any&DC=10&DE=2&_v=2&OPs=MDRTP&Search.x=40&Search.y=7&MT=", "http://www.hotbot.com/"),
            new SearchEngine("InfoSeek", "http://www.infoseek.com", Util.IMAGE_SEARCH_ROOT + "infoseek.gif", "infoseek", "col=WW&sv=IS&lk=noframes&svx=home_searchbox&qt=", "http://infoseek.go.com/Titles"),
            new SearchEngine("Lycos", "http://www.lycos.com", Util.IMAGE_SEARCH_ROOT + "lycos.gif", "lycos", "cat=dir&x=63&y=12&maxhits=10&query=", "http://www.lycos.com/cgi-bin/pursuit"),
            //new SearchEngine("MediaFind", "http://www.mediafind.com", Util.IMAGE_SEARCH_ROOT + "mediafind.gif", "mediafind", "", ""),
            new SearchEngine("WebCrawler", "http://www.webcrawler.com", Util.IMAGE_SEARCH_ROOT + "webcrawler.gif", "webcrawler", "searchText=", "http://www.webcrawler.com/cgi-bin/WebQuery"),
            new SearchEngine("Yahoo", "http://www.yahoo.com", Util.IMAGE_SEARCH_ROOT + "yahoo.gif", "yahoo", "p=", "http://search.yahoo.com/bin/search"),
          };

	@Property @Persist
	private String searchString;
	
	@Property @Persist
	private Integer searchEngineId;
	
	
	@SetupRender
	public void setup() {
		
		if( searchEngineId == null  ||  searchEngineId < 0 ||  searchEngineId >= search_engines.length ) {
			searchEngineId = search_engines.length-1;
		}
	}
	
	
	public SearchEngine getSearchEngine() {
		
		return search_engines[searchEngineId];
	}
	
	
	public Object onSuccessFromSearch() {
		
		System.out.println( "Search string: " + searchString );
		
		SearchEngine searchEngine = getSearchEngine();
		
		URL fallback = null;
		try { new URL( searchEngine.getAddress() ); } catch( MalformedURLException mal ) {}
		
		try {
			return new URL( "http://search.yahoo.com/bin/search?p=" + URLEncoder.encode( searchString, "utf-8" ) );
		} catch( Exception e ) {
			
			return fallback;
		}
	}
	
	
	public String getNiceDate() {
		
		return Util.getNiceDate();
	}
}
