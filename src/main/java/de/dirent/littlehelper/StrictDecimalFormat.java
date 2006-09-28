package de.dirent.littlehelper;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;


/**
 * A strict form of java.textDecimalFormat, 
 * i.e. the parse method throws a ParseException,  
 *    1) if the value string contains illegal characters
 *    2) if the value string contains more or less integer digits 
 *       in accordance with the pattern.
 *    3) if the value string contains more or less fraction digits 
 *       in accordance with the pattern.
 */
public class StrictDecimalFormat implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8257870962670826614L;


    private DecimalFormat df = null;
    private String pattern = null;
    
    // Constants for characters used in programmatic (unlocalized) patterns.
    private static final char       PATTERN_ZERO_DIGIT         = '0';
    private static final char       PATTERN_GROUPING_SEPARATOR = ',';
    private static final char       PATTERN_DECIMAL_SEPARATOR  = '.';
    //private static final char       PATTERN_PER_MILLE          = '\u2030';
    //private static final char       PATTERN_PERCENT            = '%';
    //private static final char       PATTERN_CURRENCY           = '\u00a4';
    private static final char       PATTERN_DIGIT              = '#';
    //private static final char       PATTERN_SEPARATOR          = ';';
    //private static final char       PATTERN_EXPONENT           = 'E';
    //private static final char       PATTERN_MINUS              = '-';
    //private static final char       PATTERN_REPLACEBUFFER      = '\u0000';
    
    
    private int minIntegerDigits = 0;
    private int maxIntegerDigits = 0;
    private int minFractionDigits = 0;
    private int maxFractionDigits = 0; 
    
    public StrictDecimalFormat ( Locale locale, String pattern ) {
        String patternCurrency = "$";
        if (pattern.lastIndexOf( patternCurrency ) > -1) {

            /*
             *  Removed as currency pattern is no longer supported
             */
            //String currencySymbol = Constants.MESSAGE_RESOURCES.getMessage( locale,"misc.currencysymbol" );
            pattern = pattern.substring(0, pattern.lastIndexOf(patternCurrency) ).trim();// + currencySymbol;
            //df = (DecimalFormat) DecimalFormat.getCurrencyInstance( locale );#
            df = (DecimalFormat) DecimalFormat.getNumberInstance( locale );
            df.applyPattern(pattern);
        } else {

            df = (DecimalFormat) DecimalFormat.getNumberInstance( locale );
            df.applyPattern(pattern);
        }
        this.pattern = pattern;
        
        boolean pre = true;
        for( int i=0; i<pattern.length(); i++ ) {
            char c = pattern.charAt(i);
            switch(c) {
            
            case PATTERN_DECIMAL_SEPARATOR:
                pre = false;
                break;
                
            case PATTERN_ZERO_DIGIT:
                if( pre ) { maxIntegerDigits++; minIntegerDigits++; }
                else { minFractionDigits++; maxFractionDigits++; }
                break;
                
            case PATTERN_DIGIT:
                if( pre ) { maxIntegerDigits++; }
                else { maxFractionDigits++; }
                break;
            }
        }
    }
    
    
    public Number parse( String value ) throws ParseException {
        
        ParsePosition ps = new ParsePosition(0);
        Number result = df.parse(value,ps);
        if( value.length() > ps.getIndex() ) throw new ParseException( "Illegal character", ps.getIndex() );
        
        // check maximum integer digits and maximum fraction digits
        int integerDigits = 0;
        int fractionDigits = 0;
        boolean pre = true;
        for( int i=0; i<value.length(); i++ ) {
            char c = value.charAt(i);
            if( df.getDecimalFormatSymbols().getDecimalSeparator() == c ) pre = false;
            if( Character.isDigit(c) ) {
                if( pre ) integerDigits++;
                else fractionDigits++;
            }  
        }
        if( integerDigits < minIntegerDigits ) throw new ParseException( "Too few integer digits", 0 );
        if( integerDigits > maxIntegerDigits ) throw new ParseException( "Too many integer digits", 0 );
        if( fractionDigits > maxFractionDigits ) throw new ParseException( "Too many fraction digits", 0 );
        if( fractionDigits < minFractionDigits ) throw new ParseException( "Too few fraction digits", 0 );
        
        return result;
    }
    
    public String format( double value ) {
        return df.format( value );
    }
    

    public String toPattern() {
        return pattern;
    }


    public String toLocalizedPattern() {
        StringBuffer result = new StringBuffer( pattern );
        for( int i=0; i<result.length(); i++ ) {
            switch( result.charAt(i) ) {
            
            case PATTERN_DECIMAL_SEPARATOR:
                result.setCharAt(i, df.getDecimalFormatSymbols().getDecimalSeparator());
                break;
                
            case PATTERN_GROUPING_SEPARATOR:
                result.setCharAt(i, df.getDecimalFormatSymbols().getGroupingSeparator());
                break;
            }
        }
        
        return result.toString();
    }
}
