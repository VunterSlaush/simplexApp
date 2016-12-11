package com.developments.vunterslaush.simplexapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;


public class TabsActivity extends FragmentActivity implements IUnityAdsListener
{
    private static final int MIN_SCROLLS_TO_SHOW_ADD = 15;
    TabsAdapter tabsAdapter;
    FloatingActionButton optionButton, btn1, btn2;
    ViewPager mViewPager;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        optionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        btn1 = (FloatingActionButton)findViewById(R.id.firstOption);
        btn2 = (FloatingActionButton)findViewById(R.id.floatingActionButton3);

        RevMobManager.getInstance().addBannerTo(this,(ViewGroup) findViewById(R.id.bannerLayoutTabs));

        mViewPager.setAdapter(tabsAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                if(Utils.getInstance().isApkSigned())
                onScrollPage();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        hideFixedButtons();

        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (btn1.getVisibility() == View.VISIBLE)
                    hideFixedButtons();
                else
                    showFixedButtons();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().takeScreenshot(TabsActivity.this);
            }
        });


    }

    private void onScrollPage()
    {
        Utils.getInstance().increaseSlides();
        if(Utils.getInstance().getSlideCount() % MIN_SCROLLS_TO_SHOW_ADD == 0)
        {
            if(!UnityAds.isInitialized())
                UnityAds.initialize(this,getString(R.string.unity_id),this);
            else
                UnityAds.show(this);
        }

        Crashlytics.log(Log.DEBUG,"VUNTER","/--/> "+getLocalClassName()+" onScrollPage() Slides:"+
                Utils.getInstance().getSlideCount());
    }

    private void hideFixedButtons()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
            }
        });
    }

    private void showFixedButtons()
    {
        btn1.setVisibility(View.VISIBLE);
        btn2.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        RevMobManager.getInstance().addBannerTo(this,(ViewGroup) findViewById(R.id.bannerLayoutTabs));
    }

    @Override
    public void onUnityAdsReady(String s)
    {

        UnityAds.show(this);
        Crashlytics.log(Log.DEBUG,"ADS","A Video Ad is Show Up!");
    }

    @Override
    public void onUnityAdsStart(String s) {

    }

    @Override
    public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {

    }

    @Override
    public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s)
    {

    }
}

class TabsAdapter extends FragmentStatePagerAdapter
{
    public TabsAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int i)
    {
        Fragment fragment = TabFragment.newInstance(i, i+1 == Tabla.getInstance().steps.size());
        return fragment;
    }

    @Override
    public int getCount()
    {
        return Tabla.getInstance().steps.size();
    }
}

