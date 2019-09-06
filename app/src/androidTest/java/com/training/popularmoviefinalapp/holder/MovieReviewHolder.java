package com.training.popularmoviefinalapp.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.training.popularmoviefinalapp.R;
import com.training.popularmoviefinalapp.model.Movie;
import com.training.popularmoviefinalapp.model.MovieReview;
import com.training.popularmoviefinalapp.service.MovieServiceClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.training.popularmoviefinalapp.activity.MainActivity.getMeasuredPosterHeight;
import static com.training.popularmoviefinalapp.activity.MainActivity.getScreenWidth;
import static com.training.popularmoviefinalapp.activity.MainActivity.movieImagePathBuilder;

public class MovieReviewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.movie_review_user) public TextView txMovieReviewUser;
    @BindView(R.id.cv_moviereview_cardview) public CardView cvMoviewReviewCard;
    @BindView(R.id.movie_review_content) public TextView txMoviewReviewContent;

    public MovieReviewHolder(View view) {
        super(view);
        ButterKnife.bind(this,view);
    }

    public void createBind(final MovieReview movieReview) {
        txMovieReviewUser.setText(movieReview.getAuthor()); //Author = user
        txMoviewReviewContent.setText(movieReview.getContent());


    }//end method

}
