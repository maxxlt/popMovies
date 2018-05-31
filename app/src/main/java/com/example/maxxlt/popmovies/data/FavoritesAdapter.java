package com.example.maxxlt.popmovies.data;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.maxxlt.popmovies.DetailActivity;
import com.example.maxxlt.popmovies.R;
import com.squareup.picasso.Picasso;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private Cursor mCursor;
    private Context mContext;

    public FavoritesAdapter (Context mContext){
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public FavoritesAdapter.FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thumbnail_main,parent,false);
        FavoritesViewHolder favoritesViewHolder = new FavoritesViewHolder(view);
        return favoritesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.FavoritesViewHolder holder, int position) {
        int thumbnailIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_THUMBNAIL);
        int titleIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE);
        int releaseDateIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE);
        int backdropIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP);
        int overviewIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW);
        int voteCountIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_COUNT);
        int movieIdIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);

        mCursor.moveToPosition(position);

        Picasso.get().load("http://image.tmdb.org/t/p/w185"+ mCursor.getString(thumbnailIndex)).into(holder.thumbnail);
        final Intent intent = new Intent(mContext,DetailActivity.class);
        intent.putExtra("THUMBNAIL", mCursor.getString(thumbnailIndex));
        intent.putExtra("MOVIE_TITLE", mCursor.getString(titleIndex));
        intent.putExtra("RELEASE_DATE",mCursor.getString(releaseDateIndex));
        intent.putExtra("BACKDROP_PATH",mCursor.getString(backdropIndex));
        intent.putExtra("OVERVIEW",mCursor.getString(overviewIndex));
        intent.putExtra("VOTE_COUNT",mCursor.getDouble(voteCountIndex));
        intent.putExtra("ID",mCursor.getInt(movieIdIndex));
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    class FavoritesViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        public FavoritesViewHolder(View itemView){
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail_iv);
        }
    }
}
