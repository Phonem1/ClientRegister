package com.anewtech.clientregister.Model;

import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

/**
 * Created by heriz on 9/1/2018.
 */

public class ClientInfoModel {

    private static ClientInfoModel INSTANCE = null;
    private ClientInfoModel(){}
    public static synchronized ClientInfoModel getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ClientInfoModel();
        }
        return(INSTANCE);
    }

    private String name;
    private String email;
    private String phoneNo;
    private String companyName;
    private String staffSeeking;
    private Bitmap photoId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStaffSeeking() {
        return staffSeeking;
    }

    public void setStaffSeeking(String staffSeeking) {
        this.staffSeeking = staffSeeking;
    }

    public Bitmap getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Bitmap photoid) {
        this.photoId = photoid;
    }
}
