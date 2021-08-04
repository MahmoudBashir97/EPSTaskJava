package com.mahmoudbashir.taskepsj.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mahmoudbashir.taskepsj.R;
import com.mahmoudbashir.taskepsj.pojo.DataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<DataModel> mlist;
    ImageView imageView;
    TextView txt_code,txt_title,txt_address;

    public ViewPagerAdapter(Context context, List<DataModel> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v=layoutInflater.inflate(R.layout.single_pager_item,container,false);
        imageView = v.findViewById(R.id.imageView);
        txt_code = v.findViewById(R.id.txt_code);
        txt_address = v.findViewById(R.id.txt_address);
        txt_title = v.findViewById(R.id.txt_title);


        if (mlist.get(position).getImgUri().length() > 3){
            Picasso.get().load(mlist.get(position).getImgUri())
                    .placeholder(R.drawable.ic_baseline_camera_alt_24)
                    .into(imageView);

            txt_code.setText(mlist.get(position).getOptNumber());
            txt_title.setText(mlist.get(position).getTitle());
            txt_address.setText(mlist.get(position).getAddress());
        }

        container.addView(v,0);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
