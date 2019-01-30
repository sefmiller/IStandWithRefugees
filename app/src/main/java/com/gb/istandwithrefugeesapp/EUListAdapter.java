package com.gb.istandwithrefugeesapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.gb.istandwithrefugeesapp.Model.Bookmark;
import com.gb.istandwithrefugeesapp.Model.BookmarkType;
import com.gb.istandwithrefugeesapp.Model.Charity;
import com.gb.istandwithrefugeesapp.Model.EUMarker;
import com.gb.istandwithrefugeesapp.Model.Fundraiser;
import com.gb.istandwithrefugeesapp.Model.LatLong;
import com.gb.istandwithrefugeesapp.Model.UkMarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp on 08/03/2018.
 */

class EUListAdapter extends RecyclerView.Adapter<EUListAdapter.ViewHolder> implements Filterable, s3DownloaderInterface {
    private final EUListAdapter euListAdapter = this;
    private final MainActivity mainActivity;
    private final MainActivity.ListEuropeFragment listMarkersFrag;
    private SparseArray<EUMarker>  mDataset;
    private final SparseArray<EUMarker> filterOrgsList;
    private SparseArray<EUMarker>  filterAllList = new SparseArray <>();
    private String title;
    private SparseArray<ArrayList<Boolean>> regionsSelected = new SparseArray<>();
    private int selectedMarkerId;
    private String selectedTitle;
    private int bookmarkId;
    private Bookmark selectedBookmark;

    public SparseArray<EUMarker> getmDataset() {
        return mDataset;
    }

    private SparseArray <LatLong>  filterLatLongsList;

    private AllFilter allFilter;

