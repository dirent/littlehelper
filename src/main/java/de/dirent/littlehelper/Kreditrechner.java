package de.dirent.littlehelper;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Kreditrechner implements Serializable {

	private static final long serialVersionUID = 1923541436664902174L;

	
	public static void main(String[] args) {

		boolean zeigeZwischenschritt = false;
		
        double start = 150000.0;
		double zins = 5.0/100.0;
		double tilgung = 1.0/100.0;
		Kreditrechner rechner = new Kreditrechner( start, zins, tilgung );
        
		Calendar c = Calendar.getInstance();
		NumberFormat nf = NumberFormat.getPercentInstance( Locale.GERMAN );
		nf.setMaximumFractionDigits(2);
		DateFormat df = new SimpleDateFormat( "MM/yyyy" ); 
		
		System.out.println( "Startbetrag: " + rechner.getStart() + "; Zins: " + nf.format(rechner.getZins()) + 
				" (" + nf.format(rechner.getTilgung()) + " Tilgung); Rate: " + rechner.getRate() );
		
		while( !rechner.isGetilgt() ) {
			
            double letzterRest = rechner.getRest();
			rechner.tilge();
            double getilgt = letzterRest - rechner.getRest();
			c.add( Calendar.MONTH, 1 );
			
			if( zeigeZwischenschritt )
				System.out.println( rechner.getCount() + ". Monat [" + df.format(c.getTime()) + "]: Rest=" + 
						rechner.getRest() + "(Getilgt=" + getilgt + "; Rate: " + rechner.getRate() + ")" );
		}
		
		System.out.println( "Kredit " + start + " Euro getilgt nach " + rechner.getCount() + 
                " Monaten (=" + ((double) rechner.getCount()/12.0) + " Jahre). Bezahlt: " + rechner.getBezahlt() + " Euro." );
	}

    private double zins;
    private double tilgung;
    private double start;
    
    private double rest;
    private double bezahlt;
    
    private double rate;
    
    private int count; 
    
    public Kreditrechner( double start, double zins, double tilgung ) throws IllegalArgumentException {
        
    	if( start<=0  ||  zins <0  ||  tilgung<=0 ) {
    		
    		throw new IllegalArgumentException( "All parameters must be positive." );
    	}
    	
        this.start = start;
        this.zins = zins;
        this.tilgung = tilgung;

        init();
    }
    
    public void init() {
    	
        this.count = 0;
        this.rest = start;
        this.rate = Math.round( 100.0 * start * (zins+tilgung) / 12.0 ) / 100.0;
        this.bezahlt = 0.0;
    }
    
	public void tilge() {
				
        count++;
        bezahlt += rate;
		rest -= rate - rest*zins/12.0;  
	}

	public boolean isGetilgt() {
        
        return getRest() <= 0;
    }
    
    
    public double getBezahlt() {
        return this.bezahlt;
    }
    
    
    public double getRate() {
        return rate;
    }


    public double getRest() {
        return rest;
    }


    public double getStart() {
        return start;
    }
    
    public void setStart( double start ) {
    	this.start = start;
    	
    	init();
    }


    public double getTilgung() {
        return tilgung;
    }
    
    public void setTilgung( double tilgung ) {
    	this.tilgung = tilgung;
    }


    public double getZins() {
        return zins;
    }
    
    public void setZins( double zins ) {
    	this.zins = zins;
    }
    
    public int getCount() {
        return this.count;
    }
}
