package com.training.popularmoviefinalapp.service;

import com.training.popularmoviefinalapp.model.Movie;
import com.training.popularmoviefinalapp.model.MovieTrailer;

public interface MovieServiceClickListener {
    void onMovieTrailerClicker(MovieTrailer movieTrailer);
    void onMovieClicker (Movie movie);
}
