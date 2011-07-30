package de.dirent.littlehelper.pages;


import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;


@Import( stylesheet="context:/css/littlehelper.css" )
public class Kreditrechner {

	@Property @Persist
	private boolean startKreditrechner;
	
	@Property @Persist
	private boolean showResult;
	
	@Property @Persist
	private de.dirent.littlehelper.Kreditrechner kreditRechner;
	
	@Property @Persist( PersistenceConstants.FLASH )
	private String errorMessage;
	
	@SetupRender
	public void init() {
		
		if( kreditRechner == null ) {

			reset();
		}
	}
	
	protected void reset() {
		
		kreditRechner = new de.dirent.littlehelper.Kreditrechner( 10000.0, 0.05, 0.01 );
		startKreditrechner = false;
	}
	
	public void onSuccessFromCalculate() {
		
		startKreditrechner = true;
	}
	
	public void onActionFromReset() {
		
		reset();
	}
}
