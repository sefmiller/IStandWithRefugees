package com.gb.istandwithrefugeesapp;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.gb.istandwithrefugeesapp.Model.BookmarkType;
import com.gb.istandwithrefugeesapp.Model.Charity;
import com.gb.istandwithrefugeesapp.Model.Fundraiser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp on 08/03/2018.
 */

class ContributionsAdapter extends RecyclerView.Adapter<ContributionsAdapter.ViewHolder> implements Filterable {
    private final MainActivity.ManageContributionsFragment contributionsFragment;
    private final MainActivity mainActivity;
    private TreeMap<Integer, Object>  mDataset;
    private final TreeMap<Integer, Object>  filterOrgsList;
    private String title;
    private Bitmap currentBookmarkBitmap;
    private Bitmap resourcesBookmarkBitmap;
    private int markerId;



    private AllFilter allFilter;






    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        final RelativeLayout mRelativeLayout;
        ViewHolder(RelativeLayout v) {
            super(v);
            mRelativeLayout = v;
            mRelativeLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Fragment fragment;
            int position = this.getAdapterPosition();
            TreeMap currentMap = (TreeMap) mDataset.get(position);
            if (currentMap.containsKey(BookmarkType.UK)) {
                HashMap currentUkMap = (HashMap) currentMap.get(BookmarkType.UK);
                if (currentUkMap.containsKey("Organisation")) {
                    final Charity charity = (Charity) currentUkMap.get("Organisation");
                    mainActivity.setCharityToBeAdded(charity);
                    mainActivity.setCharityLogo(null);
                    fragment = new MainActivity.AddTitleUkFragment();
                    mainActivity.loadFragment(fragment);
                }
                else {
                    final Fundraiser fundraiser = (Fundraiser) currentUkMap.get("Fundraiser");
                    mainActivity.setFundraiserToBeAdded(fundraiser);
                    mainActivity.setCharityLogo(null);
                    fragment = new MainActivity.AddTitleUkFundFragment();
                    mainActivity.loadFragment(fragment);
                }
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContributionsAdapter(MainActivity anActivity, TreeMap<Integer, Object> myDataset, MainActivity.ManageContributionsFragment frag)  {
        mDataset = myDataset;
        mainActivity = anActivity;
        filterOrgsList = mDataset;
        contributionsFragment = frag;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContributionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manage_contributions_item, parent, false);

        return new ViewHolder(relativeLayout);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TreeMap currentMap = (TreeMap) mDataset.get(position);
        final TextView textview = holder.mRelativeLayout.findViewById(R.id.manage_title);
        Typeface titleFont = Typeface.
                createFromAsset(holder.mRelativeLayout.getContext().getAssets(), "fonts/Lobster_1.3.otf");
        textview.setTypeface(titleFont);

        if (currentMap.containsKey(BookmarkType.UK)) {
            HashMap currentUkMap = (HashMap) currentMap.get(BookmarkType.UK);
            if (currentUkMap.containsKey("Organisation")) {
                final Charity charity = (Charity) currentUkMap.get("Organisation");
                CircleImageView logo = holder.mRelativeLayout.findViewById(R.id.logo_image);

                Glide.with(mainActivity).load(charity.getImageUrl()).apply(new RequestOptions()
                        .signature(new ObjectKey(charity.getLastModified()))
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                        .into(logo);
                markerId = charity.getMarkerId();
                textview.setText(charity.getTitle());
                title = charity.getTitle();
                //set image
            } else {
                final Fundraiser fundraiser = (Fundraiser) currentUkMap.get("Fundraiser");
                markerId = fundraiser.getMarkerId();
                CircleImageView logo = holder.mRelativeLayout.findViewById(R.id.logo_image);

                Glide.with(mainActivity).load(fundraiser.getaCharity().getImageUrl()).apply(new RequestOptions()
                        .signature(new ObjectKey(fundraiser.getaCharity().getLastModified()))
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                        .into(logo);
                textview.setText(fundraiser.getTitle() + "\n" + "\n" + "For: " + fundraiser.getaCharity().getTitle());
                title = fundraiser.getTitle();
                //set Image
            }
        }
        else if(currentMap.containsKey(BookmarkType.OVERSEAS)) {
            //to do
        }
        else {
            //to do
        }
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
            allFilter = new ContributionsAdapter.AllFilter();
        return allFilter;
    }

      

    class AllFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            TreeMap<Integer, Object> filterAllList = filterOrgsList;

            FilterResults filterResults = new FilterResults();
            if (mainActivity.getType()!=null) {
                Set<Integer> keys;
                TreeMap<Integer,Object> tempMap = new TreeMap<>();
                keys = filterAllList.keySet();
                int indexKey = 0;
                for (int key: keys) {
                    TreeMap currentMap = (TreeMap) filterAllList.get(key);
                    if (currentMap.containsKey(mainActivity.getType())){
                        tempMap.put(indexKey, currentMap);
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
            filterAllList = (TreeMap)filterResults.values;

            filterResults = new FilterResults();
            if (mainActivity.getSearchString()!=null && mainActivity.getSearchString().length()>0) {
                ArrayList<String> tempList = new ArrayList<>();
                TreeMap<Integer, Object> tempMap = new TreeMap<>();
                Set<Integer> keys = filterAllList.keySet();
                int indexKey = 0;
                for (int key: keys) {
                    TreeMap currentMap = (TreeMap) filterAllList.get(key);
                    if (currentMap.containsKey(BookmarkType.UK)) {
                        HashMap currentUkMap = (HashMap) currentMap.get(BookmarkType.UK);
                        if (currentUkMap.containsKey("Organisation")) {
                            Charity charity = (Charity) currentUkMap.get("Organisation");
                            String name = charity.getTitle();
                            if (name.toLowerCase().contains(mainActivity.getSearchString().toLowerCase())) {
                                tempMap.put(indexKey, currentMap);
                                indexKey = indexKey + 1;
                            }
                        } else {
                            Fundraiser fun = (Fundraiser) currentUkMap.get("Fundraiser");
                            String name = fun.getTitle() + " for: " + fun.getaCharity().getTitle();
                            if (name.toLowerCase().contains(mainActivity.getSearchString().toLowerCase())) {
                                tempMap.put(indexKey, currentMap);
                                indexKey = indexKey + 1;
                            }
                        }
                    } else {
                        //TODO overseas, online search}
                    }
                }
                filterResults.count = tempMap.size();
                filterResults.values = tempMap;
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
            contributionsFragment.updateRecylerView();


        }
    }
}