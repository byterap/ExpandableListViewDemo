package com.example.hasee.expandablelistviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowseNewsActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_news);
        webView = (WebView)findViewById(R.id.webView);
        String pic_url = getIntent().getStringExtra("content_url");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(pic_url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
    }
}
