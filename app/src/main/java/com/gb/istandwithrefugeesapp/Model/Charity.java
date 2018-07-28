package com.gb.istandwithrefugeesapp.Model;

/**
 * Created by hp on 06/03/2018.
 */

public class Charity extends UkMarker {
    private String imageUrl;

    @Override
    public String toString() {
        return "Charity{" +
                "imageUrl='" + imageUrl + '\'' +
                ", houseNoOrBuldingName='" + houseNoOrBuldingName + '\'' +
                ", street='" + street + '\'' +
                ", otherAddress='" + otherAddress + '\'' +
                ", cityOrTown='" + cityOrTown + '\'' +
                ", postcode='" + postcode + '\'' +
                ", region='" + region + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                ", cityOrTown='" + cityOrTown + '\'' +
                ", markerId=" + markerId +
                '}';
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Charity(String title, String description, String website, String buildingNameOrStreetNo,
                   String street, String otherAddress, String cityorTown, String postcode, String region, String imageUrl, int markerId, int loginId) {
        super(title, description, website, buildingNameOrStreetNo, street, otherAddress, cityorTown, postcode, region, markerId, loginId);
        this.imageUrl = imageUrl;
    }
}
