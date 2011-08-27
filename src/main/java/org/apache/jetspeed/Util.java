package org.apache.jetspeed;


import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// MFO: fixed problems with name clashes between java.util.Calendar and org.apache.jetspeed.Calendar
//      but we probably need to rename the servlet because this is really inconvenient
public class Util  {


    /**
    Name of JetSpeed.  This needs to be a constant so that it can later be localized.
    */
    public final static   String    PRODUCT                           = "JetSpeed";

    public final static   String    PRODUCT_VERSION                   = "0.1.10";
    public final static   String    PRODUCT_BUILD                     = "2001";

    /**
    A full description of the product and what it does.
    */

    //might be a good idea to move all this to a Verson class which includes:
    //  PRODUCT_NAME
    //  PRODUCT_VERSION
    //  PRODUCT_BUILD
    //  PRODUCT_MANTR
    //  PRODUCT_AUTHORS[]
    //and also the GPL.   Also some code included here doesn't use the GPL so
    //we will have to include these licenses in an array.

    public final static   String    URL_JETSPEED_HOME                 = "http://java.apache.org/jetspeed";
    public final static   String    URL_JETSPEED_BUGZILLA             = "http://relativity.yi.org/bugzilla";


    public final static   String    PRODUCT_MANTRA                    = "JetSpeed is an implementation of a GroupWare/Portal " +
                                                                        "product. It is based on IMAP/POP3/SMTP/NNTP/LDAP and " +
                                                                        "uses HTML/DHTML as its presentation. The final product " +
                                                                        "is intended to compete with Lotus Notes/Microsoft " +
                                                                        "Exchange and portal sites like DejaNews/Hotmail/Yahoo, etc.";





    //FIX ME: Why can't I get this to compile under PRODUCT_AUTHORS?                                                                        
    public static URLStore burtons[] = {
                                        new URLStore("JetSpeed", URL_JETSPEED_HOME),
                                        new URLStore("Messaging", URL_JETSPEED_HOME)
                                        };


    //MARCEL... Please
    public static URLStore offermans[] = {
                                        new URLStore("JetSpeed", URL_JETSPEED_HOME),
                                        new URLStore("Calendaring", URL_JETSPEED_HOME)
                                        };


    public final static   Authors   PRODUCT_AUTHORS[]                   = {
                                                                        new Authors("Kevin",
                                                                                    "Burton",
                                                                                    "burton@relativity.yi.org",
                                                                                    "Silicon Valley (CA US) Software Engineer.",
                                                                                    burtons
                                                                                    )
                                                                        };


    public final static   String    CONTENT_ROOT                      = "";
    public final static   String    IMAGE_ROOT                        = CONTENT_ROOT + "images/";
    public final static   String    IMAGE_SEARCH_ROOT                 = CONTENT_ROOT + "images/search/";
    public final static   String    JAVASCRIPT_ROOT                   = CONTENT_ROOT + "javascript/";
    public final static   String    DYNAPI_ROOT                       = JAVASCRIPT_ROOT + "dynapi/";
    public final static   String    JETSPEED_JS_ROOT                  = JAVASCRIPT_ROOT + "jetspeed/";

    public final static   String    PRODUCT_LOGO                      = IMAGE_ROOT + "microphone.gif";

    public final static   String    COLOR_DEFAULT_BACKGROUND          = "white";
    public final static   String    COLOR_CONTROL_BACKGROUND          = "silver";
    public final static   String    COLOR_CONTROL_DARK_GREY           = "c0c0c0"; //FIX ME:  migrate this to be all # based.
    public final static   String    COLOR_CONTROL_VIOLET              = "FFCC00";
    public final static   String    COLOR_CONTROL_FOLDER              = "FFFFFF";
    public final static   String    COLOR_HOME_BACKGROUND             = "#336699";  //default is blue...
    public final static   String    COLOR_INTERFACE_BACKGROUND        = "#FFFFFF";
    public final static   String    COLOR_CONTROL_DARK                = "#666666";

    public final static   String    COLOR_ENUM_MESSAGE_HEAD           = "#A8A8A8";
    public final static   String    COLOR_ENUM_MESSAGE_TOGGLE_OFF     = "#C0C0C0";
    public final static   String    COLOR_ENUM_MESSAGE_TOGGLE_ON      = "#B0B0B0";


