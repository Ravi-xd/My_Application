package com.example.newsapi.RoomDataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.newsapi.Article;

import java.util.List;

@Dao
public interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Article> articleList);

    @Query("SELECT * FROM news_api")
    LiveData<List<Article>> getArticles();

    @Query("DELETE FROM news_api")
    void deleteAll();
}
