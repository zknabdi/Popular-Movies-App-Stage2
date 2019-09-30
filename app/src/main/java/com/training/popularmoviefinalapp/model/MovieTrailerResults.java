package com.training.popularmoviefinalapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieTrailerResults implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private ArrayList<MovieTrailer> MovieTrailerResults;
    //private final static long serialVersionUID = -1001810300984621272L;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<MovieTrailer> getMovieTrailerResults() {
        return MovieTrailerResults;
    }

    public void setMovieTrailerResults(ArrayList<MovieTrailer> movieTrailerResults) {
        MovieTrailerResults = movieTrailerResults;
    }
}
