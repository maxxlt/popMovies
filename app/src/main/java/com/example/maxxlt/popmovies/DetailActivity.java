package com.example.maxxlt.popmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.maxxlt.popmovies.data.MovieContract;
import com.example.maxxlt.popmovies.data.NetworkUtils;
import com.example.maxxlt.popmovies.extraData.Review;
import com.example.maxxlt.popmovies.extraData.ReviewAdapter;
import com.example.maxxlt.popmovies.extraData.Trailer;
import com.example.maxxlt.popmovies.extraData.TrailerAdapter;
import com.example.maxxlt.popmovies.extraData.retroData;
import com.example.maxxlt.popmovies.extraData.reviewResults;
import com.example.maxxlt.popmovies.extraData.trailerResults;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    FloatingActionButton floatingActionButton2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);

        Intent intent = getIntent();
        PopulateUI(intent);
        int movieID = intent.getExtras().getInt("ID");
        final RecyclerView trailerView = findViewById(R.id.recycler_trailer);
        final RecyclerView reviewView = findViewById(R.id.recycler_review);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.MOVIES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retroData retroData = retrofit.create(com.example.maxxlt.popmovies.extraData.retroData.class);
        Call<trailerResults> trailerCall = retroData.getListTrailers(movieID, NetworkUtils.API_KEY);
        Call<reviewResults> reviewCall = retroData.getListReviws(movieID, NetworkUtils.API_KEY);
        trailerCall.enqueue(new Callback<trailerResults>() {
            @Override
            public void onResponse(Call<trailerResults> call, Response<trailerResults> response) {
                trailerResults trailerResults = response.body();
                ArrayList<Trailer> trailerArrayList = new ArrayList<>(Arrays.asList(trailerResults.getResults()));
                trailerView.setAdapter(new TrailerAdapter(DetailActivity.this, trailerArrayList));
                trailerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
            }

            @Override
            public void onFailure(Call<trailerResults> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

        reviewCall.enqueue(new Callback<reviewResults>() {
            @Override
            public void onResponse(Call<reviewResults> call, Response<reviewResults> response) {
                reviewResults reviewResults = response.body();
                ArrayList<Review> reviewArrayList = new ArrayList<>(Arrays.asList(reviewResults.getResults()));
                reviewView.setAdapter(new ReviewAdapter(DetailActivity.this, reviewArrayList));
                reviewView.setLayoutManager(new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
            }

            @Override
            public void onFailure(Call<reviewResults> call, Throwable t) {

            }
        });
        buttonHandler(intent);

    }

    private void PopulateUI(Intent intent) {

        ImageView backdrop = findViewById(R.id.backdrop_iv);
        ImageView thumbnail = findViewById(R.id.movie_poster_iv);
        TextView releaseDate = findViewById(R.id.movie_releaseDate_tv);
        TextView rating = findViewById(R.id.movie_rating_tv);
        TextView overview = findViewById(R.id.movie_overview_tv);

        String thumbnailString = intent.getExtras().getString("THUMBNAIL");
        String movieTitleString = intent.getExtras().getString("MOVIE_TITLE");
        String releaseDateString = intent.getExtras().getString("RELEASE_DATE");
        String backdropString = intent.getExtras().getString("BACKDROP_PATH");
        String overviewString = intent.getExtras().getString("OVERVIEW");
        String vote = Double.toString(intent.getExtras().getDouble("VOTE_COUNT"));

        Picasso.get().load("http://image.tmdb.org/t/p/original" + backdropString).into(backdrop);
        Picasso.get().load("http://image.tmdb.org/t/p/w185" + thumbnailString).into(thumbnail);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_detail);


        toolbar.setTitle(movieTitleString);
        setSupportActionBar(toolbar);
        releaseDate.setText(releaseDateString);
        rating.setText(vote);
        overview.setText(overviewString);
    }

    private void buttonHandler(final Intent intent) {
        floatingActionButton = findViewById(R.id.floating_button);
        floatingActionButton2 = findViewById(R.id.floating_button2);
        final int movieID = intent.getExtras().getInt("ID");
        boolean foundMovie = lookUpMovie(movieID);
        if (foundMovie) {
            floatingActionButton.setVisibility(floatingActionButton.INVISIBLE);
            floatingActionButton2.setVisibility(floatingActionButton2.VISIBLE);
        } else {
            floatingActionButton.setVisibility(floatingActionButton.VISIBLE);
            floatingActionButton2.setVisibility(floatingActionButton2.INVISIBLE);
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String thumbnailString = intent.getExtras().getString("THUMBNAIL");
                String movieTitleString = intent.getExtras().getString("MOVIE_TITLE");
                String releaseDateString = intent.getExtras().getString("RELEASE_DATE");
                String backdropString = intent.getExtras().getString("BACKDROP_PATH");
                String overviewString = intent.getExtras().getString("OVERVIEW");
                String vote = Double.toString(intent.getExtras().getDouble("VOTE_COUNT"));
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieContract.MovieEntry.COLUMN_THUMBNAIL, thumbnailString);
                contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movieTitleString);
                contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, releaseDateString);
                contentValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP, backdropString);
                contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, overviewString);
                contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, vote);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieID);
                Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
                if (uri != null) {
                    Toast.makeText(getBaseContext(), "Movie is added to Favorites", Toast.LENGTH_LONG).show();
                }
                floatingActionButton.setVisibility(floatingActionButton.INVISIBLE);
                floatingActionButton2.setVisibility(floatingActionButton2.VISIBLE);
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idToDelete = Integer.toString(movieID);
                long movieDeleted = getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, "movieID = ?", new String[]{idToDelete});
                if (movieDeleted != 0) {
                    Toast.makeText(getBaseContext(), "Movie is deleted from Favorites", Toast.LENGTH_LONG).show();
                }
                floatingActionButton.setVisibility(floatingActionButton.VISIBLE);
                floatingActionButton2.setVisibility(floatingActionButton2.INVISIBLE);
            }
        });
    }

    private boolean lookUpMovie(int id) {
        String idToFind = Integer.toString(id);
        String[] projection = new String[]{"movieID"};
        Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, projection, "movieID = ?", new String[]{idToFind}, null);
        if (cursor.moveToFirst())
            return true;
        else
            return false;
    }
}
