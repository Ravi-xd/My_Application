package com.example.newsapi.RoomDataBase;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.newsapi.Article;

@Database(entities = { Article.class}, version = 1,exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class NewsDataBase extends RoomDatabase {

    private static NewsDataBase instance;

    public abstract ArticleDao articleDao();

    public static synchronized NewsDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NewsDataBase.class, "news_api")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
           // new PopulateDbAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ArticleDao articleDao;

        PopulateDbAsyncTask(NewsDataBase db) {
            articleDao = db.articleDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // articleDao.deleteAll();
            return null;
        }
    }


}
