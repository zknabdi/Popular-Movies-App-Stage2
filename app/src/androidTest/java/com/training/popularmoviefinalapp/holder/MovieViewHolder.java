package com.training.popularmoviefinalapp.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.training.popularmoviefinalapp.R;
import com.training.popularmoviefinalapp.model.Movie;
import com.training.popularmoviefinalapp.service.MovieServiceClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.training.popularmoviefinalapp.activity.MainActivity.getMeasuredPosterHeight;
import static com.training.popularmoviefinalapp.activity.MainActivity.getScreenWidth;
import static com.training.popularmoviefinalapp.activity.MainActivity.movieImagePathBuilder;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.im_movie_poster) public ImageView imMoviePoster;
    @BindView(R.id.cv_movie_card) public CardView cvMovieCard;

    public MovieViewHolder(View view) {
        super(view);
        ButterKnife.bind(this,view);
    }

    public void createBind(final Movie movie, final MovieServiceClickListener movieServiceClickListener) {

        cvMovieCard.setLayoutParams(new ViewGroup.LayoutParams(getScreenWidth()/2, getMeasuredPosterHeight(getScreenWidth()/2)));

    Picasso.with(imMoviePoster.
            getContext()).
            load(movieImagePathBuilder(movie.getPosterPath())).
            placeholder(R.drawable.ic_launcher_background).
            fit().centerCrop().into(imMoviePoster);
    itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            movieServiceClickListener.onMovieClicker(movie);
        }
    });
    }//end method

}
