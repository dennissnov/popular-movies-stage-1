package com.example.dennissnov.popularmovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dennissnov on 7/2/2017.
 */

public interface TmdbApiInterface {
    @GET("/3/movie/{category}")
    Call<MovieResults> listOfMovies(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("page") int page

    );
    //https://api.themoviedb.org/3/movie/popular?api_key=de7ce7dc8e26496a6f816fd3de17d36e&language=en-US&page=1

}
