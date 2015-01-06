package com.hichamridouane.smartshop.model;

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
import android.widget.CheckBox;
import android.widget.TextView;

import com.cengalabs.flatui.views.FlatButton;
import com.cengalabs.flatui.views.FlatCheckBox;
import com.hichamridouane.smartshop.R;

import java.util.ArrayList;


/**
 * Created by Hicham on 08/10/2014.
 */
public class TimelineSettings extends DialogFragment {
    ArrayList<Integer> selected_categories = new ArrayList<Integer>();
    boolean[] itemsChecked = {false, false, false, false, false, false};
    private FlatCheckBox fourniture,nourriture,voyages,habillement,medias,autres;
    private FlatButton settings_btn,quit_btn;


    public interface dialoglistener {
        public void onOkay(ArrayList<Integer> selected);
        public void onCancel();
    }
    dialoglistener mlistener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // ensure that the host activity implements the callback interface
        try {
            // Instantiate the dialogListener so we can send events to the host
            mlistener = (dialoglistener) activity;
        } catch (ClassCastException e) {
            // if activity doesn't implement the interface, throw an exception
            throw new ClassCastException(activity.toString()
                    + " must implement dialogListener");
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        for(int i=0;i<=itemsChecked.length;i++){
            if(selected_categories.contains((String)String.valueOf(i)))
                itemsChecked[i]=true;
        }
                        builder.setMultiChoiceItems(R.array.categories, itemsChecked,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int indexselected,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    if (!selected_categories.contains(indexselected)) {
                                        selected_categories.add(indexselected);
                                        itemsChecked[indexselected] = true;
                                    }
                                } else if (selected_categories.contains(indexselected)) {
                                    // Else, if the item is already in the array, remove it
                                    selected_categories.remove(indexselected);
                                    itemsChecked[indexselected] = false;
                                }
                            }
                        })
                .setTitle("Choisissez vos préférences")
                .setIcon(R.drawable.ic_shopping_bag_50)
                        // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //checkboxState();
                        mlistener.onOkay(selected_categories);
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mlistener.onCancel();
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