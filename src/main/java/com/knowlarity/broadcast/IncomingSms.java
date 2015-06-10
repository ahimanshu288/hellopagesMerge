package com.knowlarity.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.knowlarity.hellopages.NumberVerifyScreen;
import com.knowlarity.sharedprefrences.AppPreferences;

/**
 * Created by LinchPin-Dev on 6/9/2015.
 */
public class IncomingSms extends BroadcastReceiver {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    AppPreferences app_pref;

    public void onReceive(Context context, Intent intent) {
        app_pref = new AppPreferences(context);
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0]);
                String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                String senderNum = phoneNumber;
                String message = currentMessage.getDisplayMessageBody();


                if (message.contains("4321")) {
                    if (((System.currentTimeMillis()-app_pref.getVerifiedTime()) <= (1000 * 60 * 2))) {
                        NumberVerifyScreen.isnumberverified=true;
                    }
               }
            }
        } catch (Exception e) {
        }
    }
}