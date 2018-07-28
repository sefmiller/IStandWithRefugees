package com.gb.istandwithrefugeesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;


class CharityAdapter extends ArrayAdapter implements Filterable {
    private ArrayList<String> charitiesNameArray;
    private final LayoutInflater mLayoutInflater;
    private CharityFilter charityFilter;
    private final ArrayList<String> filterCharitiesList;



    public CharityAdapter(Context context, ArrayList<String> countries) {
        super(context, R.layout.simple_list_item_1, countries);
        mLayoutInflater = LayoutInflater.from(context);
        charitiesNameArray = countries;
        filterCharitiesList = countries;
    }

    /**
     * List item view
     *
     * @param position    position of item
     * @param convertView View of item
     * @param parent      parent view of item's view
     * @return covertView
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.simple_list_item_1, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mNameView = convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mNameView.setText(charitiesNameArray.get(position));
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if(charityFilter == null)
            charityFilter = new CharityFilter();
        return charityFilter;
    }

    @Override
    public int getCount() {
        return charitiesNameArray.size();
    }

    @Override
    public String getItem(int position) {
        return charitiesNameArray.get(position);
    }
    /**
     * View holder for caching
     */
    private static class ViewHolder {
        TextView mNameView;
    }

    class CharityFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<String> tempList = new ArrayList<>();

                for (String name : filterCharitiesList) {
                    if (name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(name);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;

            } else {
                filterResults.count = filterCharitiesList.size();
                filterResults.values = filterCharitiesList;
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            charitiesNameArray = (ArrayList<String>) results.values;
            notifyDataSetChanged();

        }
    }
}
