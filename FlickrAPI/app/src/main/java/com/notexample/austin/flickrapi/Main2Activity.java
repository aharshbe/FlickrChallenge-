package com.notexample.austin.flickrapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView textView, textView2;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final String image = getIntent().getStringExtra("image");
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(image);

        final String url = getIntent().getStringExtra("url");
        textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText(url);


        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(getIntent().getStringExtra("url"));

    }


}
