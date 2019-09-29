package com.training.popularmoviefinalapp.service;

import com.training.popularmoviefinalapp.model.MovieTrailerResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetMovieTrailerService {
    @GET("movie/{id}/videos")
    Call<MovieTrailerResults> getMovieTrailer(@Path("id") int movieId, @Query("api_key") String db_api_key);


}
