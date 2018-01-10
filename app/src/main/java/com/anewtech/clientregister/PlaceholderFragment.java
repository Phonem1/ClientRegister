package com.anewtech.clientregister;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.anewtech.clientregister.Adapter.CustomViewAdapter;
import com.anewtech.clientregister.Model.ClientInfoModel;
import com.anewtech.clientregister.Model.StaffDataModel;
import com.anewtech.clientregister.Service.StaffDataService;

import static android.app.Activity.RESULT_OK;

/**
 * A placeholder fragment containing a simple view.
 */

public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Context context;

    public View rootView = null;
    private StaffDataModel sm = new StaffDataModel();
    private StaffDataService sds;
    private CustomViewAdapter cva = CustomViewAdapter.getInstance();
    public static ClientInfoModel cim = ClientInfoModel.getInstance();

    private EditText name, email, phoneNo, companyName;
    private ImageView clientImg;
    private boolean isPhotoTaken;

    public PlaceholderFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getArguments().getInt(ARG_SECTION_NUMBER)==1){
            rootView = inflater.inflate(R.layout.fragment_welcome, container, false);
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
            rootView = inflater.inflate(R.layout.fragment_info_1, container, false);
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER)==3){
            rootView = inflater.inflate(R.layout.fragment_info_2, container, false);
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER)==4){
            rootView = inflater.inflate(R.layout.fragment_photo, container, false);
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER)==5){
            rootView = inflater.inflate(R.layout.fragment_tc, container, false);
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER)==6){
            rootView = inflater.inflate(R.layout.fragment_confirm, container, false);
        }
        else {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        }
        initialiseUI(rootView);

        return rootView;
    }
    private void initialiseUI(@NonNull final View mainView){

        //Tab 1...
        Button signIn = mainView.getRootView().findViewById(R.id.sign_in_btn);
        if(signIn != null){
            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("xxx", "Sign in test...");
                    TabLayout tab = mainView.getRootView().findViewById(R.id.tabs);
                    if (tab != null) {
                        tab.getTabAt(1).select();
                    }
                }
            });
        }

        //Tab 2...
        name = mainView.getRootView().findViewById(R.id.name_edtxt);
        email = mainView.getRootView().findViewById(R.id.email_edtxt);
        phoneNo = mainView.getRootView().findViewById(R.id.phoneno_edtxt);
        companyName = mainView.getRootView().findViewById(R.id.companyname_edtxt);

        Button nextInfoBtn = mainView.getRootView().findViewById(R.id.info_next_btn);
        if(nextInfoBtn != null){
            nextInfoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewPager mpager = mainView.getRootView().findViewById(R.id.container);
                    int currentitem = mpager.getCurrentItem();
                    ViewGroup container = (ViewGroup) mainView.getRootView();

                    cim.setName(name.getText().toString());
                    cim.setEmail(email.getText().toString());
                    cim.setPhoneNo(phoneNo.getText().toString());
                    cim.setCompanyName(companyName.getText().toString());
                    toLog("infomodel - name: "+cim.getName());
                    toLog("infomodel - email: "+cim.getEmail());
                    toLog("infomodel - phone no.:"+cim.getPhoneNo());
                    toLog("infomodel - company name:"+cim.getCompanyName());

                    TabLayout tab = mainView.getRootView().findViewById(R.id.tabs);
                    if (tab != null) {
                        tab.getTabAt(2).select();
                    }
                }
            });
        }

        //Tab3...
        final ListView staffList = mainView.getRootView().findViewById(R.id.staff_list);

        if(staffList != null){
            if(cva != null){
                staffList.setAdapter(cva);
                toLog("Adapter set!");
            }
            staffList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String staff = cva.getItemName(i);
                    cim.setStaffSeeking(staff);
                    cva.setmSelectedItem(i);
                    cva.setInitial(false);
                    cva.notifyDataSetChanged();
                    toLog("onItemClicked: "+staff);
                }
            });
        }

        Button nextStaffBtn = mainView.getRootView().findViewById(R.id.info_nexttab_btn);
        if(nextStaffBtn != null){
            nextStaffBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TabLayout tab = mainView.getRootView().findViewById(R.id.tabs);
                    if(tab != null){
                        tab.getTabAt(3).select();
                        toLog("infomodel - email: "+cim.getEmail());
                    }
                }
            });
        }

        //Tab4...
        final Button nextPhotoBtn = mainView.findViewById(R.id.photo_next_btn);
        Button takePhoto = mainView.findViewById(R.id.photo_btn);
        if(takePhoto != null){
            takePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchTakePictureIntent();
                    nextPhotoBtn.setVisibility(View.VISIBLE);
                }
            });
        }


        if(nextPhotoBtn != null){
            nextPhotoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TabLayout tab = mainView.getRootView().findViewById(R.id.tabs);
                    if(tab != null){
                        tab.getTabAt(4).select();
                        toLog("infomodel - phone no.:"+cim.getPhoneNo());
                        toLog("infomodel - staff seeking: "+cim.getStaffSeeking());
                    }
                }
            });
        }

        clientImg = mainView.findViewById(R.id.clientImg);
        if(clientImg != null){
            if(isPhotoTaken){
                clientImg.setImageBitmap(cim.getPhotoId());
            }
        }

        //Tab5...
        Button agreeBtn = mainView.findViewById(R.id.tc_agree_btn);
        if(agreeBtn != null){
            agreeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TabLayout tab = mainView.getRootView().findViewById(R.id.tabs);
                    if(tab != null){
                        tab.getTabAt(5).select();
                        toLog("infomodel - company name:"+cim.getCompanyName());
                    }
                }
            });
        }

        //...and set UI for Tab 6.
        ImageView confirmImg = mainView.findViewById(R.id.confirmImg);
        if(confirmImg != null){
            confirmImg.setImageBitmap(Bitmap.createScaledBitmap(cim.getPhotoId(), 1080, 620, false));
        }
        TextView confirmtv = mainView.findViewById(R.id.confirmtv);
        if(confirmtv != null){
            String confirmMsg = "My name is "+cim.getName()+". I am from "+cim.getCompanyName()+".\n I am looking for "+cim.getStaffSeeking()+".";
            confirmtv.setText(confirmMsg);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cim.setPhotoId(imageBitmap);
            clientImg.setImageBitmap(imageBitmap);
            isPhotoTaken = true;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(cva.getPm()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void toLog(String msg){
        Log.e("fragment", msg);
    }




//    public void setCim(ClientInfoModel cim){
//        this.cim = cim;
//    }

//    public void getCVA(CustomViewAdapter customViewAdapter){
//        cva = customViewAdapter;
//    }
//    public void getDetails(Context c, List<StaffDetails> details){
//        this.context = c;
//        getSDObservable(details)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(getSDObserver());
//    }
//    private Observable<List<StaffDetails>> getSDObservable(List<StaffDetails> details){
//        return Observable.just(details);
//    }
//    private Observer<List<StaffDetails>> getSDObserver() {
//        return new Observer<List<StaffDetails>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(List<StaffDetails> staffDetails) {
//                cva = new CustomViewAdapter(context, staffDetails);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//                toLog("observer complete!");
//            }
//        };
//    }
}
