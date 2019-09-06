package com.training.popularmoviefinalapp.activity;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.training.popularmoviefinalapp.R;

public class MainActivity extends AppCompatActivity {

    private static final String DB_API_KEY = "0000000000000000";
    private Toolbar movieToolbar;
    private TextView mToolbarTitle;

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

        movieToolbar = findViewById(R.id.movie_toolbar);
        setSupportActionBar(movieToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mToolbarTitle = findViewById(R.id.movie_toolbar_title);

    }

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
}
