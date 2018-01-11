package com.anewtech.clientregister.Service;

import android.util.Log;

import com.anewtech.clientregister.Model.StaffDataModel;
import com.anewtech.clientregister.Model.StaffDetails;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by heriz on 9/1/2018.
 */

public class StaffDataService {
    private Gson g;
    private StaffDataModel sm;

    public StaffDataService(){
        g = new Gson();
    }

    public void setJsonData(String requiredJson){
        sm = g.fromJson(requiredJson, StaffDataModel.class);
    }

    public List<StaffDetails> getDetailsList(){
        return sm.details;
    }

    public void toLog(){
        Log.e("from model", "uploadedby:"+sm.uploadedby);
    }
}
