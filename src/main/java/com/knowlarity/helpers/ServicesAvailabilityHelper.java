package com.knowlarity.helpers;



import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.content.Context;
import android.location.LocationManager;

/**
 * Utility class for checking that certain services are available 
 */
public class ServicesAvailabilityHelper {

	/**
	 * Checks that the Google Play apk is installed
	 * 
	 * @return true if it's installed, false if it's not.
	 */
	public static boolean isGooglePlayServicesAvailable(Context context) {
		// Check that Google Play services is available

		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(context);
		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			// Continue

			return true;
		} else {
			// Get the error code
			return false;
		}
	}

	/**
	 * Checks the location manager to see if either the network location
	 * provider or the GPS location provider is enabled.
	 * 
	 * @param context
	 *            the application provider
	 * @return true if at least ONE of the two providers is enabled, false if it
	 *         is not
	 */
	public static boolean isLocationServicesEnabled(Context context) {
		LocationManager lManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		if (lManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
				|| lManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			return true;
		}
		return false;
	}

}
