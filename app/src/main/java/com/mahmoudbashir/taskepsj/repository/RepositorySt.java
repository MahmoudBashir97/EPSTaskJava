package com.mahmoudbashir.taskepsj.repository;

import android.app.Application;

import com.mahmoudbashir.taskepsj.retrofit.RetrofitClient;
import com.mahmoudbashir.taskepsj.utils.Constants;
import com.mahmoudbashir.taskepsj.pojo.DataModel;
import com.mahmoudbashir.taskepsj.pojo.NewsResponse;
import com.mahmoudbashir.taskepsj.retrofit.ApiClient;
import com.mahmoudbashir.taskepsj.retrofit.NewApi;
import com.mahmoudbashir.taskepsj.room.StDao;
import com.mahmoudbashir.taskepsj.room.StDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.Response;

public class RepositorySt implements IRepositoy{
    private NewApi api;
    private StDao dao;

    public RepositorySt( Application app) {
        StDatabase db = StDatabase.getInstance(app);
        dao = db.dao();
    }

    @Override
    public Observable<NewsResponse> getBreakingNews() {
      /*  NewApi api =  ApiClient.getRetrofit().create(NewApi.class);
        return api.getBreakingNews("us",1, Constants.API_KEY);*/

        return RetrofitClient.getInstance().getApiService().getBreakingNews("us",1, Constants.API_KEY);
    }

    @Override
    public Completable insertToRoom(DataModel model) {
        return dao.insert(model);
    }

    @Override
    public LiveData<List<DataModel>> getAllDataStored() {
        return dao.getAllDataStored();
    }
}
