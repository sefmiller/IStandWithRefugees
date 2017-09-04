package com.gb.istandwithrefugeesapp;

import com.gb.istandwithrefugeesapp.Model.DBLatLongPoints;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
    {
        private static final String JSON_URL = "http://ec2-52-90-145-100.compute-1.amazonaws.com/get_all_latlongmarkers.php";
        DrawerLayout drawerLayout;
        NavigationView navigationView;
        MenuItem previousItem;
        ActionBarDrawerToggle drawerToggle = null;

        public static String getJsonUrl() {
            return JSON_URL;
        }

        Toolbar toolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main_activity);
            Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            mTitle.setTypeface(custom_font);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
            drawerLayout.setDrawerListener(toggle);
            toggle.syncState();

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            //CurrentPlatform.Init();
            //TodoItem item = new TodoItem { Text = "Awesome item" };
            //await MobileService.GetTable<TodoItem>().InsertAsync(item);

            // Tie DrawerLayout events to the ActionBarToggle
            drawerLayout.addDrawerListener(drawerToggle);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            navigationView = (NavigationView)findViewById(R.id.nvView);


            Menu m = navigationView.getMenu();
            for (int i=0;i<m.size();i++) {
                MenuItem mi = m.getItem(i);

                //for aapplying a font to subMenu ...
                SubMenu subMenu = mi.getSubMenu();
                if (subMenu!=null && subMenu.size() >0 ) {
                    //for (int j=0; j <subMenu.size();j++) {
                    //   MenuItem subMenuItem = subMenu.getItem(j);
                    //  applyFontToMenuItem(subMenuItem);
                    // }
                }

                //the method we have create in activity
                applyFontToMenuItem(mi);
            }

            android.view.View hView =  navigationView.getHeaderView(0);
            TextView nav_user = (TextView)hView.findViewById(R.id.nav_header_title);
            nav_user.setTypeface(custom_font);
            navigationView.setNavigationItemSelectedListener(this);
            ImageView nav_header_img = (ImageView)hView.findViewById(R.id.navheader_userimage);
            Glide.with(this).load("https://s3.amazonaws.com/bedunfamily/11891432_10207423110566616_8056001941608593653_o.jpg").into(nav_header_img);
        }
        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            switch (item.getItemId())
            {
                case R.id.nav_first_fragment:
                    ListItemClicked(0);
                    break;
                case R.id.nav_second_fragment:
                    ListItemClicked(1);
                    break;
                case R.id.nav_third_fragment:
                    ListItemClicked(2);
                    break;
                case R.id.nav_fourth_fragment:
                    ListItemClicked(3);
                    break;
                case R.id.nav_fith_fragment:
                    ListItemClicked(4);
                    break;
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        private void applyFontToMenuItem(MenuItem mi) {
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
            SpannableString mNewTitle = new SpannableString(mi.getTitle());
            mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mi.setTitle(mNewTitle);
        }


        private void ListItemClicked(int position)
        {
            android.support.v4.app.Fragment fragment = null;
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            switch (position)
            {
                case 0:
                    fragment = new UKFragment();
                    mTitle.setText(R.string.uk_frag);
                    break;
                case 1:
                    fragment = new OverseasFragment();
                    mTitle.setText(R.string.overseas_frag);
                    break;
                case 2:
                    fragment = new OnlineFragment();
                    mTitle.setText(R.string.online_frag);
                    break;
                case 3:
                    fragment = new PickupsFragment();
                    mTitle.setText(R.string.pickups_frag);
                    break;
                case 4:
                    fragment = new PickupTitleFragment();
                    mTitle.setText(R.string.profile_frag);
                    break;
            }

            getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item)     {
            if (drawerToggle.onOptionsItemSelected(item))
            {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        public static class UKFragment extends android.support.v4.app.Fragment implements View.OnClickListener
        {

            ImageView ukimg_1; ImageView ukimg_2; ImageView ukimg_3; ImageView ukimg_4; ImageView ukimg_5; ImageView ukimg_6;

            @Override
            public  View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
            {

                View view = inflater.inflate(R.layout.uk_fragment, viewGroup, false);

                ukimg_1 = (ImageView)view.findViewById(R.id.ukimg1);
                ukimg_2 = (ImageView)view.findViewById(R.id.ukimg2);
                ukimg_3 = (ImageView)view.findViewById(R.id.ukimg3);
                ukimg_4 = (ImageView)view.findViewById(R.id.ukimg4);
                ukimg_5 = (ImageView)view.findViewById(R.id.ukimg5);
                ukimg_6 = (ImageView)view.findViewById(R.id.ukimg6);

                Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/ukflag.png")
                .into(ukimg_1);
                Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/addevent.png")
                .into(ukimg_2);
                Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/list.png")
                .into(ukimg_3);
                Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/addcharity.png")
                .into(ukimg_4);
                Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/list2.png")
                .into(ukimg_5);
                Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/favourites.png")
                .into(ukimg_6);


                //pickup button
                ukimg_1.setOnClickListener(this);
                ukimg_4.setOnClickListener(this);
                return view;
            }

            @Override
            public void onClick(View v) {
                if(v == ukimg_4) {
                    android.support.v4.app.Fragment frag = new CharityTitleFragment();

                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                    ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    ft.replace(R.id.content_frame, frag);
                    ft.addToBackStack(null).commit();
                }
                if(v == ukimg_1) {
                    android.support.v4.app.Fragment frag = new Myfragment();

                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                    ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    ft.replace(R.id.content_frame, frag);
                    ft.addToBackStack(null).commit();
                }
            }
        }
        public static class OverseasFragment extends android.support.v4.app.Fragment implements View.OnClickListener
        {
            ImageView overseasimg_1; ImageView overseasimg_2; ImageView overseasimg_3; ImageView overseasimg_4;

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
            {
                View view = inflater.inflate(R.layout.overseas_fragment, viewGroup, false);
                overseasimg_1 = (ImageView)view.findViewById(R.id.overseasimg1);
                overseasimg_2 = (ImageView)view.findViewById(R.id.overseasimg2);
                overseasimg_3 = (ImageView)view.findViewById(R.id.overseasimg3);
                overseasimg_4 = (ImageView)view.findViewById(R.id.overseasimg4);

                Glide.with(this)
               .load("https://istandwithrefugees.blob.core.windows.net/resources/list2.png")
               .into(overseasimg_1);
                Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/addevent.png")
                .into(overseasimg_2);
                Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/update.png")
                .into(overseasimg_3);
                Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/favourites.png")
                .into(overseasimg_4);

                return view;
            }

            @Override
            public void onClick(View v) {

            }
        }
        public static class OnlineFragment extends android.support.v4.app.Fragment implements View.OnClickListener
        {
            ImageView onlineimg_1; ImageView onlineimg_2; ImageView onlineimg_3; ImageView onlineimg_4;

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
            {

                View view = inflater.inflate(R.layout.online_fragment, viewGroup, false);
                onlineimg_1 = (ImageView)view.findViewById(R.id.onlineimg1);
                onlineimg_2 = (ImageView)view.findViewById(R.id.onlineimg2);
                onlineimg_3 = (ImageView)view.findViewById(R.id.onlineimg3);
                onlineimg_4 = (ImageView)view.findViewById(R.id.onlineimg4);

                Glide.with(this)
               .load("https://istandwithrefugees.blob.core.windows.net/resources/petitions.png")
               .into(onlineimg_1);
                Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/list.png")
                .into(onlineimg_2);
                Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/addevent.png")
                .into(onlineimg_3);
                Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/favourites.png")
                .into(onlineimg_4);

                return view;
            }

            @Override
            public void onClick(View v) {

            }
        }

        public static class AddEventFragment extends android.support.v4.app.Fragment implements View.OnClickListener
        {
            ImageView mapimg; ImageView eventimg1; ImageView eventimg2; ImageView eventimg3;

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
            {

                View view = inflater.inflate(R.layout.addevent, viewGroup, false);
                eventimg1 = (ImageView)view.findViewById(R.id.button4);
                eventimg2 = (ImageView)view.findViewById(R.id.button5);
                eventimg2 = (ImageView)view.findViewById(R.id.editText4);
                mapimg = (ImageView)view.findViewById(R.id.addeventmapimg);
                Spinner spinner = (Spinner)view.findViewById(R.id.event_type_list);

                Glide.with(this)
              .load("https://istandwithrefugees.blob.core.windows.net/resources/datebutton.png")
              .into(eventimg1);
                Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/timebutton.png")
                .into(eventimg2);
                Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/descriptionbutton.png")
                .into(eventimg3);
                Glide.with(this)
               .load("https://istandwithrefugees.blob.core.windows.net/resources/map.png")
               .into(mapimg);
                return view;
            }

            @Override
            public void onClick(View v) {

            }
        }
    public static class CharityTitleFragment extends android.support.v4.app.Fragment implements View.OnClickListener
    { //set title
        String Title; ImageView charityimg; ImageView charityimg2;
        @Override
        public  View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
        {

            View view = inflater.inflate(R.layout.charitytitle, viewGroup, false);
                        
            charityimg = (ImageView)view.findViewById(R.id.back);
            charityimg2 = (ImageView)view.findViewById(R.id.forward);
            
            Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/back.png")
                .into(charityimg);
            Glide.with(this)
           .load("https://istandwithrefugees.blob.core.windows.net/resources/forward.png")
           .into(charityimg2);
            return view;
            
        }

        @Override
        public void onClick(View v) {
            if (v==charityimg2) {
                android.support.v4.app.Fragment frag = new CharityEmailFragment();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }

        }
    }

    public static class CharityEmailFragment extends android.support.v4.app.Fragment implements View.OnClickListener
    { //set email address
        String Title; ImageView charityimg; ImageView charityimg2; ImageView charityimg3;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
        {

            View view = inflater.inflate(R.layout.email, viewGroup, false);
            charityimg = (ImageView)view.findViewById(R.id.back);
            charityimg2 = (ImageView)view.findViewById(R.id.forward);

            Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/back.png")
                .into(charityimg);
            Glide.with(this)
           .load("https://istandwithrefugees.blob.core.windows.net/resources/forward.png")
           .into(charityimg2);
            return view;
        }

        @Override
        public void onClick(View v) {
        if (v == charityimg2){
            android.support.v4.app.Fragment frag = new CharityWebsiteFragment();

            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

            ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
            ft.replace(R.id.content_frame, frag);
            ft.addToBackStack(null).commit();
        }
        }
    }

    public static class CharityWebsiteFragment extends android.support.v4.app.Fragment implements View.OnClickListener
    {
        //set departure location
        String Title; ImageView charityimg; ImageView charityimg2; ImageView charityimg3;
        @Override
        public  View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
        {

            View view = inflater.inflate(R.layout.website, viewGroup, false);
            charityimg = (ImageView)view.findViewById(R.id.back);
            charityimg2 = (ImageView)view.findViewById(R.id.forward);

            Glide.with(this)
                .load("https://istandwithrefugees.blob.core.windows.net/resources/back.png")
                .into(charityimg);
            Glide.with(this)
           .load("https://istandwithrefugees.blob.core.windows.net/resources/forward.png")
           .into(charityimg2);


            return view;

        }

        @Override
        public void onClick(View v) {
            if (v == charityimg2){
                android.support.v4.app.Fragment frag = new CharityLogoFragment();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
        }
    }

    public static class CharityLogoFragment extends android.support.v4.app.Fragment implements View.OnClickListener
    { //set logo
        String Title; ImageView charityimg; ImageView charityimg2; ImageView charityimg3;
        @Override
        public  View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
        {

            View view = inflater.inflate(R.layout.logo, viewGroup, false);
            charityimg3 = (ImageView)view.findViewById(R.id.edittitlepickup);
            Glide.with(this)
           .load("https://istandwithrefugees.blob.core.windows.net/resources/inslogo.jpg")
           .into(charityimg3);
            charityimg = (ImageView)view.findViewById(R.id.back);
            charityimg2 = (ImageView)view.findViewById(R.id.forward);

            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/back.png")
                    .into(charityimg);
            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/forward.png")
                    .into(charityimg2);


            return view;

        }

        @Override
        public void onClick(View v) {
            if (v == charityimg2){
                android.support.v4.app.Fragment frag = new CharityMapFragment();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
        }
    }

    public static class CharityMapFragment extends android.support.v4.app.Fragment implements View.OnClickListener
    {
        //set Date
        String Title; ImageView charityimg; ImageView charityimg2; ImageView charityimg3;
        public @Override View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
        {

            View view = inflater.inflate(R.layout.charitymap, viewGroup, false);
            charityimg3 = (ImageView)view.findViewById(R.id.date);
            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/map.png")
                    .into(charityimg3);
            charityimg = (ImageView)view.findViewById(R.id.back);
            charityimg2 = (ImageView)view.findViewById(R.id.forward);

            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/back.png")
                    .into(charityimg);
            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/forward.png")
                    .into(charityimg2);


            return view;

        }

        @Override
        public void onClick(View v) {
            if (v == charityimg2){
                android.support.v4.app.Fragment frag = new CharityDescriptionFragment();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
        }

        }

       public static class CharityDescriptionFragment extends android.support.v4.app.Fragment implements View.OnClickListener
    {
       String Title; ImageView charityimg; ImageView charityimg2; ImageView charityimg3;
        public @Override View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
        {

            View view = inflater.inflate(R.layout.charitydescription, viewGroup, false);
            charityimg = (ImageView)view.findViewById(R.id.back);
            Glide.with(this)
    .load("https://istandwithrefugees.blob.core.windows.net/resources/back.png")
    .into(charityimg);
           return view;
        }


        @Override
        public void onClick(View v) {
            if (v == charityimg){
                android.support.v4.app.Fragment frag = new CharityMapFragment();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
        }
    }

    public static class PickupTitleFragment extends android.support.v4.app.Fragment implements View.OnClickListener
    { //set title
        String Title; ImageView pickupimg; ImageView pickupimg2;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
        {

            View view = inflater.inflate(R.layout.pickupstitle, viewGroup, false);
            pickupimg = (ImageView)view.findViewById(R.id.back);
            pickupimg2 = (ImageView)view.findViewById(R.id.forward);

            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/back.png")
                    .into(pickupimg);
            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/forward.png")
                    .into(pickupimg2);


            return view;

        }

        @Override
        public void onClick(View v) {
            if (v == pickupimg2){
                android.support.v4.app.Fragment frag = new PickupIconFragment();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
        }
    }

    public static class PickupIconFragment extends android.support.v4.app.Fragment implements View.OnClickListener
    { //set email address
        String Title; ImageView pickupimg; ImageView pickupimg2; ImageView pickupimg3;
        public @Override View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
        {

            View view = inflater.inflate(R.layout.email, viewGroup, false);
            pickupimg = (ImageView)view.findViewById(R.id.back);
            pickupimg2 = (ImageView)view.findViewById(R.id.forward);
            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/back.png")
                    .into(pickupimg);
            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/forward.png")
                    .into(pickupimg2);


            return view;

        }

        @Override
        public void onClick(View v) {
            if (v == pickupimg2){
                android.support.v4.app.Fragment frag = new PickupLocationFragment();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
        }

        }


    public static class PickupLocationFragment extends android.support.v4.app.Fragment implements View.OnClickListener
    {
        //set departure location
        String Title; ImageView pickupimg; ImageView pickupimg2; ImageView pickupimg3;
        public @Override View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
        {

            View view = inflater.inflate(R.layout.pickupsdeparture, viewGroup, false);
            pickupimg = (ImageView)view.findViewById(R.id.back);
            pickupimg2 = (ImageView)view.findViewById(R.id.forward);
            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/back.png")
                    .into(pickupimg);
            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/forward.png")
                    .into(pickupimg2);


            return view;

        }

        @Override
        public void onClick(View v) {
            if (v == pickupimg2){
                android.support.v4.app.Fragment frag = new PickupEndLocationFragment();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
        }
    }

    public static class PickupEndLocationFragment extends android.support.v4.app.Fragment implements View.OnClickListener
    { //set final location
        String Title; ImageView pickupimg; ImageView pickupimg2; ImageView pickupimg3;
        public @Override View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
        {

            View view = inflater.inflate(R.layout.pickupsdestination, viewGroup, false);
            pickupimg = (ImageView)view.findViewById(R.id.back);
            pickupimg2 = (ImageView)view.findViewById(R.id.forward);
            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/back.png")
                    .into(pickupimg);
            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/forward.png")
                    .into(pickupimg2);

            return view;

        }

        @Override
        public void onClick(View v) {
            if (v == pickupimg2){
                android.support.v4.app.Fragment frag = new PickupDateFragment();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
        }
    }

    public static class PickupDateFragment extends android.support.v4.app.Fragment implements View.OnClickListener
    {
        //set Date
        String Title; ImageView pickupimg; ImageView pickupimg2; ImageView pickupimg3; private  TextView textView;
        public @Override View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
        {

            View view = inflater.inflate(R.layout.pickupsdate, viewGroup, false);
            pickupimg = (ImageView)view.findViewById(R.id.back);
            pickupimg2 = (ImageView)view.findViewById(R.id.forward);
            pickupimg3 = (ImageView)view.findViewById(R.id.date);
            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/back.png")
                    .into(pickupimg);
            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/forward.png")
                    .into(pickupimg2);
            Glide.with(this)
          .load("https://istandwithrefugees.blob.core.windows.net/resources/date.png")
          .into(pickupimg3);
            textView = (TextView)view.findViewById(R.id.edittitlepickup);

            return view;
            };
        @Override
        public void onClick(View v) {
            if (v == pickupimg2){
                android.support.v4.app.Fragment frag = new PickupVehicleFragment();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
        }
}
    public static class PickupVehicleFragment extends android.support.v4.app.Fragment implements View.OnClickListener
    {
        //set vehicle details
        String Title; ImageView pickupimg; ImageView pickupimg2; ImageView pickupimg3;
        public @Override View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
        {

            View view = inflater.inflate(R.layout.pickupsvehicle, viewGroup, false);
            pickupimg2 = (ImageView)view.findViewById(R.id.forward);
            pickupimg3 = (ImageView)view.findViewById(R.id.date);
            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/back.png")
                    .into(pickupimg);
            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/forward.png")
                    .into(pickupimg2);
            Glide.with(this)
                    .load("https://istandwithrefugees.blob.core.windows.net/resources/date.png")
                    .into(pickupimg3);


            return view;

        }
        @Override
        public void onClick(View v) {
            if (v == pickupimg2){
                android.support.v4.app.Fragment frag = new PickupDescriptionFragment();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
        }
    }

    public static class PickupDescriptionFragment extends android.support.v4.app.Fragment implements View.OnClickListener
    {
        //set vehicle details
        String Title; ImageView pickupimg; ImageView pickupimg2; ImageView pickupimg3;
        public @Override View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
        {

            View view = inflater.inflate(R.layout.pickupsdescription, viewGroup, false);
            pickupimg = (ImageView)view.findViewById(R.id.back);

            Glide.with(this)
    .load("https://istandwithrefugees.blob.core.windows.net/resources/back.png")
    .into(pickupimg);
            return view;

        }
        @Override
        public void onClick(View v) {
            if (v == pickupimg2){
                android.support.v4.app.Fragment frag = new PickupEndLocationFragment();

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
        }
    }

    public static class ProfileFragment extends android.support.v4.app.Fragment implements View.OnClickListener
        {
            private Button mapButton;
            public @Override View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.profile_fragment, viewGroup, false);
                Button mapButton = (Button) view.findViewById(R.id.button1);
                return view;
            }
                @Override
            public void onClick(View v) {
                if (v == mapButton){
                    android.support.v4.app.Fragment frag = new Myfragment();

                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                    ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    ft.replace(R.id.content_frame, frag);
                    ft.addToBackStack(null).commit();
                }
            }
        }

        public static class PickupsFragment extends android.support.v4.app.Fragment implements View.OnClickListener
        {
            ImageView pickupsimg_1; ImageView pickupsimg_2;
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
            {
                View view = inflater.inflate(R.layout.aidpickups, viewGroup, false);
                pickupsimg_1 = (ImageView)view.findViewById(R.id.back);
                pickupsimg_2 = (ImageView)view.findViewById(R.id.forward);
                Glide.with(this)
                        .load("https://istandwithrefugees.blob.core.windows.net/resources/back.png")
                        .into(pickupsimg_1);
                Glide.with(this)
                        .load("https://istandwithrefugees.blob.core.windows.net/resources/forward.png")
                        .into(pickupsimg_2);

                return view;

            }

            @Override
            public void onClick(View v) {
                if (v == pickupsimg_1){
                    android.support.v4.app.Fragment frag = new PickupTitleFragment();

                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                    ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                    ft.replace(R.id.content_frame, frag);
                    ft.addToBackStack(null).commit();
                }
            }
        }


        public static class Myfragment extends android.support.v4.app.Fragment implements View.OnClickListener, OnMapReadyCallback {
            private View rootView;
            private GoogleMap googleMap;
            private HashMap<Integer, ArrayList<String>> latLongMap;
            static DBLatLongPoints dblatLong;

            @Override
            public  void onDetach()
            {
                super.onDetach();
            }

            @Override
            public void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                getLatLongs();
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
            {
                try
                {
                    rootView = inflater.inflate(R.layout.map_fragment, container, false);

                }
                catch (InflateException e)
                {
                    throw e;
                }
                return rootView;
            }
            @Override
            public void onViewCreated(View v, Bundle savedInstanceState){
                SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            }

            public void getLatLongs() {
                class GetMarkers extends AsyncTask<Object, Object, Object> {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Void doInBackground(Object... params) {
                        dblatLong = new DBLatLongPoints();
                        dblatLong.getLatLongArray("http://ec2-52-90-145-100.compute-1.amazonaws.com/get_all_latlongmarkers.php");
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        latLongMap = dblatLong.getLongLatMap();
                        //System.out.println(latLongMap.toString() + "test");
                        addLatLongsToMap();
                    }
                }
                GetMarkers gm = new GetMarkers();
                gm.execute();
            }

            private void addLatLongsToMap() {

            }

            @Override
            public void onClick(View v) {

            }

            @Override
            public void onMapReady(GoogleMap map) {
                googleMap = map;
                for(int i=0; i< latLongMap.size(); i++){
                    ArrayList<String> al = latLongMap.get(i+1);
                    //String lat = al.get(0);
                    Double lat = Double.parseDouble(al.get(0));
                    Double lon = Double.parseDouble(al.get(1));
                    //System.out.println(lat);
                    //System.out.println(lon);
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title("A Charity"));
                }
                LatLng sheffield = new LatLng(53.3811, -1.4701);
                googleMap.addMarker(new MarkerOptions().position(sheffield)
                        .title("AssistSheffield").snippet("2 Saville Street: S3 9LN"));
                float zoomLevel = 9; //This goes up to 21
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sheffield, zoomLevel));
            }
        }

    }




