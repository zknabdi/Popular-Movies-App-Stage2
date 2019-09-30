package com.training.popularmoviefinalapp.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.training.popularmoviefinalapp.activity.MainActivity.movieImagePathBuilder;

import com.squareup.picasso.Picasso;
import com.training.popularmoviefinalapp.R;
import com.training.popularmoviefinalapp.adapter.MovieReviewAdapter;
import com.training.popularmoviefinalapp.adapter.MovieTrailerAdapter;
import com.training.popularmoviefinalapp.model.Movie;
import com.training.popularmoviefinalapp.model.MovieReview;
import com.training.popularmoviefinalapp.model.MovieReviewResults;
import com.training.popularmoviefinalapp.model.MovieTrailer;
import com.training.popularmoviefinalapp.model.MovieTrailerResults;
import com.training.popularmoviefinalapp.moviedb.FavoriteDBCreator;
import com.training.popularmoviefinalapp.service.GetMoveReviewService;
import com.training.popularmoviefinalapp.service.GetMovieTrailerService;
import com.training.popularmoviefinalapp.service.MovieTrailerServiceClickerList;
import com.training.popularmoviefinalapp.service.RetrofitInstance;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends AppCompatActivity {

    public static String YOUTUBE = "http://www.youtube.com/watch?v=";
    private Movie myMovie;
    private MovieTrailerAdapter movieTrailerAdapter;
    private ArrayList<MovieTrailer> movieTrailerArrayList;
    private ArrayList<MovieReview> movieReviewArrayList;
    private MovieReviewAdapter movieReviewAdapter;


    private static final String TAG = "MovieActivity";
    @BindView(R.id.tv_movie_trailer)
    RecyclerView movieTrailerRecylerView;
    @BindView(R.id.movie_title)
    TextView txMovieTitle;
    @BindView(R.id.movie_poster_im)
    ImageView imMoviePoster;
    @BindView(R.id.movie_descriptions_tv)
    TextView tvMoveDescOverview;
    @BindView(R.id.movie_released_tv)
    TextView txMovieReleaseDate;
    @BindView(R.id.movie_rate_tv)
    TextView txMovieRate;
    @BindView(R.id.movie_activity_trailer_label)
    TextView txMovieTrailer;
    @BindView(R.id.movie_activity_review_reader)
    TextView txMovieReviewRead;
    @BindView(R.id.movie_activity_favorite)
    FloatingActionButton movieFavoriteActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ButterKnife.bind(this);

        movieTrailerRecylerView.setLayoutManager(new LinearLayoutManager(this));
        movieTrailerRecylerView.setNestedScrollingEnabled(false);

        if(savedInstanceState == null){
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            myMovie = (Movie)bundle.getSerializable("movie");
            populateMovieActivity(myMovie);
            if(isThereConnection()){
                getMovieTrailer(myMovie.getId());
                getMovieReviews(myMovie.getId());
            }//end inner if

        }//end outer if
        else{
            myMovie = (Movie) savedInstanceState.getSerializable("movie");
            populateMovieActivity(myMovie);
            if(isThereConnection()){
                movieReviewArrayList = (ArrayList<MovieReview>) savedInstanceState.getSerializable("movie_reviews");
                populateMovieReview(movieReviewArrayList);
                movieTrailerArrayList = (ArrayList<MovieTrailer>) savedInstanceState.getSerializable("movie_trailers");
                populateMovieTrailer(movieTrailerArrayList);
            }
        }


    }

    private void updateFloatBTNDrawable(){
        if (isInFavoriate(myMovie.getId())){
            movieFavoriteActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_border_white_36dp));

        }
         else {
             movieFavoriteActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_border_white_36dp));
        }
    }

    private boolean isInFavoriate(int id) {
        Cursor cursor = getContentResolver().query(FavoriteDBCreator.MovieFavoriteEntity.estabFavoriteUriWithId(id)
        ,null,null,null,null);
        boolean b = cursor.getCount() > 0;
        Log.d(TAG, "getCount>0"+b);
        return b;
    }

    @Override
    public void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        outstate.putSerializable("movie", myMovie);
        if(isThereConnection()){
            outstate.putSerializable("movie_reviews", movieReviewArrayList);
            outstate.putSerializable("movie_trailers", movieTrailerArrayList);
        }
    }

    private boolean isThereConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @OnClick(R.id.movie_activity_review_reader)
    public void readMovieReviews(){
        Intent intendReviews = new Intent(this,MovieReviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie_reviews_list", movieReviewArrayList);
        bundle.putString("movie_title", myMovie.getTitle());
        intendReviews.putExtras(bundle);
        startActivity(intendReviews);
    }

    @OnClick(R.id.movie_activity_favorite)
    public void setMovieFavoriteActionButton(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteDBCreator.MovieFavoriteEntity.MOVIE_ID, myMovie.getId());
        contentValues.put(FavoriteDBCreator.MovieFavoriteEntity.MOVIE_TITLE, myMovie.getTitle());
        contentValues.put(FavoriteDBCreator.MovieFavoriteEntity.MOVIE_OVERVIEW, myMovie.getOverview());
        contentValues.put(FavoriteDBCreator.MovieFavoriteEntity.MOVIE_VOTE_COUNT, myMovie.getVoteCount());
        contentValues.put(FavoriteDBCreator.MovieFavoriteEntity.MOVIE_VOTE_AVERAGE, myMovie.getVoteAverage());
        contentValues.put(FavoriteDBCreator.MovieFavoriteEntity.MOVIE_RELEASE_DATE, myMovie.getReleaseDate());
        contentValues.put(FavoriteDBCreator.MovieFavoriteEntity.MOVIE_POSTER_PATH, myMovie.getPosterPath());


        if(!isInFavoriate(myMovie.getId())){
            getContentResolver().insert(FavoriteDBCreator.MovieFavoriteEntity.CONTENT_URI,contentValues);
            Toast.makeText(this, R.string.addedToFavorite, Toast.LENGTH_SHORT).show();

        } //end if
        else {
            getContentResolver().delete(FavoriteDBCreator.MovieFavoriteEntity.CONTENT_URI, "movie_id=?",
                    new String[]{String.valueOf(myMovie.getId())});
            Toast.makeText(this, R.string.removedFromFavorite, Toast.LENGTH_SHORT).show();
        }//end else
        updateFloatBTNDrawable();
    }

    private void getMovieReviews(int movieID){
        GetMoveReviewService moveReviewService = RetrofitInstance.getRetrofitInstance().create(GetMoveReviewService.class);
        Call<MovieReviewResults> call = moveReviewService.getMovieReviews(movieID, MainActivity.getDbApiKey());

        call.enqueue(new Callback<MovieReviewResults>() {
            @Override
            public void onResponse(Call<MovieReviewResults> call, Response<MovieReviewResults> response) {
                movieReviewArrayList = response.body().getResults();
                populateMovieReview(movieReviewArrayList);
            }

            @Override
            public void onFailure(Call<MovieReviewResults> call, Throwable t) {
                    Toast.makeText(MovieActivity.this, R.string.unknown_message, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void populateMovieReview(ArrayList<MovieReview> movieReviewArrayList) {
        if(movieReviewArrayList.size() > 0){
            txMovieReviewRead.setVisibility(View.VISIBLE);
        }
    }
    
    private void getMovieTrailer(int movieID){
        GetMovieTrailerService movieTrailerService = RetrofitInstance.getRetrofitInstance().create(GetMovieTrailerService.class);
        Call<MovieTrailerResults> trailerResultsCall= movieTrailerService.getMovieTrailer(movieID, MainActivity.getDbApiKey());
        trailerResultsCall.enqueue(new Callback<MovieTrailerResults>() {
            @Override
            public void onResponse(Call<MovieTrailerResults> call, Response<MovieTrailerResults> response) {
                movieTrailerArrayList = response.body().getMovieTrailerResults();
                populateMovieTrailer(movieTrailerArrayList);
            }

            @Override
            public void onFailure(Call<MovieTrailerResults> call, Throwable t) {
                Toast.makeText(MovieActivity.this, R.string.error_message, Toast.LENGTH_SHORT).show();
            }
        });
        
    }

    private void populateMovieTrailer(final ArrayList<MovieTrailer> movieTrailerArrayList) {
        if(movieTrailerArrayList.size() > 0){
            txMovieReviewRead.setVisibility(View.VISIBLE);
            movieTrailerRecylerView.setVisibility(View.VISIBLE);
            movieTrailerAdapter = new MovieTrailerAdapter(new MovieTrailerServiceClickerList() {
                @Override
                public void onMovieTrailerClicker(MovieTrailer movieTrailer) {
                    Intent trailerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE + movieTrailer.getKey()));
                    startActivity(trailerIntent);
                }
            }, movieTrailerArrayList);
            movieTrailerRecylerView.setAdapter(movieReviewAdapter);

        }//end if
    }


    private void populateMovieActivity(Movie theMovie){
        updateFloatBTNDrawable();
        Picasso.with(this).load(movieImagePathBuilder(theMovie.getPosterPath())).into(imMoviePoster);
        txMovieTitle.setText(theMovie.getTitle());
        tvMoveDescOverview.setText(theMovie.getOverview());
        txMovieReleaseDate.setText(theMovie.getReleaseDate());
        String rateMovie = String.valueOf(theMovie.getVoteAverage()) + "/10";
        txMovieRate.setText(rateMovie);
    }

}
