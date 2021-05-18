package com.example.newsapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiIntrface {
    @GET("v2/top-headlines")
    Call<Example> getNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
    @GET("v2/everything")
    Call<Example> getNewsSearch(
            @Query("q") String keyword,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );
}
