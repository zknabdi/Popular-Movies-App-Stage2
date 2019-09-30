package com.training.popularmoviefinalapp.moviedb;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteDBCreator {

    public static final String MOVIECONTENT_LOC_AUTH = "com.training.popularmoviefinalapp.moviedb";

    public static final Uri BASE_MOVIECONTENT_URI = Uri.parse("content://" + MOVIECONTENT_LOC_AUTH);

    public static final class MovieFavoriteEntity implements BaseColumns{
        public static final String TABLE_NAME = "moviefav";
        public static String MOVIE_ID = "movie_id";
        public static String MOVIE_TITLE = "movie_title";
        public static String MOVIE_OVERVIEW = "movie_overview";
        public static String MOVIE_VOTE_COUNT = "movie_vote_count";
        public static String MOVIE_VOTE_AVERAGE = "movie_vote_average";
        public static String MOVIE_RELEASE_DATE = "movie_release_date";
        public static String MOVIE_POSTER_PATH = "movie_poster_path";

        public static final Uri CONTENT_URI = BASE_MOVIECONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static Uri estabFavoriteUriWithId(long id){
            return CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build();
        }
    }
}
