package com.gb.istandwithrefugeesapp;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gb.istandwithrefugeesapp.Model.Charity;
import com.gb.istandwithrefugeesapp.Model.EUMarker;
import com.gb.istandwithrefugeesapp.Model.Fundraiser;
import com.gb.istandwithrefugeesapp.Model.LatLong;
import com.gb.istandwithrefugeesapp.Model.UkMarker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Custom Dialog for displaying list Views in custom dialog layout xmls rather then standard android spinner layout
 */
class CustomDialogEUMap extends Dialog implements View.OnClickListener {

    CheckBox genCheck;
    CheckBox womCheck;
    CheckBox empCheck;
    CheckBox medCheck;
    CheckBox lgbtqiaCheck;
    CheckBox foodCheck;
    CheckBox actCheck;
    CheckBox accCheck;
    CheckBox legCheck;
    CheckBox techCheck;
    CheckBox creativeCheck;
    CheckBox socialCheck;
    CheckBox faithCheck;
    CheckBox garCheck;
    TextView countryText;

    private SparseArray<LatLong> filterLatLongsList = new SparseArray<>();



    MainActivity anActivity;
    private final MainActivity.EUMapFragment overseasFragment;

    private final SparseArray<EUMarker> filterOrgsList;
    private SparseArray<EUMarker> filterAllList = new SparseArray<>();
    private SparseArray<ArrayList<Boolean>> regionsSelected = new SparseArray<>();


    Button filterMapButton;
    private ImageView dropArrow;



    public CustomDialogEUMap(Activity a, MainActivity.EUMapFragment euMapFragment) {
        super(a);
        anActivity = (MainActivity) a;
        this.overseasFragment = euMapFragment;
        filterOrgsList = anActivity.getDbHelper().getOverseasMarkersMap();
        // TODO Auto-generated constructor stub
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_eu_map_filter);
        TextView tx = findViewById(R.id.country_title);
        TextView tx3 = findViewById(R.id.type_title);
        TextView tx4 = findViewById(R.id.dialog_title);
        Typeface titleFont = Typeface.
                createFromAsset(getContext().getAssets(), "fonts/Pacifico.ttf");
        tx.setTypeface(titleFont);
        tx3.setTypeface(titleFont);
        tx4.setTypeface(titleFont);

        genCheck = findViewById(R.id.gen_check);
        womCheck = findViewById(R.id.wom_check);
        empCheck = findViewById(R.id.emp_check);
        medCheck = findViewById(R.id.med_check);
        lgbtqiaCheck = findViewById(R.id.lgbtqia_check);
        foodCheck = findViewById(R.id.food_check);
        actCheck = findViewById(R.id.act_check);
        accCheck = findViewById(R.id.acc_check2);
        legCheck = findViewById(R.id.leg_check);
        techCheck = findViewById(R.id.tech_check);
        creativeCheck = findViewById(R.id.creative_check);
        socialCheck = findViewById(R.id.social_check);
        faithCheck = findViewById(R.id.faith_check);
        garCheck = findViewById(R.id.gar_check);
        dropArrow = findViewById(R.id.countries_drop);
        countryText = findViewById(R.id.country_text);

        regionsSelected = overseasFragment.getRegionsSelected();
        Glide.with(anActivity)
                .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/dropdown_arrow.png")
                .into(dropArrow);

        countryText.setOnClickListener(this);
        genCheck.setOnClickListener(this);
        womCheck.setOnClickListener(this);
        empCheck.setOnClickListener(this);
        medCheck.setOnClickListener(this);
        lgbtqiaCheck.setOnClickListener(this);
        foodCheck.setOnClickListener(this);
        actCheck.setOnClickListener(this);
        accCheck.setOnClickListener(this);
        legCheck.setOnClickListener(this);
        techCheck.setOnClickListener(this);
        creativeCheck.setOnClickListener(this);
        socialCheck.setOnClickListener(this);
        faithCheck.setOnClickListener(this);
        garCheck.setOnClickListener(this);
        filterMapButton = findViewById(R.id.filter_button);
        filterMapButton.setOnClickListener(this);

