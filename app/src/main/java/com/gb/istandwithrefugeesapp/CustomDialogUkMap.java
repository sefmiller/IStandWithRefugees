package com.gb.istandwithrefugeesapp;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.gb.istandwithrefugeesapp.Model.Charity;
import com.gb.istandwithrefugeesapp.Model.Fundraiser;
import com.gb.istandwithrefugeesapp.Model.LatLong;
import com.gb.istandwithrefugeesapp.Model.UkMarker;

import java.util.HashMap;


/**
 * Custom Dialog for displaying list Views in custom dialog layout xmls rather then standard android spinner layout
 */
class CustomDialogUkMap extends Dialog implements View.OnClickListener {

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

    private SparseArray<LatLong> filterLatLongsList = new SparseArray<>();

    CheckBox scotlandCheck;
    CheckBox yorksCheck;
    CheckBox gLondonCheck;
    CheckBox walesCheck;
    CheckBox sEastCheck;
    CheckBox sWestCheck;
    CheckBox nIrelandCheck;
    CheckBox eMidlandsCheck;
    CheckBox wMidlandsCheck;
    CheckBox eAngliaCheck;
    CheckBox nEastCheck;
    CheckBox nWestCheck;
    CheckBox rIrelandCheck;

    MainActivity anActivity;
    private final MainActivity.UkMapFragment ukMapFragment;

    private final SparseArray<HashMap<String, UkMarker>> filterOrgsList;
    private SparseArray<HashMap<String, UkMarker>> filterAllList = new SparseArray<>();


    Button filterMapButton;


    public CustomDialogUkMap(Activity a, MainActivity.UkMapFragment ukMapFragment) {
        super(a);
        anActivity = (MainActivity) a;
        this.ukMapFragment = ukMapFragment;
        filterOrgsList = anActivity.getDbHelper().getMarkersMap();
        // TODO Auto-generated constructor stub
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_uk_map_filter);
        TextView tx = findViewById(R.id.region_title);
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

        scotlandCheck = findViewById(R.id.scotland);
        yorksCheck = findViewById(R.id.yorks);
        gLondonCheck = findViewById(R.id.g_london);
        walesCheck = findViewById(R.id.wales);
        sEastCheck = findViewById(R.id.s_east);
        sWestCheck = findViewById(R.id.s_west);
        nIrelandCheck = findViewById(R.id.n_ireland);
        eMidlandsCheck = findViewById(R.id.e_midlands);
        wMidlandsCheck = findViewById(R.id.w_midlands);
        eAngliaCheck = findViewById(R.id.e_anglia);
        nEastCheck = findViewById(R.id.n_east);
        nWestCheck = findViewById(R.id.n_west);
        rIrelandCheck = findViewById(R.id.rep_ireland);
        scotlandCheck.setOnClickListener(this);
        yorksCheck.setOnClickListener(this);
        gLondonCheck.setOnClickListener(this);
        walesCheck.setOnClickListener(this);
        sEastCheck.setOnClickListener(this);
        sWestCheck.setOnClickListener(this);
        nIrelandCheck.setOnClickListener(this);
        eMidlandsCheck.setOnClickListener(this);
        wMidlandsCheck.setOnClickListener(this);
        eAngliaCheck.setOnClickListener(this);
        nEastCheck.setOnClickListener(this);
        nWestCheck.setOnClickListener(this);
        rIrelandCheck.setOnClickListener(this);


        setupTypeCheckBoxes();
        setupRegionCheckBoxes();
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

    public void setupRegionCheckBoxes() {

        if (isScotlandCheckBoxSelected()) {
            scotlandCheck.setChecked(true);
        } else {
            scotlandCheck.setChecked(false);
        }

        if (isNorthEastCheckBoxSelected()) {
            nEastCheck.setChecked(true);
        } else {
            nEastCheck.setChecked(false);
        }

        if (isNorthWestCheckBoxSelected()) {
            nWestCheck.setChecked(true);
        } else {
            nWestCheck.setChecked(false);
        }

        if (isYorkshireCheckBoxSelected()) {
            yorksCheck.setChecked(true);
        } else {
            yorksCheck.setChecked(false);
        }

        if (isEastMidlandsCheckBoxSelected()) {
            eMidlandsCheck.setChecked(true);
        } else {
            eMidlandsCheck.setChecked(false);
        }

        if (isWestMidlandsCheckBoxSelected()) {
            wMidlandsCheck.setChecked(true);
        } else {
            wMidlandsCheck.setChecked(false);
        }

        if (isEastAngliaCheckBoxSelected()) {
            eAngliaCheck.setChecked(true);
        } else {
            eAngliaCheck.setChecked(false);
        }

        if (isGreaterLondonCheckBoxSelected()) {
            gLondonCheck.setChecked(true);
        } else {
            gLondonCheck.setChecked(false);
        }

        if (isSouthEastCheckBoxSelected()) {
            sEastCheck.setChecked(true);
        } else {
            sEastCheck.setChecked(false);
        }

        if (isSouthWestCheckBoxSelected()) {
            sWestCheck.setChecked(true);
        } else {
            sWestCheck.setChecked(false);
        }

        if (isWalesCheckBoxSelected()) {
            walesCheck.setChecked(true);
        } else {
            walesCheck.setChecked(false);
        }

        if (isNorthernIrelandCheckBoxSelected()) {
            nIrelandCheck.setChecked(true);
        } else {
            nIrelandCheck.setChecked(false);
        }

        if (isRepIrelandCheckBoxSelected()) {
            rIrelandCheck.setChecked(true);
        } else {
            rIrelandCheck.setChecked(false);
        }
    }

