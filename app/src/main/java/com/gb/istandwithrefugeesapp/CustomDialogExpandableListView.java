package com.gb.istandwithrefugeesapp;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.TextView;


/**
 * Custom Dialog for displaying list Views in custom dialog layout xmls rather then standard android spinner layout
 */
class CustomDialogExpandableListView extends Dialog {
    MainActivity anActivity;
    ExpandableListView expandableListView;


    public CustomDialogExpandableListView(Activity a) {
        super(a);
        anActivity = (MainActivity) a;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_expandable_list_view);
        TextView tx = findViewById(R.id.dialog_title);
        Typeface titleFont = Typeface.
                createFromAsset(getContext().getAssets(), "fonts/Pacifico.ttf");
        tx.setTypeface(titleFont);

    }



}