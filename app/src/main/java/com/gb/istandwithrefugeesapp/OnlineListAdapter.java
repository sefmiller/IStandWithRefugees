package com.gb.istandwithrefugeesapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import com.gb.istandwithrefugeesapp.Model.Fundraiser;
import com.gb.istandwithrefugeesapp.Model.LatLong;
import com.gb.istandwithrefugeesapp.Model.OnlineAid;
import com.gb.istandwithrefugeesapp.Model.UkMarker;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

class OnlineListAdapter extends RecyclerView.Adapter<OnlineListAdapter.ViewHolder> implements Filterable, s3DownloaderInterface {

    private final OnlineListAdapter onlineListAdapter = this;
    private final MainActivity mainActivity;
    private final MainActivity.ListOnlineFragment listOnlineFragment;

    private SparseArray<OnlineAid> mDataset;
    private final SparseArray<OnlineAid> filterOrgsList;
    private SparseArray <OnlineAid> filterAllList = new SparseArray <>();
    private String title;
    private int selectedMarkerId;
    private String selectedTitle;
    private int bookmarkId;

    private AllFilter allFilter;
    private Bookmark selectedBookmark;

    public OnlineListAdapter getOnlineListAdapter() {
        return onlineListAdapter;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }


    public MainActivity.ListOnlineFragment getListOnlineFragment() {
        return listOnlineFragment;
    }

    public SparseArray<OnlineAid> getmDataset() {
        return mDataset;
    }

    public void setmDataset(SparseArray<OnlineAid> mDataset) {
        this.mDataset = mDataset;
    }

    public SparseArray<OnlineAid> getFilterOrgsList() {
        return filterOrgsList;
    }

    public SparseArray<OnlineAid> getFilterAllList() {
        return filterAllList;
    }

    public void setFilterAllList(SparseArray<OnlineAid> filterAllList) {
        this.filterAllList = filterAllList;
    }

    public int getSelectedMarkerId() {
        return selectedMarkerId;
    }

    public void setSelectedMarkerId(int selectedMarkerId) {
        this.selectedMarkerId = selectedMarkerId;
    }

    public String getSelectedTitle() {
        return selectedTitle;
    }

    public void setSelectedTitle(String selectedTitle) {
        this.selectedTitle = selectedTitle;
    }

    public int getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public AllFilter getAllFilter() {
        return allFilter;
    }

