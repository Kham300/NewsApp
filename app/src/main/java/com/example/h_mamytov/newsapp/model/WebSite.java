package com.example.h_mamytov.newsapp.model;

import java.util.List;
import com.example.h_mamytov.newsapp.model.Source;

public class WebSite {
    private String status;
    private List<Source> sources;

    public WebSite(String status, List<Source> sources) {
        this.status = status;
        this.sources = sources;
    }


    public WebSite() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
