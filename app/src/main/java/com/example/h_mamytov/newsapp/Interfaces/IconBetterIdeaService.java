package com.example.h_mamytov.newsapp.Interfaces;

import com.example.h_mamytov.newsapp.model.IconBetterIdea;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IconBetterIdeaService {
    @GET
    Call<IconBetterIdea> getIconUrl(@Url String url);
}
