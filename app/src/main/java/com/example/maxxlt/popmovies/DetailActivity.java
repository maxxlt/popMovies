package com.example.maxxlt.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);
        PopulateUI();
    }
    private void PopulateUI(){

        ImageView backdrop = findViewById(R.id.backdrop_iv);
        ImageView thumbnail = findViewById(R.id.movie_poster_iv);
        TextView movieTitle = findViewById(R.id.movie_title_tv);
        TextView releaseDate = findViewById(R.id.movie_releaseDate_tv);
        TextView rating = findViewById(R.id.movie_rating_tv);
        TextView overview = findViewById(R.id.movie_overview_tv);

        Intent intent = getIntent();
        String thumbnailString = intent.getExtras().getString("THUMBNAIL");
        String movieTitleString = intent.getExtras().getString("MOVIE_TITLE");
        String releaseDateString = intent.getExtras().getString("RELEASE_DATE");
        String backdropString = intent.getExtras().getString("BACKDROP_PATH");
        String overviewString = intent.getExtras().getString("OVERVIEW");
        String vote = Double.toString(intent.getExtras().getDouble("VOTE_COUNT"));
        int movieID = intent.getExtras().getInt("ID");

        Picasso.get().load("http://image.tmdb.org/t/p/original"+backdropString).into(backdrop);
        Picasso.get().load("http://image.tmdb.org/t/p/w185"+thumbnailString).into(thumbnail);
        movieTitle.setText(movieTitleString);
        releaseDate.setText(releaseDateString);
        rating.setText(vote);
        overview.setText(overviewString);
    }
}
