package com.example.popularmovis;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieGridFragment extends Fragment {

    public ImageAdapter mMovieAdapter;
    private ArrayList<MovieDetails> mMovieDetailList;

    public static final String POSTER = "poster";
    public static final String MOVIE_OBJECT = "movie";

    public MovieGridFragment() {
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
        FetchMovieData movieData = new FetchMovieData();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_sort_key),getString(R.string.pref_sort_by_popularity));
        movieData.execute(sortOrder);

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mMovieAdapter = new ImageAdapter(rootView.getContext());
        final GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movies);
        gridView.setAdapter(mMovieAdapter); // uses the view to get the context instead of getActivity().
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
//                intent.putExtra("position", position);
//                startActivity(intent);
                ImageView poster = (ImageView) view;
                poster.buildDrawingCache();
                Bitmap bitmap = poster.getDrawingCache();
                intent.putExtra(POSTER, bitmap);
                intent.putExtra(MOVIE_OBJECT, mMovieDetailList.get(position));
                startActivity(intent);
            }
        });
        return rootView;

    }

    public class FetchMovieData extends AsyncTask<String, Void, String[]>{
        private final String LOG_TAG = FetchMovieData.class.getSimpleName();

        private String[] parseMovieData(String jsonResponse)
                                            throws JSONException{
            //These json mMovieAdapter need to be extracted
            if (jsonResponse != null)
            {
                // your code
            }
            final String MOVIE_RESULT = "results";
            final String MOVIE_POSTER_PATH = "poster_path";
            final String MOVIE_OVERVIEW = "overview";
            final String MOVIE_TITLE = "original_title";
            final String MOVIE_RELEASE_DATE = "release_date";
            final String MOVIE_USER_RATING = "vote_average";
            String posterPath;
            JSONObject movieData = new JSONObject(jsonResponse);
            JSONArray movieArray = movieData.getJSONArray(MOVIE_RESULT);
            String[] posterAddress = new String[movieArray.length()];
            mMovieDetailList = new ArrayList<>();
            //Fill the poster Address with the URL to mMovieAdapter
            for(int j=0; j < movieArray.length(); j++){
                MovieDetails movieDetails = new MovieDetails();
                JSONObject movieArrayItem = movieArray.getJSONObject(j);

                posterPath = movieArrayItem.getString(MOVIE_POSTER_PATH);

                movieDetails.setOverview(movieArrayItem.getString(MOVIE_OVERVIEW));
                movieDetails.setPosterPath(posterPath);
                movieDetails.setReleaseDate(movieArrayItem.getString(MOVIE_RELEASE_DATE));
                movieDetails.setTitle(movieArrayItem.getString(MOVIE_TITLE));
                movieDetails.setUserRating(movieArrayItem.getString(MOVIE_USER_RATING));

                mMovieDetailList.add(movieDetails);

                posterAddress[j] = (posterPath.equals("null")) ? null : "http://image.tmdb.org/t/p//w185/" + posterPath;



            }
            for (String s : posterAddress) {
                Log.v(LOG_TAG, "Thumbnail Links: " + s);
            }
            return posterAddress;
        }

        @Override
        protected String[] doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String JsonData = null;

            String country = "US";
            String rating = "R";
            String sort = "popularity.desc";
            String apiKey = Secrets.API_KEY;
            //building the URL for the movie DB

            try {
                //building the URL for the movie DB
                final String movieDbUrl = "https://api.themoviedb.org/3/discover/movie?";
                final String releaseCountry = "certification_country";
                final String certificate = "certification";
                final String sortingOrder = "sort_by";
                final String apiId = "api_key";

                Uri buildUri = Uri.parse(movieDbUrl).buildUpon()
                        .appendQueryParameter(releaseCountry, country)
                        .appendQueryParameter(certificate, rating)
                        .appendQueryParameter(sortingOrder, params[0])
                        .appendQueryParameter(apiId, apiKey)
                        .build();

                //Converting URI to URL
                URL url = new URL(buildUri.toString());
                Log.v(LOG_TAG, "Built URI = "+ buildUri.toString());


                //Create the request to the Movie Database
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Read the input from the database into string
                InputStream inputStream = urlConnection.getInputStream();

                //StringBuffer for string Manipulation
                StringBuffer buffer = new StringBuffer();


                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                JsonData = buffer.toString();
                Log.v(LOG_TAG,"Forecast Jason String" + JsonData );
            } catch (IOException e) {
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }

                }
                }
                try {
                    return parseMovieData(JsonData);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }

            return null;
        }
        protected void onPostExecute(String[] posterAddress) {
            if(posterAddress != null) {
                mMovieAdapter.clearItems();
                for (String s : posterAddress) {
                    mMovieAdapter.addItem(s);
                }
                mMovieAdapter.notifyDataSetChanged();

            }
        }
    }
}
