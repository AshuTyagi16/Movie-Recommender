package com.sasuke.recommender.network;

import com.sasuke.recommender.model.Movie;
import com.sasuke.recommender.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by abc on 4/23/2018.
 */

public interface RecommenderApiInterface {

    @GET("/login.php")
    Call<User> login(@Query("username") String username, @Query("password") String password);

    @GET("/register.php")
    Call<Boolean> register(@Query("username") String username, @Query("password") String password,
                           @Query("phoneNumber") String phoneNumber,  @Query("age") int age);

    @GET("/fetchMovies.php")
    Call<ArrayList<Movie>> getAllMovies();

    @GET("/fetchAllGenres.php")
    Call<ArrayList<String>> getCategories();

    @GET("/fetchMoviesById.php")
    Call<ArrayList<Movie>> getMovieById(@Query("movieId") String commaSeperatedMovieIds);

    @GET("/fetchRecommendationsForQuery.php")
    Call<ArrayList<Movie>> getRecommendationsForQuery(@Query("movieName") String movieName);

    @GET("/fetchMoviesForGenre.php")
    Call<ArrayList<Movie>> getMoviesForGenre(@Query("genre") String genre);

    @GET("/fetchRecommendationsForQueryAndGenre.php")
    Call<ArrayList<Movie>> getRecommendationsForQueryAndGenre(@Query("movieName") String movieName, @Query("genre") String genre);
}
