package com.training.popularmoviefinalapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.training.popularmoviefinalapp.R;
import com.training.popularmoviefinalapp.holder.MovieReviewHolder;
import com.training.popularmoviefinalapp.model.MovieReview;

import java.util.List;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewHolder> {

    private final List<MovieReview> movieReviewsList;

    public MovieReviewAdapter(List<MovieReview> movieReviewsList) {
        this.movieReviewsList = movieReviewsList;
    }


    @NonNull
    @Override
    public MovieReviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.moviereview_cardview, viewGroup, false);
        MovieReviewHolder movieReviewHolder = new MovieReviewHolder(view);
        return movieReviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewHolder movieViewHolder, int i) {
        MovieReview movieReview = this.movieReviewsList.get(i);
        movieViewHolder.createBind(movieReview);

    }

    @Override
    public int getItemCount() {
        return movieReviewsList.size();
    }

    @Override
    public void onViewRecycled(@NonNull MovieReviewHolder holder) {
        super.onViewRecycled(holder);
    }
}
