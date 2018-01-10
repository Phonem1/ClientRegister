package com.anewtech.clientregister.Service;

import android.telecom.CallScreeningService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by heriz on 10/1/2018.
 */

public interface Api {
    @GET("/posts")
    Call<ResponseBody> getPost();
}
