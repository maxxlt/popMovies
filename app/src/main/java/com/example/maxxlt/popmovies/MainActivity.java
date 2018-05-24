package com.example.maxxlt.popmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.maxxlt.popmovies.data.Movie;
import com.example.maxxlt.popmovies.data.MovieAdapter;
import com.example.maxxlt.popmovies.data.NetworkUtils;
import com.example.maxxlt.popmovies.data.parseMovieJson;

import java.net.URL;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {


    ArrayAdapter<String> spinnerAdapter;
    Spinner spinner;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        spinner = findViewById(R.id.spinner);
        gridView = findViewById(R.id.movies_grid);
        String sortName[] = {"",getResources().getString(R.string.sortPopular), getResources().getString(R.string.sortTopRated),getResources().getString(R.string.sortFavorite)};

        spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,sortName);
        spinner.setAdapter(spinnerAdapter);

        gridView.setAdapter(new MovieAdapter(this,new ArrayList<Movie>()));
        //setting default data to be "popular"
        loadData(getResources().getString(R.string.popular));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        loadData(getResources().getString(R.string.popular));
                        break;
                    case 2:
                        loadData(getResources().getString(R.string.top_rated));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //Loading data according to the sort
    private void loadData(String sort){
        gridView.setAdapter(new MovieAdapter(this,new ArrayList<Movie>()));
        new FetchMovieTask().execute(sort);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String>{
        private MovieAdapter adapter;

        //Handling HTTP request in background thread
        @Override
        protected String doInBackground(String... params) {
            if (params.length == 0)
                return null;
            String sortPath = params[0];
            URL movieRequestUrl = NetworkUtils.buildURL(sortPath);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                return jsonMovieResponse;

            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        //filling up our adapter with our movie arraylist
        @Override
        protected void onPostExecute(String jsonFile) {
            adapter = (MovieAdapter) gridView.getAdapter();
            ArrayList<Movie> movies = parseMovieJson.parsemovieJson(jsonFile);
            if (movies != null){
                adapter.addAll(movies);
            }
        }
    }
}