package com.gb.istandwithrefugeesapp;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private final ArrayList<String> expandableListTitle;
    private final HashMap<String, ArrayList<String>> expandableListDetail;
    public static final int CHOICE_MODE_MULTIPLE = AbsListView.CHOICE_MODE_MULTIPLE;
    private int choiceMode;
    private SparseArray<ArrayList<Boolean>> regionsSelected = new SparseArray<>();


    public CustomExpandableListAdapter(ArrayList<String> expandableListTitle,
                                       HashMap<String, ArrayList<String>> expandableListDetail, SparseArray<ArrayList<Boolean>> regionsSelected) {
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.regionsSelected = regionsSelected;
    }

    public SparseArray<ArrayList<Boolean>> getRegionsSelected() {
        return regionsSelected;
    }

    public void setRegionsSelected(SparseArray<ArrayList<Boolean>> regionsSelected) {
        this.regionsSelected = regionsSelected;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(android.R.layout.simple_list_item_multiple_choice, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(android.R.id.text1);
        expandedListTextView.setText(expandedListText);
        if (regionsSelected.get(listPosition).get(expandedListPosition) == true){
           CheckedTextView checkedTextView = (CheckedTextView) convertView;
            checkedTextView.setChecked(true);
        }
        else{
            CheckedTextView checkedTextView = (CheckedTextView) convertView;
            checkedTextView.setChecked(false);
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(final int listPosition, boolean isExpanded,
                             View convertView, final ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) LayoutInflater.from(parent.getContext());

            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.country_checkBox);
        checkBox.setFocusable(false);
        if (regionsSelected.get(listPosition).contains(true)){
            checkBox.setChecked(true);
        }
        else{
            checkBox.setChecked(false);

        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    for(int i = 0; i < regionsSelected.get(listPosition).size(); i++) {
                        regionsSelected.get(listPosition).set(i, true);
                    }
                }
                else {
                    for(int i = 0; i < regionsSelected.get(listPosition).size(); i++) {
                        regionsSelected.get(listPosition).set(i, false);
                    }
                }
                System.out.println(expandableListDetail);

                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    public void setChoiceMode(int choiceMode) {
        this.choiceMode = choiceMode;
    }

    public void setClicked(int groupPosition, int childPosition) {
        if (regionsSelected.get(groupPosition).get(childPosition) == true){
            regionsSelected.get(groupPosition).set(childPosition,false);
        }
        else {
            regionsSelected.get(groupPosition).set(childPosition,true);
        }
        System.out.println(expandableListDetail);
        notifyDataSetChanged();
    }
}
