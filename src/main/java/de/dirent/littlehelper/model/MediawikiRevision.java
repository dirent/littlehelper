package de.dirent.littlehelper.model;

import org.jsoup.nodes.Document;


public class MediawikiRevision {

	private int revisionNumber;
	private String revisionTitle;
	
	
	public MediawikiRevision( int revisionNumber, Document revision ) throws Exception {
		
		this.revisionNumber = revisionNumber;
		
		this.revisionTitle = revision.title();
	}
	
	
	public int getRevisionNumber() {
		
		return revisionNumber;
	}
	
	public String getTitle() {
		
		return revisionTitle;
	}
}
