package com.hichamridouane.smartshop.controller;

import com.hichamridouane.smartshop.view.Fragment1;
import com.hichamridouane.smartshop.view.Fragment2;
import com.hichamridouane.smartshop.view.Fragment3;
import com.viewpagerindicator.IconPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter{

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getIconResId(int index) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Fragment getItem(int position)
    {
        // TODO Auto-generated method stub
        Fragment fragment = new Fragment1();
        switch(position){
            case 0:
                fragment = new Fragment1();
                break;
            case 1:
                fragment = new Fragment2();
                break;
            case 2:
                fragment = new Fragment3();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String title = "";
        switch(position){
            case 0:
                title = "première vue";
                break;
            case 1:
                title = "deuxième vue";
                break;
            case 2:
                title = "3ème vue";
                break;
        }
        return title;
    }

}