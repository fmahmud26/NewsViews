package com.example.firoz.newsviewsv2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.firoz.newsviewsv2.R;
import com.example.firoz.newsviewsv2.listener.ItemClickListener;
import com.example.firoz.newsviewsv2.model.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArtitleViewHolder> {

    private Context context;
    private List<Article> articleList;
    private ItemClickListener itemClickListener;

    public ArticleAdapter(Context context, List<Article> articleList, Fragment fragment) {
        this.context = context;
        this.articleList = articleList;
        itemClickListener = (ItemClickListener) fragment;
    }

    @NonNull
    @Override
    public ArtitleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.newslayout, null);
        return new ArtitleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtitleViewHolder artitleViewHolder, int i) {
        // Set image using Glide Library
        Glide.with(context).load(articleList.get(i).getUrlToImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.newspaper).error(R.drawable.newspaper))
                .into(artitleViewHolder.newsImage);

        artitleViewHolder.newsTitle.setText(articleList.get(i).getTitle());
        artitleViewHolder.publishedTime.setText(articleList.get(i).getPublishedAt());
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }


    // ---- View Holder Class -----
    class ArtitleViewHolder extends RecyclerView.ViewHolder {

        ImageView newsImage;
        TextView newsTitle, publishedTime;

        public ArtitleViewHolder(@NonNull View itemView) {
            super(itemView);

            newsImage = itemView.findViewById(R.id.newsImage);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            publishedTime = itemView.findViewById(R.id.publishedTime);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.itemClick(getAdapterPosition());
                }
            });
        }
    }
}
