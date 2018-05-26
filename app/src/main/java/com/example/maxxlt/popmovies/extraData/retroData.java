package com.example.maxxlt.popmovies.extraData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface retroData {
    @GET("{movie_id}/videos")
    Call<trailerResults> getListTrailers(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key);
    @GET("{movie_id}/reviews")
    Call<reviewResults> getListReviws(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key);
        }
