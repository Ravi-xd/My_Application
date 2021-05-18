package com.example.newsapi.RoomDataBase;

import androidx.room.TypeConverter;

import com.example.newsapi.Article;
import com.example.newsapi.Source;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverter implements Serializable {

    @TypeConverter
    public static List<Article> fromString1(String str)
    {
        Type type1=new TypeToken<List<Article>>()
        {}.getType();
        return new Gson().fromJson(str,type1);
    }
    @TypeConverter
    public static String fromArticle(List<Article> article)
    {
        Gson gson=new Gson();
        String json1 = gson.toJson(article);
        return json1;
    }

    @TypeConverter
    public static Source fromString(String value)
    {

        Type type=new TypeToken<Source>()
        {}.getType();
        return new Gson().fromJson(value,type);
    }
    @TypeConverter
    public static String fromSource(Source source)
    {

        Gson gson=new Gson();
       String json = gson.toJson(source);
       return json;
    }

}
