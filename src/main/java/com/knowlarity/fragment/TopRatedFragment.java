package com.knowlarity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.knowlarity.adapter.CustomAdapter;
import com.knowlarity.hellopages.R;

/**
 * Created by shivangi on 5/6/15.
 */
public class TopRatedFragment extends Fragment {
    ListView lv;

    public static String [] prgmNameList={"Buisness 1","Buisness 2","Buisness 3","Buisness 4","Buisness 5","Buisness 6","Buisness 7","Buisness 8","Buisness 9"};
    public static int  prgmImages=R.drawable.profile_img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //  ListView lv = (ListView)getActivity().findViewById(R.id.lv_contact);

        View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);
        lv=(ListView)rootView. findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(getActivity(), prgmNameList,prgmImages));

        return rootView;
    }
}