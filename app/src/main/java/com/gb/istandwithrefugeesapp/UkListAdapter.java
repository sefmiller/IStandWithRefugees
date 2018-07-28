package com.gb.istandwithrefugeesapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gb.istandwithrefugeesapp.Model.Charity;
import com.gb.istandwithrefugeesapp.Model.Fundraiser;
import com.gb.istandwithrefugeesapp.Model.LatLong;
import com.gb.istandwithrefugeesapp.Model.UkMarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp on 08/03/2018.
 */

class UkListAdapter extends RecyclerView.Adapter<UkListAdapter.ViewHolder> implements Filterable, s3DownloaderInterface {
    private final UkListAdapter ukListAdapter = this;
    private final MainActivity mainActivity;
    private final MainActivity.ListMarkersFragment listMarkersFrag;

    private SparseArray<HashMap<String, UkMarker>> mDataset;
    private final SparseArray<HashMap<String, UkMarker>> filterOrgsList;
    private SparseArray <HashMap<String, UkMarker>> filterAllList = new SparseArray <>();
    private String title;

    private int selectedMarkerId;
    private String selectedTitle;

    public SparseArray<HashMap<String,UkMarker>> getmDataset() {
        return mDataset;
    }

    private SparseArray <LatLong>  filterLatLongsList;

    private AllFilter allFilter;
    private OrgOnlyFilter orgOnlyFilter;
    private FundraiserOnlyFilter fundFilter;



