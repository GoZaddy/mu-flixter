package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;
import com.example.flixter.databinding.ActivityMovieTrailerBinding;
import com.example.flixter.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {
    private ActivityMovieTrailerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieTrailerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final String videoId = "tKodtNFpzBA";

        YouTubePlayerView playerView = binding.player;

        AsyncHttpClient client = new AsyncHttpClient();

        String videoID = (String) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));


        playerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                System.out.println("video id: "+videoID);
                youTubePlayer.cueVideo(videoID);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player: " + youTubeInitializationResult.toString());
            }
        });


    }


}