    public final static   String    IMAGE_FOLDER                      = IMAGE_ROOT + "folder.gif";  //display the left pane
    public final static   String    IMAGE_HOME                        = IMAGE_ROOT + "misc-folders/home.gif";
    public final static   String    IMAGE_ABOUT                       = IMAGE_ROOT + "misc-folders/dep.gif";


    public final static   String    IMAGE_REPLY                       = IMAGE_ROOT + "reply.gif";
    public final static   String    IMAGE_LEFT                        = IMAGE_ROOT + "left.gif";
    public final static   String    IMAGE_RIGHT                       = IMAGE_ROOT + "right.gif";
    public final static   String    IMAGE_DIVIDER                     = IMAGE_ROOT + "divider.gif";
    public final static   String    IMAGE_REFRESH                     = IMAGE_ROOT + "refresh.gif";
    public final static   String    IMAGE_FOLDER_OPEN                 = IMAGE_ROOT + "folderopen.gif";
    public final static   String    IMAGE_MESSAGES                    = IMAGE_ROOT + "messages.gif";
    public final static   String    IMAGE_LOGOUT                      = IMAGE_ROOT + "logout.gif";
    public final static   String    IMAGE_DELETE                      = IMAGE_ROOT + "delete.gif";

    public final static   int       ERROR_NO_MESSAGES_ID              = 1;
    public final static   String    ERROR_NO_MESSAGES                 = "Sorry.  No messages in this folder.";

    public final static   int       ERROR_TOO_MANY_SESSIONS_ID        = 2;
    public final static   String    ERROR_TOO_MANY_SESSIONS           = "Sorry.  " + Util.PRODUCT + " is unavailable at this time.  There are too many current users.  Try again later." ;

    public final static   String    ERROR_LOGON_FAILED                = "It appears your logon has failed.  Please check your username and or password.";
    public final static   String    ERROR_CONNECTION_FAILED           = "Could not connect to the host you specified.  Please make sure you specified the right hostname and that it is available and running the specified service.";

    public final static   String    ERROR_INVALID_PROVIDER            = "The provider you selected is invalid.  This is most likely because the servers classpath is incorrect.  Please contact the server admin.";

    
    /**
    The full <A> tag for this product.
    */
    public final static   String    URL_PRODUCT                     = "<A HREF=\"" + URL_JETSPEED_HOME + "\">" + Util.PRODUCT + "</A>";




    /**
        returns the string specified as an int or -1 if it can't convert it...
    */
    //*************************************************************************
    public static int string2int(String convert) {

      if ((convert != null) && (convert.length() > 0)) {
         return (new Integer(convert)).intValue();
      } else {
        return -1;
      }


    }

    /**
    Method to prompt for assistance with a specific JetSpeed feature.  This will
    be a full page.
    */

    //**************************************************************************
    public static void notImplementedPage( ServletOutputStream out ) throws IOException {

      Util.genHTMLHead( out );
      out.println("<br>");
      out.println("<img src=\"" + Util.PRODUCT_LOGO + "\" align=\"left\"></p>");

      Util.notImplementedMessage( out );
      Util.genHTMLTail( out );

    }

    /**
    Method to prompt for assistance with a specific JetSpeed feature.  Normally
    one that is not implemented.  This will be just a message.
    */

    //**************************************************************************
    public static void notImplementedMessage( ServletOutputStream out ) throws IOException {
      out.println("<p><h2>This feature is currently not implemented in " + URL_PRODUCT + ".</h2><p>");
      out.println("<p>" + Util.PRODUCT_MANTRA + "</p>");
    }


    //**************************************************************************
    public static void genHTMLHead( ServletOutputStream out ) throws IOException {

		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<META http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
    out.println( Util.getCSS() );
		out.println("</HEAD>");
		out.println("<BODY bgcolor=\"" + Util.COLOR_DEFAULT_BACKGROUND + "\" text=\"#000000\" link=\"#006699\" vlink=\"#006699\" alink=\"#006699\" TOPMARGIN=\"0\" LEFTMARGIN=\"4\">");

    }

    //**************************************************************************
    public static void genRootHTMLHead( ServletOutputStream out ) throws IOException {

		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<META http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
    out.println( Util.getCSS() );
		out.println("</HEAD>");
		out.println("<BODY bgcolor=\"" + Util.COLOR_HOME_BACKGROUND + "\" text=\"#000000\" link=\"#006699\" vlink=\"#006699\" alink=\"#006699\" TOPMARGIN=\"0\" LEFTMARGIN=\"4\">");

    }



