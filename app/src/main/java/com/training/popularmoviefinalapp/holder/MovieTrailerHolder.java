package com.training.popularmoviefinalapp.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.training.popularmoviefinalapp.R;
import com.training.popularmoviefinalapp.model.MovieTrailer;
import com.training.popularmoviefinalapp.service.MovieServiceClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieTrailerHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_movietrailer_name)
    public TextView tvMovieTrailerName;
    @BindView(R.id.cv_movietrailer_cardview)
    public CardView cvMovieTrailerCardView;

    public MovieTrailerHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void createBind(final MovieTrailer movieTrailer, final MovieServiceClickListener movieTrailerServiceClickListener) {
    tvMovieTrailerName.setText(movieTrailer.getName());
    itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            movieTrailerServiceClickListener.onMovieTrailerClicker(movieTrailer);
        }
    });
    }//end method

}
