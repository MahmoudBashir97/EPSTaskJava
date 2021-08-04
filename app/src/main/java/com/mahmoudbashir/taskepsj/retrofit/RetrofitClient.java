package com.mahmoudbashir.taskepsj.retrofit;


import android.content.Context;


import com.mahmoudbashir.taskepsj.BuildConfig;
import com.mahmoudbashir.taskepsj.utils.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private Retrofit retrofit;
    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();
    private static RetrofitClient instance = null;
    private static NewApi apiInterface = null;
    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor();

    private RetrofitClient() {
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder().
                        method(originalRequest.method(), originalRequest.body());
                okhttp3.Response response = chain.proceed(builder.build());

                return response;
            }
        });

        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            // add logging as last interceptor
            httpClient.addInterceptor(logging);
        }

        retrofit = new Retrofit.Builder().client(httpClient.build()).
                baseUrl(Constants.BASE_URL).
                addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        apiInterface = retrofit.create(NewApi.class);
    }


    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public NewApi getApiService() {
        return apiInterface;
    }
}
