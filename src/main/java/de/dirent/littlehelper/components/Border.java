package de.dirent.littlehelper.components;


import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;


public class Border {
	
	public static final Map<String, String> pageNameMap;
	
	static {
		
		pageNameMap = new LinkedHashMap<String, String>();
		
		pageNameMap.put( "Index", "Home" );
		pageNameMap.put( "Kreditrechner", "Kreditrechner" );
	}
	

	@SuppressWarnings( "unused" )
	@Parameter( required=false, defaultPrefix="literal" ) @Property
	private String title = "Little Helper";
	
	@Parameter( required=false, defaultPrefix="block" ) @Property
	private Block sidebar;
	
	
	@SetupRender
	public void setUp() {
		
		if( sidebar == null ) {
			
			sidebar = resources.getBlock( "emptySideBar" );
		}
	}
	
	
	public Collection<String> getPageNames() {
		
		return pageNameMap.keySet();
	}
	
	@Property
	private String pageName;
	
	
	@Inject
	private ComponentResources resources;
	
	public boolean isDisabled() {
		
		return resources.getPageName().equals( pageName );
	}
	
	public String getDisplayName() {
		
		return pageNameMap.get( pageName );
	}
}