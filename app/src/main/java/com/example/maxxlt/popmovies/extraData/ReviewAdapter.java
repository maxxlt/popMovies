package com.example.maxxlt.popmovies.extraData;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maxxlt.popmovies.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    private static final String TAG = "ReviewAdapter";
    private ArrayList<Review> reviewArrayList;
    private Context context;

    public ReviewAdapter(Context context, ArrayList<Review> reviewArrayList){
        this.context = context;
        this.reviewArrayList = reviewArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_main,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.author.setText(reviewArrayList.get(position).getAuthor());
        holder.content.setText(reviewArrayList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView author;
        TextView content;
        public ViewHolder(View itemView){
            super(itemView);
            author = itemView.findViewById(R.id.review_author);
            content = itemView.findViewById(R.id.review_content);
        }
    }
}
