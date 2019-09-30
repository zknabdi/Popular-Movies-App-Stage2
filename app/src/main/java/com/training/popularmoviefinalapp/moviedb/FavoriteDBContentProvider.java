package com.training.popularmoviefinalapp.moviedb;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FavoriteDBContentProvider extends ContentProvider {

    private FavoriteDbGenerator favoriteDbGenerator;
    public static final int MOVIE_FAVORITE_CD = 100;
    public static final int MOVIE_FAVORITE_BY_ID = 101;

    private static final UriMatcher URI_MATCHER = doUriMatcher();


    @Override
    public boolean onCreate() {
        favoriteDbGenerator = new FavoriteDbGenerator(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;

        switch (URI_MATCHER.match(uri)) {
            case MOVIE_FAVORITE_BY_ID: {
                String ID = uri.getLastPathSegment();
                String[] getArguments = new String[]{ID};
                cursor = favoriteDbGenerator.getReadableDatabase().query(
                        FavoriteDBCreator.MovieFavoriteEntity.TABLE_NAME,
                        projection,
                        FavoriteDBCreator.MovieFavoriteEntity.MOVIE_ID + " =? ",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MOVIE_FAVORITE_CD:{
                cursor = favoriteDbGenerator.getReadableDatabase().query(
                        FavoriteDBCreator.MovieFavoriteEntity.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Uknown uri: "+uri);
        }//end switch
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase database = favoriteDbGenerator.getWritableDatabase();

        switch (URI_MATCHER.match(uri)){
            case MOVIE_FAVORITE_CD:
                database.insert(FavoriteDBCreator.MovieFavoriteEntity.TABLE_NAME,null,values);
                long id = Integer.parseInt(values.get("movie_id").toString());
                if(id!=1){
                    getContext().getContentResolver().notifyChange(uri,null);
                }//end if
                return FavoriteDBCreator.MovieFavoriteEntity.estabFavoriteUriWithId(id);
                default:
                    return null;
        }//end switch
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase database = favoriteDbGenerator.getWritableDatabase();
        switch (URI_MATCHER.match(uri)){
            case MOVIE_FAVORITE_CD:
                database.delete(FavoriteDBCreator.MovieFavoriteEntity.TABLE_NAME,selection,selectionArgs);

        }//end switch

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }



    private static UriMatcher doUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String movieAuthorizer = FavoriteDBCreator.MOVIECONTENT_LOC_AUTH;

        uriMatcher.addURI(movieAuthorizer, FavoriteDBCreator.MovieFavoriteEntity.TABLE_NAME, MOVIE_FAVORITE_CD);
        uriMatcher.addURI(movieAuthorizer, FavoriteDBCreator.MovieFavoriteEntity.TABLE_NAME + "/#",
                MOVIE_FAVORITE_BY_ID);

        return uriMatcher;
    }
}
