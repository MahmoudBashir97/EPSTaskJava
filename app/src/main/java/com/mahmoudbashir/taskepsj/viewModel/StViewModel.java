package com.mahmoudbashir.taskepsj.viewModel;

import android.app.Application;
import android.util.Log;

import com.mahmoudbashir.taskepsj.pojo.DataModel;
import com.mahmoudbashir.taskepsj.pojo.NewsResponse;
import com.mahmoudbashir.taskepsj.repository.RepositorySt;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class StViewModel extends AndroidViewModel {
    private RepositorySt repo;
    private LiveData<List<DataModel>> allData;
    MutableLiveData<String> message = new MutableLiveData<>();
    MutableLiveData<NewsResponse> breakingNews = new MutableLiveData<>();

    public StViewModel(@NonNull Application application) {
        super(application);
        repo = new RepositorySt(application);
    }
    public Completable insert(DataModel model){
        return repo.insertToRoom(model);
    }

    public LiveData<String> insertMessage(DataModel model){
        repo.insertToRoom(model).subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        message.postValue("completed");
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        message.postValue("error");
                    }
                });

        return message;
    }

    public LiveData<List<DataModel>> getAllData(){
        allData = repo.getAllDataStored();
        return allData;
    }

    public LiveData<NewsResponse> getBreakingNews(){
        repo.getBreakingNews().subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<NewsResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull NewsResponse newsResponse) {
                       breakingNews.postValue(newsResponse);
                        Log.d("fetchingArticle: ",newsResponse.getStatus());
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return breakingNews;
    }

}
