package com.hichamridouane.smartshop;

/**
 * Created by Hicham Ridouane on 05/07/2014.
 */
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

import android.app.Application;

public class ParseApplication extends Application {

    static final String TAG = "MyApp";
    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.initialize(this, "eUxEGbrPnNmn0UMdp070lopZHlHuyXtr0llNJmZF", "gAaIiqunLDMcysQiIPAIZCB0FJAsImYk1JGTVq34") ;
        ParseFacebookUtils.initialize(getString(R.string.APP_ID));
        PushService.setDefaultPushCallback(this,MainActivity.class);

        ParseInstallation.getCurrentInstallation().saveInBackground();


    }

}
