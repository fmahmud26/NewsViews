package com.example.firoz.newsviewsv2.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {
    // -- for get something
    @GET
    Call<String> getNumberInfo(@Url String url);
}
