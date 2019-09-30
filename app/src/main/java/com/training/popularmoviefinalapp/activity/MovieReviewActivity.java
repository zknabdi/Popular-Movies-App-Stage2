package com.training.popularmoviefinalapp.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.training.popularmoviefinalapp.R;
import com.training.popularmoviefinalapp.adapter.MovieReviewAdapter;
import com.training.popularmoviefinalapp.model.MovieReview;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieReviewActivity extends AppCompatActivity {

    @BindView(R.id.rv_movie_reviews)
    RecyclerView movieReviewRecylerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_review);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        getMovieReviewIntent();
    }

    private void getMovieReviewIntent() {
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        assert b !=null;
        ArrayList<MovieReview> movieReviewArrayList = (ArrayList<MovieReview>) b.getSerializable("reviews");
        String movieTitle = b.getString("movie_title");
        setTitle(movieTitle + getString(R.string.reviewer_name));

        MovieReviewAdapter movieReviewAdapter = new MovieReviewAdapter(movieReviewArrayList);
        movieReviewRecylerView.setLayoutManager(new LinearLayoutManager(this));
        movieReviewRecylerView.setAdapter(movieReviewAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
