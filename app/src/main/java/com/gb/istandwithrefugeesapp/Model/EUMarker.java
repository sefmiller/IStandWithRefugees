package com.gb.istandwithrefugeesapp.Model;

/**
 * Created by hp on 06/03/2018.
 */

public class EUMarker extends Marker {
    private String imageUrl;
    String country;

    public EUMarker(String title, String description, String website, String cityOrTown, String region, String typeOfAid, String lastModified, int markerId, int loginId, String imageUrl, String country) {
        super(title, description, website, cityOrTown, region, typeOfAid, lastModified, markerId, loginId);
        this.imageUrl = imageUrl;
        this.country = country;
    }

    @Override
    public String toString() {
        return "EUMarker{" +
                "imageUrl='" + imageUrl + '\'' +
                ", country='" + country + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                ", cityOrTown='" + cityOrTown + '\'' +
                ", markerId=" + markerId +
                ", region='" + region + '\'' +
                ", typeOfAid='" + typeOfAid + '\'' +
                ", lastModified='" + lastModified + '\'' +
                '}';
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
