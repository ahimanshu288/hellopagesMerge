package com.knowlarity.activity;

/**
 * Created by shivangi on 5/6/15.
 */


import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.knowlarity.adapter.TabsPagerAdapter;
import com.knowlarity.hellopages.R;


public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private MenuItem mSearchAction;

    ListView lv;
    Context context;

    ArrayList prgmName;
    // Tab titles
    private String[] tabs = { "BUISNESS", "MESSAGES" };
    public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};
    public static int  prgmImages= R.drawable.profile_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_bar);

       /* lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(this, prgmNameList,prgmImages));*/

        actionBar=getActionBar();

        // add the custom view to the action bar
        actionBar.setCustomView(R.layout.actionbar_view);

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00b9fa")));
        // actionBar.setIcon(R.drawable.search);
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        EditText search = (EditText) actionBar.getCustomView().findViewById(
                R.id.searchfield);
        //search.getBackground().setColorFilter(getResources().getColor(R.color.trans_dark_grey), PorterDuff.Mode.SRC_ATOP);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                Toast.makeText(MainActivity.this, "Search triggered",
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);


        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);

        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);


        getActionBar().setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#00b9fa")));
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        actionBar.addTab(actionBar.newTab().setText(" BUISNESS").setTabListener(this).setIcon(R.drawable.buisness));
        actionBar.addTab(actionBar.newTab().setText("MESSAGES").setTabListener(this).setIcon(R.drawable.chatmessage));
        // Adding Tabs
      /* for (String tab_name : tabs) {
           *//* actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this)*//**//*.setIcon(R.drawable.buisness)*//**//*);*//*
           actionBar.addTab(actionBar.newTab().setText(tab_name)
                   .setTabListener(this).setIcon(R.drawable.chatmessage));

        }*/

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    /*   @Override
       public boolean onOptionsItemSelected(MenuItem item) {
           int id = item.getItemId();
           if (id == R.id.action_search) {
               if (mSearchOpened) {
                   closeSearchBar();
               } else {
                   openSearchBar(mSearchQuery);
               }
               return true;
           }
           return super.onOptionsItemSelected(item);
       }*/


}