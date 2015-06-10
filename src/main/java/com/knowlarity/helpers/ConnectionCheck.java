package com.knowlarity.helpers;

import android.content.Context;
import android.net.ConnectivityManager;

public class ConnectionCheck {

	private  Context context;

	public ConnectionCheck(Context context) {
		this.context = context;
	}

	public  boolean isConnected() {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager.getActiveNetworkInfo() != null
				&& connectivityManager.getActiveNetworkInfo().isAvailable()
				&& connectivityManager.getActiveNetworkInfo().isConnected()) {

			return true;

		} else {
			return false;
		}
	}

}
