package com.sasuke.recommender.event;

/**
 * Created by abc on 4/27/2018.
 */

public class RecommendationDatasetReadyEvent {
    public String commaSeperatedMovieId;

    public RecommendationDatasetReadyEvent(String commaSeperatedMovieId) {
        this.commaSeperatedMovieId = commaSeperatedMovieId;
    }
}
