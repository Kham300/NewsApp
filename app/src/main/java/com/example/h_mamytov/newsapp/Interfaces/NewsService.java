package com.example.h_mamytov.newsapp.Interfaces;

import com.example.h_mamytov.newsapp.model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsService {
    @GET("v1/sources?language=en")
    Call<WebSite> getSources();

}
