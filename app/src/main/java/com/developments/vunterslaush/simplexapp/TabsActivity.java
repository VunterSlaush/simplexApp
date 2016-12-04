package com.developments.vunterslaush.simplexapp;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;


public class TabsActivity extends FragmentActivity
{
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
        mViewPager.setAdapter(tabsAdapter);
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

