package com.mahmoudbashir.taskepsj.retrofit;

import com.mahmoudbashir.taskepsj.pojo.NewsResponse;

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewApi {
    @GET("v2/top-headlines")
    public Observable<NewsResponse> getBreakingNews(
            @Query("country")
            String countryCode,
            @Query("page")
            int pageNumber,
            @Query("apiKey")
            String apiKey);
}
