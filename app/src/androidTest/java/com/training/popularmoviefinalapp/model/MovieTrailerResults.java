package com.training.popularmoviefinalapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MovieTrailerResults implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private List<MovieTrailer> MovieTrailerResults;
    //private final static long serialVersionUID = -1001810300984621272L;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieTrailer> getMovieTrailerResults() {
        return MovieTrailerResults;
    }

    public void setMovieTrailerResults(List<MovieTrailer> movieTrailerResults) {
        MovieTrailerResults = movieTrailerResults;
    }
}
