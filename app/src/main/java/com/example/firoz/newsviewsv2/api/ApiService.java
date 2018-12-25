package com.example.firoz.newsviewsv2.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {
    // -- For getting number info from Number Api
    @GET
    Call<String> getNumberInfo(@Url String url);

    // --- For getting News from News Api
    @GET
    Call<String> getNewsDetails(@Url String url);
}
