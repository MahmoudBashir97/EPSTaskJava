package com.mahmoudbashir.taskepsj.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mahmoudbashir.taskepsj.R;
import com.mahmoudbashir.taskepsj.adapters.ViewPagerAdapter;
import com.mahmoudbashir.taskepsj.databinding.FragmentViewPagerImagesBinding;
import com.mahmoudbashir.taskepsj.pojo.DataModel;
import com.mahmoudbashir.taskepsj.viewModel.StViewModel;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerImages_Fragment extends Fragment {
    private FragmentViewPagerImagesBinding pagerBinding;
    private int dotscount =0;
    LinearLayout sliderDotspanel;
    private StViewModel viewModel;
    private ViewPagerAdapter pagerAdapter;
    private List<DataModel> mlist;

    public ViewPagerImages_Fragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pagerBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_view_pager_images_, container, false);

        doInitialize();
        gettingDataStored();



        return pagerBinding.getRoot();
    }

    private void doInitialize(){
        mlist = new ArrayList<>();
        viewModel = ViewModelProviders.of(this).get(StViewModel.class);
        pagerAdapter = new ViewPagerAdapter(getContext(),mlist);
    }
    private void gettingDataStored() {
        viewModel.getAllData().observe(getViewLifecycleOwner(), new Observer<List<DataModel>>() {
            @Override
            public void onChanged(List<DataModel> dataModels) {
                if (dataModels !=null){
                    mlist.addAll(dataModels);
                }

                pagerAdapter.notifyDataSetChanged();
                dotscount=pagerAdapter.getCount();
                pagerBinding.viewPager.setAdapter(pagerAdapter);
                if (dotscount >0){
                    setUpDots();
                }

            }
        });

    }

    private void setUpDots(){
        Log.d("countT: ","$dotscount");
        ImageView[] dots = new ImageView[dotscount];

        for (int i=0;i<dotscount;i++){
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.non_active));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(8,0,8,0);
            pagerBinding.SliderDots.addView(dots[i],params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dots));

        pagerBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0 ;i<dotscount;i++) {
                    dots[i].setImageDrawable(
                            ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.non_active
                            )
                    );
                }

                dots[position].setImageDrawable(
                        ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.active_dots
                        )
                );
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}