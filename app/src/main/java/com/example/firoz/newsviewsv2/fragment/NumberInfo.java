package com.example.firoz.newsviewsv2.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firoz.newsviewsv2.R;
import com.example.firoz.newsviewsv2.activity.BaseActivity;
import com.example.firoz.newsviewsv2.api.ApiClient;
import com.example.firoz.newsviewsv2.api.ApiService;
import com.example.firoz.newsviewsv2.api.ApiUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NumberInfo extends Fragment {

    @BindView(R.id.numberInfoTextView)
    TextView numberInfoTextView;
    Unbinder unbinder;
    private String API_URL = "http://numbersapi.com/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_number, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        numberInfoTextView.setText(BaseActivity.number);

        //  loadNumberData();
        new GetNumberInfo().execute();
    }

    /*
    private void loadNumberData() {

        ApiUtils.getService().getNumberInfo(API_URL + BaseActivity.number).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Okay", Toast.LENGTH_SHORT).show();

                }
                Toast.makeText(getContext(), "" + response.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                Log.e("number_fetch_error", t.getMessage());
            }
        });
    }
    */

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    // Get number info using AsyncTask
    class GetNumberInfo extends AsyncTask<String, String, String> {

        private String result = "";

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL(API_URL + BaseActivity.number);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                result = reader.readLine();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            numberInfoTextView.setText(result);
        }
    }
}
