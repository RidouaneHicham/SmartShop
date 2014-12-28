package com.hichamridouane.smartshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * Created by S813046 on 21/12/2014.
 */
public class RadiusSettings extends DialogFragment {

    TextView distance;
    int distanceValue = 50;


    public interface radiuslistener {
        public void onRadiusOkay(int radius);
        public void onRadiusCancel();
    }
    radiuslistener mlistener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // ensure that the host activity implements the callback interface
        try {
            // Instantiate the dialogListener so we can send events to the host
            mlistener = (radiuslistener) activity;
        } catch (ClassCastException e) {
            // if activity doesn't implement the interface, throw an exception
            throw new ClassCastException(activity.toString()
                    + " must implement radiuslistener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.custom_radius,null);


        builder.setTitle("Paramètres de recherche")
                .setIcon(R.drawable.ic_gps)
                .setView(v);
        distance = (TextView)v.findViewById(R.id.distance_text);
        SeekBar seekBar = (SeekBar)v.findViewById(R.id.distance_seekbar);
        seekBar.setMax(200);
        seekBar.setProgress(distanceValue);
        distance.setText("Rayon de recherche : "+distanceValue+ " Kilomètres");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance.setText("Rayon de recherche : "+Integer.toString(progress)+" Kilomètres");
                distanceValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

                        // Set the action buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mlistener.onRadiusOkay(distanceValue);
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //mlistener.onRadiusCancel();
                        dialog.cancel();
                    }
                });
        final AlertDialog d = builder.show();
        int dividerId = d.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = d.findViewById(dividerId);
        divider.setBackgroundColor(getResources().getColor(R.color.very_light_gray));

        int textViewId = d.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
        TextView tv = (TextView) d.findViewById(textViewId);
        tv.setTextColor(getResources().getColor(R.color.hot_orange));
        tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf"));

        Button b = d.getButton(DialogInterface.BUTTON_NEGATIVE);
        b.setTextColor(getResources().getColor(R.color.gray2));
        b.setBackgroundColor(getResources().getColor(R.color.snow_primary));

        Button b2 = d.getButton(DialogInterface.BUTTON_POSITIVE);
        b2.setTextColor(getResources().getColor(R.color.gray2));
        b2.setBackgroundColor(getResources().getColor(R.color.snow_primary));


        return d;

    }

}
