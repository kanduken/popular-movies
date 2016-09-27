package com.randap.popularmovis.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.randap.popularmovis.Activities.DetailActivity;
import com.randap.popularmovis.Adapters.RecyclerImageAdapter;
import com.randap.popularmovis.Model.Movie;
import com.randap.popularmovis.Model.MovieResponse;
import com.randap.popularmovis.R;
import com.randap.popularmovis.Rest.ApiClient;
import com.randap.popularmovis.Rest.ApiInterface;
import com.randap.popularmovis.Secrets;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieGridFragment extends Fragment {

    public static final String POSTER = "poster";
    public static final String MOVIE_OBJECT = "movie";
    public static final String MOVIE_ID = "id";
    public RecyclerImageAdapter mMovieAdapter;
    private ArrayList<Movie> mMovieDetailList;
    private Context mContext;
    private RecyclerView gridView;
    private int positionSelected;


    public MovieGridFragment() {
    }

    public static MovieGridFragment newInstance() {
        MovieGridFragment fragment = new MovieGridFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.datafragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            updateMovieList();
//            FetchMovieData movieData = new FetchMovieData();
//            movieData.execute();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateMovieList() {
        getMovieData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mContext = getActivity();

        gridView = (RecyclerView) rootView.findViewById(R.id.gridview_movies);
        gridView.setLayoutManager(new GridLayoutManager(mContext, 2));

        mMovieDetailList = new ArrayList<>();
//        if(savedInstanceState != null) {
//            mMovieDetailList = savedInstanceState.getParcelableArrayList("movieDetailList");
//            gridView.setAdapter(new ImageAdapter(mContext, mMovieDetailList));
//        } else {
        getMovieData();
//        }


//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), DetailActivity.class);
//                ImageView poster = (ImageView) view;
//                poster.buildDrawingCache();
//                Bitmap bitmap = poster.getDrawingCache();
//                intent.putExtra(POSTER, bitmap);
//                intent.putExtra(MOVIE_OBJECT,  (Parcelable) mMovieDetailList.get(position));
//                startActivity(intent);
//            }
//        });
        return rootView;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movieDetailList", mMovieDetailList);
    }

    private void getMovieData() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiService.getTopRatedMovies(Secrets.API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                mMovieDetailList = response.body().getResults();
                mMovieAdapter = new RecyclerImageAdapter(getActivity(), mMovieDetailList, new RecyclerImageAdapter.MovieClickHandler() {
                    @Override
                    public void getTheClickPosition(int position, Bitmap bitmap) {
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(POSTER, bitmap);
                        Movie movie = mMovieDetailList.get(position);
                        intent.putExtra(MOVIE_OBJECT, (Parcelable) mMovieDetailList.get(position));
                        startActivity(intent);
                    }
                });
                gridView.setAdapter(mMovieAdapter);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.i("LOG_TAG", "Failed");
            }
        });
    }

//    @Override
//    public void getTheClickPosition(int position, Bitmap bitmap) {
//        Intent intent = new Intent(getActivity(), DetailActivity.class);
//        intent.putExtra(POSTER, bitmap);
//        intent.putExtra(MOVIE_OBJECT, (Parcelable) mMovieDetailList.get(position));
//        startActivity(intent);
//
//    }
}


