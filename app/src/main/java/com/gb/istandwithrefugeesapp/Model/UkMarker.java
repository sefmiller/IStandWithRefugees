package com.gb.istandwithrefugeesapp.Model;

/**
 * Created by hp on 06/03/2018.
 */

public abstract class UkMarker extends Marker {


    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    String houseNoOrBuldingName;
    String street;
    String otherAddress;
    String postcode;
    String region;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getHouseNoOrBuldingName() {
        return houseNoOrBuldingName;
    }

    public void setHouseNoOrBuldingName(String houseNoOrBuldingName) {
        this.houseNoOrBuldingName = houseNoOrBuldingName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getOtherAddress() {
        return otherAddress;
    }

    public void setOtherAddress(String otherAddress) {
        this.otherAddress = otherAddress;
    }


    UkMarker(String title, String description, String website, String houseNoOrBuldingName, String street
            , String otherAddress, String cityOrTown, String postcode, String region, int markerId, int loginId) {
        super(title, description, website, cityOrTown, markerId, loginId);

        this.houseNoOrBuldingName = houseNoOrBuldingName;
        this.street = street;
        this.otherAddress = otherAddress;
        this.postcode = postcode;
        this.region = region;

    }

    UkMarker(){}
}
