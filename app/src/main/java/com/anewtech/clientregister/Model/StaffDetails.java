package com.anewtech.clientregister.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by heriz on 9/1/2018.
 */

public class StaffDetails implements Serializable{
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("phoneno")
    public String phoneno;
    @SerializedName("email")
    public String email;
}
