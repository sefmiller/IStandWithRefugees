package com.gb.istandwithrefugeesapp;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.gb.istandwithrefugeesapp.Model.Charity;
import com.gb.istandwithrefugeesapp.Model.Fundraiser;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp on 21/03/2018.
 */

class CustomInfoViewAdapter implements com.androidmapsextensions.GoogleMap.InfoWindowAdapter {

    private final LayoutInflater mInflater;
    private final HashMap<String, String> markersMap;
    private final MainActivity mainActivity;
    private String logoUrl;
    private String lastModified;

    public CustomInfoViewAdapter(LayoutInflater inflater, HashMap<String, String> markersMap, MainActivity mainActivity) {
        this.mInflater = inflater;
        this.markersMap = markersMap;
        this.mainActivity = mainActivity;
    }

    @Override public View getInfoWindow(final com.androidmapsextensions.Marker marker) {
        final View popup = mInflater.inflate(R.layout.info_window_layout, null);
        String id = marker.getId();
        String markerId = markersMap.get(id);
        int markId = Integer.valueOf(markerId);
        System.out.println(markId);
        HashMap currentMap = mainActivity.getDbHelper().getMarkersMap().get(markId);
        Fundraiser fundraiser = null;
        if (currentMap.containsKey("Organisation")) {
            final Charity charity = (Charity) currentMap.get("Organisation");
            logoUrl = charity.getImageUrl();
            lastModified = charity.getLastModified();
        } else {
            fundraiser = (Fundraiser) currentMap.get("Fundraiser");
            logoUrl = fundraiser.getaCharity().getImageUrl();
            lastModified = fundraiser.getaCharity().getLastModified();
        }
        ((TextView) popup.findViewById(R.id.info_win_title)).setText(marker.getTitle());
        Typeface titleFont = Typeface.
                createFromAsset(mInflater.getContext().getAssets(), "fonts/Lobster_1.3.otf");
        ((TextView) popup.findViewById(R.id.info_win_title)).setTypeface(titleFont);
        ((TextView) popup.findViewById(R.id.info_win_desc)).setText(marker.getSnippet());
        CircleImageView circleImageView = popup.findViewById(R.id.nat_circle_image_view);
        int dimen = (int) mainActivity.getResources().getDimensionPixelSize(R.dimen._30sdp);
        Glide.with(mainActivity).load(logoUrl).apply(new RequestOptions()
                .signature(new ObjectKey(lastModified))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)).listener(new RequestListener<Drawable>() {

            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (marker.isInfoWindowShown()) {
                    marker.hideInfoWindow();
                    marker.showInfoWindow();
                }
                return false;
            }
        }).apply(new RequestOptions().override(dimen, dimen))
                .into(circleImageView);
        return popup;
        //return null;
    }

    @Override public View getInfoContents(com.androidmapsextensions.Marker marker) {
        marker.showInfoWindow();
        final View popup = mInflater.inflate(R.layout.info_window_layout, null);
        ((TextView) popup.findViewById(R.id.info_win_title)).setText(marker.getTitle());
        ((TextView) popup.findViewById(R.id.info_win_desc)).setText(marker.getSnippet());
        CircleImageView circleImageView = popup.findViewById(R.id.nat_circle_image_view);
        Glide.with(mainActivity).load(logoUrl).apply(new RequestOptions()
                .signature(new ObjectKey(lastModified))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(circleImageView);
        return popup;
    }
}
