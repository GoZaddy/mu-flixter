package com.example.flixter.adapters;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixter.MovieDetailsActivity;
import com.example.flixter.R;
import com.example.flixter.databinding.ItemMovieBinding;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;
    private static final String TAG = "MovieAdapter";
    private ItemMovieBinding binding;

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemMovieBinding.inflate(LayoutInflater.from(context));
//        View movieView = LayoutInflater.from(context).inflate(R.id., parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull ItemMovieBinding itemView) {
            super(itemView.getRoot());
            tvTitle = itemView.tvTitle;
            tvOverview = itemView.overviewTV;
            ivPoster = itemView.ivPoster;
            itemView.getRoot().setOnClickListener(this);
        }

        public void bind(Movie movie){
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageURL;
            int placeholder;

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageURL = movie.getBackdropPath();
                placeholder = R.drawable.flicks_backdrop_placeholder;
            } else {
                imageURL = movie.getPosterPath();
                placeholder = R.drawable.flicks_movie_placeholder;
            }

            int radius = 30;
            int margin = 10;

            Glide.with(context).
                    load(imageURL).
                    centerCrop().
                    transform(new RoundedCorners(20)).
                    placeholder(placeholder).
                    into(ivPoster);
        }

        @Override
        public void onClick(View v) {
            // gets item position
            Log.i(TAG, "on click ran");
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                System.out.println("short name: "+Movie.class.getSimpleName());
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }
        }
    }
}
