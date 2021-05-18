package com.example.newsapi.RoomDataBase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newsapi.Article;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {

    private NewsRepository articleRepository;
    private LiveData<List<Article>> getAllArticle;
    public ArticleViewModel(@NonNull Application application) {
        super(application);
        articleRepository=new NewsRepository(application);
        getAllArticle=articleRepository.getArticles();
    }
    public void insert(List<Article> articleList)
    {
        articleRepository.insert(articleList);
    }


    public LiveData<List<Article>> getArticles() {
        return getAllArticle;
    }
    public void deleteAll(){articleRepository.deleteAll();}
}
