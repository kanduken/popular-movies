package com.randap.popularmovis.Activities;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.randap.popularmovis.Fragments.MovieGridFragment;
import com.randap.popularmovis.Model.Movie;
import com.randap.popularmovis.Model.MovieTrailers;
import com.randap.popularmovis.R;
import com.randap.popularmovis.Rest.ApiClient;
import com.randap.popularmovis.Rest.ApiInterface;
import com.randap.popularmovis.Secrets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Hello", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Bitmap poster  =  getIntent().getParcelableExtra(MovieGridFragment.POSTER);
        Movie movie = (Movie)
                getIntent().getSerializableExtra(MovieGridFragment.MOVIE_OBJECT);

//        Log.v(DetailActivity.class.getSimpleName(),"The position is " + Integer.toString(position));

        ImageView moviePoster = (ImageView) findViewById(R.id.movie_poster);
        TextView movieTitle = (TextView) findViewById(R.id.movie_title);
        TextView movieOverview = (TextView) findViewById(R.id.movie_overview);
        TextView movieRating = (TextView) findViewById(R.id.movie_rating);
        TextView movieReleaseDate = (TextView) findViewById(R.id.movie_year);

        int id = movie.getId();

        callApiForTrailers(id);

        moviePoster.setImageBitmap(poster);
        moviePoster.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        movieOverview.setText(movie.getOverview());
        movieRating.setText(Double.toString(movie.getVoteAverage()));
        movieTitle.setText(movie.getTitle());
        movieReleaseDate.setText(movie.getReleaseDate().substring(0, 4));

    }

    private void callApiForTrailers(int id) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieTrailers> call = apiService.getMovieTrailers(id, Secrets.API_KEY);

        call.enqueue(new Callback<MovieTrailers>() {
            @Override
            public void onResponse(Call<MovieTrailers> call, Response<MovieTrailers> response) {

//                mMovieDetailList = response.body().getResults();
//                mMovieAdapter = new ImageAdapter(mContext, mMovieDetailList);
//                gridView.setAdapter(mMovieAdapter);
                Log.i("LOG_TAG", response.body().toString());
            }

            @Override
            public void onFailure(Call<MovieTrailers> call, Throwable t) {
                Log.i("LOG_TAG", "The connection failed");
            }
        });
    }
}
