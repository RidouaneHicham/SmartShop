package com.hichamridouane.smartshop.fragments;

/**
 * Created by Hicham Ridouane on 08/07/2014.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hichamridouane.smartshop.R;


public class Fragment1 extends Fragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment1, null);
        return v;
    }
}
