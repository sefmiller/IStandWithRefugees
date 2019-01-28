package com.gb.istandwithrefugeesapp.Model;

/**
 * Created by hp on 06/03/2018.
 */

public class OnlineAid {
    String title;
    String description;
    String website;
    int markerId;
    String lastModified;
    String logoUrl;
    private int loginId;

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
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


    public OnlineAid(String title, String description, String website, String lastModified, String logoUrl,
                     int markerId, int loginId) {
        this.title = title;
        this.description = description;
        this.website = website;
        this.markerId = markerId;
        this.loginId = loginId;
        this.loginId = loginId;
        this.lastModified = lastModified;
        this.logoUrl = logoUrl;
    }

    public void setMarkerId(int markerId) {
        this.markerId = markerId;
    }
}
