package com.mahmoudbashir.taskepsj.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahmoudbashir.taskepsj.R;
import com.mahmoudbashir.taskepsj.pojo.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class apiAdapter extends RecyclerView.Adapter<apiAdapter.ViewHolder> {
    List<Article> mlist;

    public apiAdapter(List<Article> mlist) {
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_preview,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(mlist.get(position).getTitle());
        holder.tvDescription.setText(mlist.get(position).getDescription());
        holder.tvPublishedAt.setText(mlist.get(position).getPublishedAt().toString());
        holder.tvSource.setText(mlist.get(position).getSource().getName());


        Picasso.get().load(mlist.get(position)
                .getUrlToImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.ivArticleImage);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivArticleImage;
        TextView tvSource,tvTitle,tvDescription,tvPublishedAt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivArticleImage = itemView.findViewById(R.id.ivArticleImage);
            tvSource = itemView.findViewById(R.id.tvSource);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPublishedAt = itemView.findViewById(R.id.tvPublishedAt);
        }
    }
}
