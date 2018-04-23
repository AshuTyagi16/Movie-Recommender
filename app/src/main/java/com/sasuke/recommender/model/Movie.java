package com.sasuke.recommender.model;

/**
 * Created by abc on 4/23/2018.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("movieId")
    @Expose
    private Integer movieId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

}