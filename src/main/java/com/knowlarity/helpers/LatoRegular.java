package com.knowlarity.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by LinchPin-Dev on 6/9/2015.
 */
public class LatoRegular extends TextView {
    Context con;

    public LatoRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        con = context;
        init();
    }

    public LatoRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        con = context;
        init();
    }

    public LatoRegular(Context context) {
        super(context);
        con = context;
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "lato_regular.ttf");
        setTypeface(tf);
    }

}