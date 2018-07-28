package com.gb.istandwithrefugeesapp;

import android.content.Context;

import com.gb.istandwithrefugeesapp.Model.LatLong;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.HashMap;


/**
 * Created by hp on 25/03/2018.
 */

class MyClusterRenderer extends DefaultClusterRenderer<LatLong> {

    private final MainActivity anActivity;

    public MyClusterRenderer(Context context, GoogleMap map, ClusterManager<LatLong> clusterManager) {
        super(context, map, clusterManager);
        anActivity = (MainActivity) context;

    }

    @Override
    protected void onBeforeClusterItemRendered(LatLong item,
                                               MarkerOptions markerOptions) {
        String stringId = item.getMarkerId();
        int id= Integer.parseInt(stringId);
        HashMap currentMap = anActivity.getDbHelper().getMarkersMap().get(id);
        if (currentMap.containsKey("Fundraiser")) {
            BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
            markerOptions.icon(markerDescriptor);
        }

    }



    @Override
    protected void onClusterItemRendered(LatLong item, Marker marker) {
        super.onClusterItemRendered(item, marker);
    }

    @Override
    protected void onClusterRendered(Cluster<LatLong> cluster, Marker marker) {
        super.onClusterRendered(cluster, marker);
        //marker.getPosition()
    }
}
