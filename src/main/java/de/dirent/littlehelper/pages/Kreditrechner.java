package de.dirent.littlehelper.pages;


import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;


@Import( stylesheet="context:/css/littlehelper.css" )
public class Kreditrechner {

	@Property @Persist
	private boolean startKreditrechner;

	@Property @Persist
	private boolean showResult;

	@Property @Persist
	private de.dirent.littlehelper.Kreditrechner kreditRechner;

	@Property
	private int monatIndex;
	
	@Property
	private double zwischenbetrag;

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

	
	public List<Double> getKontoauszug() {
	
		List<Double> kontoauszug = new ArrayList<Double>();
	
		while( kreditRechner != null  &&  !kreditRechner.isGetilgt() ) {
		
			kreditRechner.tilge();
			kontoauszug.add( new Double( kreditRechner.getRest() ) );
		}
	
		return kontoauszug;
	}

	
	public void onSuccessFromCalculate() {

		startKreditrechner = true;
		if( kreditRechner != null ) kreditRechner.init();
	}

	public void onActionFromReset() {

		reset();
	}

	
	@Inject
	private JavaScriptSupport javascriptSupport;
	
	@AfterRender
	public void afterRender() {
		
		if( showResult ) {
		
			javascriptSupport.addScript( "$( \"resultpanel\" ).show();" );
		}
	}
	
	@Inject
	private ComponentResources resources;

	public String convertToPercent( double value ) {

		NumberFormat nf = NumberFormat.getPercentInstance( resources.getLocale() );
		nf.setMaximumFractionDigits(2);
		return nf.format(value);
	}

	public String convertToCurrency( double value ) {

		NumberFormat nf = NumberFormat.getNumberInstance( resources.getLocale() );
		nf.setMaximumFractionDigits(2);
		return nf.format(value);
	}
	
	public String convertToYear( int value ) {

		NumberFormat nf = NumberFormat.getNumberInstance( resources.getLocale() );
		nf.setMaximumFractionDigits(2);
		return nf.format( (double) value/12.0 );
	}
	
	public String convertToTime( int index ) {

		Calendar c = Calendar.getInstance();
		c.add( Calendar.MONTH, index );

		DateFormat df = new SimpleDateFormat( "MM/yyyy" ); 
		return df.format(c.getTime());
	}
}
