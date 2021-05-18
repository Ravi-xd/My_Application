package com.example.newsapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL="http://newsapi.org/";
    private static Retrofit retrofit;
    public static Retrofit getClient()
    {
        if (retrofit==null)
        {
            retrofit= new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
