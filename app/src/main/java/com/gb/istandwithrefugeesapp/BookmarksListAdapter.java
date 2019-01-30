package com.gb.istandwithrefugeesapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import com.gb.istandwithrefugeesapp.Model.Bookmark;
import com.gb.istandwithrefugeesapp.Model.BookmarkType;
import com.gb.istandwithrefugeesapp.Model.Charity;
import com.gb.istandwithrefugeesapp.Model.EUMarker;
import com.gb.istandwithrefugeesapp.Model.Fundraiser;
import com.gb.istandwithrefugeesapp.Model.LatLong;
import com.gb.istandwithrefugeesapp.Model.OnlineAid;
import com.gb.istandwithrefugeesapp.Model.UkMarker;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp on 08/03/2018.
 */

class BookmarksListAdapter extends RecyclerView.Adapter<BookmarksListAdapter.ViewHolder> implements Filterable, s3DownloaderInterface {
    private final BookmarksListAdapter bookmarksListAdapter = this;
    private final MainActivity mainActivity;
    private final MainActivity.BookmarksFragment bkFrag;

    private ArrayList<Bookmark> mDataset;
    private final ArrayList<Bookmark> filterOrgsList;
    private String title;

    private int selectedMarkerId;
    private BookmarkType selectedBookmarkType;
    private int bookmarkArrayIndex;
    private Bookmark selectedBookmark;

    public ArrayList<Bookmark> getmDataset() {
        return mDataset;
    }

    public void setmDataset(ArrayList<Bookmark> mDataset) {
        this.mDataset = mDataset;
    }

    private AllFilter allFilter;
    private int selectedBookmarkId;


    //remove correct type of bookmark (uk online etc)
    @Override
    public void downloadResult() {
            removeBookmark();
    }


