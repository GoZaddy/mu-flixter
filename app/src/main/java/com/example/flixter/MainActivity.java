package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;

import okhttp3.Headers;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String apiKey = "7fad9ede458a27ae5c62e0acdb973339";
        client.get("https://api.themoviedb.org/3/movie/now_playing?api_key="+apiKey+"&language=en-US&page=1", new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, String response) {
                        // called when response HTTP status is "200 OK"
                        System.out.println(response);
                        System.out.println(headers.size());
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String errorResponse, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        System.out.println("status code:"+Integer.toString(statusCode));
                    }
                }
        );


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}