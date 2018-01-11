package com.anewtech.clientregister.Adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anewtech.clientregister.Model.StaffDetails;
import com.anewtech.clientregister.R;

import java.util.List;

/**
 * Created by heriz on 9/1/2018.
 */

public class CustomViewAdapter extends BaseAdapter {

    private static CustomViewAdapter INSTANCE = null;

    private CustomViewAdapter() {}

    public static CustomViewAdapter getInstance(){
        if(INSTANCE == null){
            INSTANCE = new CustomViewAdapter();
        }
        return(INSTANCE);
    }

    static class ViewHolder {
        TextView staffname;
        ImageView staffImg;

        ViewHolder(View v){
            staffname = v.findViewById(R.id.stafftv);
            staffImg = v.findViewById(R.id.staffimg);
        }
    }

    private Context context;
    private List<StaffDetails> staffnames;
    private int mSelectedItem;
    private boolean initial;
    private PackageManager pm;

    public void initialize(Context c, List<StaffDetails> staffnames){
        this.context = c;
        this.staffnames = staffnames;
    }

    @Override
    public int getCount() {
        return staffnames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public String getItemName(int i){
        return staffnames.get(i).name;
    }

    public int getmSelectedItem() {
        return mSelectedItem;
    }

    public void setmSelectedItem(int mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    public PackageManager getPm() {
        return pm; // For fragment to use
    }

    public void setPm(PackageManager pm) {
        this.pm = pm; // Get from Activity
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.fragment_staff_details, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        if(!(i == getmSelectedItem()) || isInitial()){
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }else{
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGray));
        }
        holder.staffname.setText(this.staffnames.get(i).name);
        return view;
    }
}
