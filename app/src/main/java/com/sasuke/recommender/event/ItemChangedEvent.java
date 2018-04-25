package com.sasuke.recommender.event;

import com.sasuke.recommender.model.Movie;

/**
 * Created by abc on 4/26/2018.
 */

public class ItemChangedEvent {
    public Movie movie;

    public ItemChangedEvent(Movie movie) {
        this.movie = movie;
    }
}
