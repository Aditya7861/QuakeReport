
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<ArrayList<Earthquake>>{

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2017-01-01&endtime=2017-09-25&minfelt=50&minmagnitude=5";
    private static final int EATHQUAKE_LOADER_ID = 0;

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    private TextView mEmptyStateTextView;


    ListView listView;
    ArrayList<Earthquake> earthQuakes;
    EarthquakAdaptor earthquakAdaptor;

    /*
    * Up here i am initializing the list view and ArrayList of type EarthQuake for storing the data of the EarthQuake
    * And one earthQuakAdaptor for setting up the earthquake adaptor and the list view,adaptor are use to insert the data in
    * the list view with the help of custom Adaptor
    *
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        listView = (ListView) findViewById(R.id.list);
        earthQuakes = new ArrayList<>();
        earthquakAdaptor = new EarthquakAdaptor(this, earthQuakes);
        listView.setAdapter(earthquakAdaptor);

        /*
            Up here i am creating the object of the earthQuakes and adaptor class and the setting the Arraylist in the adaptor
             and the setting adaptor in the view

        * */

         // for showing empty view


        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

//      this is for checking network connectivity for the app

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected())
        {

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EATHQUAKE_LOADER_ID, null, this);
        }
        else {

         /*   mEmptyStateTextView.setText(R.string.no_internet_connection);
            ProgressBar progressBar=(ProgressBar)findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);

*/
        }


    }

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    /*Here we are using loader for Better optimization*/

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        // TODO: Create a new loader for the given URL

        return new EarthquakeLoader(EarthquakeActivity.this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {
        // TODO: Update the UI with the result

        earthquakAdaptor=new EarthquakAdaptor(EarthquakeActivity.this,earthquakes);

        ListView listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(earthquakAdaptor);

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_earthquake);
/*

        ProgressBar progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
*/


    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        // TODO: Loader reset, so we can clear out our existing data.

        earthquakAdaptor.clear();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}


