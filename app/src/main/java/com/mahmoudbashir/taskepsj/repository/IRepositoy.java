package com.mahmoudbashir.taskepsj.repository;

import com.mahmoudbashir.taskepsj.pojo.DataModel;
import com.mahmoudbashir.taskepsj.pojo.NewsResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.Response;

public interface IRepositoy {

    Observable<NewsResponse> getBreakingNews();

    Completable insertToRoom(DataModel model);
    LiveData<List<DataModel>> getAllDataStored();
}