    @Override
    public void downloadResult() {
        if (mainActivity.getDbHelper().getBookmarksArray().contains(selectedMarkerId)){
            removeBookmark();
        }
        else{
            addBookmark();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewRecycled(UkListAdapter.ViewHolder holder) {
        super.onViewRecycled(holder);
        RequestManager requestManager;
        if (!mainActivity.isFinishing()) {
            Glide.with(mainActivity).clear(holder.logo);
            Glide.with(mainActivity).clear(holder.locationImg);
            Glide.with(mainActivity).clear(holder.dateImg);
            Glide.with(mainActivity).clear(holder.timeImg);
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
        ImageView dateImg;
        ImageView timeImg;
        ImageView locationImg;
        ImageView webImage;
        ImageView bookmarkImage;
        ImageView mapImage;

        ViewHolder(CardView v) {
            super(v);
            mCardView = v;
            locationImg = mCardView.findViewById(R.id.location_img);
            logo = mCardView.findViewById(R.id.nat_circle_image_view);
            dateImg = mCardView.findViewById(R.id.date_img);
            timeImg = mCardView.findViewById(R.id.time_img);
            webImage = mCardView.findViewById(R.id.web_button);
            bookmarkImage = mCardView.findViewById(R.id.bookmarks_button);
            mapImage = mCardView.findViewById(R.id.map_button);



        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UkListAdapter(MainActivity anActivity, SparseArray <HashMap<String, UkMarker>> myDataset, SparseArray <LatLong> latlongMap,
                         MainActivity.ListMarkersFragment listMarkersFrag)  {
        mDataset = myDataset;
        mainActivity = anActivity;
        filterOrgsList = mDataset;
        filterLatLongsList = latlongMap;
        this.listMarkersFrag = listMarkersFrag;
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
        HashMap currentMap = (HashMap) mDataset.get(position + 1);
        final TextView textview = holder.mCardView.findViewById(R.id.ref_name);
        Typeface titleFont = Typeface.
                createFromAsset(holder.mCardView.getContext().getAssets(), "fonts/Lobster_1.3.otf");
        textview.setTypeface(titleFont);
        TextView dateText = holder.mCardView.findViewById(R.id.title_date);
        TextView timeText = holder.mCardView.findViewById(R.id.title_time);
        Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/map_google_icon.png")
                .into(holder.locationImg);
        Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/ic_menu_my_calendar.png")
                .into(holder.dateImg);
        Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/time.png")
                .into(holder.timeImg);
        Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/hyperlink_icon.png")
                .into(holder.webImage);
        Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/map_button.png")
                .into(holder.mapImage);
        System.out.println(position);
        int markerId;
        if (currentMap.containsKey("Organisation")) {
            RelativeLayout relativeLayout = holder.mCardView.findViewById(R.id.relativeLayout2);
            relativeLayout.setBackgroundResource(R.drawable.rounded_edit_text_two);
            RelativeLayout dateContainer = holder.mCardView.findViewById(R.id.date_container);
            dateContainer.setVisibility(View.GONE);
            final Charity charity = (Charity) currentMap.get("Organisation");
            markerId = charity.getMarkerId();
            textview.setText(charity.getTitle());
            title = charity.getTitle();
            Glide.with(mainActivity).load(charity.getImageUrl())
                    .into(holder.logo);
            TextView textViewAddress = holder.mCardView.findViewById(R.id.address);
            String houseNoOrBuldingName = charity.getHouseNoOrBuldingName();
            String street = charity.getStreet();
            String otherAddress = charity.getOtherAddress();
            String cityOrTown = charity.getCityOrTown();
            if (street.length() != 0) {
                street = houseNoOrBuldingName + " " + street + ", ";
            }

            if (otherAddress.length() != 0) {
                otherAddress = otherAddress + ", ";
            }

            if (cityOrTown.length() != 0 && charity.getPostcode().length() != 0) {
                cityOrTown = cityOrTown + ", ";
            }

            textViewAddress.setText(street + otherAddress + cityOrTown + charity.getPostcode());
            TextView textViewDescription = holder.mCardView.findViewById(R.id.card_desc);
            textViewDescription.setText(charity.getDescription());
            holder.webImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(charity.getWebsite());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mainActivity.startActivity(intent);
                }
            });
        }
        else {
            final Fundraiser fundraiser = (Fundraiser) currentMap.get("Fundraiser");
            markerId = fundraiser.getMarkerId();
            RelativeLayout dateContainer = holder.mCardView.findViewById(R.id.date_container);
            dateContainer.setVisibility(View.VISIBLE);
            Glide.with(mainActivity).load(fundraiser.getaCharity().getImageUrl())
                    .into(holder.logo);
            RelativeLayout relativeLayout = holder.mCardView.findViewById(R.id.relativeLayout2);
            relativeLayout.setBackgroundResource(R.drawable.rounded_edit_text_five);
            dateText.setText(fundraiser.getDate());
            timeText.setText(fundraiser.getTime());
            textview.setText(fundraiser.getTitle() + "\n" + "\n" + "For: " + fundraiser.getaCharity().getTitle());
            title = fundraiser.getTitle();
            TextView textViewAddress = holder.mCardView.findViewById(R.id.address);
            String houseNoOrBuldingName = fundraiser.getHouseNoOrBuldingName();
            String street = fundraiser.getStreet();
            String otherAddress = fundraiser.getOtherAddress();
            String cityOrTown = fundraiser.getCityOrTown();
            if (street.length() != 0) {
                street = houseNoOrBuldingName + " " + street + ", ";
            }

            if (otherAddress.length() != 0) {
                otherAddress = otherAddress + ", ";
            }

            if (cityOrTown.length() != 0 && fundraiser.getPostcode().length() != 0) {
                cityOrTown = cityOrTown + ", ";
            }

            textViewAddress.setText(street + otherAddress + cityOrTown + fundraiser.getPostcode());
            TextView desc = holder.mCardView.findViewById(R.id.card_desc);
            desc.setText(fundraiser.getDescription());
            TextView textViewDescription = holder.mCardView.findViewById(R.id.card_desc);
            textViewDescription.setText(fundraiser.getDescription());


            holder.webImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(fundraiser.getWebsite());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mainActivity.startActivity(intent);
                }
            });
        }
        if(mainActivity.getDbHelper().getBookmarksArray().contains(markerId)){
            Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button_alt.png")
                    .into(holder.bookmarkImage);        }
        else{
            Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button.png")
                    .into(holder.bookmarkImage);
                   }

