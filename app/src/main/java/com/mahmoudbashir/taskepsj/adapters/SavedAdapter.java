package com.mahmoudbashir.taskepsj.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.mahmoudbashir.taskepsj.R;
import com.mahmoudbashir.taskepsj.pojo.DataModel;
import com.squareup.picasso.Picasso;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {
    List<DataModel> mlist;
    public SavedAdapter(List<DataModel> list){
        this.mlist = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_saved_data,parent,false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_title.setText(mlist.get(position).getTitle());
        holder.txt_code.setText(mlist.get(position).getOptNumber());
        holder.txt_address.setText(mlist.get(position).getAddress());

        Picasso.get()
                .load(mlist.get(position).getImgUri())
                .placeholder(R.drawable.ic_baseline_camera_alt_24)
                .into(holder.img_pic);

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_code,txt_title,txt_address;
        ImageView img_pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_code = itemView.findViewById(R.id.txt_code);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_address = itemView.findViewById(R.id.txt_address);
            img_pic = itemView.findViewById(R.id.img_pic);
        }
    }


}
