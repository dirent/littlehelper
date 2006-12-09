package de.dirent.littlehelper.pages;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.tapestry.annotations.InitialValue;
import org.apache.tapestry.html.BasePage;

import de.dirent.littlehelper.Kreditrechner;

public abstract class KreditrechnerPage extends BasePage {	
	
	@InitialValue( "100000.0" )
	public abstract double getBetrag();
	@InitialValue( "'0.05'" )
	public abstract double getZins();
	@InitialValue( "'0.01'" )
	public abstract double getTilgung();

	@InitialValue( "false" )
	public abstract boolean isShowResult();
	
	
	@InitialValue( "null" )
	public abstract Kreditrechner getKreditrechner();
	public abstract void setKreditrechner( Kreditrechner rechner );

	public abstract int getMonatIndex();
	public abstract Double getZwischenbetrag();

	
	@InitialValue( "''" )
	public abstract String getErrorMessage();
	public abstract void setErrorMessage( String error );
	
	
	public void calculate() {
		
		try {
		
			setKreditrechner( new Kreditrechner( getBetrag(), getZins(), getTilgung() ) );
			
		} catch( IllegalArgumentException iae ) {
			
			setErrorMessage( "Illegal parameter: " + iae.getMessage() );
		}
	}
	
	public List<Double> getKontoauszug() {
		
		List<Double> kontoauszug = new ArrayList<Double>();
		
		while( !getKreditrechner().isGetilgt() ) {
			
			getKreditrechner().tilge();
			kontoauszug.add( new Double(getKreditrechner().getRest() ) );
		}
		
		return kontoauszug;
	}
	
	
	public String convertToPercent( double value ) {
		
		NumberFormat nf = NumberFormat.getPercentInstance( getLocale() );
		nf.setMaximumFractionDigits(2);
		return nf.format(value);
	}
	
	public String convertToCurrency( double value ) {
		
		NumberFormat nf = NumberFormat.getNumberInstance( getLocale() );
		nf.setMaximumFractionDigits(2);
		return nf.format(value);
	}

	public String convertToNumber( double value ) {
		
		NumberFormat nf = NumberFormat.getNumberInstance( getLocale() );
		nf.setMaximumFractionDigits(2);
		return nf.format(value);
	}

	public String convertToTime( int index ) {
		
		Calendar c = Calendar.getInstance();
		c.add( Calendar.MONTH, index );
		
		DateFormat df = new SimpleDateFormat( "MM/yyyy" ); 
		return df.format(c.getTime());
	}
}
