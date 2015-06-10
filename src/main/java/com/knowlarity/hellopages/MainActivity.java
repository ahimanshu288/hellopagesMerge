package com.knowlarity.hellopages;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.knowlarity.alerts.LocationAlerts;
import com.knowlarity.async.SendAndGetData;
import com.knowlarity.helpers.ConnectionCheck;
import com.knowlarity.helpers.ServicesAvailabilityHelper;
import com.knowlarity.sharedprefrences.AppPreferences;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {


    String SENDER_ID = "275235507717";
    int LOCATION_TIMEOUT = 5000;
    private Integer scaledDistanceFilter = 10;
    private Integer locationTimeout = 30;
    Criteria criteria;
    GoogleCloudMessaging gcm;
    String regId;
    AppPreferences pref;
    private LocationManager locationManager;
    ConnectionCheck connection_check;
    boolean isLocationAndRegisterationFound = false;
    String address = "";
    public Handler mHandler = new Handler();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connection_check = new ConnectionCheck(this);
        setCriteria();
        locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        pref = new AppPreferences(this);
        if (connection_check.isConnected()) {

            if (ServicesAvailabilityHelper.isLocationServicesEnabled(this)) {
                GetAndSendGcmRegistrationId();
            }
            setTimeOut();
        } else {
            LocationAlerts.interNetNotAvailable(MainActivity.this);
        }

    }

    public void setTimeOut() {
        mHandler.postDelayed(mUpdateTimeTask, LOCATION_TIMEOUT);
    }

    /**
     * Set criteria for best location provider.
     */
    public void setCriteria() {
        criteria = new Criteria();
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_LOW);
    }

    /**
     * Checking for register id of GCM and send to server.
     */
    public void GetAndSendGcmRegistrationId() {
        if (connection_check.isConnected()) {
            if (ServicesAvailabilityHelper.isGooglePlayServicesAvailable(this)) {

                gcm = GoogleCloudMessaging.getInstance(this);
                regId = pref.getRegisterId();

                if (regId.equals("")) {
                    registerInBackground();
                } else if (!regId.equals("") && !pref.getRegIsSavedServer()) {
                    registerInBackground();
                } else if (!regId.equals("") && pref.getRegIsSavedServer()) {
                    getLocation();
                }
            } else {
                LocationAlerts
                        .showGooglePlayServiceIsNotAvailableAlert(MainActivity.this);
            }
        } else {
            ShowLocation(getResources().getString(R.string.Not_connected));
        }
    }

    /**
     * Checking for google play services and location services ON/OFF.
     */
    protected void onResume() {
        super.onResume();
        if (ServicesAvailabilityHelper.isGooglePlayServicesAvailable(this)) {
            if (!ServicesAvailabilityHelper.isLocationServicesEnabled(this)) {
                LocationAlerts.showLocationServicesDisabledAlert(this);
            }
        }
    }

    /**
     * Lets disconnect the location-manager to updates
     */
    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(this);
    }

    /**
     * If user don't enable location services , send him to manual find location
     * screen.
     */
    public void GoNextActivityAfterSkip() {
        if (connection_check.isConnected()) {
            locationManager.removeUpdates(this);
            Intent inteskip = new Intent(this, ManualFindLocation.class);
            startActivity(inteskip);
            finish();
        } else {
            ShowLocation(getResources().getString(
                    R.string.Not_connected));
            finish();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (connection_check.isConnected()) {
            new ReverseGeoCodingRequestTask(location).execute();
        } else {
            ShowLocation(getResources().getString(
                    R.string.Not_connected));
        }
        locationManager.removeUpdates(this);
    }

    public void ShowLocation(String message) {
        Toast.makeText(MainActivity.this, "" + message, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    protected String ReverseGeoCoder(Location location) {
        String errorMessage = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address.
                    1);
        } catch (IOException ioException) {

        } catch (IllegalArgumentException illegalArgumentException) {

        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
            }
        } else {
            Address address = addresses.get(0);
            return address.getLocality();
        }
        return errorMessage;
    }

    private class ReverseGeoCodingRequestTask extends
            AsyncTask<Void, Void, String> {

        Location location;

        ReverseGeoCodingRequestTask(Location location) {
            this.location = location;
        }

        protected String doInBackground(Void... params) {
            String address = ReverseGeoCoder(location);
            return address;
        }

        protected void onPostExecute(String address) {
            if (!address.equals("")) {
                isLocationAndRegisterationFound = true;
                // OpenLocationFoundScreen(address);
                MainActivity.this.address = address;
            }
        }
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging
                                .getInstance(MainActivity.this);
                    }
                    regId = gcm.register(SENDER_ID);
                    pref.setRegisterId(regId);
                    msg = regId;
                } catch (IOException ex) {
                }
                return msg;
            }

            protected void onPostExecute(String msg) {
                if (!msg.equals("")) {
                    if (connection_check.isConnected()) {
                        String webservice = "";
                        new SendAndGetData(MainActivity.this, webservice).execute();
                    }
                }
            }
        }.execute();
    }

    /**
     * It will get the location then call the reverse geocoding.
     */
    public void getLocation() {
        Location locationcurrent;
        if (ServicesAvailabilityHelper.isLocationServicesEnabled(this)) {
            String bestprovider = LocationManager.NETWORK_PROVIDER;
            locationcurrent = locationManager
                    .getLastKnownLocation(bestprovider);
            if (locationcurrent != null) {
                new ReverseGeoCodingRequestTask(locationcurrent).execute();

            } else {
                bestprovider = LocationManager.GPS_PROVIDER;
                locationcurrent = locationManager
                        .getLastKnownLocation(bestprovider);
                if (locationcurrent == null) {
                    locationManager.requestLocationUpdates(
                            locationManager.getBestProvider(criteria, true),
                            locationTimeout * 1000, scaledDistanceFilter, this);
                } else {
                    new ReverseGeoCodingRequestTask(locationcurrent).execute();
                }
            }
        } else {
        }
    }

    public void OpenLocationFoundScreen(String address) {
        Intent intelocation = new Intent(MainActivity.this,
                com.knowlarity.activity.MainActivity.class);
        //intelocation.putExtra("Address", address);
        startActivity(intelocation);
        finish();
    }

    /**
     * It's basically a timeout for location finder , if location didn't found till thread time then
     * option for retry and manual search.
     */
    public Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (isLocationAndRegisterationFound) {
                OpenLocationFoundScreen(address);
            } else {
                LocationAlerts.LocationNotFoundAlert(MainActivity.this);
            }
        }
    };
}