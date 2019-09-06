package com.training.popularmoviefinalapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.training.popularmoviefinalapp.R;
import com.training.popularmoviefinalapp.holder.MovieViewHolder;
import com.training.popularmoviefinalapp.model.Movie;
import com.training.popularmoviefinalapp.service.MovieServiceClickListener;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private final MovieServiceClickListener movieServiceClickListener;
    private final List<Movie> movieList;

    public MovieAdapter(MovieServiceClickListener movieServiceClickListener, List<Movie> movieList) {
        this.movieServiceClickListener = movieServiceClickListener;
        this.movieList = movieList;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_cardview, viewGroup, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movie movie = this.movieList.get(i);
        movieViewHolder.createBind(movie, movieServiceClickListener);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public void onViewRecycled(@NonNull MovieViewHolder holder) {
        super.onViewRecycled(holder);
    }
}
