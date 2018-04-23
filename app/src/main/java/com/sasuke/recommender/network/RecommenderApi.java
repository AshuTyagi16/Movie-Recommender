package com.sasuke.recommender.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sasuke.recommender.model.Movie;
import com.sasuke.recommender.model.User;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abc on 4/23/2018.
 */

public class RecommenderApi {

    public final static int CONNECTION_TIMEOUT = 30;
    private final static int READ_TIMEOUT = 30;
    private final static int WRITE_TIMEOUT = 30;
    public static final String BASE_API_URL = "http://rentkart.000webhostapp.com/";

    private static volatile RecommenderApi instance;

    private RecommenderApiInterface service;

    private RecommenderApi() {
    }

    public static RecommenderApi getInstance() {
        if (instance == null) {
            synchronized (RecommenderApi.class) {
                if (instance == null) {
                    instance = new RecommenderApi();
                    instance.init();
                }
            }
        }
        return instance;
    }

    private void init() {
        Gson gson = new GsonBuilder().create();
        OkHttpClient httpClient = createHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);

        service = retrofit.create(RecommenderApiInterface.class);
    }

    public Call<User> login(String username, String password) {
        return service.login(username, password);
    }

    public Call<Boolean> register(String username, String password,
                                  String phoneNumber, int age) {
        return service.register(username, password, phoneNumber, age);
    }

    public Call<ArrayList<Movie>> getAllMovies() {
        return service.getAllMovies();
    }

    public Call<ArrayList<String>> getCategories() {
        return service.getCategories();
    }

    public Call<ArrayList<Movie>> getMovieById(String commaSeperatedMovieIds) {
        return service.getMovieById(commaSeperatedMovieIds);
    }

    public Call<ArrayList<Movie>> getRecommendationsForQuery(String movieName) {
        return service.getRecommendationsForQuery(movieName);
    }

    public Call<ArrayList<Movie>> getMoviesForGenre(String genre) {
        return service.getMoviesForGenre(genre);
    }

    public Call<ArrayList<Movie>> getRecommendationsForQueryAndGenre(String movieName, String genre) {
        return service.getRecommendationsForQueryAndGenre(movieName, genre);
    }

    /********Interceptor********/

    private OkHttpClient createHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(logging);

        return builder.build();
    }
}
