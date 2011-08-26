package org.apache.jetspeed;

/**
   URLStore... basic class to store a URLName and an Address.  Thus an HREF
   can be stored like <A HREF="URLStore.getAddress()">URLStore.getName()</A>
*/
//******************************************************************************
public class URLStore {



    private String name;
    private String address;
    private String image;
    private String image_name;



    //**************************************************************************
     public URLStore(String name, String address) {
        this.name = name;
        this.address = address;
    }



    //**************************************************************************
     public URLStore(String name, String address, String image, String image_name) {

        this.name         = name;
        this.address      = address;
        this.image        = image;
        this.image_name   = image_name;

    }



    //**************************************************************************
    public URLStore(String name, String address, String image) {

        this.name         = name;
        this.address      = address;
        this.image        = image;

    }




    //**************************************************************************
    public String getName() {
        return this.name;
    }



    //**************************************************************************    
    public String getAddress() {
        return this.address;
    }

    //**************************************************************************
    public String getImage() {
        return this.image;
    }

    //**************************************************************************
    public String getImageName() {
        return this.image_name;
    }



}