package de.dirent.littlehelper;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.PeriodType;

public class QuartalTilger {


	public static void main(String[] args) throws Exception {

		int kredit = 9000;
		double zins = 0.03;
		int tilgung = 250;
		DateFormat df1 = new SimpleDateFormat( "dd.MM.yyyy" );
		Date start = df1.parse( "15.11.2011" );
		
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		
		QuartalTilger tilger = new QuartalTilger(kredit, tilgung, zins, c);
		
		NumberFormat nf = NumberFormat.getPercentInstance( Locale.GERMAN );
		nf.setMaximumFractionDigits(2);
		DateFormat df = new SimpleDateFormat( "MM/yyyy" ); 
		
		System.out.println( "Nächste Rate: " + tilger.getNextRate() );
	}
	
	
	private Period quartal = new Period( Months.THREE );
	private LocalDate today;
	private double rest;
	private double tilgung;
	private double zins;
	
	public QuartalTilger( int kredit, double tilgung, double zins, Calendar today ) {
		
		this.rest = kredit;
		this.tilgung = tilgung;
		this.zins = zins;
		this.today = new LocalDate( today.getTimeInMillis() );
	}
	
	public double getQuartalsZins() {

		double qz = rest * zins * getDaysToQuartalEnd()/365.0;
		
		return Math.round( 100.0*qz)/100.0;
	}
	
	public LocalDate getNextQuartalStart() {
		
		int month = today.monthOfYear().get();
		int monthsToNextQuartal;
		switch( month % 3 ) {
		case 1:
			monthsToNextQuartal = 3;
			break;
			
		case 2:
			monthsToNextQuartal = 2;
			break;
			
		default:
			monthsToNextQuartal = 1;
		}
		
		return today.plusMonths( monthsToNextQuartal ).withDayOfMonth(1);
	}
	
	public int getDaysToQuartalEnd() {
		
		return Days.daysBetween( today, getNextQuartalStart() ).getDays();
	}
	
	public double getTilgung() {
		
		return this.tilgung;
	}
	
	public double getNextRate() {
		
		return this.tilgung + getQuartalsZins();
	}
}
