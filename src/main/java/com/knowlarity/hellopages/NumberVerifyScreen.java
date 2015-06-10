package com.knowlarity.hellopages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.knowlarity.helpers.DotsProgressBar;
import com.knowlarity.sharedprefrences.AppPreferences;


/**
 * Created by Knowlarity on 6/8/2015.
 */
public class NumberVerifyScreen extends Activity implements TextWatcher {
    TextView textView_verify, textView_mobile;
    EditText editText_number;
    DotsProgressBar dots_progress;
    AppPreferences app_pref;
    public static boolean isnumberverified = false;
    Handler mhandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_verify_screen);
        app_pref = new AppPreferences(this);
        mhandler = new Handler();
        textView_verify = (TextView) findViewById(R.id.textView_verify);
        textView_mobile = (TextView) findViewById(R.id.textView_mobile);
        editText_number = (EditText) findViewById(R.id.editText_number);
        dots_progress = (DotsProgressBar) findViewById(R.id.dots_progress);
        editText_number.addTextChangedListener(this);
        setEnability(false);
        dots_progress.setVisibility(View.INVISIBLE);
        textView_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_mobile.setText("Verifying Number...");
                dots_progress.setVisibility(View.VISIBLE);
                app_pref.setVerifyTime();
                mhandler.postDelayed(mUpdateTimeTask, 2000);
            }
        });
    }

    public void setEnability(boolean isEnable) {
        textView_verify.setEnabled(isEnable);
        textView_verify.setClickable(isEnable);
        if (isEnable) {
            textView_verify.setTextColor(getResources().getColor(R.color.verify_text_color_after));
        } else {
            textView_verify.setTextColor(getResources().getColor(R.color.verify_text_color_before));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 9) {
            setEnability(true);
        } else {
            setEnability(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (((System.currentTimeMillis() - app_pref.getVerifiedTime()) <= (1000 * 60 * 2))) {
                mhandler.postDelayed(mUpdateTimeTask, 1000);
                if (isnumberverified) {
                    mhandler.removeCallbacks(mUpdateTimeTask);
                    Intent intverified = new Intent(NumberVerifyScreen.this, NumberVerifiedScreen.class);
                    startActivity(intverified);
                    finish();
                }
            } else {
                mhandler.removeCallbacks(mUpdateTimeTask);
            }
        }
    };
}