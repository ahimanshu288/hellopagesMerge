package com.knowlarity.hellopages;


import com.knowlarity.adapter.ManualSearchedAdapter;
import com.knowlarity.helpers.ConnectionCheck;
import com.knowlarity.webservice.HttpConnection;
import com.knowlarity.webservice.WebServices;
import android.os.AsyncTask;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import models.GoogleSearchedApiResults;

public class ManualFindLocation extends Activity implements TextWatcher {
    ListView listView_places;
    EditText editText_search;
    TextView search_icon;
    ManualSearchedAdapter adapter;
    ArrayList<GoogleSearchedApiResults> arrgooglesearch;
    AsyncTask async;
    ConnectionCheck cnschk;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_find_location);
        arrgooglesearch = new ArrayList<GoogleSearchedApiResults>();
        cnschk=new ConnectionCheck(this);
        initialiseComponents();
    }

    public void initialiseComponents() {
        listView_places = (ListView) findViewById(R.id.listView_places);
        editText_search = (EditText) findViewById(R.id.editText_search);
        search_icon= (TextView) findViewById(R.id.search_icon);
        editText_search.addTextChangedListener(this);
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowSearchPlaces();
            }
        });
    }
    public void ShowLocation(String message) {
        Toast.makeText(ManualFindLocation.this, "" + message, Toast.LENGTH_LONG)
                .show();
    }
    public void ShowSearchPlaces() {
    if(editText_search.getText()!=null && !editText_search.getText().toString().equals("")) {
        String chardata=editText_search.getEditableText().toString();
        System.out.println("chardata"+chardata);
        if(chardata.length()>3 && !chardata.endsWith(" "))
        {



    String webservice = WebServices.map_search + "?query="
            + editText_search.getText().toString().replace(" ","%20")
            + "&key=AIzaSyA8Suu08_bYSdH7x2Z9oamdqCkUjjJYzI0&sensor=false";
       /* if(async!=null ) {
            async.cancel(true);
            async=null;*/
        if(cnschk.isConnected()) {
            sendshowdata(webservice);
        }
        else
        {
            ShowLocation(getString(R.string.Not_connected));
        }
    //    }

        }
        else
        {

        }
    }
    }

    public void ShowSearchedData(String searcheddata) {
        try {

            JSONObject jobjdata = new JSONObject(searcheddata);
            if (jobjdata.getString("status").equals("OK")) {
                if(arrgooglesearch!=null && !arrgooglesearch.isEmpty()) {
                    arrgooglesearch.clear();
                }
                JSONArray jsonarray = jobjdata.getJSONArray("results");
                for (int i = 0; i < jsonarray.length(); i++) {
                    Long lat = jsonarray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getLong("lat");
                    Long lng = jsonarray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getLong("lng");
                    String name = jsonarray.getJSONObject(i).getString("name");
                    GoogleSearchedApiResults google_search_api = new GoogleSearchedApiResults(lat, lng, name);
                    arrgooglesearch.add(google_search_api);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new ManualSearchedAdapter(this);
        adapter.setListarray(arrgooglesearch);
        listView_places.setAdapter(adapter);
    }



    private void sendshowdata(final String webservice) {
        async= new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    String response = null;
                    try {
                        response = HttpConnection.reasStream(webservice);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return response;
                } catch (Exception ex) {
                }
                return msg;
            }

            protected void onPostExecute(String msg) {
                if (!msg.equals("")) {
                    ShowSearchedData(msg);
                }
            }
        }.execute();
    }
    @Override
    public void afterTextChanged(Editable s) {

        if(s.length()>0)
        {
            search_icon.setTextColor(getResources().getColor(R.color.white_color));
            ShowSearchPlaces();
        }
        else
        {
            search_icon.setTextColor(getResources().getColor(R.color.text_color_search));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }
}
