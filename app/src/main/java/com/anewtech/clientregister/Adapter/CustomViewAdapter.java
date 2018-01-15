package com.anewtech.clientregister.Adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.anewtech.clientregister.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;

import io.reactivex.Observable;

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
    private Resources resources;
    private File directory;
    private ArrayList<String> imgNoNamae;

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

        //shade background to show selected item
        if(!(i == getmSelectedItem()) || isInitial()){
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }else{
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGray));
        }
        holder.staffname.setText(this.staffnames.get(i).name);

//        Bitmap bm = BitmapFactory.decodeResource(resources, R.drawable.staff_black);
//        if(this.staffnames.get(i).name.equals("Jack Black")){
////            holder.staffImg.setImageBitmap(bm);
//            holder.staffImg.setImageBitmap(imageBM);
//        }

        String img = "";
        for(int j =0; j < imgNoNamae.size(); j++){
            if(j == i){
                img = imgNoNamae.get(j);
            }
        }

        //Get image based on name
        File file = new File(directory, img);
        FileInputStream inStream = null;
        try{
            inStream = new FileInputStream(file);
            try{
                Bitmap bitomape = BitmapFactory.decodeStream(inStream);
                Bitmap croppedBm = cropBitmapToSq(bitomape);
                holder.staffImg.setImageDrawable(cropBitmapToRound(croppedBm));
            }finally {
                inStream.close();
            }
        }catch(IOException fnfe){
            toLog(fnfe.getMessage());
        }



        return view;
    }

    private RoundedBitmapDrawable cropBitmapToRound(Bitmap bm){
//        Bitmap imageBitmap=BitmapFactory.decodeResource(getResources(),  R.drawable.theenigma_pp); //get bitmap from resources drawable
        Bitmap image = Bitmap.createScaledBitmap(bm, 400, 400, false);
        RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(resources, image);

//setting radius
        roundedBitmapDrawable.setCornerRadius(200.0f);
        roundedBitmapDrawable.setAntiAlias(true);
        return roundedBitmapDrawable;
    }

    private Bitmap cropBitmapToSq(Bitmap srcBmp){
        Bitmap dstBmp;
        if (srcBmp.getWidth() >= srcBmp.getHeight()){

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );

        }else{

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
        }

        Bitmap image = Bitmap.createScaledBitmap(dstBmp, 400, 400, false);
        return image;
    }

    public void getResources(Resources res){
        resources = res;
    }

    public void getListofNames(ArrayList<String> names){
        imgNoNamae = names;
    }

    public void getFileDir(File dir){
        directory = dir;
    }

    private void toLog(String msg){
        Log.e("cva", msg);
    }
}
