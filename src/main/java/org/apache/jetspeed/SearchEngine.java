package org.apache.jetspeed;



/**

   URLStore... basic class to store a URLName and an Address.  Thus an HREF

   can be stored like <A HREF="URLStore.getAddress()">URLStore.getName()</A>

*/

//******************************************************************************

public class SearchEngine extends URLStore {



    /**

    QueryString here is just the prefix to the engine... (less the ?)



    So for ex:

    www.search.com/search?query=string



    then the QueryString would be



    query=



    and the user simple appends the query string to the end...



    */



    private String QueryString;

    private String CGIURL;







    //**************************************************************************

    public SearchEngine(String name, String address, String image, String image_name, String QueryString, String CGIURL) {

        super( name, address, image, image_name );

        this.QueryString  = QueryString;

        this.CGIURL = CGIURL;

    }





    //**************************************************************************

    String getQueryString() {

        return this.QueryString;

    }



    //**************************************************************************

    String getCGIURL() {

        return this.CGIURL;

    }







}