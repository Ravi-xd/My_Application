package com.example.newsapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class SecondActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView appbar_title,appbar_subtitle,date,time,title;
    private boolean isHideTolbarView=false;
    private FrameLayout date_behavior;
    private AppBarLayout appBarLayout;
    private LinearLayout titleAppbar;
    private Toolbar toolbar;
    private String mUrl,mIng,mTilt,mDate,mSource,mAuthor;
    private ExampleAdapter exampleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
        date_behavior=findViewById(R.id.date_behavior);
        titleAppbar=findViewById(R.id.title_appbar);
        imageView=findViewById(R.id.backdrop);
        appbar_title=findViewById(R.id.title_on_appbar);
       appbar_subtitle=findViewById(R.id.subtitle_on_appbar);
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        title=findViewById(R.id.title);


        Intent intent=getIntent();


        mUrl=intent.getStringExtra("url");
        mIng=intent.getStringExtra("img");
        mTilt=intent.getStringExtra("title");
        mDate=intent.getStringExtra("date");
        mSource=intent.getStringExtra("source");
        mAuthor=intent.getStringExtra("author");
        RequestOptions requestOptions=new RequestOptions();
        Glide.with(this)
                .load(mIng)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
        appbar_title.setText(mSource);
        appbar_subtitle.setText(mUrl);
        date.setText(Utils.DateFormat(mDate));
        title.setText(mTilt);
        time.setText(mSource+Utils.DateToTimeFormat(mDate));
        initWebView(mUrl);

    }
    private void initWebView(String url){
        WebView webView= findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}