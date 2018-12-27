package com.example.firoz.newsviewsv2.fragment;

import android.content.Intent;
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
import com.example.firoz.newsviewsv2.activity.DetailsActivity;
import com.example.firoz.newsviewsv2.activity.MainActivity;
import com.example.firoz.newsviewsv2.adapter.ArticleAdapter;
import com.example.firoz.newsviewsv2.api.ApiService;
import com.example.firoz.newsviewsv2.api.ApiUtils;
import com.example.firoz.newsviewsv2.listener.ItemClickListener;
import com.example.firoz.newsviewsv2.model.Article;
import com.example.firoz.newsviewsv2.model.GetNewsViews;
import com.example.firoz.newsviewsv2.utility.AppConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends Fragment implements ItemClickListener {


    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private List<String> API_LIST = new ArrayList<>();

    private List<Article> articleList = new ArrayList<>();
    private ApiService apiService;

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
        initListeners();
        initApiList();
        loadNews();
    }


    private void initViews() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    private void initListeners() {
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews();
            }

        });
    }

    private void initApiList() {
        API_LIST.add(AppConstant.API_1);
        API_LIST.add(AppConstant.API_2);
        API_LIST.add(AppConstant.API_3);
        API_LIST.add(AppConstant.API_4);
        API_LIST.add(AppConstant.API_5);
        API_LIST.add(AppConstant.API_6);
        API_LIST.add(AppConstant.API_7);
        API_LIST.add(AppConstant.API_8);
        API_LIST.add(AppConstant.API_9);
        API_LIST.add(AppConstant.API_10);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    // --- Method for loading the news from news api
    private void loadNews() {

        // --- At first check , is internet connected or not
        if (!((MainActivity) getActivity()).isInternetConnected()) {
            ((MainActivity) getActivity()).showSnackBar("No internet connection");
            refreshLayout.setRefreshing(false);
            return;
        }
        apiService = ApiUtils.getService();

        for (int i = 0; i < 10; i++) {
            load(API_LIST.get(i));
        }

    }

    private void load(String url) {
        apiService.getNewsDetails(url + AppConstant.API_KEY).enqueue(new Callback<GetNewsViews>() {
            @Override
            public void onResponse(Call<GetNewsViews> call, Response<GetNewsViews> response) {

                if (response.isSuccessful()) {

                    if (response.body().getArticles() != null) {
                        // articleList = response.body().getArticles();
                        articleList.addAll(response.body().getArticles());
                        ArticleAdapter adapter = new ArticleAdapter(getContext(), articleList, Home.this);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    }
                }

                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<GetNewsViews> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to response", Toast.LENGTH_SHORT).show();
                Log.e("news_response", t.getMessage());
                refreshLayout.setRefreshing(false);

            }
        });
    }

    @Override
    public void itemClick(int position) {
        // --- Go to details activity
        getActivity().startActivity(new Intent(getContext(), DetailsActivity.class).putExtra("article", articleList.get(position)));
    }
}
