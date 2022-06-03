package com.example.flixter.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    Integer id;
    Double voteAverage;
    String backdropPath;
    String posterPath;
    String title;
    String overview;

    public Movie(){

    }

    public Movie(Integer id, String posterPath, String title, String overview, String backdropPath, Double voteAverage){
        this.id = id;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.title = title;
        this.overview = overview;
        this.voteAverage = voteAverage;
    }
    public static Movie fromJSON(JSONObject jsonObject) throws JSONException {
        return new Movie(jsonObject.getInt("id"),jsonObject.getString("poster_path"), jsonObject.getString("title"), jsonObject.getString("overview"), jsonObject.getString("backdrop_path"), jsonObject.getDouble("vote_average"));
    }
    public static List<Movie> fromJSONArray(JSONArray jsonArray) throws JSONException {
        List<Movie> output = new ArrayList<Movie>();
        for (int i = 0; i < jsonArray.length(); ++i){
            output.add(Movie.fromJSON(jsonArray.getJSONObject(i)));
        }
        return output;
    }

    public void Print(){
        Log.i("MainActivity", "Title: " + this.title);
        System.out.println("ID: " + this.id);
        System.out.println("Poster Path: " + this.posterPath);
        System.out.println("Backdrop Path: " + this.backdropPath);
        System.out.println("Overview: " + this.overview);
        System.out.println("");
    }

    public String getPosterPath(){
        return String.format("https://image.tmdb.org/t/p/w342/%s", this.posterPath);
    }

    public String getBackdropPath(){
        return String.format("https://image.tmdb.org/t/p/w342/%s", this.backdropPath);
    }

    public String getTitle(){
        return this.title;
    }

    public String getOverview(){
        return this.overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Integer getId() {
        return id;
    }
}
