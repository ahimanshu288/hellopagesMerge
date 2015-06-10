package com.knowlarity.hellopages;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class LocationFoundScreen extends Activity {
	TextView textView_location;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_found_screen);
		textView_location=(TextView) findViewById(R.id.textView_location);
		if(getIntent().getExtras()!=null)
		{
			textView_location.setText("Your Location is :- "
					+ getIntent().getStringExtra("Address"));
		}
	}
}