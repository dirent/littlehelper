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
	private Double betrag;
	
	@Property @Persist
	private Double zins;
	
	@Property @Persist
	private Double tilgung;
	
	@Property @Persist
	private boolean showResult;
	
	@Property @Persist( PersistenceConstants.FLASH )
	private String errorMessage;
	
	@SetupRender
	public void init() {
		
		if( betrag == null ) betrag = 10000.0;
		if( zins == null ) zins = 0.05;
		if( tilgung == null ) tilgung = 0.01; 
	}
	
	public void onSuccessFromCalculate() {
		
		startKreditrechner = true;
	}
}
