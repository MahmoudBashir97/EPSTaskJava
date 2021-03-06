package com.mahmoudbashir.taskepsj.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {

    @SerializedName("status")
    String status;
    @SerializedName("totalResults")
    int totalResults;
    @SerializedName("articles")
    List<Article> articles;


    public NewsResponse(List<Article> articles, String status, int totalResults) {
        this.articles = articles;
        this.status = status;
        this.totalResults = totalResults;
    }

    public NewsResponse() {
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
