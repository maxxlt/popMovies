package com.example.maxxlt.popmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    public MovieAdapter(Activity context, ArrayList<Movie> movies){
        super(context,0,movies);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Movie movie = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.thumbnail_main, parent,false);
        }
        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail_iv);
        String poster_url = "http://image.tmdb.org/t/p/w185" + movie.getThumbnail();
        Picasso.get().load(poster_url).into(thumbnail);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentDetailActivity(movie);
            }
        });
        return convertView;
    }
    private void intentDetailActivity(Movie movie){
        Intent intent = new Intent(getContext(),DetailActivity.class);
        intent.putExtra("THUMBNAIL", movie.getThumbnail());
        intent.putExtra("MOVIE_TITLE", movie.getMovieTitle());
        intent.putExtra("RELEASE_DATE",movie.getReleaseDate());
        intent.putExtra("BACKDROP_PATH",movie.getBackdropPathPath());
        intent.putExtra("OVERVIEW",movie.getOverview());
        intent.putExtra("VOTE_COUNT",movie.getVoteCount());
        intent.putExtra("ID",movie.getMovieID());
        getContext().startActivity(intent);
    }

}