    public boolean isScotlandCheckBoxSelected() {
        Boolean selected = anActivity.getRegions().contains("Scotland");
        return selected;
    }

    public boolean isNorthEastCheckBoxSelected() {
        Boolean selected = anActivity.getRegions().contains("North East");
        return selected;
    }

    public boolean isNorthWestCheckBoxSelected() {
        Boolean selected = anActivity.getRegions().contains("North West");
        return selected;
    }

    public boolean isYorkshireCheckBoxSelected() {
        Boolean selected = anActivity.getRegions().contains("Yorkshire and the Humber");
        return selected;
    }

    public boolean isEastMidlandsCheckBoxSelected() {
        Boolean selected = anActivity.getRegions().contains("East Midlands");
        return selected;
    }

    public boolean isWestMidlandsCheckBoxSelected() {
        Boolean selected = anActivity.getRegions().contains("West Midlands");
        return selected;
    }

    public boolean isEastAngliaCheckBoxSelected() {
        Boolean selected = anActivity.getRegions().contains("East Anglia");
        return selected;
    }

    public boolean isGreaterLondonCheckBoxSelected() {
        Boolean selected = anActivity.getRegions().contains("Greater London");
        return selected;
    }

    public boolean isSouthEastCheckBoxSelected() {
        Boolean selected = anActivity.getRegions().contains("South East");
        return selected;
    }

    public boolean isSouthWestCheckBoxSelected() {
        Boolean selected = anActivity.getRegions().contains("South West");
        return selected;
    }

    public boolean isWalesCheckBoxSelected() {
        Boolean selected = anActivity.getRegions().contains("Wales");
        return selected;
    }

    public boolean isNorthernIrelandCheckBoxSelected() {
        Boolean selected = anActivity.getRegions().contains("Northern Ireland");
        return selected;
    }

    public boolean isRepIrelandCheckBoxSelected() {
        Boolean selected = anActivity.getRegions().contains("Republic of Ireland");
        return selected;
    }


