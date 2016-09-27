package com.randap.popularmovis.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.randap.popularmovis.Data.MovieContract.MovieEntry;

/**
 * Created by Lovleen on 8/2/2016.
 */
//public class MovieDbHelper extends SQLiteOpenHelper {
//
//    public static final int DATABASE_VERSION = 1;
//    public static final String DATABASE_NAME = "Movies.db";
//
//    public MovieDbHelper(Context context){
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        final String  SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + "(" +
//                MovieEntry._ID + " INTEGER PRIMARY KEY, " +
//                MovieEntry.COLUMN_POSTER + " TEXT, " +
//                MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
//                MovieEntry.COLUMN_RELEASE_DATE + " TEXT," +
//                MovieEntry.COLUMN_LANGUAGE + " TEXT, " +
//                MovieEntry.COLUMN_USER_RATING + "TEXT " +
//                ");" ;
//        db.execSQL(SQL_CREATE_MOVIES_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//}
public class MovieDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Movie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_STATEMENT = "CREATE TABLE " + MovieEntry.TABLE_NAME
                + "(" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_LANGUAGE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
                MovieEntry.COLUMN_RELEASE_DATE + " REAL, " +
                MovieEntry.COLUMN_USER_RATING + " REAL, " +
                MovieEntry.COLUMN_POSTER + " TEXT "
                + ")";

        db.execSQL(CREATE_TABLE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
