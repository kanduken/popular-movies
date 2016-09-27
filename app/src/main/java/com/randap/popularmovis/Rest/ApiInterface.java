package com.randap.popularmovis.Rest;

import com.randap.popularmovis.Model.MovieResponse;
import com.randap.popularmovis.Model.MovieTrailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by User on 8/1/2016.
 */
public interface ApiInterface {

    @GET("3/movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("3/movie/id")
    Call<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/videos")
    Call<MovieTrailers> getMovieTrailers(@Path("id") Integer id, @Query("api_key") String apiKey);

    @GET("3/movie/upcoming")
    Call<MovieResponse> getupcomingMovies(@Query("api_key") String apiKey);

}