    @Override
    public void onClick(View view) {
        System.out.println("clicked");
        //switch between checkbox views. set getregions / gettypeofAid true / false filter non cancelable
        switch (view.getId()) {
            case R.id.gen_check:
                if (isGeneralCheckBoxSelected()) {
                    anActivity.getSelectedTypeOfAid().remove("General or Multifaceted Support");
                    System.out.println("clicked gen");

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

            case R.id.scotland:
                if (isScotlandCheckBoxSelected()) {
                    anActivity.getRegions().remove("Scotland");
                } else {
                    anActivity.getRegions().add("Scotland");
                }
                break;

            case R.id.n_east:
                if (isNorthEastCheckBoxSelected()) {
                    anActivity.getRegions().remove("North East");
                } else {
                    anActivity.getRegions().add("North East");
                }
                break;

            case R.id.n_west:
                if (isNorthWestCheckBoxSelected()) {
                    anActivity.getRegions().remove("North West");
                } else {
                    anActivity.getRegions().add("North West");
                }
                break;

            case R.id.s_west:
                if (isSouthWestCheckBoxSelected()) {
                    anActivity.getRegions().remove("South West");
                } else {
                    anActivity.getRegions().add("South West");
                }
                break;

            case R.id.s_east:
                if (isSouthEastCheckBoxSelected()) {
                    anActivity.getRegions().remove("South East");
                } else {
                    anActivity.getRegions().add("South East");
                }
                break;

            case R.id.wales:
                if (isWalesCheckBoxSelected()) {
                    anActivity.getRegions().remove("Wales");
                } else {
                    anActivity.getRegions().add("Wales");
                }
                break;

            case R.id.n_ireland:
                if (isNorthernIrelandCheckBoxSelected()) {
                    anActivity.getRegions().remove("Northern Ireland");
                } else {
                    anActivity.getRegions().add("Northern Ireland");
                }
                break;

            case R.id.rep_ireland:
                if (isRepIrelandCheckBoxSelected()) {
                    anActivity.getRegions().remove("Republic of Ireland");
                } else {
                    anActivity.getRegions().add("Republic of Ireland");
                }
                break;

            case R.id.yorks:
                if (isYorkshireCheckBoxSelected()) {
                    anActivity.getRegions().remove("Yorkshire and the Humber");
                } else {
                    anActivity.getRegions().add("Yorkshire and the Humber");
                }
                break;

            case R.id.e_midlands:
                if (isEastMidlandsCheckBoxSelected()) {
                    anActivity.getRegions().remove("East Midlands");
                } else {
                    anActivity.getRegions().add("East Midlands");
                }
                break;

            case R.id.w_midlands:
                if (isWestMidlandsCheckBoxSelected()) {
                    anActivity.getRegions().remove("West Midlands");
                } else {
                    anActivity.getRegions().add("West Midlands");
                }
                break;

            case R.id.g_london:
                if (isGreaterLondonCheckBoxSelected()) {
                    anActivity.getRegions().remove("Greater London");
                } else {
                    anActivity.getRegions().add("Greater London");
                }
                break;

            case R.id.e_anglia:
                if (isEastAngliaCheckBoxSelected()) {
                    anActivity.getRegions().remove("East Anglia");
                } else {
                    anActivity.getRegions().add("East Anglia");
                }
                break;

            case R.id.filter_button:
                System.out.println("Here");
                filterMapMarkers();
                break;

        }
    }

    private void filterMapMarkers() {
        if (anActivity.getRegions() != null && anActivity.getRegions().size() > 0) {
            SparseArray<HashMap<String, UkMarker>> tempMap = new SparseArray<>();
            HashMap<String, UkMarker> tempMarkerMap;
            SparseArray<LatLong> latLongSparseArray = new SparseArray<>();
            filterAllList = filterOrgsList;
            filterLatLongsList = anActivity.getDbHelper().getLongLatMap();
            int indexKey = 1;
            for (int i = 0; i < filterAllList.size(); i++) {
                tempMarkerMap = filterAllList.get(i + 1);
                if (tempMarkerMap.containsKey("Organisation")) {
                    Charity charity = (Charity) tempMarkerMap.get("Organisation");
                    String name = charity.getRegion();
                    if (anActivity.getRegions().contains(name)) {
                        tempMap.put(indexKey, filterAllList.get(i + 1));
                        LatLong latLong = filterLatLongsList.get(i + 1);
                        latLongSparseArray.put(indexKey, latLong);
                        indexKey = indexKey + 1;
                    }
                } else {
                    Fundraiser fun = (Fundraiser) tempMarkerMap.get("Fundraiser");
                    String name = fun.getRegion();
                    if (anActivity.getRegions().contains(name)) {
                        tempMap.put(indexKey, filterAllList.get(i + 1));
                        LatLong latLong = filterLatLongsList.get(i + 1);
                        latLongSparseArray.put(indexKey, latLong);
                        indexKey = indexKey + 1;
                    }
                }
            }
            filterAllList = tempMap;
            filterLatLongsList = latLongSparseArray;
            if (anActivity.getSelectedTypeOfAid() != null && anActivity.getSelectedTypeOfAid().size() > 0) {
                tempMap = new SparseArray<>();
                latLongSparseArray = new SparseArray<>();
                indexKey = 1;
                for (int i = 0; i < filterAllList.size(); i++) {
                    tempMarkerMap = filterAllList.get(i + 1);
                    if (tempMarkerMap.containsKey("Organisation")) {
                        Charity charity = (Charity) tempMarkerMap.get("Organisation");
                        String typeOfAid = charity.getTypeOfAid();
                        if (anActivity.getSelectedTypeOfAid().contains(typeOfAid)) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            indexKey = indexKey + 1;
                        }
                    } else {
                        Fundraiser fun = (Fundraiser) tempMarkerMap.get("Fundraiser");
                        String typeOfAid = fun.getTypeOfAid();
                        if (anActivity.getSelectedTypeOfAid().contains(typeOfAid)) {
                            tempMap.put(indexKey, filterAllList.get(i + 1));
                            LatLong latLong = filterLatLongsList.get(i + 1);
                            latLongSparseArray.put(indexKey, latLong);
                            indexKey = indexKey + 1;
                        }
                    }
                }
                filterAllList = tempMap;
                filterLatLongsList = latLongSparseArray;
                System.out.println(filterLatLongsList.size());
                ukMapFragment.setLatLongMap(filterLatLongsList);
                ukMapFragment.onMapReady(ukMapFragment.getGoogleMap());
                anActivity.getCustomDialogUkMap().dismiss();
            }
            else{
                Toast.makeText(anActivity,"Please select at least one Category",
                        Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(anActivity,"Please select at least one Region",
                    Toast.LENGTH_LONG).show();
        }

    }
}