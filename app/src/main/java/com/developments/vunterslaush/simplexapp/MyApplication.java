package com.developments.vunterslaush.simplexapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by Slaush on 27/12/2016.
 */

public class MyApplication extends Application
{
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getInstance()
    {
        return instance.getApplicationContext();
    }
}