        holder.mapImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(filterLatLongsList);
                LatLong l = filterLatLongsList.get(position + 1);
                System.out.println("oy" + l);
                float lat = l.getLat();
                float lon = l.getLon();
                Fragment frag = new MainActivity.UkMapFragment();
                loadFragment(frag, lat, lon);
            }
        });

        holder.bookmarkImage.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {

                HashMap currentMap = (HashMap) mDataset.get(position + 1);
                if (currentMap.containsKey("Organisation")) {
                    final Charity charity = (Charity) currentMap.get("Organisation");
                    selectedMarkerId = charity.getMarkerId();
                    selectedTitle = charity.getTitle() ;
                }
                else {
                    final Fundraiser fundraiser = (Fundraiser) currentMap.get("Fundraiser");
                    selectedMarkerId = fundraiser.getMarkerId();
                    selectedTitle = fundraiser.getTitle() ;
                }
                mainActivity.checkDownloadPermissionsBookmarksUk(0, ukListAdapter);

        }
    });
}

    private void removeBookmark() {
        String toastMsg = selectedTitle + " bookmark removed.";
        Toast.makeText(mainActivity, toastMsg, Toast.LENGTH_SHORT).show();
        mainActivity.getDbHelper().getBookmarksArray().remove(Integer.valueOf(selectedMarkerId));
        listMarkersFrag.setResults();
        mainActivity.getDbHelper().removeBookmark(selectedMarkerId);

    }

    private void addBookmark() {
        String toastMsg = selectedTitle + " bookmarked.";
        Toast.makeText(mainActivity, toastMsg, Toast.LENGTH_SHORT).show();
        mainActivity.getDbHelper().getBookmarksArray().add(selectedMarkerId);
        listMarkersFrag.setResults();
        mainActivity.getDbHelper().addBookmark(selectedMarkerId);
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

    public Filter getFundFilter() {
        if(fundFilter == null)
            fundFilter = new FundraiserOnlyFilter();
        return fundFilter;
    }

    public Filter getOrgOnlyFilter() {
        if(orgOnlyFilter == null)
            orgOnlyFilter = new OrgOnlyFilter();
        return orgOnlyFilter;
    }


    class FundraiserOnlyFilter extends Filter {

        private Set<Integer> keys;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filterLatLongsList = mainActivity.getDbHelper().getLongLatMap();
            SparseArray<LatLong> latLongSparseArray = new SparseArray<>();
            filterAllList = filterOrgsList;
            System.out.println(filterAllList.get(0));
            FilterResults filterResults = new FilterResults();
            SparseArray<HashMap<String, UkMarker>> tempMap = new SparseArray<>();
            if (mainActivity.getArea()!=null && mainActivity.getArea().length()>0
                    && !mainActivity.getArea().equals("UK")) {
                HashMap<String, UkMarker> tempMarkerMap;
                int indexKey = 1;
                for (int i = 0; i < filterAllList.size(); i++) {
                    tempMarkerMap = filterAllList.get(i + 1);
                    if (tempMarkerMap.containsKey("Organisation")) {
                        Charity charity = (Charity) tempMarkerMap.get("Organisation");
                        String name = charity.getRegion();
                        if (name.toLowerCase().contains(mainActivity.getArea().toLowerCase())) {
                             LatLong latLong = filterLatLongsList.get(i + 1);
                             latLongSparseArray.put(indexKey, latLong);
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            indexKey = indexKey + 1;
                        }
                    } else {
                        Fundraiser fun = (Fundraiser) tempMarkerMap.get("Fundraiser");
                        String name = fun.getRegion();
                        if (name.toLowerCase().contains(mainActivity.getArea().toLowerCase())) {
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            indexKey = indexKey + 1;
                        }
                    }
                }
                filterResults.count = tempMap.size();
                filterResults.values = tempMap;
                filterLatLongsList = latLongSparseArray;
            }
            else {

                filterResults.count = filterAllList.size();
                filterResults.values = filterAllList;
            }

            filterAllList = (SparseArray<HashMap<String, UkMarker>>) filterResults.values;

            filterResults = new FilterResults();

            if (constraint!=null && constraint.length()>0) {

                tempMap = new SparseArray<>();
                latLongSparseArray = new SparseArray<>();
                HashMap<String, UkMarker> tempMarkerMap;
                int indexKey = 1;
                for (int i = 0; i < filterAllList.size(); i++) {
                        if (filterAllList.get(i + 1) != null) {
                            tempMarkerMap = filterAllList.get(i + 1);
                            if (tempMarkerMap.containsKey(constraint)) {
                                tempMap.put(indexKey, filterAllList.get(i + 1));
                                LatLong latLong = filterLatLongsList.get(i + 1);
                                latLongSparseArray.put(indexKey, latLong);
                                indexKey = indexKey + 1;
                            }
                        }
                                    }
                filterResults.count = tempMap.size();
                filterResults.values = tempMap;
                filterLatLongsList = latLongSparseArray;
            }
            else {
                    filterResults.count = filterAllList.size();
                    filterResults.values = filterAllList;
            }

            filterAllList = (SparseArray<HashMap<String, UkMarker>>) filterResults.values;


            filterResults = new FilterResults();
            if (mainActivity.getSearchString()!=null && mainActivity.getSearchString().length()>0) {
                ArrayList<String> tempList = new ArrayList<>();
                tempMap = new SparseArray<>();
                latLongSparseArray = new SparseArray<>();
                HashMap<String, UkMarker> tempMarkerMap;
                int indexKey = 1;
                for (int i = 0; i < filterAllList.size(); i++) {
                    tempMarkerMap = filterAllList.get(i + 1);
                    if (tempMarkerMap.containsKey("Organisation")) {
                        Charity charity = (Charity) tempMarkerMap.get("Organisation");
                        String name = charity.getTitle();
                        if (name.toLowerCase().contains(mainActivity.getSearchString().toLowerCase())) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            indexKey = indexKey + 1;
                        }
                    }
                    else{
                        Fundraiser fun = (Fundraiser) tempMarkerMap.get("Fundraiser");
                        String name = fun.getTitle() + " for: " +  fun.getaCharity().getTitle();
                        if (name.toLowerCase().contains(mainActivity.getSearchString().toLowerCase())) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            indexKey = indexKey + 1;
                        }
                    }
                }
                filterResults.count = tempMap.size();
                filterResults.values = tempMap;
                filterLatLongsList = latLongSparseArray;
            }
            else {
                filterResults.count = filterAllList.size();
                filterResults.values = filterAllList;
            }
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
            mDataset = (SparseArray<HashMap<String,UkMarker>>) results.values;
            listMarkersFrag.setResults();

        }
    }
    class OrgOnlyFilter extends Filter {
        private Set<Integer> keys;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filterAllList = filterOrgsList;
            filterLatLongsList = mainActivity.getDbHelper().getLongLatMap();
            SparseArray<LatLong> latLongSparseArray = new SparseArray<>();
            System.out.println(filterAllList.size() + "oy");
            FilterResults filterResults = new FilterResults();

            if (mainActivity.getArea()!=null && mainActivity.getArea().length()>0
                    && !mainActivity.getArea().equals("UK")) {
                SparseArray<HashMap<String, UkMarker>> tempMap = new SparseArray<>();
                HashMap<String, UkMarker> tempMarkerMap;
                int indexKey = 1;
                for (int i = 0; i < filterAllList.size(); i++) {
                    tempMarkerMap = filterAllList.get(i + 1);
                    if (tempMarkerMap.containsKey("Organisation")) {
                        Charity charity = (Charity) tempMarkerMap.get("Organisation");
                        String name = charity.getRegion();
                        if (name.toLowerCase().contains(mainActivity.getArea().toLowerCase())) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            indexKey = indexKey + 1;
                        }
                    }
                    else{
                        Fundraiser fun = (Fundraiser) tempMarkerMap.get("Fundraiser");
                        String name = fun.getRegion();
                        if (name.toLowerCase().contains(mainActivity.getArea().toLowerCase())) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            indexKey = indexKey + 1;
                        }
                    }
                }
                filterResults.count = tempMap.size();
                filterResults.values = tempMap;
                filterLatLongsList = latLongSparseArray;
            }
            else {

                filterResults.count = filterAllList.size();
                filterResults.values = filterAllList;
            }
            System.out.println(filterResults.count + "oyoy");
            filterAllList = (SparseArray<HashMap<String, UkMarker>>) filterResults.values;

            filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {

                SparseArray<HashMap<String, UkMarker>> tempMap = new SparseArray<>();
                latLongSparseArray = new SparseArray<>();
                HashMap<String, UkMarker> tempMarkerMap;
                int indexKey = 1;
                for (int i = 0; i < filterAllList.size(); i++) {
                    if (filterAllList.get(i + 1) != null) {
                        tempMarkerMap = filterAllList.get(i + 1);
                        if (tempMarkerMap.containsKey(constraint)) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            indexKey = indexKey + 1;
                        }
                    }
                }
                filterResults.count = tempMap.size();
                filterResults.values = tempMap;
                filterLatLongsList = latLongSparseArray;
            }
            else {
                filterResults.count = filterAllList.size();
                filterResults.values = filterAllList;
            }
            System.out.println(filterResults.count + "hey");
            filterAllList = (SparseArray<HashMap<String,UkMarker>>) filterResults.values;

            if (mainActivity.getSearchString()!=null && mainActivity.getSearchString().length()>0) {
                ArrayList<String> tempList = new ArrayList<>();
                SparseArray<HashMap<String, UkMarker>> tempMap = new SparseArray<>();
                latLongSparseArray = new SparseArray<>();
                HashMap<String, UkMarker> tempMarkerMap;
                int indexKey = 1;
                for (int i = 0; i < filterAllList.size(); i++) {
                    tempMarkerMap = filterAllList.get(i + 1);
                    if (tempMarkerMap.containsKey("Organisation")) {
                        Charity charity = (Charity) tempMarkerMap.get("Organisation");
                        String name = charity.getTitle();
                        if (name.toLowerCase().contains(mainActivity.getSearchString().toLowerCase())) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            indexKey = indexKey + 1;
                        }
                    }
                    else{
                        Fundraiser fun = (Fundraiser) tempMarkerMap.get("Fundraiser");
                        String name = fun.getTitle() + " for: " +  fun.getaCharity().getTitle();
                        if (name.toLowerCase().contains(mainActivity.getSearchString().toLowerCase())) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            indexKey = indexKey + 1;
                        }
                    }
                }
                filterResults.count = tempMap.size();
                filterResults.values = tempMap;
                filterLatLongsList = latLongSparseArray;
            }
            else {
                filterResults.count = filterAllList.size();
                filterResults.values = filterAllList;
            }

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
            mDataset = (SparseArray<HashMap<String, UkMarker>>) results.values;
            listMarkersFrag.setResults();
        }
    }

    class AllFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filterAllList = filterOrgsList;
            filterLatLongsList = mainActivity.getDbHelper().getLongLatMap();
            System.out.println(filterLatLongsList.size());
            SparseArray<LatLong> latLongSparseArray = new SparseArray<>();
            FilterResults filterResults = new FilterResults();
            if (mainActivity.getArea()!=null && mainActivity.getArea().length()>0
                    && !mainActivity.getArea().equals("UK")) {
                SparseArray<HashMap<String, UkMarker>> tempMap = new SparseArray<>();
                HashMap<String, UkMarker> tempMarkerMap;
                int indexKey = 1;
                for (int i = 0; i < filterAllList.size(); i++) {
                    tempMarkerMap = filterAllList.get(i + 1);
                    if (tempMarkerMap.containsKey("Organisation")) {
                        Charity charity = (Charity) tempMarkerMap.get("Organisation");
                        String name = charity.getRegion();
                        if (name.toLowerCase().contains(mainActivity.getArea().toLowerCase())) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            indexKey = indexKey + 1;
                        }
                    }
                    else{
                        Fundraiser fun = (Fundraiser) tempMarkerMap.get("Fundraiser");
                        String name = fun.getRegion();
                        if (name.toLowerCase().contains(mainActivity.getArea().toLowerCase())) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            indexKey = indexKey + 1;
                        }
                    }
                }
                filterResults.count = tempMap.size();
                filterResults.values = tempMap;
                filterLatLongsList = latLongSparseArray;
                System.out.println(filterLatLongsList.size() + "oy");
            }
            else {
                filterResults.count = filterAllList.size();
                filterResults.values = filterAllList;
            }
            filterAllList = (SparseArray<HashMap<String,UkMarker>>) filterResults.values;

            filterResults = new FilterResults();
            if (mainActivity.getSearchString()!=null && mainActivity.getSearchString().length()>0) {
                ArrayList<String> tempList = new ArrayList<>();
                SparseArray<HashMap<String, UkMarker>> tempMap = new SparseArray<>();
                latLongSparseArray = new SparseArray<>();
                HashMap<String, UkMarker> tempMarkerMap;
                int indexKey = 1;
                for (int i = 0; i < filterAllList.size(); i++) {
                    tempMarkerMap = filterAllList.get(i + 1);
                    if (tempMarkerMap.containsKey("Organisation")) {
                        Charity charity = (Charity) tempMarkerMap.get("Organisation");
                        String name = charity.getTitle();
                        if (name.toLowerCase().contains(mainActivity.getSearchString().toLowerCase())) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            indexKey = indexKey + 1;
                        }
                    }
                    else{
                        Fundraiser fun = (Fundraiser) tempMarkerMap.get("Fundraiser");
                        String name = fun.getTitle() + " for: " +  fun.getaCharity().getTitle();
                        if (name.toLowerCase().contains(mainActivity.getSearchString().toLowerCase())) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            indexKey = indexKey + 1;
                        }
                    }
                }
                filterResults.count = tempMap.size();
                filterResults.values = tempMap;
                filterLatLongsList = latLongSparseArray;
                System.out.println(filterLatLongsList.size() + "oyoy");

            }
            else {
                filterResults.count = filterAllList.size();
                filterResults.values = filterAllList;

            }
            filterAllList = (SparseArray<HashMap<String,UkMarker>>) filterResults.values;



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
            mDataset = (SparseArray<HashMap<String, UkMarker>>) results.values;
            System.out.println(mDataset);
            listMarkersFrag.setResults();

        }
    }
}