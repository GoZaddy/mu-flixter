package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;
import com.example.flixter.databinding.ActivityMovieDetailsBinding;
import com.example.flixter.models.Movie;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;

    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView poster;
    private ActivityMovieDetailsBinding binding;
    private String videoID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tvTitle = binding.tvTitle;
        tvOverview = binding.tvOverview;
        rbVoteAverage = binding.rbVoteAverage;
        poster = binding.imageDetailsPoster;



        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        Glide.with(this).load(movie.getPosterPath()).placeholder(R.drawable.flicks_movie_placeholder).into(poster);

        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);

        AsyncHttpClient client = new AsyncHttpClient();

        String apiURL = getString(R.string.api_base_url)+"movie/"+Integer.toString(movie.getId())+"/videos?api_key="+getString(R.string.tmdb_api_key)+"&language=en-US&page=1";

        client.get(apiURL, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, String response) {
                        try {
                            videoID = (new JSONObject(response)).getJSONArray("results").getJSONObject(0).getString("key");
                        } catch (JSONException e) {
                            videoID = null;
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String errorResponse, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        videoID = null;
                        Log.i("MovieTrailerActivity", "HTTP error. status code:"+Integer.toString(statusCode));
                    }
                }
        );
    }

    public void openVideo(View view){
        Integer movieID = movie.getId();
        System.out.println("movie id: "+movie.getId());
        if (movieID != null){
            Intent intent = new Intent(this, MovieTrailerActivity.class);
            // serialize the movie using parceler, use its short name as a key
            intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(videoID));
            // show the activity
            this.startActivity(intent);
        } else {
            Toast.makeText(this, "No videos available!", Toast.LENGTH_SHORT).show();
        }

    }

}