        setupTypeCheckBoxes();
    }

    public void setupTypeCheckBoxes() {
        if (isTechCheckBoxSelected()) {
            techCheck.setChecked(true);
        } else {
            techCheck.setChecked(false);
        }
        if (isGardenCheckBoxSelected()) {
            garCheck.setChecked(true);
        } else {
            garCheck.setChecked(false);
        }
        if (isActivismCheckBoxSelected()) {
            actCheck.setChecked(true);
        } else {
            actCheck.setChecked(false);
        }
        if (isHousingCheckBoxSelected()) {
            accCheck.setChecked(true);
        } else {
            accCheck.setChecked(false);
        }
        if (isSocialWorkCheckBoxSelected()) {
            socialCheck.setChecked(true);
        } else {
            socialCheck.setChecked(false);
        }
        if (isFoodCheckBoxSelected()) {
            foodCheck.setChecked(true);
        } else {
            foodCheck.setChecked(false);
        }
        if (isCreativeCheckBoxSelected()) {
            creativeCheck.setChecked(true);
        } else {
            creativeCheck.setChecked(false);
        }
        if (isLgbtqiaCheckBoxSelected()) {
            lgbtqiaCheck.setChecked(true);
        } else {
            lgbtqiaCheck.setChecked(false);
        }
        if (isMedicalCheckBoxSelected()) {
            medCheck.setChecked(true);
        } else {
            medCheck.setChecked(false);
        }
        if (isLegalCheckBoxSelected()) {
            legCheck.setChecked(true);
        } else {
            legCheck.setChecked(false);
        }
        if (isWomenCheckBoxSelected()) {
            womCheck.setChecked(true);
        } else {
            womCheck.setChecked(false);
        }
        if (isEducationCheckBoxSelected()) {
            empCheck.setChecked(true);
        } else {
            empCheck.setChecked(false);
        }
        if (isGeneralCheckBoxSelected()) {
            genCheck.setChecked(true);
        } else {
            genCheck.setChecked(false);
        }
        if (isFaithCheckBoxSelected()) {
            faithCheck.setChecked(true);
        } else {
            faithCheck.setChecked(false);
        }
    }


    public boolean isTechCheckBoxSelected() {
        Boolean selected = anActivity.getSelectedTypeOfAid().contains("Technology / IT");
        return selected;
    }

    public boolean isGardenCheckBoxSelected() {
        Boolean selected = anActivity.getSelectedTypeOfAid().contains("Gardening / Permaculture");
        return selected;
    }

    public boolean isHousingCheckBoxSelected() {
        Boolean selected = anActivity.getSelectedTypeOfAid().contains("Accomodation / Housing Support");
        return selected;
    }

    public boolean isActivismCheckBoxSelected() {
        Boolean selected = anActivity.getSelectedTypeOfAid().contains("Activism / Awareness");
        return selected;
    }

    public boolean isSocialWorkCheckBoxSelected() {
        Boolean selected = anActivity.getSelectedTypeOfAid().contains("Social work / Mental health");
        return selected;
    }

    public boolean isFoodCheckBoxSelected() {
        Boolean selected = anActivity.getSelectedTypeOfAid().contains("Food");
        return selected;
    }

    public boolean isCreativeCheckBoxSelected() {
        Boolean selected = anActivity.getSelectedTypeOfAid().contains(anActivity.getString(R.string.creative));
        return selected;
    }

    public boolean isLgbtqiaCheckBoxSelected() {
        Boolean selected = anActivity.getSelectedTypeOfAid().contains("Lgbtqia");
        return selected;
    }

    public boolean isMedicalCheckBoxSelected() {
        Boolean selected = anActivity.getSelectedTypeOfAid().contains(anActivity.getString(R.string.medical));
        return selected;
    }

    public boolean isLegalCheckBoxSelected() {
        Boolean selected = anActivity.getSelectedTypeOfAid().contains("Legal / Provision of Information");
        return selected;
    }

    public boolean isEducationCheckBoxSelected() {
        Boolean selected = anActivity.getSelectedTypeOfAid().contains(anActivity.getString(R.string.education));
        return selected;
    }

    public boolean isWomenCheckBoxSelected() {
        Boolean selected = anActivity.getSelectedTypeOfAid().contains("Women and/or Children");
        return selected;
    }

    public boolean isGeneralCheckBoxSelected() {
        Boolean selected = anActivity.getSelectedTypeOfAid().contains("General or Multifaceted Support");
        return selected;
    }

    public boolean isFaithCheckBoxSelected() {
        Boolean selected = anActivity.getSelectedTypeOfAid().contains("Faith-Based Organisation");
        return selected;
    }




    @Override
    public void onClick(View view) {
        //switch between checkbox views. set getregions / gettypeofAid true / false filter non cancelable
        switch (view.getId()) {
            case R.id.gen_check:
                if (isGeneralCheckBoxSelected()) {
                    anActivity.getSelectedTypeOfAid().remove("General or Multifaceted Support");

                } else {
                    anActivity.getSelectedTypeOfAid().add("General or Multifaceted Support");
                }
                break;

            case R.id.wom_check:
                if (isWomenCheckBoxSelected()) {
                    anActivity.getSelectedTypeOfAid().remove("Women and/or Children");
                } else {
                    anActivity.getSelectedTypeOfAid().add("Women and/or Children");

                }
                break;

            case R.id.emp_check:
                if (isEducationCheckBoxSelected()) {
                    anActivity.getSelectedTypeOfAid().remove(anActivity.getString(R.string.education));
                } else {
                    anActivity.getSelectedTypeOfAid().add(anActivity.getString(R.string.education));

                }
                break;

            case R.id.leg_check:
                if (isLegalCheckBoxSelected()) {
                    anActivity.getSelectedTypeOfAid().remove("Legal / Provision of Information");
                } else {
                    anActivity.getSelectedTypeOfAid().add("Legal / Provision of Information");

                }
                break;

            case R.id.med_check:
                if (isMedicalCheckBoxSelected()) {
                    anActivity.getSelectedTypeOfAid().remove(anActivity.getString(R.string.medical));
                } else {
                    anActivity.getSelectedTypeOfAid().add(anActivity.getString(R.string.medical));

                }
                break;

            case R.id.tech_check:
                if (isTechCheckBoxSelected()) {
                    anActivity.getSelectedTypeOfAid().remove("Technology / IT");
                } else {
                    anActivity.getSelectedTypeOfAid().add("Technology / IT");

                }
                break;

            case R.id.lgbtqia_check:
                if (isLgbtqiaCheckBoxSelected()) {
                    anActivity.getSelectedTypeOfAid().remove("Lgbtqia");
                } else {
                    anActivity.getSelectedTypeOfAid().add("Lgbtqia");

                }
                break;

            case R.id.food_check:
                if (isFoodCheckBoxSelected()) {
                    anActivity.getSelectedTypeOfAid().remove("Food");
                } else {
                    anActivity.getSelectedTypeOfAid().add("Food");

                }
                break;

            case R.id.creative_check:
                if (isCreativeCheckBoxSelected()) {
                    anActivity.getSelectedTypeOfAid().remove(anActivity.getString(R.string.creative));
                } else {
                    anActivity.getSelectedTypeOfAid().add(anActivity.getString(R.string.creative));

                }
                break;

            case R.id.social_check:
                if (isSocialWorkCheckBoxSelected()) {
                    anActivity.getSelectedTypeOfAid().remove("Social work / Mental health");
                } else {
                    anActivity.getSelectedTypeOfAid().add("Social work / Mental health");

                }
                break;

            case R.id.act_check:
                if (isActivismCheckBoxSelected()) {
                    anActivity.getSelectedTypeOfAid().remove("Activism / Awareness");
                } else {
                    anActivity.getSelectedTypeOfAid().add("Activism / Awareness");
                }
                break;

            case R.id.acc_check2:
                if (isHousingCheckBoxSelected()) {
                    anActivity.getSelectedTypeOfAid().remove("Accomodation / Housing Support");
                } else {
                    anActivity.getSelectedTypeOfAid().add("Accomodation / Housing Support");
                }
                break;

            case R.id.faith_check:
                if (isFaithCheckBoxSelected()) {
                    anActivity.getSelectedTypeOfAid().remove("Faith-Based Organisation");
                } else {
                    anActivity.getSelectedTypeOfAid().add("Faith-Based Organisation");
                }
                break;

            case R.id.gar_check:
                if (isGardenCheckBoxSelected()) {
                    anActivity.getSelectedTypeOfAid().remove("Gardening / Permaculture");
                } else {
                    anActivity.getSelectedTypeOfAid().add("Gardening / Permaculture");
                }
                break;
            case R.id.filter_button:
                filterMapMarkers();
                break;
        }
        if (view == countryText || view == dropArrow) {
            final ArrayList<String> expandableListTitle = new ArrayList<>(anActivity.getDbHelper().getCountryAndRegionsArray().keySet());
            final LinkedHashMap<String, ArrayList<String>> expandableListChildren = anActivity.getDbHelper().getCountryAndRegionsArray();
            final CustomDialogExpandableListView cdd = new CustomDialogExpandableListView(anActivity);
            cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            cdd.show();
            cdd.setCancelable(false);
            Window v = cdd.getWindow();
            ExpandableListView expandableListView = v.findViewById(R.id.dialog_listview);
            final CustomExpandableListAdapter customExpandableListAdapter = new CustomExpandableListAdapter(expandableListTitle, expandableListChildren, regionsSelected);
            expandableListView.setAdapter(customExpandableListAdapter);
            customExpandableListAdapter.setChoiceMode(customExpandableListAdapter.CHOICE_MODE_MULTIPLE);
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    customExpandableListAdapter.setClicked(groupPosition, childPosition);
                    return false;
                }
            });
            Button button = v.findViewById(R.id.next_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    regionsSelected = customExpandableListAdapter.getRegionsSelected();
                    ArrayList tempCountries = new ArrayList();
                    if (regionsSelected.get(0).contains(true)){
                        tempCountries.add("Bosnia");
                    }
                    if (regionsSelected.get(1).contains(true)){
                        tempCountries.add("Serbia");
                    }
                    if (regionsSelected.get(2).contains(true)){
                        tempCountries.add("Greece");
                    }
                    if (regionsSelected.get(3).contains(true)){
                        tempCountries.add("Belgium");
                    }
                    if (regionsSelected.get(4).contains(true)){
                        tempCountries.add("Macedonia");
                    }
                    if (regionsSelected.get(5).contains(true)){
                        tempCountries.add("Italy");
                    }
                    if (regionsSelected.get(6).contains(true)){
                        tempCountries.add("France");
                    }
                    anActivity.setSelectedCountries(tempCountries);

                    if (anActivity.getSelectedCountries().size() > 1) {
                        countryText.setText("Multiple");
                        cdd.dismiss();
                    } else if (anActivity.getSelectedCountries().size() == 0) {
                        Toast.makeText(anActivity, "Please select at least one Region",
                                Toast.LENGTH_LONG).show();
                    } else {
                        countryText.setText(anActivity.getSelectedCountries().get(0));
                        cdd.dismiss();
                    }
                }
            });
        }
    }

    private void filterMapMarkers() {
        if (regionsSelected!= null && regionsSelected.size() > 0) {
            SparseArray<EUMarker> tempMap = new SparseArray<>();
            SparseArray<LatLong> latLongSparseArray = new SparseArray<>();
            LinkedHashMap<String, ArrayList<String>> selectedCountriesandRegions = anActivity.getDbHelper().getCountryAndRegionsArray();
            filterAllList = filterOrgsList;
            filterLatLongsList = anActivity.getDbHelper().getLongLatMapEU();
            int indexKey = 1;
            for (int i = 0; i < filterAllList.size(); i++) {
                EUMarker euMarker = filterAllList.get(i + 1);
                String country = euMarker.getCountry();
                String region = euMarker.getRegion();
                if (region.length() <= 1){
                    region = "Not Specified";
                }
                int regionIndex = selectedCountriesandRegions.get(country).indexOf(region);
                int countryIndex = 0;
                switch (country){
                    case "Bosnia":
                        countryIndex = 0;
                        break;
                    case "Serbia":
                        countryIndex = 1;
                        break;
                    case "Greece":
                        countryIndex = 2;
                        break;
                    case "Belgium":
                        countryIndex = 3;
                        break;
                    case "Macedonia":
                        countryIndex = 4;
                        break;
                    case "Italy":
                        countryIndex = 5;
                        break;
                    case "France":
                        countryIndex = 6;
                        break;
                }
                if (regionsSelected.get(countryIndex).get(regionIndex) != false) {
                    tempMap.put(indexKey, filterAllList.get(i + 1));
                    LatLong latLong = filterLatLongsList.get(i + 1);
                    latLongSparseArray.put(indexKey, latLong);
                    indexKey = indexKey + 1;
                }
            }
            filterAllList = tempMap;
            filterLatLongsList = latLongSparseArray;
            if (anActivity.getSelectedTypeOfAid() != null && anActivity.getSelectedTypeOfAid().size() > 0) {
                EUMarker euMarker;
                tempMap = new SparseArray<>();
                latLongSparseArray = new SparseArray<>();
                indexKey = 1;
                for (int i = 0; i < filterAllList.size(); i++) {
                    euMarker = filterAllList.get(i + 1);
                    String typeOfAid = euMarker.getTypeOfAid();
                    if (anActivity.getSelectedTypeOfAid().contains(typeOfAid)) {
                        tempMap.put(indexKey, filterAllList.get(i + 1));
                        LatLong latLong = filterLatLongsList.get(i + 1);
                        latLongSparseArray.put(indexKey, latLong);
                        indexKey = indexKey + 1;
                    }
                }
                filterAllList = tempMap;
                filterLatLongsList = latLongSparseArray;
                overseasFragment.setLatLongMap(filterLatLongsList);
                overseasFragment.onMapReady(overseasFragment.getGoogleMap());
                anActivity.getCustomDialogEUMap().dismiss();

            } else {
                Toast.makeText(anActivity, "Please select at least one Category",
                        Toast.LENGTH_LONG).show();
            }
        }
            else{
            Toast.makeText(anActivity,"Please select at least one Region",
                    Toast.LENGTH_LONG).show();
        }
        }

    }
