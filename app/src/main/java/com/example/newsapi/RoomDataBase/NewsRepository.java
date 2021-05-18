package com.example.newsapi.RoomDataBase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.newsapi.Article;
import com.example.newsapi.Example;

import java.util.List;

public class NewsRepository {
    private NewsDataBase dataBase;
    private LiveData<List<Article>> getAllArticle;

    public NewsRepository(Application application) {
        dataBase = NewsDataBase.getInstance(application);
        getAllArticle = dataBase.articleDao().getArticles();

    }

    public void insert(List<Article> articleList) {
        new InsertAsyncTask(dataBase).execute(articleList);

    }

    public LiveData<List<Article>> getArticles() {
        return getAllArticle;
    }
    public void deleteAll(){new DeleteAsyncTask(dataBase).execute();}

    class InsertAsyncTask extends AsyncTask<List<Article>, Void, Void> {
        private ArticleDao articleDao;

        InsertAsyncTask(NewsDataBase newsDataBase) {
            articleDao = newsDataBase.articleDao();
        }

        @Override
        protected Void doInBackground(List<Article>... articles) {
            articleDao.insert((List<Article>) articles[0]);
            return null;
        }
    }
    class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private ArticleDao articleDao;

        DeleteAsyncTask(NewsDataBase newsDataBase) {
            articleDao = newsDataBase.articleDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            articleDao.deleteAll();
            return null;
        }
    }
}
