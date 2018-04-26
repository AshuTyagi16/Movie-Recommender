package com.sasuke.recommender.event;

import com.sasuke.recommender.model.MovieRating;

import java.util.ArrayList;

/**
 * Created by abc on 4/27/2018.
 */

public class MovieRatedEvent {
    public MovieRating movieRating;

    public MovieRatedEvent(MovieRating movieRating) {
        this.movieRating = movieRating;
    }
}
