package de.dirent.littlehelper.model;

import org.jsoup.nodes.Document;


public class MediawikiRevision {

	private int revisionNumber;
	private String revisionTitle;
	private String revisionInfo;
	
	
	public MediawikiRevision( int revisionNumber, Document revision ) throws Exception {
		
		this.revisionNumber = revisionNumber;
		
		this.revisionTitle = revision.title();
		
		this.revisionInfo = revision.select( "#mw-revision-info" ).text();
	}
	
	
	public int getRevisionNumber() {
		
		return revisionNumber;
	}
	
	public String getTitle() {
		
		return revisionTitle;
	}
	
	public String getInfo() {
		
		return revisionInfo;
	}
	
	/**
	 * Returns true, if this revision is deleted
	 */
	public boolean isDeleted() {
		
		return revisionInfo == null  ||  revisionInfo.trim().length() == 0;
	}
}
