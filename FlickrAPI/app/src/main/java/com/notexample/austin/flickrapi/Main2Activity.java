package com.notexample.austin.flickrapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final String url = getIntent().getStringExtra("url");
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(url);

    }


}
