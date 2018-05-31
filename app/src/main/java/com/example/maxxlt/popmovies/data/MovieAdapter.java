package com.example.maxxlt.popmovies.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.maxxlt.popmovies.DetailActivity;
import com.example.maxxlt.popmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    ArrayList<Movie> movieArrayList;
    Context context;
    public MovieAdapter (Context context, ArrayList<Movie> movies){
        this.context = context;
        this.movieArrayList = movies;
    }
    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thumbnail_main,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        final Movie movie = movieArrayList.get(position);
        String poster_url = "http://image.tmdb.org/t/p/w185" + movie.getThumbnail();
        Picasso.get().load(poster_url).into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentDetailActivity(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;

        public ViewHolder (View itemView){
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail_iv);
        }
    }

    private void intentDetailActivity(Movie movie){
        Intent intent = new Intent(context,DetailActivity.class);
        intent.putExtra("THUMBNAIL", movie.getThumbnail());
        intent.putExtra("MOVIE_TITLE", movie.getMovieTitle());
        intent.putExtra("RELEASE_DATE",movie.getReleaseDate());
        intent.putExtra("BACKDROP_PATH",movie.getBackdropPathPath());
        intent.putExtra("OVERVIEW",movie.getOverview());
        intent.putExtra("VOTE_COUNT",movie.getVoteCount());
        intent.putExtra("ID",movie.getMovieID());
        context.startActivity(intent);
    }

    public void setMovieArrayList(ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
    }

    //    public MovieAdapter(Activity context, ArrayList<Movie> movies){
//        super(context,0,movies);
//    }
//
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        final Movie movie = getItem(position);
//        if(convertView == null){
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.thumbnail_main, parent,false);
//        }
//        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail_iv);
//        String poster_url = "http://image.tmdb.org/t/p/w185" + movie.getThumbnail();
//        Picasso.get().load(poster_url).into(thumbnail);
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intentDetailActivity(movie);
//            }
//        });
//        return convertView;
//    }
//    private void intentDetailActivity(Movie movie){
//        Intent intent = new Intent(getContext(),DetailActivity.class);
//        intent.putExtra("THUMBNAIL", movie.getThumbnail());
//        intent.putExtra("MOVIE_TITLE", movie.getMovieTitle());
//        intent.putExtra("RELEASE_DATE",movie.getReleaseDate());
//        intent.putExtra("BACKDROP_PATH",movie.getBackdropPathPath());
//        intent.putExtra("OVERVIEW",movie.getOverview());
//        intent.putExtra("VOTE_COUNT",movie.getVoteCount());
//        intent.putExtra("ID",movie.getMovieID());
//        getContext().startActivity(intent);
//    }

}
