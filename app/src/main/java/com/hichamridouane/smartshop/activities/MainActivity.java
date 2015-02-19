package com.hichamridouane.smartshop.activities;

/**
 * Created by Hicham Ridouane on 05/07/2014.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
import com.hichamridouane.smartshop.R;
import com.hichamridouane.smartshop.model.GPSTracker;
import com.hichamridouane.smartshop.model.ParseApplication;
import com.hichamridouane.smartshop.controllers.RadiusSettings;
import com.hichamridouane.smartshop.controllers.TimelineSettings;
import com.hichamridouane.smartshop.utils.TypefaceSpan;
import com.hichamridouane.smartshop.presenter.Articles;
import com.hichamridouane.smartshop.adapters.ListViewAdapter;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MainActivity extends Activity implements TimelineSettings.dialoglistener, RadiusSettings.radiuslistener {

    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ListViewAdapter adapter;
    long max_time = 5000;
    TimelineSettings settings;
    RadiusSettings radiusSettings;
    private String[] selectedArray;
    double rayon = 50;
    private Dialog progressDialog;
    GPSTracker gps;
    ParseGeoPoint device_location;
    ParseUser currentUser = ParseUser.getCurrentUser();
    private List<Articles> list_of_articles = null;
    List<String> zak = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionbar = getActionBar();
        actionbar.setBackgroundDrawable(FlatUI.getActionBarDrawable(MainActivity.this, R.array.custom_theme_2,false));
        SpannableString s = new SpannableString("SmartShop");
        s.setSpan(new TypefaceSpan(this,"Roboto-Thin.ttf"),0,s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionbar.setTitle(s);
        actionbar.setDisplayShowHomeEnabled(false);

        setContentView(R.layout.main_activity_layout);
        ParseAnalytics.trackAppOpened(getIntent());
        //get the array that contains the categories
        Resources res = getResources();
        selectedArray = res.getStringArray(R.array.categories);
        settings = new TimelineSettings();
        radiusSettings = new RadiusSettings();
        gps = new GPSTracker(MainActivity.this);
        // check if GPS enabled
        if(gps.canGetlocation() ){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            device_location = new ParseGeoPoint(latitude,longitude);
            // for testing purpose only
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            gps.showSettingsAlert();
        }
        new RemoteDataTask().execute();

    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_go_home:
                //go home action
                if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
                    Intent i = new Intent(this, UserProfileActivity.class);
                    startActivity(i);
                }
                else {
                    showConnectAlert();
                }
                return true;
            case R.id.action_select_categories:
                //select categories
                settings.show(getFragmentManager(),"categories");
                return true;

            case R.id.action_select_radius:
                radiusSettings.show(getFragmentManager(),"radius");
                return true;

            case R.id.action_refresh:
                Intent j = new Intent(getIntent());
                j.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(j);
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onOkay(ArrayList<Integer> selected) {
        StringBuilder stringBuilder = new StringBuilder();
        if (selected.size() != 0) {
            zak.clear();
            for (int i = 0; i < selected.size(); i++) {
                String categories = selectedArray[selected.get(i)];
                zak.add(categories);
                stringBuilder = stringBuilder.append(", " + categories);
            }
            Toast.makeText(this, "You have selected: "
            + stringBuilder.toString(), Toast.LENGTH_SHORT).show();

            new RemoteDataTask().execute();
        }
    }
    @Override
    public void onCancel() {

    }

    @Override
    public void onRadiusOkay(int radius) {
        rayon = radius;
        new RemoteDataTask().execute();

    }

    @Override
    public void onRadiusCancel(){

    }

    public void showConnectAlert(){

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        //définition d'un view pour remplacer celui par défaut de l'alertdialog :
        final View customView = inflater.inflate(R.layout.custom_connect,null);
        alertdialog.setView(customView);
        alertdialog.setPositiveButton(" ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onLoginButtonClicked();
            }
        });

        alertdialog.setNegativeButton(" ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog d = alertdialog.show();
        Button b = d.getButton(DialogInterface.BUTTON_NEGATIVE);
        b.setText("Non, Merci");
        b.setTextColor(getResources().getColor(R.color.gray2));
        b.setBackgroundColor(getResources().getColor(R.color.snow_primary));


        Button b2 = d.getButton(DialogInterface.BUTTON_POSITIVE);
        b2.setText("Créer");
        b2.setTextColor(getResources().getColor(R.color.gray2));
        b2.setBackgroundColor(getResources().getColor(R.color.snow_primary));
        d.show();
    }
    private void showProfile(){
        Intent intent = new Intent(getApplicationContext(),UserProfileActivity.class);
        startActivity(intent);
    }
    private void onLoginButtonClicked() {

        if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
            MainActivity.this.progressDialog = ProgressDialog.show(
                    MainActivity.this, "", "Connexion en cours...", true);
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
                    showProfile();
                } else {
                    Log.d(ParseApplication.TAG,
                            "L'utilisateur est loggé à FB");
                    showProfile();
                }
            }
        });
    }
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            long delayInMillis = 1000;
            Timer timer = new Timer();

            //creating the progress dialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMessage(Html.fromHtml(getString(R.string.progress_txt)));
            //show the progressdialog...Only if gpslocation is available !!
            if (gps.canGetlocation()){
                mProgressDialog.show();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mProgressDialog.dismiss();
                    }
                },delayInMillis );
                }
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array

           /* timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mProgressDialog.dismiss();
                }
            },delayInMillis );*/
            list_of_articles = new ArrayList<Articles>();
            try {

                // Locate the class table named "Article" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "Article");
                query.whereWithinKilometers("Localisation_Vendeur", device_location, rayon);
                if(zak.size() == 0) {
                    query.whereContainedIn ("Categorie",Arrays.asList(selectedArray));
                } else {
                    query.whereContainedIn ("Categorie", zak);
                }
                query.orderByDescending("createdAt");
                ob = query.find();
                for (ParseObject article : ob) {
                    // Locate images in article_image column
                    ParseFile image = (ParseFile) article.get("Image_Article");
                    Articles map = new Articles();
                    map.setArticle_name((String) article.get("Nom_Article"));
                    map.setArticle_vendor((String) article.get("Nom_Vendeur"));
                    map.setArticle_category((String) article.get("Categorie"));
                    map.setArticle_reduction((String) article.get("Reduction"));
                    map.setArticle_favoris((String) article.get("Favoris"));
                    map.setArticle_description((String) article.get("Description"));
                    map.setArticle_date(article.getCreatedAt());
                    map.setArticle_image(image.getUrl());
                    list_of_articles.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(MainActivity.this,
                    list_of_articles);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
}