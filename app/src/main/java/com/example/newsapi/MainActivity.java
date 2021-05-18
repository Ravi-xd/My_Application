package com.example.newsapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.newsapi.RoomDataBase.ArticleViewModel;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ExampleAdapter.OnItemClickListener{
    public static final String apikey = "e3c3611c72bb45ba8313017b57a8ce4f";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArticleViewModel articleViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ExampleAdapter exampleAdapter;
    private static final String TAG = "MainActivity";
    private static List<Article> articleList;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        boolean connected = isConnectedToInternet();
        Log.d(TAG, "onCreate: " + connected);

        if (connected) {
            File dbFile = this.getDatabasePath("news_api");
            if (dbFile.exists()) {

                dbFile.delete();
                //articleViewModel.deleteAll();
            }
            LoadJson("");
            showNews();
        } else if (!connected) {
            showNews();
        }
    }

    private boolean isConnectedToInternet() {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()
                && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
            connected = true;
            return connected;
        }
        return connected;

    }

    private void showNews() {
        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        articleViewModel.getArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articleList) {
                exampleAdapter = new ExampleAdapter(MainActivity.this,
                        (articleList));

                recyclerView.setAdapter(exampleAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void LoadJson(final String keyword) {
        ApiIntrface apiIntrface = ApiClient.getClient().create(ApiIntrface.class);
        Call<Example> call;
        if (keyword.length() > 0) {
            call = apiIntrface.getNewsSearch(keyword, "publishedAt", apikey);
        } else {
            call = apiIntrface.getNews("in", apikey);

        }

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()) {

                    articleList = (List<Article>) response.body().getArticles();

                    articleViewModel.insert(articleList);


                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.meu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem searchMenuitem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("search Latest News...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                boolean connected = isConnectedToInternet();
//              File file = getDatabasePath("news_api");
//
//
//                    if (file.exists()) {
//
//                        file.delete();
//                        //articleViewModel.deleteAll();
//                    }

                loadjson(query);

//                    Runnable task = () -> {
//                        try {
//                            while (true) {
//                                Thread.sleep(2500);
//                                if (query.length() > 3){
//
//                            }
//                        } catch (InterruptedException e) {
//                        }
//                    };
//                    Thread thread = new Thread(task);
//                    thread.setDaemon(true);
//                    thread.start();



                return false;
            }

//            private File getDatabasePath(String data) {
//                File dbFile = this.getDatabasePath(data);
//                return dbFile;
//            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                File dbFile = getDatabasePath("news_api");
//                if (dbFile.exists()) {
//
//                    dbFile.delete();
//                    //articleViewModel.deleteAll();
//                }
                loadjson(newText);
//                showNews();
                return false;
            }
        });
        searchMenuitem.getIcon().setVisible(false, false);
        return true;
    }

    @Override
    public void onRefresh() {
        boolean connected = isConnectedToInternet();
//        File dbFile = this.getDatabasePath("news_api");
//        if (dbFile.exists()) {
//
//            dbFile.delete();
//            Log.d(TAG, "onCreate: " +"refresh is working ");
//            //articleViewModel.deleteAll();
        if (connected){
            loadjson("");
            showNews();
        }else if (!connected){
            showNews();
        }

        }

    private void loadjson(String keyword) {
        ApiIntrface apiIntrface = ApiClient.getClient().create(ApiIntrface.class);
        Call<Example> call;
        if (keyword.length() > 0) {
            call = apiIntrface.getNewsSearch(keyword, "publishedAt", apikey);
        } else {
            call = apiIntrface.getNews("in", apikey);

        }

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()) {

                    articleList = (List<Article>) response.body().getArticles();
                    exampleAdapter = new ExampleAdapter(MainActivity.this,
                            (articleList));

                    recyclerView.setAdapter(exampleAdapter);
                    swipeRefreshLayout.setRefreshing(false);



                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });

    }


    @Override
    public void onItemClick(View x, int position) {
        String str = exampleAdapter.articleList.get(position).getTitle();
        Log.d("ghost", str);
        Intent intent = new Intent(this, SecondActivity.class);
        Log.d("adapter position :", String.valueOf(position));
        Article article = exampleAdapter.articleList.get(position);
        intent.putExtra("title", exampleAdapter.articleList.get(position).getTitle());
        intent.putExtra("url", exampleAdapter.articleList.get(position).getUrl());
        intent.putExtra("img", exampleAdapter.articleList.get(position).getUrlToImage());
        intent.putExtra("date", exampleAdapter.articleList.get(position).getPublishedAt());
        intent.putExtra("source", exampleAdapter.articleList.get(position).getSource().getName());
        intent.putExtra("author", exampleAdapter.articleList.get(position).getAuthor());

        {
            Toast.makeText(this, "onItem working", Toast.LENGTH_LONG).show();
        }
        startActivity(intent);
    }
}

