package com.example.popularmovis;

import java.io.Serializable;

/**
 * Created by Lovleen on 7/6/2016.
 */
public class MovieDetails implements Serializable {

    private String posterPath, overview, releaseDate, title, userRating;

    public MovieDetails(){}

    public String getPosterPath(){
        return "http://image.tmdb.org/t/p/w185/"+posterPath;
    }

    public String getOverview(){
        return overview;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public String getTitle(){
        return title;
    }

    public String getUserRating(){
        return userRating;
    }

    public void setPosterPath(String posterPath){
        this.posterPath = posterPath;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }
}


