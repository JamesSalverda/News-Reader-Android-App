package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.parameter.Articles;
import com.example.project.parameter.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    final String API_KEY = "45cfd28b6ecf49e3b23d3955ac9ed1bc";
    Button refresh, logout, settings;
    List<Articles> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recycle);
        refresh = findViewById(R.id.btnRefresh);
        logout = findViewById(R.id.btnLogout);
        settings = findViewById(R.id.btnSettings);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final String country = getCountry();

        fetchJSON(country, API_KEY);

        // runs fetchJSON method to update feed
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchJSON(country, API_KEY);
                Toast.makeText(HomeActivity.this, "Up To Date!", Toast.LENGTH_LONG).show();
            }
        });

        // destroy HomeActivity and go back to MainActivity
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.this.finish();
                Toast.makeText(HomeActivity.this, "Logged Out", Toast.LENGTH_LONG).show();
            }
        });

        // sends the user to the settings activity
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    // grabs the data from Client and Headlines
    private void fetchJSON(String country, String api_key) {
        Call<Headlines> call = Client.getInstance().getApi().getHeadlines(country, api_key);

        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful() && response.body().getArticles() != null){
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(HomeActivity.this, articles);
                    recyclerView.setAdapter((RecyclerView.Adapter) adapter);
                }
                else{
                    Toast.makeText(HomeActivity.this, "POO POO", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    // finds the country local to the user
    private String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();

        return country.toLowerCase();
    }
}