package com.anewtech.clientregister.Adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Process;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anewtech.clientregister.Model.StaffDetails;
import com.anewtech.clientregister.Model.VisitorModel;
import com.anewtech.clientregister.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    private List<VisitorModel> hostdetails;

    private int mSelectedItem;
    private boolean initial;
    private boolean isLoadedLocally = false;
    private PackageManager pm;
    private Resources resources;
    private File directory;
    private ArrayList<String> imgNoNamae;

    private ViewHolder holder;
    String img = "";

    public void initialize(Context c, List<StaffDetails> staffnames){
        this.context = c;
        this.staffnames = staffnames;
    }

    public void initialize( List<VisitorModel> hostdetails){
        this.hostdetails = hostdetails;
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
        return mSelectedItem; // is used for shading(item selected)
    }

    public void setmSelectedItem(int mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    public boolean isInitial() {
        return initial; // is used for shading(item selected)
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

    public void getFileDir(File dir){
        directory = dir;
    }

    public void getResources(Resources res){
        resources = res;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.fragment_staff_details, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        //shade background to show selected item
        if(!(i == getmSelectedItem()) || isInitial()){
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }else{
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGray));
        }

        //staffnames from local json
        if(isLoadedLocally) {
            if (staffnames != null) {
                holder.staffname.setText(this.staffnames.get(i).name);
            }


            //imgname from local img
            if (imgNoNamae != null) {
                for (int j = 0; j < imgNoNamae.size(); j++) {
                    // This loop prevents indexOutOfBound error
                    final int index = j;

                    if (j == i) {
                        img = imgNoNamae.get(i);

                        File f = new File("/storage/emulated/0/Android/data/com.anewtech.clientregister/files/StaffImages/" + img);
                        Picasso.with(context).load(f).into(holder.staffImg);
                    }
                }
            }
        }else {
            //host details from db
            if (hostdetails != null) {

            }
        }

        return view;
    }

    public void getListofNames(ArrayList<String> names){
        imgNoNamae = names;
    }

    private void toLog(String msg){
        Log.e("cva", msg);
    }

}
