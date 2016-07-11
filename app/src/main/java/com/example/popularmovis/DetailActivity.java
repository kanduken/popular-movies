package com.example.popularmovis;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bitmap poster  =  getIntent().getParcelableExtra(MovieGridFragment.POSTER);
        MovieDetails movieDetails = (MovieDetails)
                getIntent().getSerializableExtra(MovieGridFragment.MOVIE_OBJECT);

//        Log.v(DetailActivity.class.getSimpleName(),"The position is " + Integer.toString(position));

        ImageView moviePoster = (ImageView) findViewById(R.id.movie_poster);
        TextView movieTitle = (TextView) findViewById(R.id.movie_title);
        TextView movieOverview = (TextView) findViewById(R.id.movie_overview);
        TextView movieRating = (TextView) findViewById(R.id.movie_rating);
        TextView movieReleaseDate = (TextView) findViewById(R.id.movie_year);

        moviePoster.setImageBitmap(poster);
        moviePoster.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT  ));

        movieOverview.setText(movieDetails.getOverview());
        movieRating.setText(movieDetails.getUserRating());
        movieTitle.setText(movieDetails.getTitle());
        movieReleaseDate.setText(movieDetails.getReleaseDate().substring(0,4));

    }
}
