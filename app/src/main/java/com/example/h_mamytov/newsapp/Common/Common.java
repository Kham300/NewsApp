package com.example.h_mamytov.newsapp.Common;

import com.example.h_mamytov.newsapp.Interfaces.IconBetterIdeaService;
import com.example.h_mamytov.newsapp.Interfaces.NewsService;
import com.example.h_mamytov.newsapp.Remote.IconBetterIdeaClient;
import com.example.h_mamytov.newsapp.Remote.RetrofitClient;
import com.example.h_mamytov.newsapp.model.IconBetterIdea;

public class Common {
    private static String BASE_URL = "https://newsapi.org/";
    public static final String API_KEY="6f5a370cc7a8489b8317688681df865f";

    public static NewsService getNewsService(){
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    public static IconBetterIdeaService getIconService(){
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }


}
