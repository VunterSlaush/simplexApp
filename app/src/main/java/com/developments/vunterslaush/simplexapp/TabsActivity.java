package com.developments.vunterslaush.simplexapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;


public class TabsActivity extends FragmentActivity
{
    TabsAdapter tabsAdapter;
    ViewPager mViewPager;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(tabsAdapter);
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
        Fragment fragment = TabFragment.newInstance(i);
        return fragment;
    }

    @Override
    public int getCount()
    {
        return Tabla.getInstance().steps.size();
    }
}

