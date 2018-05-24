package com.example.maxxlt.popmovies.data;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String API = "api_key";
    private static final String API_KEY = "4c3ebe95dd4d936b48ab51afa19e7cf3";
    public static URL buildURL(String sortPath){
        Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendPath(sortPath)
                .appendQueryParameter(API,API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.v(TAG,"Built URI: " + url);
        return url;
    }
    //Copied from Sunshine project
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
