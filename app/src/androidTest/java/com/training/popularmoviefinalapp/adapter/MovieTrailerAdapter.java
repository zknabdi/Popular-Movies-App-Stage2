package com.training.popularmoviefinalapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.training.popularmoviefinalapp.R;
import com.training.popularmoviefinalapp.holder.MovieTrailerHolder;
import com.training.popularmoviefinalapp.model.MovieTrailer;
import com.training.popularmoviefinalapp.service.MovieServiceClickListener;

import java.util.List;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerHolder> {

    private final MovieServiceClickListener movieTrailerServiceClickListener;
    private final List<MovieTrailer> movieTrailerList;

    public MovieTrailerAdapter(MovieServiceClickListener movieTrailerServiceClickListener, List<MovieTrailer> movieTrailerList) {
        this.movieTrailerServiceClickListener = movieTrailerServiceClickListener;
        this.movieTrailerList = movieTrailerList;
    }


    @NonNull
    @Override
    public MovieTrailerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_cardview, viewGroup, false);
        MovieTrailerHolder movieTrailerHolder = new MovieTrailerHolder(view);
        return movieTrailerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailerHolder movieTrailerHolder, int i) {
        MovieTrailer movieTrailer = this.movieTrailerList.get(i);
        movieTrailerHolder.createBind(movieTrailer, movieTrailerServiceClickListener);

    }

    @Override
    public int getItemCount() {
        return movieTrailerList.size();
    }

    @Override
    public void onViewRecycled(@NonNull MovieTrailerHolder holder) {
        super.onViewRecycled(holder);
    }
}
