package com.hichamridouane.smartshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.FloatMath;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cengalabs.flatui.views.FlatButton;


/**
 * Created by Hicham Ridouane on 10/07/2014.
 **/

public class GPSTracker extends Activity implements LocationListener {

    private final Context mcontext;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    Location location;
    double latitude,longitude;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BETWEEN_UPDATE = 600000; // 10min
    protected LocationManager locationmanager;

    public GPSTracker (Context context){
        this.mcontext = context;
        getLocation();
    }

    public Location getLocation(){
        try {
            locationmanager = (LocationManager) mcontext
                    .getSystemService(LOCATION_SERVICE);

            // récupération du statut du GPS
            isGPSEnabled = locationmanager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // récupération de l'état de la connexion
            isNetworkEnabled = locationmanager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // pas de provider réseau
            } else {
                this.canGetLocation = true;
                // récupérer la position depuis le provider réseau
                if (isNetworkEnabled) {
                    locationmanager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BETWEEN_UPDATE,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationmanager != null) {
                        location = locationmanager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // si GPS activé récupérer lat/long
                if (isGPSEnabled) {
                    if (location == null) {
                        locationmanager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BETWEEN_UPDATE,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationmanager != null) {
                            location = locationmanager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if (location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetlocation(){
        return this.canGetLocation;
    }

    public void showSettingsAlert(){

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(mcontext);
        LayoutInflater inflater = LayoutInflater.from(mcontext);

        //définition d'un view pour remplacer celui par défaut de l'alertdialog :
        final View customView = inflater.inflate(R.layout.custom_gps,null);
        alertdialog.setView(customView);


/*        final AlertDialog dialog = alertdialog.create();

        bouton_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mcontext.startActivity(intent);
                dialog.cancel();

            }
        });
        bouton_quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quitter l'application :
                finish();
                System.exit(0);
            }
        });*/

        //boutons ok et annuler par défaut :

        alertdialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mcontext.startActivity(intent);
            }
        });

        alertdialog.setNegativeButton(" ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
                System.exit(0);
            }
        });
        AlertDialog d = alertdialog.show();
        Button b = d.getButton(DialogInterface.BUTTON_NEGATIVE);
        b.setText("Quitter");
        b.setTextColor(mcontext.getResources().getColor(R.color.gray2));
        b.setBackgroundColor(mcontext.getResources().getColor(R.color.snow_primary));


        Button b2 = d.getButton(DialogInterface.BUTTON_POSITIVE);
        b2.setText("Activer");
        b2.setTextColor(mcontext.getResources().getColor(R.color.gray2));
        b2.setBackgroundColor(mcontext.getResources().getColor(R.color.snow_primary));
        d.show();
    }

    public void stopUsingGPS(){
        if(locationmanager != null){
            locationmanager.removeUpdates(GPSTracker.this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}