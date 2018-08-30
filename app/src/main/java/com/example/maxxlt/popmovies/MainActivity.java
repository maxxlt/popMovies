package com.example.maxxlt.popmovies;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.maxxlt.popmovies.data.FavoritesAdapter;
import com.example.maxxlt.popmovies.data.Movie;
import com.example.maxxlt.popmovies.data.MovieAdapter;
import com.example.maxxlt.popmovies.data.MovieContract;
import com.example.maxxlt.popmovies.data.NetworkUtils;
import com.example.maxxlt.popmovies.data.parseMovieJson;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayAdapter<String> spinnerAdapter;
    Spinner spinner;
    RecyclerView mRecyclerView;
    FavoritesAdapter favoritesAdapter;
    private static final int FAVORITES_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        spinner = findViewById(R.id.spinner);
        mRecyclerView = findViewById(R.id.movies_grid);
        String sortName[] = {"", getResources().getString(R.string.sortPopular), getResources().getString(R.string.sortTopRated), getResources().getString(R.string.sortFavorite)};

        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sortName);
        spinner.setAdapter(spinnerAdapter);


        //setting default data to be "popular"
        loadData(getResources().getString(R.string.popular));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        loadData(getResources().getString(R.string.popular));
                        break;
                    case 2:
                        loadData(getResources().getString(R.string.top_rated));
                        break;
                    case 3:
                        loadLoader();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (spinner.getSelectedItemPosition() == 3) {
            getSupportLoaderManager().restartLoader(FAVORITES_LOADER_ID, null, MainActivity.this);
        }
    }

    //Loading data according to the sort
    private void loadData(String sort) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(new MovieAdapter(this, new ArrayList<Movie>()));
        new FetchMovieTask().execute(sort);
    }

    private void loadLoader() {
        favoritesAdapter = new FavoritesAdapter(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        getSupportLoaderManager().initLoader(FAVORITES_LOADER_ID, null, this);
        mRecyclerView.setAdapter(favoritesAdapter);
        getSupportLoaderManager().restartLoader(FAVORITES_LOADER_ID, null, MainActivity.this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        //Used from T09.07 To-Do list Exercise
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mFavoritesData = null;

            @Override
            protected void onStartLoading() {
                if (mFavoritesData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mFavoritesData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mFavoritesData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        favoritesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        favoritesAdapter.swapCursor(null);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String> {
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

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        //filling up our adapter with our movie arraylist
        @Override
        protected void onPostExecute(String jsonFile) {
            adapter = (MovieAdapter) mRecyclerView.getAdapter();
            ArrayList<Movie> movies = parseMovieJson.parsemovieJson(jsonFile);
            if (movies != null) {
                adapter.setMovieArrayList(movies);
                adapter.notifyDataSetChanged();
            }
        }
    }
}