    /**
    Generate a tail for HTML files.  This version provides support for passing
    values via a HttpServletRequest.
    */

    //**************************************************************************
    public static void genHTMLTail( HttpServletRequest request,
                                    HttpServletResponse response,
                                    ServletOutputStream out ) throws IOException {


      String refresh = request.getParameter("refresh");

      if ( refresh == null ) {



          //this code sends an "alert" message to the user for multi-page communication
          String alert = request.getParameter("alert");
          if (alert != null) {
            out.println("<script language=\"JavaScript\">");
            out.println("alert(\"" + alert + "\");");
            out.println("</script>");
          }


          String error = request.getParameter("error");

          if ( (error != null) && (error.equals("1")) ) {
            out.println("<SCRIPT LANGUAGE=\"JavaScript\">");
            out.println("alert('" + request.getParameter("error_message") + "');");
            out.println("</SCRIPT>");
          }


        }


        out.println("</BODY>");
        out.println("</HTML>");

    }


    /**
    Generate a tail for HTML files.
    */

    //**************************************************************************
    public static void genHTMLTail( ServletOutputStream out ) throws IOException {


        out.println("</BODY>");
        out.println("</HTML>");

    }



    //**************************************************************************
    public static String getCSS() {

		return "<style type=\"text/css\">\n" +
		"        {background:  white}\n" +
		"        BODY  {font-family: \"Verdana\"; font-size: 9pt; color: 000000}\n" +
		"        TD  {font-family: \"Verdana\"; font-size: 9pt; color: 000000}\n" +
		"        TR  {font-family: \"Verdana\"; font-size: 9pt; color: 000000}\n" +
		"        FONT  {font-family: \"Verdana\"; font-size: 9pt; color: 000000}\n" + 
		"        I  {font-family: \"Verdana\"; font-size: 9pt; color: 000000;font-style: italic}\n" + 
		"        BIG  {font-family: \"Verdana\"; font-size: 13pt; color: 000000;font-weight: bold}\n" + 
		"        TH  {font-family: \"Verdana\"; font-size: 9pt; color: 000000;font-weight: bold}\n" + 
		"        A:link {font-family: \"Verdana\"; font-size: 10pt; color: NAVY;font-weight: bold}\n" + 
		"        A:visited {font-family: \"Verdana\"; font-size: 10pt; color: BLACK;font-weight: bold}\n" + 
		"        H1 {font-family: \"Arial\"; font-size: 30pt; color: navy;}\n" +
		"        STRONG {font-family: \"Verdana\"; font-size: 9pt; color: 000000;text-decoration: none; margin-left=0}\n" +
		"        H2 {font-family: \"Verdana\"; font-size: 12pt; color: 000000;text-decoration: none; margin-left=0}\n" +
		"        H3 {font-family: \"Verdana\"; font-size: 9pt; color: 000000;text-decoration: none; margin-left=0}\n" +
		"        PRE {font-family: \"Verdana\"; font-size: 9pt; color: 000000}\n" +
		"        P {font-family: \"Verdana\"; font-size: 9pt; color: 000000}\n" +
		"        BLOCKQUOTE {font-family: \"Verdana, Arial, Helvetica\"; font-size:9pt; color: 000000}\n" +
    "        .WELCOMETITLE {font-family: \"Verdana\"; font-size: 9pt; color: " + Util.COLOR_DEFAULT_BACKGROUND + "; background: " + Util.COLOR_CONTROL_DARK + "; font-weight: bold}\n" +
    "        .TOOLBAR {font-family: \"Verdana\"; font-size: 9pt; color: 000000;font-weight: normal}\n" +
    "        .TOOLBAR:link {font-family: \"Verdana\"; font-size: 9pt; color: 000000;font-weight: normal}\n" +
    "        .TOOLBAR:visited {font-family: \"Verdana\"; font-size: 9pt; color: 000000;font-weight: normal}\n" +
    "        .TITLEBAR {font-family: \"Verdana\"; font-size: 9pt; color: FFFFFF; background: navy;font-weight: bold\n}" +
		"        .MESSAGE {font-family: \"Verdana\"; font-size: 8pt; color: 000000;font-weight: normal}\n" +
		"        .MESSAGE:link {font-family: \"Verdana\"; font-size: 8pt; color: NAVY;font-weight: normal}\n" +
		"        .MESSAGE:visited {font-family: \"Verdana\"; font-size: 8pt; color: BLACK;font-weight: normal}\n" +
		"</style>\n";

    


    }


