package com.training.popularmoviefinalapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.training.popularmoviefinalapp.R;
import com.training.popularmoviefinalapp.adapter.MovieAdapter;
import com.training.popularmoviefinalapp.model.Movie;
import com.training.popularmoviefinalapp.model.MovieResults;
import com.training.popularmoviefinalapp.moviedb.FavoriteDBCreator;
import com.training.popularmoviefinalapp.service.EndlessRecylerViewListener;
import com.training.popularmoviefinalapp.service.GetMovieService;
import com.training.popularmoviefinalapp.service.MovieServiceClickListener;
import com.training.popularmoviefinalapp.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int INITIAL_COUNT = 1;
    private int currentMood = 1;
    private static final String DB_API_KEY = "d23914181a70b399fef78701d2e07cb3";
    private Toolbar movieToolbar;
    private TextView mToolbarTitle;
    private Call<MovieResults> movieResultsCall;
    private List<Movie> movieList;
    private MovieAdapter movieAdapter;
    private int totalOfPages;

    @BindView(R.id.rv_movies)
    RecyclerView recyclerView;
    @BindView(R.id.cl_internet_error)
    ConstraintLayout constLayoutNoInternet;

    public static String movieImagePathBuilder(String path) {
        {
            return "https://image.tmdb.org/t/p/" +
                    "w500" +
                    path;
        }


    }

    public static String getDbApiKey() {
        return DB_API_KEY;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        movieToolbar = findViewById(R.id.movie_toolbar);
        setSupportActionBar(movieToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mToolbarTitle = findViewById(R.id.movie_toolbar_title);
        if(!isThereConnection()){
            recyclerView.setVisibility(View.GONE);
            constLayoutNoInternet.setVisibility(View.VISIBLE);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                return 1;
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);
        EndlessRecylerViewListener endlessRecylerViewListener = new EndlessRecylerViewListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if((page + 1) <= totalOfPages && currentMood !=3){
                    goMoviePage(page+1);
                }
            }
        };

        recyclerView.addOnScrollListener(endlessRecylerViewListener);

        goMoviePage(INITIAL_COUNT);
    }// end onCreate

    private boolean isThereConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static int getMeasuredPosterHeight(int width) {
        return (int) (width * 1.5f);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    @OnClick(R.id.bt_refresh_internet)
    public void RefreshActiviity(){
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movie_sort_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.sort_by_pop_movies:
                currentMood = 1;
                break;
            case R.id.sort_by_top_movies:
                currentMood = 2;
                break;
            case R.id.favorite_movies_fb:
                currentMood = 3; // favorite menu and activity
                break;
        }
        if(currentMood==3){
            ArrayList<Movie> favoriteMovieList = getFavoriteMovies();

            movieAdapter = new MovieAdapter(new MovieServiceClickListener() {
                @Override
                public void onMovieClicker(Movie movie) {
                    Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("movie", movie);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            },favoriteMovieList);

            recyclerView.setAdapter(movieAdapter);
        }//end if
        else {
            goMoviePage(INITIAL_COUNT);

        }

        return super.onOptionsItemSelected(item);
    }

    private void goMoviePage(final int moviePage) {
        GetMovieService movieService = RetrofitInstance.getRetrofitInstance().create(GetMovieService.class);
        switch (currentMood){
            case 1:
                movieResultsCall = movieService.getMoviesByPopularity(moviePage,MainActivity.getDbApiKey());
                break;
            case 2:
                movieResultsCall = movieService.getMoviesByTopRated(moviePage, MainActivity.getDbApiKey());
                break;

        }//end switch


        movieResultsCall.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(@NonNull Call<MovieResults> call, @NonNull Response<MovieResults> response) {

                if(moviePage == 1){
                    assert response.body()!=null;
                    movieList = response.body().getMovieList();
                    assert response.body()!=null;
                    totalOfPages =response.body().getTotalPages();

                    movieAdapter = new MovieAdapter(new MovieServiceClickListener() {
                        @Override
                        public void onMovieClicker(Movie movie) {
                            Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                            Bundle b = new Bundle();
                            b.putSerializable("movie", movie);
                            intent.putExtras(b);
                            startActivity(intent);
                        }
                    }, movieList);
                recyclerView.setAdapter(movieAdapter);
                } else {
                    assert response.body() !=null;
                    List<Movie> movies = response.body().getMovieList();
                    for(Movie movie: movies){
                        movieList.add(movie);
                        movieAdapter.notifyItemInserted(movieList.size()-1);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResults> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private ArrayList<Movie> getFavoriteMovies() {
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        Cursor cursor = getContentResolver().query(
                FavoriteDBCreator.MovieFavoriteEntity.CONTENT_URI,null,null,null,null
        );

        assert cursor!=null;
        if(cursor.moveToFirst()){
            do{
                Movie movie =  new Movie();

                int id = cursor.getInt(cursor.getColumnIndex("movie_id"));
                String movieTitle = cursor.getString(cursor.getColumnIndex("movie_title"));
                String movieOverview = cursor.getString(cursor.getColumnIndex("movie_overview"));
                double movieVoteAverage = cursor.getDouble(cursor.getColumnIndex("movie_vote_average"));
                String movieReleaseDate = cursor.getString(cursor.getColumnIndex("movie_release_date"));
                String moviePosterLink = cursor.getString(cursor.getColumnIndex("movie_poster_path"));

                movie.setId(id);
                movie.setTitle(movieTitle);
                movie.setOverview(movieOverview);
                movie.setVoteAverage(movieVoteAverage);
                movie.setReleaseDate(movieReleaseDate);
                movie.setPosterPath(moviePosterLink);

                movieArrayList.add(movie);

            }while (cursor.moveToNext());
        }//end iff
        cursor.close();

        return movieArrayList;
    }
}