    public void setAllFilter(AllFilter allFilter) {
        this.allFilter = allFilter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OnlineListAdapter(MainActivity mainActivity, SparseArray<OnlineAid> onlineAids, MainActivity.ListOnlineFragment listOnlineFragment) {
        this.listOnlineFragment = listOnlineFragment;
        this. mainActivity = mainActivity;
        this.mDataset = onlineAids;
        filterOrgsList = mDataset;
    }

    @Override
    public void downloadResult() {
        selectedBookmark = mainActivity.findBookmark(selectedMarkerId, BookmarkType.ONLINE);
        if (selectedBookmark != null) {
            bookmarkId = selectedBookmark.getBookmarkId();
        }
        else{
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
    public void onViewRecycled(OnlineListAdapter.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (!mainActivity.isFinishing()) {
            Glide.with(mainActivity).clear(holder.logo);
            Glide.with(mainActivity).clear(holder.webImage);
            Glide.with(mainActivity).clear(holder.bookmarkImage);
        }
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        final CardView mCardView;
        CircleImageView logo;
        ImageView webImage;
        ImageView bookmarkImage;


        ViewHolder(CardView v) {
            super(v);
            mCardView = v;
            logo = mCardView.findViewById(R.id.nat_circle_image_view);
            webImage = mCardView.findViewById(R.id.web_button);
            bookmarkImage = mCardView.findViewById(R.id.bookmarks_button);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.online_list_cardview, parent, false);
        return new ViewHolder(cardView);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        System.out.println(mDataset + "here");
        OnlineAid onlineAid = (OnlineAid) mDataset.get(position + 1);
        final TextView textview = holder.mCardView.findViewById(R.id.ref_name);
        Typeface titleFont = Typeface.
                createFromAsset(holder.mCardView.getContext().getAssets(), "fonts/Lobster_1.3.otf");
        textview.setTypeface(titleFont);

        Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/hyperlink_icon.png")
                .into(holder.webImage);

        int markerId;
        RelativeLayout relativeLayout = holder.mCardView.findViewById(R.id.relativeLayout2);
        relativeLayout.setBackgroundResource(R.drawable.rounded_edit_text_two);
        markerId = onlineAid.getMarkerId();
        textview.setText(onlineAid.getTitle());
        title = onlineAid.getTitle();
        Glide.with(mainActivity).load(onlineAid.getLogoUrl()).apply(new RequestOptions()
                .signature(new ObjectKey(onlineAid.getLastModified()))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .into(holder.logo);

        TextView textViewDescription = holder.mCardView.findViewById(R.id.card_desc);
        textViewDescription.setText(onlineAid.getDescription());
        holder.webImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlineAid onlineAid = (OnlineAid) mDataset.get(position + 1);
                Uri uri = Uri.parse(onlineAid.getWebsite());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mainActivity.startActivity(intent);
            }
        });
        selectedBookmark = mainActivity.findBookmark(markerId, BookmarkType.ONLINE);
        if (selectedBookmark != null) {
            bookmarkId = selectedBookmark.getBookmarkId();
            Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button_alt.png")
                    .into(holder.bookmarkImage);
        } else {
            Glide.with(mainActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button.png")
                    .into(holder.bookmarkImage);
            bookmarkId = 0;
        }
        holder.bookmarkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlineAid onlineAid = (OnlineAid) mDataset.get(position + 1);
                selectedMarkerId = onlineAid.getMarkerId();
                    selectedTitle = onlineAid.getTitle() ;
                mainActivity.checkDownloadPermissionsBookmarksOnline(0, onlineListAdapter);
            }
            });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void removeBookmark() {
        String toastMsg = selectedTitle + " bookmark removed.";
        Toast.makeText(mainActivity, toastMsg, Toast.LENGTH_SHORT).show();
        mainActivity.getDbHelper().getBookmarksArray().remove(selectedBookmark);
        listOnlineFragment.setResults();
        mainActivity.getDbHelper().removeBookmark(bookmarkId);

    }

    private void addBookmark() {
        String toastMsg = selectedTitle + " bookmarked.";
        Toast.makeText(mainActivity, toastMsg, Toast.LENGTH_SHORT).show();
        mainActivity.getDbHelper().addBookmark(selectedMarkerId, BookmarkType.ONLINE, listOnlineFragment);
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
            FilterResults filterResults = new FilterResults();
            if (mainActivity.getSearchString()!=null && mainActivity.getSearchString().length()>0) {
                SparseArray<OnlineAid> tempMap = new SparseArray<>();
                int indexKey = 1;
                for (int i = 0; i < filterAllList.size(); i++) {
                    OnlineAid onlineAid = filterAllList.get(i + 1);
                        String name = onlineAid.getTitle();
                        if (name.toLowerCase().contains(mainActivity.getSearchString().toLowerCase())) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            indexKey = indexKey + 1;
                        }
                    }
                filterResults.count = tempMap.size();
                filterResults.values = tempMap;
            }
            else {
                filterResults.count = filterAllList.size();
                filterResults.values = filterAllList;

            }
            filterAllList = (SparseArray<OnlineAid>) filterResults.values;



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
            mDataset = (SparseArray<OnlineAid>) results.values;
            for (int i = 1; i <= filterOrgsList.size(); i++) {
                if (mDataset.get(i) == null || mDataset.get(i).equals(null)){
                }
            }
            listOnlineFragment.setResults();

        }
    }
}
