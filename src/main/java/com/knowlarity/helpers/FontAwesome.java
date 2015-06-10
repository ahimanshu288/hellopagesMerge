package com.knowlarity.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontAwesome extends TextView {
	Context con;

	public FontAwesome(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		con = context;
		init();
	}

	public FontAwesome(Context context, AttributeSet attrs) {
		super(context, attrs);
		con = context;
		init();
	}

	public FontAwesome(Context context) {
		super(context);
		con = context;
		init();
	}

	private void init() {
		Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "custom_fonts.ttf");
		setTypeface(tf);
	}

}