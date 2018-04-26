package com.sasuke.recommender.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abc on 4/26/2018.
 */

public class MovieRating {

    @SerializedName("movieRating")
    @Expose
    private double movieRating;

    @SerializedName("movie")
    @Expose
    private Movie movie;

    public double getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(double movieRating) {
        this.movieRating = movieRating;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