    @Override
    public void onViewRecycled(BookmarksListAdapter.ViewHolder holder) {
        super.onViewRecycled(holder);
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
        ImageView copyImage;

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
            copyImage = mCardView.findViewById(R.id.copy_icon);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BookmarksListAdapter(MainActivity anActivity, ArrayList<Bookmark> myDataset, MainActivity.BookmarksFragment bkFrag )  {
        mDataset = myDataset;
        mainActivity = anActivity;
        filterOrgsList = mDataset;
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
        System.out.println(mDataset);
        Bookmark bookmark = mDataset.get(position);
        // TODO: 13/01/2019 maybe position + 1?
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
        if (bookmark.getBookmarkType() == BookmarkType.UK) {
            HashMap currentUkMap = mainActivity.getDbHelper().getMarkersMap().get(bookmark.getMarkerId());
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
                        LatLong l = (LatLong) mainActivity.getDbHelper().getLongLatMap().get(markerId);
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
                        LatLong l = (LatLong) mainActivity.getDbHelper().getLongLatMap().get(markerId);
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
                    Bookmark bookmark = mDataset.get(position);
                    selectedBookmarkId = bookmark.getBookmarkId();
                    selectedBookmark = bookmark;
                    mainActivity.checkDownloadPermissionsBookmarks(1, bookmarksListAdapter);

                }
            });
        }
        else if(bookmark.getBookmarkType() == BookmarkType.ONLINE) {
            final OnlineAid onlineAid = mainActivity.getDbHelper().getOnlineAids().get(bookmark.getMarkerId());
                RelativeLayout relativeLayout = holder.mCardView.findViewById(R.id.relativeLayout2);
                relativeLayout.setBackgroundResource(R.drawable.rounded_edit_text_two);
                RelativeLayout dateContainer = holder.mCardView.findViewById(R.id.date_container);
                dateContainer.setVisibility(View.GONE);
                textview.setText(onlineAid.getTitle());
                title = onlineAid.getTitle();
                Glide.with(mainActivity).load(onlineAid.getLogoUrl())
                        .into(holder.logo);
                textViewAddress.setVisibility(View.GONE);
                TextView textViewDescription = holder.mCardView.findViewById(R.id.card_desc);
                textViewDescription.setText(onlineAid.getDescription());
                holder.webImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(onlineAid.getWebsite());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        mainActivity.startActivity(intent);
                    }
                });
                holder.mapImage.setVisibility(View.INVISIBLE);
                holder.copyImage.setVisibility(View.GONE);
                holder.locationImg.setVisibility(View.GONE);
            holder.bookmarkImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bookmark bookmark = mDataset.get(position);
                    selectedBookmarkId = bookmark.getBookmarkId();
                    selectedBookmark = bookmark;
                    mainActivity.checkDownloadPermissionsBookmarks(1, bookmarksListAdapter);

                }
            });        }
        else {
                final EUMarker currentMarker = mainActivity.getDbHelper().getOverseasMarkersMap().get(bookmark.getMarkerId());
                final int markerId;
                    RelativeLayout relativeLayout = holder.mCardView.findViewById(R.id.relativeLayout2);
                    relativeLayout.setBackgroundResource(R.drawable.rounded_edit_text_two);
                    RelativeLayout dateContainer = holder.mCardView.findViewById(R.id.date_container);
                    dateContainer.setVisibility(View.GONE);
                    markerId = currentMarker.getMarkerId();
                    textview.setText(currentMarker.getTitle());
                    title = currentMarker.getTitle();
                    Glide.with(mainActivity).load(currentMarker.getImageUrl())
                            .into(holder.logo);
                    String cityOrTown = currentMarker.getCityOrTown();
                    String region = currentMarker.getRegion();
                    String country = currentMarker.getCountry();
                    if (cityOrTown.length() != 0 && region.length() != 0) {
                        cityOrTown = cityOrTown + ", ";
                        region = cityOrTown + ", ";
                        textViewAddress.setText(cityOrTown + region + country);
                    }
                    else{
                textViewAddress.setText(country);
                    }
                    TextView textViewDescription = holder.mCardView.findViewById(R.id.card_desc);
                    textViewDescription.setText(currentMarker.getDescription());
                    holder.webImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse(currentMarker.getWebsite());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            mainActivity.startActivity(intent);
                        }
                    });
                    holder. mapImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LatLong l = (LatLong) mainActivity.getDbHelper().getLongLatMapEU().get(markerId);
                            float lat = l.getLat();
                            float lon = l.getLon();
                            Fragment frag = new MainActivity.EUMapFragment();
                            loadFragment(frag, lat, lon);
                        }
                    });
            holder.bookmarkImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bookmark bookmark = mDataset.get(position);
                    selectedBookmarkId = bookmark.getBookmarkId();
                    selectedBookmark = bookmark;
                    mainActivity.checkDownloadPermissionsBookmarks(1, bookmarksListAdapter);
                }
            });
                }
        }

    private void removeBookmark() {
        class GetUserTy extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                System.out.println(selectedBookmark + "hey");
                mainActivity.getDbHelper().getBookmarksArray().remove(selectedBookmark);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                mDataset = mainActivity.getDbHelper().getBookmarksArray();
                bkFrag.setResults();
                String toastMsg = title + " bookmark removed.";
                Toast.makeText(mainActivity, toastMsg, Toast.LENGTH_SHORT).show();
                mainActivity.getDbHelper().removeBookmark(selectedBookmarkId);
            }
        }
        GetUserTy gut = new GetUserTy();
        gut.execute();
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
            ArrayList<Bookmark> filterAllList = filterOrgsList;
            FilterResults filterResults = new FilterResults();
            ArrayList<Bookmark> tempArray = new ArrayList<>();
            if (mainActivity.getType()!=null) {
                System.out.println(filterAllList);
                for (int i = 0; i < filterAllList.size(); i++) {
                    Bookmark bookmark = filterAllList.get(i);
                    if (bookmark.getBookmarkType() == mainActivity.getType()){
                        tempArray.add(bookmark);
                    }
                }
                filterResults.count = tempArray.size();
                filterResults.values = tempArray;
            }
            else {
                filterResults.count = filterAllList.size();
                filterResults.values = filterAllList;
            }
            filterAllList = (ArrayList<Bookmark>) filterResults.values;

            filterResults = new FilterResults();
            tempArray = new ArrayList<>();
            if (mainActivity.getSearchString()!=null && mainActivity.getSearchString().length()>0) {
                String title = "";
                int indexKey = 0;
                for (int i = 0; i < filterAllList.size(); i++) {
                    Bookmark bookmark = filterAllList.get(i);

                    if (bookmark.getBookmarkType() == BookmarkType.UK) {
                        HashMap<String, UkMarker> currentMap = mainActivity.getDbHelper().getMarkersMap().get(bookmark.getMarkerId());
                        if (currentMap.containsKey("Organisation")) {
                            Charity charity = (Charity) currentMap.get("Organisation");
                            title = charity.getTitle();
                        }
                        if (currentMap.containsKey("Fundraiser")) {
                            Fundraiser fundraiser = (Fundraiser) currentMap.get("Fundraiser");
                            title = fundraiser.getTitle();
                        }
                    } else if (bookmark.getBookmarkType() == BookmarkType.ONLINE) {
                        OnlineAid onlineAid = mainActivity.getDbHelper().getOnlineAids().get(bookmark.getMarkerId());
                        title = onlineAid.getTitle();
                    }
                    else  {
                        EUMarker euMarker= mainActivity.getDbHelper().getOverseasMarkersMap().get(bookmark.getMarkerId());
                        title = euMarker.getTitle();
                    }
                    if (title.toLowerCase().contains(mainActivity.getSearchString().toLowerCase())) {
                        tempArray.add(bookmark);
                    }
                }
                filterResults.count = tempArray.size();
                filterResults.values = tempArray;
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
            mDataset = (ArrayList<Bookmark>) results.values;
            bkFrag.setResults();

        }
    }
}