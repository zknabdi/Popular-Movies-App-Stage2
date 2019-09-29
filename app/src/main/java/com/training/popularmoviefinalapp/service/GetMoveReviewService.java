package com.training.popularmoviefinalapp.service;

import com.training.popularmoviefinalapp.model.MovieReviewResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetMoveReviewService {
    @GET("movie/{id}/reviews")
    Call<MovieReviewResults> getMovieReviews(@Path("id") int movieId, @Query("api_key") String db_api_key);
}
