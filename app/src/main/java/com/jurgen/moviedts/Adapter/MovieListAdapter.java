package com.jurgen.moviedts.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jurgen.moviedts.Activity.Details;
import com.jurgen.moviedts.HTTP.API;
import com.jurgen.moviedts.Model.MovieModel;
import com.jurgen.moviedts.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MovieModel> arrayList;
    private Integer mKategori;

    public MovieListAdapter(Context context, ArrayList<MovieModel> arrayList, Integer mKategori) {
        this.context = context;
        this.arrayList = arrayList;
        this.mKategori = mKategori;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieModel model = arrayList.get(position);

        Glide.with(context).load(API.IMAGE_URL + model.getPosterPath()).into(holder.imageView);

        holder.title.setText(model.getTitle());
        holder.release.setText(model.getReleaseDate());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, Details.class)
                        .putExtra("KATEGORI", mKategori)
                        .putExtra("MOVIE_ID", model.getMovie_id())
                        .putExtra("POSTER_URL", model.getPosterPath())
                        .putExtra("BACKDROP_URL", model.getBackdropPath())
                        .putExtra("TITLE", model.getTitle())
                        .putExtra("RELEASE_DATE", model.getReleaseDate())
                        .putExtra("VOTE_AVERAGE", model.getVoteAverage())
                        .putExtra("OVERVIEW", model.getOverview()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.container)
        LinearLayout container;
        @BindView(R.id.img)
        ImageView imageView;
        @BindView(R.id.item_txt_title)
        TextView title;
        @BindView(R.id.item_txt_release)
        TextView release;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
