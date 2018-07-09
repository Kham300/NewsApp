package com.example.h_mamytov.newsapp;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.h_mamytov.newsapp.Adapter.ListSourceAdapter;
import com.example.h_mamytov.newsapp.Common.Common;
import com.example.h_mamytov.newsapp.Interfaces.NewsService;
import com.example.h_mamytov.newsapp.model.WebSite;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView listWebSites;
    RecyclerView.LayoutManager layoutManager;
    NewsService mService;
    ListSourceAdapter adapter;
    SpotsDialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init cache
        Paper.init(this);

        //init Service
        mService = Common.getNewsService();

        //init View
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebSiteSource(true);
            }
        });

        listWebSites = findViewById(R.id.list_source);
        listWebSites.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listWebSites.setLayoutManager(layoutManager);

        dialog = new SpotsDialog(this);

        loadWebSiteSource(false);
    }

    private void loadWebSiteSource(boolean isRefreshed) {
        if (!isRefreshed){
            String cache = Paper.book().read("cache");
            if (cache != null && !cache.isEmpty()){ //if have cache
                WebSite webSite = new Gson().fromJson(cache, WebSite.class);//converting cache from Json to Object
                adapter = new ListSourceAdapter(getBaseContext(), webSite);
                adapter.notifyDataSetChanged();
                listWebSites.setAdapter(adapter);
            } else {
                //if not have cache
                //dialog.show();
                //Fetch new Data
                mService.getSources().enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                        adapter = new ListSourceAdapter(getBaseContext(), response.body());
                        adapter.notifyDataSetChanged();
                        listWebSites.setAdapter(adapter);

                        //save to cache
                        Paper.book().write("cache", new Gson().toJson(response.body()));
                    }

                    @Override
                    public void onFailure(Call<WebSite> call, Throwable t) {

                    }
                });
            }
        }else {
            dialog.show();
            //Fetch new Data
            mService.getSources().enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                    adapter = new ListSourceAdapter(getBaseContext(), response.body());
                    adapter.notifyDataSetChanged();
                    listWebSites.setAdapter(adapter);

                    //save to cache
                    Paper.book().write("cache", new Gson().toJson(response.body()));
                    //Dismiss refresh progressing
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t) {

                }
            });

        }
    }
}
