package com.example.aditya.friends.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;


import com.example.aditya.friends.R;
import com.example.aditya.friends.home.HomeActivity;
import com.example.aditya.friends.startup.StartupActivity;
import com.example.aditya.friends.utils.FriendsUtils;
import com.google.firebase.FirebaseApp;

public class SplashActivity extends AppCompatActivity{

    private ImageView mFriendsLogo;
    private ImageView mFriendsLogoText;

    private Handler mHandler = new Handler();


    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseApp.initializeApp(getApplicationContext());

        FriendsUtils.addDummy1();
        FriendsUtils.addDummy2();
        FriendsUtils.addDummy3();
        FriendsUtils.addDummy4();
        FriendsUtils.addDummy5();

        mFriendsLogo = (ImageView) findViewById(R.id.friends_logo);
        mFriendsLogoText = (ImageView) findViewById(R.id.friends_logo_text);

        ScaleAnimation scaleIn = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.f, Animation.RELATIVE_TO_SELF,  0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleIn.setInterpolator(new AccelerateInterpolator());
        scaleIn.setDuration(600L);
        scaleIn.setFillAfter(true);

        final ScaleAnimation scaleOutOne = new ScaleAnimation(0.0f, 1.5f, 0.0f, 1.5f, Animation.RELATIVE_TO_SELF,  0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleOutOne.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleOutOne.setDuration(500L);
        scaleOutOne.setFillAfter(true);

        final ScaleAnimation scaleInTwo = new ScaleAnimation(1.5f, 1.3f, 1.5f, 1.3f, Animation.RELATIVE_TO_SELF,  0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleInTwo.setInterpolator(new LinearInterpolator());
        scaleInTwo.setDuration(200L);
        scaleInTwo.setFillAfter(true);

        mFriendsLogo.startAnimation(scaleIn);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFriendsLogo.setVisibility(View.GONE);
                mFriendsLogoText.setVisibility(View.VISIBLE)    ;
                mFriendsLogoText.startAnimation(scaleOutOne);
            }
        }, 610);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFriendsLogoText.startAnimation(scaleInTwo);
            }
        }, 610 + 500);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        final String pass = mPreferences.getString("password", "");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pass.isEmpty()){
                    Intent intent = new Intent(SplashActivity.this, StartupActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1500);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
