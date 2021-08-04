package com.mahmoudbashir.taskepsj.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mahmoudbashir.taskepsj.R;
import com.mahmoudbashir.taskepsj.adapters.SavedAdapter;
import com.mahmoudbashir.taskepsj.databinding.FragmentSavedDataBinding;
import com.mahmoudbashir.taskepsj.pojo.DataModel;
import com.mahmoudbashir.taskepsj.viewModel.StViewModel;

import java.util.ArrayList;
import java.util.List;


public class SavedData_Fragment extends Fragment {
    private FragmentSavedDataBinding savedDataBinding;
    private StViewModel viewModel;
    List<DataModel> mlist;
    private SavedAdapter savedAdapter;


    public SavedData_Fragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        savedDataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_saved_data_, container, false);

        doInitialization();
        setUpRecyclerView();
        gettingStoredListData();


        return savedDataBinding.getRoot();
    }

    private void setUpRecyclerView() {
        mlist = new ArrayList<>();
        savedAdapter = new SavedAdapter(mlist);
        savedDataBinding.recSavedData.setHasFixedSize(true);
        savedDataBinding.recSavedData.setAdapter(savedAdapter);
    }

    private void gettingStoredListData(){
        viewModel.getAllData().observe(this.getViewLifecycleOwner(), new Observer<List<DataModel>>() {
            @Override
            public void onChanged(List<DataModel> dataModels) {
                if (dataModels != null){
                    mlist.addAll(dataModels);
                    savedDataBinding.setIsAdded(true);
                }
            }
        });
        savedAdapter.notifyDataSetChanged();
    }


    private void doInitialization(){
        viewModel = ViewModelProviders.of(this).get(StViewModel.class);
    }
}