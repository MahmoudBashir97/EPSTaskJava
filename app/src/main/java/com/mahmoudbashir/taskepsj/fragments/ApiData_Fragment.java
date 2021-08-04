package com.mahmoudbashir.taskepsj.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mahmoudbashir.taskepsj.R;
import com.mahmoudbashir.taskepsj.adapters.apiAdapter;
import com.mahmoudbashir.taskepsj.databinding.FragmentApiDataBinding;
import com.mahmoudbashir.taskepsj.pojo.Article;
import com.mahmoudbashir.taskepsj.pojo.NewsResponse;
import com.mahmoudbashir.taskepsj.viewModel.StViewModel;

import java.util.ArrayList;
import java.util.List;

public class ApiData_Fragment extends Fragment {
    private FragmentApiDataBinding apiBinding;
    private apiAdapter adapter_api;
    private StViewModel viewModel;
    private List<Article> mlist;

    public ApiData_Fragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        apiBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_api_data_, container, false);

        doInitialize();
        setUpRecyclerView();
        fetchingDataApi();


        return apiBinding.getRoot();
    }

    private void setUpRecyclerView() {
        adapter_api = new apiAdapter(mlist);
        apiBinding.recApi.setHasFixedSize(true);
        apiBinding.recApi.setAdapter(adapter_api);
    }

    private void doInitialize() {
        viewModel = ViewModelProviders.of(this).get(StViewModel.class);
        mlist = new ArrayList<>();
    }
    private void fetchingDataApi(){
        apiBinding.setIsLoading(true);

       viewModel.getBreakingNews().observe(this.getViewLifecycleOwner(),
                newsResponse -> {
                    if (newsResponse != null){
                        apiBinding.setIsLoading(false);
                        mlist.addAll(newsResponse.getArticles());
                        adapter_api.notifyDataSetChanged();
                    }
                });
    }

}