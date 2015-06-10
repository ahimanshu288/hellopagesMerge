package com.knowlarity.sharedprefrences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {

	private static final String APP_SHARED_PREFS = "com.knowlarity.prefrences";
	private static final String RegisterId_APP_SHARED_PREFS = "com.knowlarity.prefrences.RegisterId";
	public static SharedPreferences appSharedPrefs;
	public static Editor prefsEditor;
	public static SharedPreferences RegisterId_appSharedPrefs;
	public static Editor RegisterId_prefsEditor;
	public String IS_REG_ID_SAVED_TO_SERVER="is_reg_id_saved_to_server";
	public String REG_ID="reg_id";
    public String VERIFY_TIME="ver_time";
	Context cont;

	public AppPreferences(Context context) {
		this.cont = context;
		appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS,
				Activity.MODE_PRIVATE);
		prefsEditor = appSharedPrefs.edit();
		RegisterId_appSharedPrefs = context.getSharedPreferences(
				RegisterId_APP_SHARED_PREFS, Activity.MODE_PRIVATE);
		RegisterId_prefsEditor = RegisterId_appSharedPrefs.edit();
	}

	public void empty_preferences() {
		prefsEditor.clear();
		prefsEditor.commit();
	}

	public void setRegisterId(String reg_id) {
		RegisterId_prefsEditor.putString(REG_ID, reg_id);
		RegisterId_prefsEditor.commit();
	}

	public String getRegisterId() {
		return RegisterId_appSharedPrefs.getString(REG_ID, "");
	}
	public void setRegIsSavedServer(boolean is_reg_id_saved_server) {
		RegisterId_prefsEditor.putBoolean(IS_REG_ID_SAVED_TO_SERVER, is_reg_id_saved_server);
		RegisterId_prefsEditor.commit();
	}

	public boolean getRegIsSavedServer() {
		return RegisterId_appSharedPrefs.getBoolean(IS_REG_ID_SAVED_TO_SERVER, false);
	}


    public void setVerifyTime() {
        prefsEditor.putLong(VERIFY_TIME, System.currentTimeMillis());
        prefsEditor.commit();
    }

    public Long getVerifiedTime() {
        return appSharedPrefs.getLong(VERIFY_TIME, 0);
    }

}