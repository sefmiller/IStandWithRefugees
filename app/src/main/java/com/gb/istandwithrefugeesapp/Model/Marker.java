package com.gb.istandwithrefugeesapp.Model;

/**
 * Created by hp on 06/03/2018.
 */

public abstract class Marker {
    String title;
    String description;
    String website;
    String cityOrTown;
    int markerId;
    private int loginId;
    String region;
    String typeOfAid;
    String lastModified;

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getTypeOfAid() {
        return typeOfAid;
    }

    public void setTypeOfAid(String typeOfAid) {
        this.typeOfAid = typeOfAid;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getMarkerId() {
        return markerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public String getCityOrTown() {
        return cityOrTown;
    }

    public void setCityOrTown(String cityOrTown) {
        this.cityOrTown = cityOrTown;
    }

    Marker(String title, String description, String website, String cityOrTown, String region, String typeOfAid, String lastModified,
           int markerId, int loginId) {
        this.title = title;
        this.description = description;
        this.website = website;
        this.markerId = markerId;
        this.loginId = loginId;
        this.cityOrTown = cityOrTown;
        this.loginId = loginId;
        this.cityOrTown = cityOrTown;
        this.region = region;
        this.typeOfAid = typeOfAid;
        this.lastModified = lastModified;
    }

    Marker(){}

    public void setMarkerId(int markerId) {
        this.markerId = markerId;
    }
}
