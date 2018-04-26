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
    Call<User> login(@Query("email") String email, @Query("password") String password);

    @GET("/register.php")
    Call<User> register(@Query("email") String email, @Query("password") String password,
                        @Query("name") String name, @Query("age") int age);

    @GET("/fetchMovies.php")
    Call<ArrayList<Movie>> getAllMovies(@Query("userId") int userId);

    @GET("/fetchAllGenres.php")
    Call<ArrayList<String>> getCategories();

    @GET("/fetchMoviesById.php")
    Call<ArrayList<Movie>> getMovieById(@Query("movieId") String commaSeperatedMovieIds);

    @GET("/fetchRecommendationsForQuery.php")
    Call<ArrayList<Movie>> getRecommendationsForQuery(@Query("movieName") String movieName);

    @GET("/fetchMoviesForGenre.php")
    Call<ArrayList<Movie>> getMoviesForGenre(@Query("userId") int userId, @Query("genre") String genre);

    @GET("/fetchRecommendationsForQueryAndGenre.php")
    Call<ArrayList<Movie>> getRecommendationsForQueryAndGenre(@Query("movieName") String movieName, @Query("genre") String genre);

    @GET("/unfavouriteMovie.php")
    Call<Boolean> unfavouriteMovie(@Query("userId") int userId, @Query("movieId") int movieId);

    @GET("/favouriteMovie.php")
    Call<Boolean> favouriteMovie(@Query("userId") int userId, @Query("movieId") int movieId);

    @GET("/fetchFavouriteMovies.php")
    Call<ArrayList<Movie>> fetchFavouriteMovies(@Query("userId") int userId);
}
