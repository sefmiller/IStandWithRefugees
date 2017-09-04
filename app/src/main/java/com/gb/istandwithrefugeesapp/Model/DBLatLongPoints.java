package com.gb.istandwithrefugeesapp.Model;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hp on 03/09/2017.
 */

public class DBLatLongPoints {
    private HashMap<Integer, ArrayList<String>> longLatMap = new HashMap<>();

    public HashMap<Integer, ArrayList<String>> getLongLatMap() {
        return longLatMap;
    }

    public void setLongLatMap(HashMap<Integer, ArrayList<String>> longLatMap) {
        this.longLatMap = longLatMap;
    }

    String myJSONString;
    private JSONArray latLongArray = null;
    private static final String JSON_ARRAY ="result";
    private static final String ID = "LatLongId";
    private static final String Lat= "LatVal";
    private static final String Long = "LongVal";

    public String getMyJSONString() {
        return myJSONString;
    }

    public void setMyJSONString(String myJSONString) {
        this.myJSONString = myJSONString;
    }

    private static final String markerId = "MarkerId";


    public void getLatLongArray(String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    myJSONString = sb.toString().trim();

                    try {
                        JSONObject jsonObject = new JSONObject(myJSONString);
                        latLongArray = jsonObject.getJSONArray(JSON_ARRAY);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    return null;
                }
                try {
                    for (int i = 0; i < latLongArray.length(); i++) {
                        JSONObject jsonObject = latLongArray.getJSONObject(i);
                        String lat = jsonObject.getString(Lat);
                        String lon = jsonObject.getString(Long);
                        String markerID = jsonObject.getString(markerId);
                        String idString = jsonObject.getString(ID);
                        int id = Integer.parseInt(idString);
                        ArrayList<String> tempArray = new ArrayList<>();
                        tempArray.add(lat);
                        tempArray.add(lon);
                        tempArray.add(markerID);
                        longLatMap.put(id, tempArray);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return myJSONString;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //System.out.println(longLatMap.toString());
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);
    }

}
