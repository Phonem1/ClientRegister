package com.anewtech.clientregister;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by heriz on 11/1/2018.
 */

public class SignOutActivity extends AppCompatActivity {

    private static final boolean LOG_ON_SIGN_OUT = true;

    private String name;
    private String email;
    private String phoneNo;
    private String company;

    private TextView title;
    private TextView tv;
    private TextView tv2;
    private Button negativeBtn;
    private Button positiveBtn;

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signout);

        handler = new Handler();

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        phoneNo = getIntent().getStringExtra("phoneno");
        company = getIntent().getStringExtra("company");
        toLog("Name: "+name);

        title = (TextView) findViewById(R.id.signout_title);
        tv = (TextView) findViewById(R.id.signout_tv);
        tv2 = (TextView) findViewById(R.id.signout_tv2);
        negativeBtn = (Button) findViewById(R.id.signout_no);
        negativeBtn.setVisibility(View.INVISIBLE);
        positiveBtn = (Button) findViewById(R.id.signout_yes);
        positiveBtn.setVisibility(View.INVISIBLE);

        updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void updateUI(){
        title.setText("You have Signed out");
        tv.setText(
                "Name: " + name +
                "\nEmail: " + email +
                "\nPhone no.: " + phoneNo +
                "\nCompany: " + company
        );

        tv2.setText("Click me!");
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv2.setText("Would you like to give feedback to our staff?");
                negativeBtn.setVisibility(View.VISIBLE);
                positiveBtn.setVisibility(View.VISIBLE);
            }
        });

    }

    public void negativeBtn(View v){
        tv2.setText("Thank you. Have a nice day!");
        positiveBtn.setVisibility(View.GONE);
        negativeBtn.setVisibility(View.GONE);
        handler.postDelayed(getRunnable(), 5000);
    }

    public void positiveBtn(View v){
        tv2.setText("");
        tv2.setHint("launch a dialog feedback dialog box");
        positiveBtn.setVisibility(View.GONE);
        negativeBtn.setVisibility(View.GONE);
        handler.postDelayed(getRunnable(), 5000);
    }

    private Runnable getRunnable(){
        return new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SignOutActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };
    }

    private void toLog(String msg){
        if(LOG_ON_SIGN_OUT){
            Log.e("Sign Out", msg);
        }
    }
}