    /**
      Generate a Control header... this usually has a silver background.

    */

    //**************************************************************************
    public static void genControlHTMLHead( ServletOutputStream out ) throws IOException {



    		out.println("<HTML>");
    		out.println("<HEAD>");
    		out.println( "<style type=\"text/css\">\n" +
    		"        {background:  white}\n" +
    		"        BODY  {font-family: \"Verdana\"; font-size: 9pt; color: 000000}\n" +
    		"        TD  {font-family: \"Verdana\"; font-size: 9pt; color: 000000}\n" +
    		"        TH  {font-family: \"Verdana\"; font-size: 9pt; color: 000000; background: " + COLOR_CONTROL_DARK_GREY + "}\n" +
    		"        TR  {font-family: \"Verdana\"; font-size: 9pt; color: 000000}\n" +
    		"        FONT  {font-family: \"Verdana\"; font-size: 9pt; color: 000000}\n" +
    		"        I  {font-family: \"Verdana\"; font-size: 9pt; color: 000000;font-style: italic}\n" +
    		"        BIG  {font-family: \"Verdana\"; font-size: 13pt; color: 000000;font-weight: bold}\n" +
    		"        TH  {font-family: \"Verdana\"; font-size: 9pt; color: 000000;font-weight: bold}\n" +
    		"        A:link {font-family: \"Verdana\"; font-size: 10pt; color: NAVY;font-weight: bold}\n" +
    		"        A:visited {font-family: \"Verdana\"; font-size: 10pt; color: BLACK;font-weight: bold}\n" +
    		"        H1 {font-family: \"Arial\"; font-size: 30pt; color: navy;}\n" +
    		"        STRONG {font-family: \"Verdana\"; font-size: 9pt; color: 000000;text-decoration: none; margin-left=0}\n" +
    		"        H2 {font-family: \"Verdana\"; font-size: 12pt; color: 000000;text-decoration: none; margin-left=0}\n" +
    		"        H3 {font-family: \"Verdana\"; font-size: 9pt; color: 000000;text-decoration: none; margin-left=0}\n" +
    		"        PRE {font-family: \"Verdana\"; font-size: 9pt; color: 000000}\n" +
    		"        P {font-family: \"Verdana\"; font-size: 9pt; color: 000000}\n" +
    		"        BLOCKQUOTE {font-family: \"Verdana, Arial, Helvetica\"; font-size:9pt; color: 000000}\n" +
        "        .TOOLBAR {font-family: \"Verdana\"; font-size: 9pt; color: 000000;font-weight: normal}\n" +
        "        .TOOLBAR:link {font-family: \"Verdana\"; font-size: 9pt; color: 000000;font-weight: normal}\n" +
        "        .TOOLBAR:visited {font-family: \"Verdana\"; font-size: 9pt; color: 000000;font-weight: normal}\n" +
        "        .TITLEBAR {font-family: \"Verdana\"; font-size: 9pt; color: FFFFFF; background: navy;font-weight: bold}\n" +
    		"        .MESSAGE {font-family: \"Verdana\"; font-size: 8pt; color: 000000;font-weight: normal}\n" +
    		"        .MESSAGE:link {font-family: \"Verdana\"; font-size: 8pt; color: NAVY;font-weight: normal}\n" +
    		"        .MESSAGE:visited {font-family: \"Verdana\"; font-size: 8pt; color: BLACK;font-weight: normal}\n" +
    		"</style>\n" );



    		out.println("<META http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
    		out.println("</HEAD>");
    		out.println("<BODY bgcolor=\"" + Util.COLOR_CONTROL_BACKGROUND + "\" text=\"#000000\" link=\"#006699\" vlink=\"#006699\" alink=\"#006699\" TOPMARGIN=\"0\" LEFTMARGIN=\"4\">");



  }

    /**
    Returns the full url from an HttpServletRequest
    */
    //**************************************************************************
    public static String getFullURL( HttpServletRequest request ) throws IOException {
      return getURLBase(request) + request.getRequestURI() + "?" + request.getQueryString();
    }

    /**
    Returns the URL Base for this server... IE http://host:port
    */
    //**************************************************************************
    public static String getURLBase(  HttpServletRequest request ) {

      return "http://" + request.getServerName() + ":"  + request.getServerPort(); 

    }

    /**
    Returns the URL that this request was to.. includes the URI (http://) servername and port
    */

    //**************************************************************************
    public static String getBaseURL( HttpServletRequest request ) throws IOException {
      return "http://" + request.getServerName() + ":"  + request.getServerPort() + request.getRequestURI();
    }




    public static String getNiceDate() {

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


    /**
      outputs a gui style dialog box
    */
    //**************************************************************************
    public static void genDialogBox(ServletOutputStream out, String caption, String body) throws IOException {


      out.println("<table bgcolor=\"silver\" border=\"1\" height=\"300\" cellpadding=\"0\" align=center>");


      out.println("<tr>");
      out.println("<td COLSPAN=\"2\" ALIGN=\"LEFT\" CLASS=\"TITLEBAR\" height=25 width=\"100%\">");
      out.println("&nbsp;" + caption);
      out.println("</td>");
      out.println("</tr>");
      out.println("<tr>");
      out.println("<td height=\"100%\">");

      out.println(body);

      out.println("</td>");
      out.println("</tr>");
      out.println("</table>");


    }

    /**
    Used for formatting a string into an HTML formatted string.
    */
    //**************************************************************************
    public static String escape(String HTML) {
      StringBuffer buffer = new StringBuffer(HTML);

      //find each reference of a " " and change it to "%20"

      //find the first reference of a space...

      int space_location = buffer.toString().indexOf(" ");

      while(space_location != -1) {
        buffer.replace(space_location, space_location + 1, "%20");
        space_location = buffer.toString().indexOf(" ");
      }

      return buffer.toString();

    }

    /**
    Should be use for any text/plain datatype for turning into html for reading

    Identifies paragraphs and places <p> and </p> around them.  This keeps

    Locates uses of " " for placement and changes them to &nbsp;

    Locates hard \n and changes them to <br>

    Locates "<" and changes them to "&lt;"



    */
    //**************************************************************************
    //FIX ME:  rename escapeText
    public static String escapeInline(String message) {

      //FIX ME: this entire method's speed could be improved by simply going
      //through the string char through char and analyzing each character to
      //determine if it needs to be replaced.  This should theoritically increate
      //speed by 300% as instead of evaluating the string 3 times it would only
      //have to eval it once. 

      message = globalStringReplace( message, "<", "&lt;" );
      message = globalStringReplace( message, "\n", "<br>" );

      //fix me:  this shouldn't need to be a global replace... only lines that begin
      //with spaces ...
      message = globalStringReplace( message, " ", "&nbsp;" );


      //at this point i should be set to the first non-white space...  change it to be this character plus a <p>\n



      return message;

    }





    /**
    Given a string... replaces all occurences of "find" with "replacement" in "original"
    */
    //**************************************************************************
    public static String globalStringReplace(String original, String find, String replacement) {
      StringBuffer buffer = new StringBuffer(original);


      int space_location = buffer.toString().indexOf(find);

      while(space_location != -1) {
        buffer.replace(space_location, space_location + 1, replacement);

        //this speed could be improved by starting off where the last string was found...
        //this is why it starts off from space_location.. the length of the string you are finding.. plus 1 
        space_location = buffer.toString().indexOf(find, space_location + find.length() + 1);
      }

      return buffer.toString();

    }


}


//******************************************************************************
class Authors {

  private String first;
  private String last;
  private String email;
  private String description;
  private URLStore projects[];

  //****************************************************************************
  Authors(String first, String last, String email, String description, URLStore projects[]) {
    this.first = first;
    this.last = last;
    this.email = email;
    this.description = description;
    this.projects = projects;
  }

  //****************************************************************************
  String getFirst() {
    return first;
  }

  //****************************************************************************
  String getLast() {
    return last;
  }

  //****************************************************************************
  String getEmail() {
    return email;
  }

  //****************************************************************************
  String getDescription() {
    return description;
  }

  //****************************************************************************
  URLStore[] getProjects() {
    return projects;
  }

  String getName() {
    return first + " " + last;
  }

}
