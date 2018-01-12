package com.anewtech.clientregister;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.anewtech.clientregister.Adapter.CustomViewAdapter;
import com.anewtech.clientregister.Adapter.SectionsPagerAdapter;
import com.anewtech.clientregister.Fragment.PlaceholderFragment;
import com.anewtech.clientregister.Model.ClientInfoModel;
import com.anewtech.clientregister.Model.StaffDetails;
import com.anewtech.clientregister.Service.Api;
import com.anewtech.clientregister.Service.StaffDataService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private CustomViewAdapter cva;
    private StaffDataService sds;
    private ClientInfoModel cim = ClientInfoModel.getInstance();

    private List<StaffDetails> details;

    private final String URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        sds = new StaffDataService();
        sds.setJsonData(loadJsonFromAsset());
        sds.toLog();

        details = sds.getDetailsList();
        cva = CustomViewAdapter.getInstance();
        cva.setInitial(true);
        cva.initialize(this, details); //pass to load ListView items -> setAdapter()
        cva.setPm(getPackageManager()); //pass to

        //Reset client model to null
        cim.setName(null);
        cim.setEmail(null);
        cim.setPhoneNo(null);
        cim.setCompanyName(null);
        cim.setStaffSeeking(null);
        cim.setPhotoId(null);
        cim.setSignedIn(0,false);
        cim.setSignedIn(1,false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(cim.isSignedIn().get(1) && !cim.isSignedIn().get(0)){
            finish();
        }
    }

    public void getJsonRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .build();

        Api api = retrofit.create(Api.class);

        api.getPost().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    toLog("RetrofitExample: "+response.body().string());
                }catch(IOException e){
                    toLog("RetrofitError: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public String loadJsonFromAsset(){
        String json = null;
        try{
            InputStream is = getAssets().open("staffdetails.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }catch(IOException e){
            Log.e("xxx",e.getMessage());
            return null;
        }
        Log.e("xxx", json);
        return json;
    }

    private void toLog(String msg){
        Log.e("main", msg);
    }

}
