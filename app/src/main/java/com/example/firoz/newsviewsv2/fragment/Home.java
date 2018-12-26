package com.example.firoz.newsviewsv2.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.firoz.newsviewsv2.R;
import com.example.firoz.newsviewsv2.adapter.ArticleAdapter;
import com.example.firoz.newsviewsv2.api.ApiService;
import com.example.firoz.newsviewsv2.api.ApiUtils;
import com.example.firoz.newsviewsv2.model.Article;
import com.example.firoz.newsviewsv2.model.GetNewsViews;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    private String strUrl = "http://numbersapi.com/10";
    private String NEWS_API = "https://newsapi.org/v2/top-headlines?sources=al-jazeera-english&apiKey=";
    private String API_KEY = "77f25792a70749929ba013c7f297261c";

    private List<Article> articleList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        loadNews();
    }

    private void initViews() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    // --- Method for loading the news from news api
    private void loadNews() {

        ApiService apiService = ApiUtils.getService();
        Call<GetNewsViews> newsViews = apiService.getNewsDetails(NEWS_API + API_KEY);

        newsViews.enqueue(new Callback<GetNewsViews>() {
            @Override
            public void onResponse(Call<GetNewsViews> call, Response<GetNewsViews> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Response Successfull", Toast.LENGTH_SHORT).show();

                    if (response.body() != null) {
                        articleList = response.body().getArticles();

                        ArticleAdapter adapter = new ArticleAdapter(getContext(), articleList);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetNewsViews> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to response", Toast.LENGTH_SHORT).show();
                Log.e("news_response", t.getMessage());
            }
        });

    }
}
