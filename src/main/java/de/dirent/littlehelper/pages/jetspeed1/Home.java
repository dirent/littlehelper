package de.dirent.littlehelper.pages.jetspeed1;


import org.apache.tapestry5.annotations.Import;


@Import( stylesheet = { "context:/css/littlehelper.css", "context:/css/jetspeed1.css" } )
public class Home {
	
	/**
	 * Originally from Util.getNiceDate()
	 */
	public String getNiceDate() {
		
	      String nice_now = "";
	      java.util.Calendar now = java.util.Calendar.getInstance();

	      //get month
	      switch (now.get(java.util.Calendar.MONTH)) {
	        case java.util.Calendar.JANUARY:
	          nice_now += "January";
	        break;

	        case java.util.Calendar.FEBRUARY:
	          nice_now += "February";
	        break;

	        case java.util.Calendar.MARCH:
	          nice_now += "March";
	        break;

	        case java.util.Calendar.APRIL:
	          nice_now += "April";
	        break;

	        case java.util.Calendar.MAY:
	          nice_now += "May";
	        break;

	        case java.util.Calendar.JUNE:
	          nice_now += "June";
	        break;

	        case java.util.Calendar.JULY:
	          nice_now += "July";
	        break;

	        case java.util.Calendar.AUGUST:
	          nice_now += "August";
	        break;

	        case java.util.Calendar.SEPTEMBER:
	          nice_now += "September";
	        break;

	        case java.util.Calendar.OCTOBER:
	          nice_now += "October";
	        break;

	        case java.util.Calendar.NOVEMBER:
	          nice_now += "November";
	        break;

	        case java.util.Calendar.DECEMBER:
	          nice_now += "December";
	        break;




	      }




	      nice_now += " " +
	                  now.get(java.util.Calendar.DAY_OF_MONTH) + " " +
	                  now.get(java.util.Calendar.YEAR) + " " +
	                  now.get(java.util.Calendar.HOUR) + ":" +
	                  now.get(java.util.Calendar.MINUTE) + " ";

	      switch(now.get(java.util.Calendar.AM_PM)) {
	        case java.util.Calendar.AM:
	          nice_now += "AM";
	        break;

	        case java.util.Calendar.PM:
	          nice_now += "PM";
	        break;

	      }

	      //+ " " + now.YEAR;

	      //get date

	      //get year



	      return nice_now;
	}
}
