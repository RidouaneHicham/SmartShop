package com.hichamridouane.smartshop.model;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cengalabs.flatui.views.FlatButton;
import com.hichamridouane.smartshop.R;
import com.hichamridouane.smartshop.controller.FragmentAdapter;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.PageIndicator;

import java.util.Arrays;
import java.util.List;

public class StartingActivity extends FragmentActivity implements View.OnClickListener {

    FragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;
    TextView Continue;
    private FlatButton loginButton;
    private Dialog progressDialog;
    GPSTracker gps;
    ParseGeoPoint device_location;
    ParseUser currentUser;
    FlatButton continueBrowsing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_activity_layout);
        if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
            loginButton.setText(R.string.deconnexion);
            showMainActivity();
        }

        mAdapter = new FragmentAdapter(getSupportFragmentManager());
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mIndicator = (LinePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        Continue = (TextView)findViewById(R.id.continue_browsing);
        Continue.setOnClickListener(this);
        loginButton = (FlatButton)findViewById(R.id.fb_login);//before Loginbutton
        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)){
                    showProfile();
                }
                else {
                    onLoginButtonClicked();
                }
            }

        });
        gps = new GPSTracker(StartingActivity.this);
        //vérifier si la connexion GPS est disponible :
        if(gps.canGetlocation() ){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            device_location = new ParseGeoPoint(latitude,longitude);

            //ce bloc est pour tester si les coordonnées ont été bien récupérées :
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

        }else{
            gps.showSettingsAlert();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        currentUser = ParseUser.getCurrentUser();
        continueBrowsing = (FlatButton)findViewById(R.id.continue_browsing);

        if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
            loginButton.setText(R.string.acceder_profile);
            continueBrowsing.setText(R.string.commencer_shopping);
            continueBrowsing.setTextColor(getResources().getColor(R.color.hot_orange));
        }
        else {
            loginButton.setText(R.string.connexion_fb);
            continueBrowsing.setText(R.string.continue_browsing);
            continueBrowsing.setTextColor(getResources().getColor(R.color.hot_orange));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

    private void onLoginButtonClicked() {

        if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
            StartingActivity.this.progressDialog = ProgressDialog.show(
                    StartingActivity.this, "", "Connexion en cours...", true);
        }
        List<String> permissions = Arrays.asList("public_profile","user_friends");
        ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                //StartingActivity.this.progressDialog.dismiss();
                if (user == null) {
                    Log.d(ParseApplication.TAG,
                            "L'utilisateur a annulé la connexion FB");
                } else if (user.isNew()) {
                    Log.d(ParseApplication.TAG,
                            "L'utilisateur est connecté et loggé via FB");
                    showMainActivity();
                } else {
                    Log.d(ParseApplication.TAG,
                            "L'utilisateur est loggé à FB");
                    showMainActivity();
                }
            }
        });
    }

    private void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
       startActivity(intent);
    }

    private void showProfile(){
        Intent intent = new Intent(this,UserProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(getBaseContext() ,MainActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    //cette partie est pour prendre en charge le bouton retour/Home tant que c'est spécifié sur le manifest :
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
