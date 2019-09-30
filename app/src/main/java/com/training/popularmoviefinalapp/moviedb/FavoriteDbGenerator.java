package com.training.popularmoviefinalapp.moviedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteDbGenerator extends SQLiteOpenHelper {
    public static final String DB_NAME = "movie.db";
    private static final int DB_VERSION = 1;


    public FavoriteDbGenerator(Context con) {
        super(con, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_FAVORITE_TABLE =
                "CREATE TABLE " + FavoriteDBCreator.MovieFavoriteEntity.TABLE_NAME + " (" +
                        FavoriteDBCreator.MovieFavoriteEntity.MOVIE_ID + " INTEGER UNIQUE PRIMARY KEY NOT NULL," +
                        FavoriteDBCreator.MovieFavoriteEntity.MOVIE_TITLE       + " TEXT NOT NULL, "                 +
                        FavoriteDBCreator.MovieFavoriteEntity.MOVIE_OVERVIEW       + " TEXT NOT NULL, "                 +
                        FavoriteDBCreator.MovieFavoriteEntity.MOVIE_VOTE_COUNT       + " INTEGER NOT NULL, "                 +
                        FavoriteDBCreator.MovieFavoriteEntity.MOVIE_VOTE_AVERAGE       + " DOUBLE NOT NULL, "                 +
                        FavoriteDBCreator.MovieFavoriteEntity.MOVIE_RELEASE_DATE       + " TEXT NOT NULL, "                 +
                        FavoriteDBCreator.MovieFavoriteEntity.MOVIE_POSTER_PATH       + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_MOVIE_FAVORITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "  + FavoriteDBCreator.MovieFavoriteEntity.TABLE_NAME);
        onCreate(db);

    }
}
