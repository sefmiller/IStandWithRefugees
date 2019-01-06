package com.gb.istandwithrefugeesapp;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;


/**
 * Custom Dialog for displaying list Views in custom dialog layout xmls rather then standard android spinner layout
 */
class CustomDialogNoSearch extends Dialog {


    public CustomDialogNoSearch(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_no_search);

        TextView tx = findViewById(R.id.dialog_title);

        Typeface titleFont = Typeface.
                createFromAsset(getContext().getAssets(), "fonts/Pacifico.ttf");
        tx.setTypeface(titleFont);

    }
}