package com.training.popularmoviefinalapp.service;

import com.training.popularmoviefinalapp.model.MovieResults;
import com.training.popularmoviefinalapp.model.MovieReviewResults;
import com.training.popularmoviefinalapp.model.MovieTrailerResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface GetMovieService {
    @GET("movie/popular")
    Call<MovieResults> getMoviesByPopularity(@Query("page") int page, @Query("api_key") String db_api_key);

    @GET("movie/top_rated")
    Call<MovieResults> getMoviesByTopRated(@Query("page") int page, @Query("api_key") String db_api_key);

    /**
     *
     * @param movieId
     * @param db_api_key
     * @see "https://api.themoviedb.org/3/movie/{movie_id}/reviews?api_key=<<api_key>>&language=en-US&page=1"
     * @return
     */
    @GET("movie/{id}/reviews")
    Call<MovieReviewResults> getMovieReviews(@Path("id") int movieId, @Query("api_key") String db_api_key);

    @GET("movie/{id}/videos")
    Call<MovieTrailerResults> getMovieTrailer(@Path("id") int movieId, @Query("api_key") String db_api_key);


}
