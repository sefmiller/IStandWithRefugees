package com.gb.istandwithrefugeesapp;

import android.app.Application;
import android.util.SparseArray;

import com.gb.istandwithrefugeesapp.Model.LatLong;
import com.gb.istandwithrefugeesapp.Model.UkMarker;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAppApplication extends Application {

    private ArrayList<String> logoUrls = new ArrayList<>();

    public ArrayList<String> getLogoUrls() {
        return logoUrls;
    }

    public void setLogoUrls(ArrayList<String> logoUrls) {
        this.logoUrls = logoUrls;
    }

    private SparseArray<HashMap<String, UkMarker>> markersMap = new SparseArray <>();
    private SparseArray<LatLong> longLatMap = new SparseArray<>();

    public SparseArray<HashMap<String, UkMarker>> getMarkersMap() {
        return markersMap;
    }

    public void setMarkersMap(SparseArray<HashMap<String, UkMarker>> markersMap) {
        this.markersMap = markersMap;
    }

    public SparseArray<LatLong> getLongLatMap() {
        return longLatMap;
    }

    public void setLongLatMap(SparseArray<LatLong> longLatMap) {
        this.longLatMap = longLatMap;
    }
}
