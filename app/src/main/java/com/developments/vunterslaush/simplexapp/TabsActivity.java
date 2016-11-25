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
    List<SolutionStep> steps;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager(),steps);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(tabsAdapter);
    }
}

class TabsAdapter extends FragmentStatePagerAdapter
{
    List<SolutionStep> steps;
    public TabsAdapter(FragmentManager fm, List<SolutionStep> steps)
    {
        super(fm);
        this.steps = steps;
    }

    @Override
    public Fragment getItem(int i)
    {
        Fragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putParcelable("Step",steps.get(i));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount()
    {
        return steps.size();
    }
}

// Instances of this class are fragments representing a single
// object in our collection.

