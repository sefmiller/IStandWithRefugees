package com.gb.istandwithrefugeesapp;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.bumptech.glide.request.RequestOptions;
import com.gb.istandwithrefugeesapp.Model.BookmarkType;
import com.gb.istandwithrefugeesapp.Model.Charity;
import com.gb.istandwithrefugeesapp.Model.Fundraiser;
import com.gb.istandwithrefugeesapp.Model.LatLong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp on 08/03/2018.
 */

class BookmarksListAdapter extends RecyclerView.Adapter<BookmarksListAdapter.ViewHolder> implements Filterable, s3DownloaderInterface {
    private final BookmarksListAdapter bookmarksListAdapter = this;
    private final MainActivity mainActivity;
    private final MainActivity.BookmarksFragment bkFrag;

    private TreeMap<Integer, Object>  mDataset;
    private final TreeMap<Integer, Object>  filterOrgsList;
    private SparseArray filterLatLongsList;
    private String title;

    private int selectedMarkerId;
    private String selectedTitle;
    private BookmarkType selectedBookmarkType;

    public TreeMap getmDataset() {
        return mDataset;
    }


    private AllFilter allFilter;
    


    //remove correct type of bookmark (uk online etc)
    @Override
    public void downloadResult() {
        if (selectedBookmarkType.equals(BookmarkType.UK)){
            downloadResultUk();
        }
        else if (selectedBookmarkType.equals(BookmarkType.OVERSEAS)){
            downloadResultOverseas();
        }
        else{
            downloadResultOnline();
        }
    }

    private void downloadResultOverseas() {
    }

    private void downloadResultOnline() {
    }

    private void downloadResultUk() {
            removeBookmarkUk();
    }