    @Override
    public void downloadResult() {
        selectedBookmark = mainActivity.findBookmark(selectedMarkerId, BookmarkType.OVERSEAS);
        if (selectedBookmark != null) {
            bookmarkId = selectedBookmark.getBookmarkId();
        }
        else {
            bookmarkId = 0;
        }
            if (bookmarkId != 0) {
            removeBookmark();
        } else {
            addBookmark();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewRecycled(EUListAdapter.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (!mainActivity.isFinishing()) {
            Glide.with(mainActivity).clear(holder.logo);
            Glide.with(mainActivity).clear(holder.locationImg);
            Glide.with(mainActivity).clear(holder.webImage);
            Glide.with(mainActivity).clear(holder.bookmarkImage);
            Glide.with(mainActivity).clear(holder.mapImage);
        }
        }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        final CardView mCardView;
        CircleImageView logo;
        ImageView locationImg;
        ImageView webImage;
        ImageView bookmarkImage;
        ImageView mapImage;
        ImageView copyImage;


        ViewHolder(CardView v) {
            super(v);
            mCardView = v;
            locationImg = mCardView.findViewById(R.id.location_img);
            logo = mCardView.findViewById(R.id.nat_circle_image_view);
            webImage = mCardView.findViewById(R.id.web_button);
            bookmarkImage = mCardView.findViewById(R.id.bookmarks_button);
            mapImage = mCardView.findViewById(R.id.map_button);
            copyImage = mCardView.findViewById(R.id.copy_icon);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EUListAdapter(MainActivity anActivity, SparseArray<EUMarker> myDataset, SparseArray <LatLong> latlongMap,
                         MainActivity.ListEuropeFragment listMarkersFrag)  {
        mDataset = myDataset;
        mainActivity = anActivity;
        filterOrgsList = mDataset;
        filterLatLongsList = latlongMap;
        this.listMarkersFrag = listMarkersFrag;
        regionsSelected = listMarkersFrag.getRegionsSelected();

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.uk_list_cardview, parent, false);
        return new ViewHolder(cardView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final EUMarker euMarker = (EUMarker) mDataset.get(position + 1);
        final TextView textview = holder.mCardView.findViewById(R.id.ref_name);
        Typeface titleFont = Typeface.
                createFromAsset(holder.mCardView.getContext().getAssets(), "fonts/Lobster_1.3.otf");
        textview.setTypeface(titleFont);
        Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/map_google_icon.png")
                .into(holder.locationImg);
        Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/hyperlink_icon.png")
                .into(holder.webImage);
        Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/map_button.png")
                .into(holder.mapImage);
        Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/copy.png")
                .into(holder.copyImage);

        final TextView textViewAddress = holder.mCardView.findViewById(R.id.address);
        holder.copyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied address", textViewAddress.getText().toString());
                clipboard.setPrimaryClip(clip);
            }
        });
        //System.out.println(position);
        int markerId;
            RelativeLayout relativeLayout = holder.mCardView.findViewById(R.id.relativeLayout2);
            relativeLayout.setBackgroundResource(R.drawable.rounded_edit_text_two);
            RelativeLayout dateContainer = holder.mCardView.findViewById(R.id.date_container);
            dateContainer.setVisibility(View.GONE);
            markerId = euMarker.getMarkerId();
            textview.setText(euMarker.getTitle());
            title = euMarker.getTitle();
            Glide.with(mainActivity).load(euMarker.getImageUrl()).apply(new RequestOptions()
                    .signature(new ObjectKey(euMarker.getLastModified()))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .into(holder.logo);
        String cityOrTown = euMarker.getCityOrTown();
        String region = euMarker.getRegion();
        String country = euMarker.getCountry();

        if (cityOrTown.length() != 0 && region.length() != 0) {
            cityOrTown = cityOrTown + ", ";
            region = region + ", ";
            textViewAddress.setText(cityOrTown + region + country);
        }
        else{
            textViewAddress.setText(country);
        }

        TextView textViewDescription = holder.mCardView.findViewById(R.id.card_desc);
            textViewDescription.setText(euMarker.getDescription());
            holder.webImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(euMarker.getWebsite());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mainActivity.startActivity(intent);
                }
            });
        selectedBookmark = mainActivity.findBookmark(markerId, BookmarkType.OVERSEAS);
        if (selectedBookmark != null) {
            bookmarkId = selectedBookmark.getBookmarkId();
            Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button_alt.png")
                    .into(holder.bookmarkImage);        }
        else{
            bookmarkId = 0;
            Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button.png")
                    .into(holder.bookmarkImage);
                   }

        holder.mapImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(filterLatLongsList);
                LatLong l = filterLatLongsList.get(position + 1);
                //System.out.println("oy" + l);
                float lat = l.getLat();
                float lon = l.getLon();
                Fragment frag = new MainActivity.EUMapFragment();
                loadFragment(frag, lat, lon);
            }
        });

        holder.bookmarkImage.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                selectedTitle = euMarker.getTitle();
                selectedMarkerId = euMarker.getMarkerId();
                mainActivity.checkDownloadPermissionsSelected(5, euListAdapter);
            }
        });
}

    private void removeBookmark() {
        String toastMsg = selectedTitle + " bookmark removed.";
        Toast.makeText(mainActivity, toastMsg, Toast.LENGTH_SHORT).show();
        mainActivity.getDbHelper().getBookmarksArray().remove(selectedBookmark);
        listMarkersFrag.setResults();
        mainActivity.getDbHelper().removeBookmark(bookmarkId);
    }

    private void addBookmark() {
        String toastMsg = selectedTitle + " bookmarked.";
        Toast.makeText(mainActivity, toastMsg, Toast.LENGTH_SHORT).show();
        mainActivity.getDbHelper().addBookmark(selectedMarkerId, BookmarkType.OVERSEAS, listMarkersFrag);
    }

    private void loadFragment(final Fragment fragment, float lat, float lon) {
        Bundle b = new Bundle();
        b.putFloat("selectedLat", lat);
        b.putFloat("selectedLon", lon);
        fragment.setArguments(b);
        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /**
     * @return Filter
     * gets country filter to filter countries
     */
    @NonNull
    @Override
    public Filter getFilter() {
        if(allFilter == null)
            allFilter = new AllFilter();
        return allFilter;
    }

    class AllFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filterAllList = filterOrgsList;
            filterLatLongsList = mainActivity.getDbHelper().getLongLatMapEU();
            //System.out.println(filterLatLongsList.size());
            SparseArray<LatLong> latLongSparseArray = new SparseArray<>();
            LinkedHashMap<String, ArrayList<String>> selectedCountriesandRegions = mainActivity.getDbHelper().getCountryAndRegionsArray();
            SparseArray<EUMarker> tempMap = new SparseArray<>();
            FilterResults filterResults = new FilterResults();
            if (regionsSelected!= null && regionsSelected.size() > 0) {
                int indexKey = 1;
                String singleorMultiple;
                for (int i = 0; i < filterAllList.size(); i++) {
                    EUMarker euMarker = filterAllList.get(i + 1);
                    String country = euMarker.getCountry();
                    String region = euMarker.getRegion();
                    if (region.length() <= 1) {
                        region = "Not Specified";
                    }
                    int regionIndex = selectedCountriesandRegions.get(country).indexOf(region);
                    int countryIndex = 0;
                    switch (country) {
                        case "Bosnia":
                            countryIndex = 0;
                            break;
                        case "Serbia":
                            countryIndex = 1;
                            break;
                        case "Greece":
                            countryIndex = 2;
                            break;
                        case "Belgium":
                            countryIndex = 3;
                            break;
                        case "Macedonia":
                            countryIndex = 4;
                            break;
                        case "Italy":
                            countryIndex = 5;
                            break;
                        case "France":
                            countryIndex = 6;
                            break;
                    }
                    if (regionsSelected.get(countryIndex).get(regionIndex) != false) {

                        tempMap.put(indexKey, filterAllList.get(i + 1));
                        LatLong latLong = filterLatLongsList.get(i + 1);
                        latLongSparseArray.put(indexKey, latLong);
                        indexKey = indexKey + 1;
                    }
                }
                filterResults.count = tempMap.size();
                filterResults.values = tempMap;
                filterLatLongsList = latLongSparseArray;
                //System.out.println(filterLatLongsList.size() + "oy");
            }
            else {
                filterResults.count = filterAllList.size();
                filterResults.values = filterAllList;
            }
            filterAllList = (SparseArray<EUMarker>) filterResults.values;

            filterResults = new FilterResults();
            if (mainActivity.getSelectedTypeOfAid()!=null && mainActivity.getSelectedTypeOfAid().size()>0) {
                tempMap = new SparseArray<>();
                latLongSparseArray = new SparseArray<>();
                int indexKey = 1;
                for (int i = 0; i < filterAllList.size(); i++) {
                    EUMarker euMarker = filterAllList.get(i + 1);
                        String typeOfAid = euMarker.getTypeOfAid();
                        if (mainActivity.getSelectedTypeOfAid().contains(typeOfAid)) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            indexKey = indexKey + 1;
                        }
                    }
                filterResults.count = tempMap.size();
                filterResults.values = tempMap;
                filterLatLongsList = latLongSparseArray;
                //System.out.println(filterLatLongsList.size() + "oy");
            }
            else {
                filterResults.count = filterAllList.size();
                filterResults.values = filterAllList;
            }
            filterAllList = (SparseArray<EUMarker>) filterResults.values;

            filterResults = new FilterResults();
            if (mainActivity.getSearchString()!=null && mainActivity.getSearchString().length()>0) {
                ArrayList<String> tempList = new ArrayList<>();
                tempMap = new SparseArray<>();
                latLongSparseArray = new SparseArray<>();
                int indexKey = 1;
                for (int i = 0; i < filterAllList.size(); i++) {
                    EUMarker euMarker = filterAllList.get(i + 1);
                        String name = euMarker.getTitle();
                        if (name.toLowerCase().contains(mainActivity.getSearchString().toLowerCase())) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            indexKey = indexKey + 1;
                        }
                    }
                filterResults.count = tempMap.size();
                filterResults.values = tempMap;
                filterLatLongsList = latLongSparseArray;
                //System.out.println(filterLatLongsList.size() + "oyoy");

            }
            else {
                filterResults.count = filterAllList.size();
                filterResults.values = filterAllList;

            }
            filterAllList = (SparseArray<EUMarker>) filterResults.values;



            return filterResults;
        }


        /**
         * @param constraint user inputted character sequence
         * @param results filtered results.
         *                recieves character sequence and sets the countries name array to the filtered results.
         *               notifies adapter to display the filtered results in the countries listview
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDataset = (SparseArray<EUMarker>) results.values;
            listMarkersFrag.setResults();
        }
    }
}