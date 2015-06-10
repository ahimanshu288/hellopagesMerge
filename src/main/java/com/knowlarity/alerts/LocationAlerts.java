package com.knowlarity.alerts;

import com.knowlarity.hellopages.MainActivity;
import com.knowlarity.hellopages.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * @author Knowlarity
 * 
 */
public class LocationAlerts {

	/**
	 * Prepares and displays an alert dialog if location services are disabled
	 * (i.e. if the gps manager or the location manager are disabled)
	 */
	public static void showLocationServicesDisabledAlert(
			final MainActivity activity) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(activity.getApplicationContext().getResources()
				.getString(R.string.no_location_access));
		builder.setMessage(
				activity.getApplicationContext().getResources()
						.getString(R.string.no_location_access_description))
				.setCancelable(false)
				.setPositiveButton(
						activity.getApplicationContext().getResources()
								.getString(R.string.allow_text),
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									final int id) {
								activity.startActivity(new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							}
						})
				.setNegativeButton(
						activity.getApplicationContext().getResources()
								.getString(R.string.skip_text),
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									final int id) {
								dialog.cancel();
								 activity.GoNextActivityAfterSkip();
							}
						});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	public static void showGooglePlayServiceIsNotAvailableAlert(
			final MainActivity activity) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(
				activity.getApplicationContext().getResources()
						.getString(R.string.no_valid_play_service))
				.setCancelable(false)
				.setNegativeButton(
						activity.getApplicationContext().getResources()
								.getString(R.string.ok_text),
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									final int id) {
								dialog.cancel();
								//activity.finish();
							}
						});
		final AlertDialog alert = builder.create();
		alert.show();
	}

    public static void LocationNotFoundAlert(
            final MainActivity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getApplicationContext().getResources()
                .getString(R.string.location_not_found));
        builder.setMessage(
                activity.getApplicationContext().getResources()
                        .getString(R.string.location_not_found_disc))
                .setCancelable(false)
                .setPositiveButton(
                        activity.getApplicationContext().getResources()
                                .getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog,
                                                final int id) {
                                dialog.cancel();
                                activity.GoNextActivityAfterSkip();
                            }
                        })
                .setNegativeButton(
                        activity.getApplicationContext().getResources()
                                .getString(R.string.retry),
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog,
                                                final int id) {
                                dialog.cancel();
activity.setTimeOut();
                                activity.GetAndSendGcmRegistrationId();
                            }
                        });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    public static void interNetNotAvailable(
            final MainActivity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(
                activity.getApplicationContext().getResources()
                        .getString(R.string.Not_connected_discription))
                .setCancelable(false)
                .setNegativeButton(
                        activity.getApplicationContext().getResources()
                                .getString(R.string.ok_text),
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog,
                                                final int id) {
                                dialog.cancel();
                                activity.finish();
                            }
                        });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
