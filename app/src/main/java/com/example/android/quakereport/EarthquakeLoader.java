package com.example.android.quakereport;


import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;



// created on 1 day before August 2017 playing 8BallPoo;
// Base loader Class for the Fetching the Data ;
public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {


    private String murl;
    public EarthquakeLoader(Context context, String url) {
        super(context);
        murl=url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        // TODO: Implement this method
        if(murl==null)
            return null;
        else
            {

        ArrayList<Earthquake> result=QueryUtils.Fentch_EarthQuak_Data(murl);
        return result;
    }


    }
}
