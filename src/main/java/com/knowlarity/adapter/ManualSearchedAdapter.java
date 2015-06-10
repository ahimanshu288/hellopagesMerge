package com.knowlarity.adapter;

/**
 * Created by Knowlarity on 6/5/2015.
 */
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.knowlarity.hellopages.R;

import models.GoogleSearchedApiResults;


public class ManualSearchedAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater = null;
    ArrayList<GoogleSearchedApiResults> rowItems;
    ArrayList selected_Roles = new ArrayList();
    public ManualSearchedAdapter(Context context) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }
    public void setListarray(ArrayList<GoogleSearchedApiResults> items) {
        this.rowItems = items;
    }
    public int getCount() {
        return rowItems.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    static class ViewHolder {
        TextView textView_searched;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.searched_items, null);

            holder = new ViewHolder();
            holder.textView_searched = (TextView) convertView
                    .findViewById(R.id.textView_searched);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView_searched.setText(rowItems.get(position).getName());
        return convertView;
    }
}
