package com.randap.popularmovis.Data;

import android.provider.BaseColumns;

/**
 * Created by Lovleen on 8/2/2016.
 */
public class MovieContract {

    /*
    Inner class that defines movie columns
     */
    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_POSTER = "posterPath";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_LANGUAGE = "language";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_USER_RATING = "rating";
    }
}
