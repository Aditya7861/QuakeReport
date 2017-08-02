package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils()
    {

    }

    /*Fentch EarthQuake data from the url*/

    public static ArrayList<Earthquake> Fentch_EarthQuak_Data(String request_url) {
        URL url = createUrl(request_url);
        String jsonresponse = null;

        /*
        * THis block of code for force delaying the data for checking the progressbar wroking
        * */
      /*  try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


        try {
            jsonresponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        ArrayList<Earthquake> earthquak = extractFeatureFromJson(jsonresponse);
        return earthquak;

    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@Jearthquakes Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    private static ArrayList<Earthquake> extractFeatureFromJson(String earthquakeJSON) {
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }
        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray featureArray = baseJsonResponse.getJSONArray("features");

            for (int i = 0; i < featureArray.length(); i++) {
                JSONObject currentEarthquake = featureArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");

                double magnitude = properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String Url = properties.getString("url");
                long date = properties.getLong("time");
                Earthquake earthquake = new Earthquake(magnitude, location, date, time, Url);
                earthquakes.add(earthquake);
              //  Log.e("earthquake_place",earthquake.getPlace());
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error in fetching data", e);
        }
        return earthquakes;
    }
}

