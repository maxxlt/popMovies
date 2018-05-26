package com.example.maxxlt.popmovies.extraData;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.maxxlt.popmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private static final String TAG = "TrailerAdapter";
    private ArrayList<Trailer> trailerArrayList;
    private Context context;

    public TrailerAdapter(Context context, ArrayList<Trailer> trailerArrayList){
        this.context = context;
        this.trailerArrayList = trailerArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_main,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.get().load("https://img.youtube.com/vi/"+trailerArrayList.get(position).getKey()+"/0.jpg").into(holder.videoThumbnail);
        holder.videoName.setText(trailerArrayList.get(position).getName());
        holder.trailerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://stackoverflow.com/questions/8840774/intent-to-youtube-app-profile-channel?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+trailerArrayList.get(position).getKey()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView videoThumbnail;
        TextView videoName;
        LinearLayout trailerLayout;
        public ViewHolder(View itemView){
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.trailer_thumbnail);
            videoName = itemView.findViewById(R.id.trailer_name);
            trailerLayout = itemView.findViewById(R.id.trailer_layout);
        }

    }
}
