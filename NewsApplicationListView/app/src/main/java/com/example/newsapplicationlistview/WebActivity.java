package com.example.newsapplicationlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebView myWebView = (WebView) findViewById(R.id.webview);

        if(getIntent() !=null  && getIntent().getExtras()!=null){
            String url = getIntent().getExtras().getString("url");
            String title = getIntent().getExtras().getString("title");
            myWebView.loadUrl(url);
            setTitle(title);
        }

    }
}
