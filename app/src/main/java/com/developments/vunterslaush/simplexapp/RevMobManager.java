package com.developments.vunterslaush.simplexapp;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;
import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.ads.banner.RevMobBanner;

/**
 * Created by Slaush on 10/12/2016.
 */

public class RevMobManager
{
    private static String TAG = "RevMob";

    private RevMob revmob;
    private RevMobBanner banner;
    private static RevMobManager instance;
    private Activity activity;
    private ViewGroup container;

    private RevMobAdsListener bannerListener =  new RevMobAdsListener(){
        @Override
        public void onRevMobAdReceived()
        {
            showBanner();

        }
        @Override
        public void onRevMobAdNotReceived(String message) {
            Crashlytics.log(Log.DEBUG,TAG,"/--/> RevMob ERROR Didnt receive the ad ..");
        }
        @Override
        public void onRevMobAdDisplayed()
        {
            Crashlytics.log(Log.DEBUG,TAG,"/--/> RevMob Ad Is Displayed!");
        }
    };

    private RevMobManager(){}

    public static RevMobManager getInstance()
    {
        if(instance == null)
            instance = new RevMobManager();
        return instance;
    }

    public void addBannerTo(Activity act, ViewGroup container)
    {
        setArgumentsToInstance(act,container);
        if(revmob == null)
        {
            revmob = RevMob.startWithListener(activity, new RevMobAdsListener() {
                @Override
                public void onRevMobSessionStarted()
                {
                    loadBanner(); // Cache the banner once the session is started
                }
                @Override
                public void onRevMobSessionNotStarted(String message)
                {
                    System.out.println("Revmob session algo?");

                }
            }, activity.getString(R.string.revmob_id));
        }
        else
        {
            putBannerOnNewLayout();
        }

        System.out.println("AddBannerTO!");
    }

    private void putBannerOnNewLayout()
    {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewGroup v = (ViewGroup) banner.getParent();
                v.removeAllViews();
                addBannerToContainer();
            }
        });
    }

    private void setArgumentsToInstance(Activity act, ViewGroup container)
    {
        this.activity = act;
        this.container = container;
    }

    public void loadBanner()
    {
        banner = revmob.preLoadBanner(activity,bannerListener);
    }
    private void showBanner()
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                addBannerToContainer();
            }
        });
    }

    private void addBannerToContainer()
    {
        container.addView(banner);
        banner.show();
    }

    public boolean isBannedShown()
    {
        return banner != null && banner.isShown();
    }

}