    @Override
    public void onViewRecycled(BookmarksListAdapter.ViewHolder holder) {
        super.onViewRecycled(holder);
        Glide.with(mainActivity).clear(holder.logo);
        Glide.with(mainActivity).clear(holder.locationImg);
        Glide.with(mainActivity).clear(holder.dateImg);
        Glide.with(mainActivity).clear(holder.timeImg);
        Glide.with(mainActivity).clear(holder.webImage);
        Glide.with(mainActivity).clear(holder.bookmarkImage);
        Glide.with(mainActivity).clear(holder.mapImage);
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
    public BookmarksListAdapter(MainActivity anActivity, TreeMap<Integer, Object> myDataset, SparseArray latlongMap, MainActivity.BookmarksFragment bkFrag )  {
        mDataset = myDataset;
        mainActivity = anActivity;
        filterOrgsList = mDataset;
        filterLatLongsList = latlongMap;
        this.bkFrag = bkFrag;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BookmarksListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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
        TreeMap currentMap = (TreeMap) mDataset.get(position + 1);
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
        Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button_alt.png")
                .into(holder.bookmarkImage);


        if (currentMap.containsKey(BookmarkType.UK)) {
            HashMap currentUkMap = (HashMap)currentMap.get(BookmarkType.UK);
            final int markerId;
            if (currentUkMap.containsKey("Organisation")){
            RelativeLayout relativeLayout = holder.mCardView.findViewById(R.id.relativeLayout2);
            relativeLayout.setBackgroundResource(R.drawable.rounded_edit_text_two);
            RelativeLayout dateContainer = holder.mCardView.findViewById(R.id.date_container);
            dateContainer.setVisibility(View.GONE);
            final Charity charity = (Charity) currentUkMap.get("Organisation");
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
                holder. mapImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LatLong l = (LatLong) filterLatLongsList.get(markerId);
                        float lat = l.getLat();
                        float lon = l.getLon();
                        Fragment frag = new MainActivity.UkMapFragment();
                        loadFragment(frag, lat, lon);
                    }
                });
        }
        else{
                final Fundraiser fundraiser = (Fundraiser) currentUkMap.get("Fundraiser");
                markerId = fundraiser.getMarkerId();
                RelativeLayout relativeLayout = holder.mCardView.findViewById(R.id.relativeLayout2);
                CircleImageView logo = holder.mCardView.findViewById(R.id.nat_circle_image_view);
                Glide.with(mainActivity).load(fundraiser.getaCharity().getImageUrl())
                        .into(logo);
                relativeLayout.setBackgroundResource(R.drawable.rounded_edit_text_five);
                RelativeLayout dateContainer = holder.mCardView.findViewById(R.id.date_container);
                dateContainer.setVisibility(View.GONE);
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
                holder. mapImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LatLong l = (LatLong) filterLatLongsList.get(markerId);
                        float lat = l.getLat();
                        float lon = l.getLon();
                        Fragment frag = new MainActivity.UkMapFragment();
                        loadFragment(frag, lat, lon);
                    }
                });
        }

            holder.bookmarkImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedBookmarkType = BookmarkType.UK;
                    System.out.println(mDataset + "Yo");
                    TreeMap currentMap = (TreeMap) mDataset.get(position + 1);
                    HashMap currentUKMap = (HashMap) currentMap.get(BookmarkType.UK);
                    if (currentUKMap.containsKey("Organisation")) {
                        final Charity charity = (Charity) currentUKMap.get("Organisation");
                        selectedMarkerId = charity.getMarkerId();
                        selectedTitle = charity.getTitle() ;
                    }
                    else {
                        final Fundraiser fundraiser = (Fundraiser) currentUKMap.get("Fundraiser");
                        selectedMarkerId = fundraiser.getMarkerId();
                        selectedTitle = fundraiser.getTitle() ;
                    }
                    mainActivity.checkDownloadPermissionsBookmarks(1, bookmarksListAdapter);

                }
            });
        }
        else if(currentMap.containsKey(BookmarkType.OVERSEAS)) {
            //to do
        }
        else {
            //to do
        }
}

    private void removeBookmarkUk() {
        class GetUserTy extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                mainActivity.getDbHelper().getBookmarksArray().remove(Integer.valueOf(selectedMarkerId));
                mainActivity.getAllbookmarks();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                mDataset = mainActivity.getBookmarksMap();
                bkFrag.setResults();
                String toastMsg = selectedTitle + " bookmark removed.";
                Toast.makeText(mainActivity, toastMsg, Toast.LENGTH_SHORT).show();
                mainActivity.getDbHelper().removeBookmark(selectedMarkerId);
            }
        }
        GetUserTy gut = new GetUserTy();
        gut.execute();
    }



    private void removeBookmarkOverseas() {
        //TODO
    }

    private void removeBookmarkOnline() {
        //TODO
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
            allFilter = new BookmarksListAdapter.AllFilter();
        return allFilter;
    }

      

    class AllFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            TreeMap<Integer, Object> filterAllList = filterOrgsList;
            filterLatLongsList = mainActivity.getDbHelper().getLongLatMap();
            SparseArray<LatLong> latLongSparseArray = new SparseArray<>();

            FilterResults filterResults = new FilterResults();
            if (mainActivity.getType()!=null) {
                Set<Integer> keys;
                TreeMap<Integer,Object> tempMap = new TreeMap<>();
                keys = filterAllList.keySet();
                int indexKey = 1;
                for (int key: keys) {
                    TreeMap currentMap = (TreeMap) filterAllList.get(key);
                    if (currentMap.containsKey(mainActivity.getType())){
                        tempMap.put(indexKey, currentMap);
                        if (currentMap.containsKey(BookmarkType.UK)) {
                            HashMap currentUkMap = (HashMap) currentMap.get(BookmarkType.UK);
                            if (currentUkMap.containsKey("Organisation")) {
                                Charity charity = (Charity) currentUkMap.get("Organisation");
                                Integer markerId = charity.getMarkerId();
                            LatLong latLong = (LatLong) filterLatLongsList.get(markerId);
                            latLongSparseArray.put(markerId, latLong);
                        }
                        else {
                                Fundraiser fundraiser = (Fundraiser) currentUkMap.get("Fundraiser");
                                Integer markerId = fundraiser.getMarkerId();
                                LatLong latLong = (LatLong) filterLatLongsList.get(markerId);
                                latLongSparseArray.put(markerId, latLong);
                            }
                        }
                            indexKey = indexKey + 1;
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
            filterAllList = (TreeMap)filterResults.values;

            filterResults = new FilterResults();
            if (mainActivity.getSearchString()!=null && mainActivity.getSearchString().length()>0) {
                ArrayList<String> tempList = new ArrayList<>();
                TreeMap<Integer, Object> tempMap = new TreeMap<>();
                latLongSparseArray = new SparseArray<>();

                Set<Integer> keys = filterAllList.keySet();
                int indexKey = 1;
                for (int key: keys) {
                    TreeMap currentMap = (TreeMap) filterAllList.get(key);
                    if (currentMap.containsKey(BookmarkType.UK)) {
                        HashMap currentUkMap = (HashMap) currentMap.get(BookmarkType.UK);
                        if (currentUkMap.containsKey("Organisation")) {
                            Charity charity = (Charity) currentUkMap.get("Organisation");
                            Integer markerId = charity.getMarkerId();
                            String name = charity.getTitle();
                            if (name.toLowerCase().contains(mainActivity.getSearchString().toLowerCase())) {
                                tempMap.put(indexKey, currentMap);
                                LatLong latLong = (LatLong) filterLatLongsList.get(markerId);
                                latLongSparseArray.put(markerId, latLong);
                                indexKey = indexKey + 1;
                            }
                        } else {
                            Fundraiser fun = (Fundraiser) currentUkMap.get("Fundraiser");
                            Integer markerId = fun.getMarkerId();
                            String name = fun.getTitle() + " for: " + fun.getaCharity().getTitle();
                            if (name.toLowerCase().contains(mainActivity.getSearchString().toLowerCase())) {
                                tempMap.put(indexKey, currentMap);
                                LatLong latLong = (LatLong) filterLatLongsList.get(markerId);
                                latLongSparseArray.put(markerId, latLong);
                                indexKey = indexKey + 1;
                            }
                        }
                    } else {
                        //TODO overseas, online search}
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
            mDataset = (TreeMap) results.values;
            System.out.println(filterLatLongsList);
            bkFrag.setResults();

        }
    }
}