package com.knowlarity.async;

import com.knowlarity.hellopages.MainActivity;
import com.knowlarity.hellopages.ManualFindLocation;
import com.knowlarity.hellopages.R;
import com.knowlarity.helpers.ConnectionCheck;
import com.knowlarity.webservice.HttpConnection;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

public class SendAndGetData extends AsyncTask<String, Void, String> {
	Context context;
    ConnectionCheck connection_check;
    String webservice;
	public SendAndGetData(Context context, String webservice) {
		this.context = context;
		//connection_check=new ConnectionCheck(context);
        this.webservice=webservice;
        connection_check=new ConnectionCheck(context);
	}
	String res = "";

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected String doInBackground(String... params) {
        String response= null;
        try {
            response = HttpConnection.reasStream(webservice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
	}

	@Override
	protected void onPostExecute(String paramType) {
		if (paramType != null && !paramType.equals("")) {
            if(context instanceof MainActivity) {
                try {
                    if (connection_check.isConnected()) {
                        ((MainActivity) context).getLocation();
                    } else {
                        ((MainActivity) context).ShowLocation(context.getResources().getString(
                                R.string.Not_connected));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(context instanceof ManualFindLocation)
            {

                    ((ManualFindLocation) context).ShowSearchedData(paramType);

            }
		} else {
            if(context instanceof MainActivity) {
                if (connection_check.isConnected()) {
                    ((MainActivity) context).getLocation();
                } else {
                    ((MainActivity) context).ShowLocation(context.getResources().getString(
                            R.string.Not_connected));
                }
            }
            else if(context instanceof ManualFindLocation)
            {
                ((MainActivity) context).getLocation();
            }
		}
	}
}