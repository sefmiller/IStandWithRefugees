package com.gb.istandwithrefugeesapp;

import android.app.Application;
import android.util.SparseArray;

import com.gb.istandwithrefugeesapp.Model.EUMarker;
import com.gb.istandwithrefugeesapp.Model.LatLong;
import com.gb.istandwithrefugeesapp.Model.OnlineAid;
import com.gb.istandwithrefugeesapp.Model.UkMarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MyAppApplication extends Application {

    private ArrayList<String> logoUrls = new ArrayList<>();

    private SparseArray<OnlineAid> onlineAids = new SparseArray<>();
    private ArrayList<String> logoUrlsOnline = new ArrayList<>();
    private SparseArray<EUMarker> overseasMarkersMap = new SparseArray<>();
    private ArrayList<String> logoUrlsOverseas = new ArrayList<>();
    private SparseArray<LatLong> longLatMapEU = new SparseArray<>();
    private LinkedHashMap<String, ArrayList<String>> countryAndRegionsArray;

    public SparseArray<EUMarker> getOverseasMarkersMap() {
        return overseasMarkersMap;
    }

    public void setOverseasMarkersMap(SparseArray<EUMarker> overseasMarkersMap) {
        this.overseasMarkersMap = overseasMarkersMap;
    }

    public ArrayList<String> getLogoUrlsOverseas() {
        return logoUrlsOverseas;
    }

    public void setLogoUrlsOverseas(ArrayList<String> logoUrlsOverseas) {
        this.logoUrlsOverseas = logoUrlsOverseas;
    }

    public SparseArray<LatLong> getLongLatMapEU() {
        return longLatMapEU;
    }

    public void setLongLatMapEU(SparseArray<LatLong> longLatMapEU) {
        this.longLatMapEU = longLatMapEU;
    }

    public SparseArray<OnlineAid> getOnlineAids() {
        return onlineAids;
    }

    public void setOnlineAids(SparseArray<OnlineAid> onlineAids) {
        this.onlineAids = onlineAids;
    }

    public ArrayList<String> getLogoUrlsOnline() {
        return logoUrlsOnline;
    }

    public void setLogoUrlsOnline(ArrayList<String> logoUrlsOnline) {
        this.logoUrlsOnline = logoUrlsOnline;
    }

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

    public void setCountryAndRegionsArray(LinkedHashMap<String,ArrayList<String>> countryAndRegionsArray) {
        this.countryAndRegionsArray = countryAndRegionsArray;
    }

    public LinkedHashMap<String, ArrayList<String>> getCountryAndRegionsArray() {
        return countryAndRegionsArray;
    }
}
