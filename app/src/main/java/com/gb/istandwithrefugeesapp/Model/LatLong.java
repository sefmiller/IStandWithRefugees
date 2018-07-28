package com.gb.istandwithrefugeesapp.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by hp on 06/03/2018.
 */

public class LatLong implements ClusterItem {
    private float lat;
    private float lon;
    private final LatLng mPosition;

    public String getMarkerId() {
        return markerId;
    }

    private final String mTitle;
    private final String mSnippet;
    private final String markerId;

    @Override
    public String toString() {
        return "LatLong{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", mPosition=" + mPosition +
                ", mTitle='" + mTitle + '\'' +
                ", mSnippet='" + mSnippet + '\'' +
                ", markerId='" + markerId + '\'' +
                '}';
    }

    public LatLong(float lat, float lon, String title, String snippet, String markerID) {
        this.lat = lat;
        this.lon = lon;
        this.mTitle = title;
        this.mPosition = new LatLng(lat,lon);
        this.mSnippet = snippet;
        this.markerId = markerID;
    }

    public float getLat() {
        return lat;
    }



    public float getLon() {
        return lon;
    }





    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }
}
