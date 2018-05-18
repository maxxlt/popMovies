package com.example.maxxlt.popmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class parseMovieJson {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    public static ArrayList<Movie> parsemovieJson(String json){

        try {
            ArrayList<Movie> movieList = new ArrayList<>();
            JSONObject read = (JSONObject) new JSONTokener(json).nextValue();
            JSONArray array = read.getJSONArray("results");
            for (int i = 0; i < array.length(); i++){
                JSONObject movie = array.getJSONObject(i);
                movieList.add(new Movie(movie.getString("poster_path"),movie.getString("title"),movie.getString("release_date"),movie.getString("backdrop_path"),movie.getString("overview"),movie.getDouble("vote_average"),movie.getInt("id")));
            }
            return movieList;
        }
        catch (Exception e){
            Log.v(LOG_TAG,"Caught Exception :)");
            e.printStackTrace();
        }

        return null;
    }
}
