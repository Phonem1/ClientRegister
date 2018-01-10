package com.anewtech.clientregister.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by heriz on 9/1/2018.
 */

public class StaffDataModel {
    public String version;
    public String title;
    public String datetime;
    public String uploadedby;
    public boolean access;
    public List<StaffDetails> details;

}
