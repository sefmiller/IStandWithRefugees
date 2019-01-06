package com.gb.istandwithrefugeesapp.Model;

/**
 * Created by hp on 06/03/2018.
 */

public class Fundraiser extends UkMarker {
    private String date;
    private String time;
    private Charity aCharity;

    @Override
    public String toString() {
        return "Fundraiser{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                ", website='" + website + '\'' +
                ", aCharity=" + aCharity +
                ", markerId=" + markerId +
                ", houseNoOrBuldingName='" + houseNoOrBuldingName + '\'' +
                ", street='" + street + '\'' +
                ", otherAddress='" + otherAddress + '\'' +
                ", cityOrTown='" + cityOrTown + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }

    public Fundraiser(String title, String description, String website, String buildingNameOrStreetNo,
                      String street, String otherAddress, String cityorTown, String postcode, String region, String typeOfAid, String lastModified, String date, String time, Charity aCharity, int markerId, int loginId) {
        super(title, description, website, buildingNameOrStreetNo, street, otherAddress, cityorTown, postcode, region, typeOfAid, lastModified, markerId, loginId);
        this.date = date;
        this.time = time;
        this.aCharity = aCharity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Charity getaCharity() {
        return aCharity;
    }

    public void setaCharity(Charity aCharity) {
        this.aCharity = aCharity;
    }

}
