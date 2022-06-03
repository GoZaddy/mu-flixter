package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.example.flixter.adapters.MovieAdapter;
import com.example.flixter.databinding.ActivityMainBinding;
import com.example.flixter.models.Movie;

import okhttp3.Headers;




public class MainActivity extends AppCompatActivity {

    public static final String ENDPOINT = "movie/now_playing";
    public static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    List<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();

        setContentView(view);

        RecyclerView rcView = binding.mainRV;

        movies = new ArrayList<Movie>();

        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        rcView.setAdapter(movieAdapter);
        rcView.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();

        String apiURL = getString(R.string.api_base_url)+ENDPOINT+"?api_key="+getString(R.string.tmdb_api_key)+"&language=en-US&page=1";

        client.get(apiURL, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, String response) {
                        JSONObject result;
                        try {
                            result = new JSONObject(response);
                            movies.addAll(Movie.fromJSONArray(result.getJSONArray("results")));
                            movieAdapter.notifyDataSetChanged();

                            // print movies for testing
//                            for(Movie movie: movies){
//                                movie.Print();
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "Hit json exception", e);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String errorResponse, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.i(TAG, "HTTP error. status code:"+Integer.toString(statusCode));
                    }
                }
        );



    }
}