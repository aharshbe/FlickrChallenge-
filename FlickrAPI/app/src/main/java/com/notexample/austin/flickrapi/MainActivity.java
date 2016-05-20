package com.notexample.austin.flickrapi;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.*;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    SearchView searchView;
    TextView button, idtv, fulltv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        searchView = (SearchView) findViewById(R.id.search);

        button = (TextView) findViewById(R.id.button);
        idtv = (TextView) findViewById(R.id.id);
        fulltv = (TextView) findViewById(R.id.all);


        final AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=cf930e4ef3a4a52af4ee0a6fe69b6b61&format=json&text=ugly&nojsoncallback=1", new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                Toast.makeText(getApplicationContext(), "Process Successful",
                        Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.VISIBLE);

                String id, full ;
                try {
                    JSONObject jsonObject = responseBody.getJSONObject("photos");
                    JSONArray jsonArray = jsonObject.getJSONArray("photo");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    full = jsonObject1.toString();
                    id = jsonObject1.getString("id");
                    idtv.setText(id);
                    fulltv.setText(full);
                    progressBar.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

            @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(MainActivity.this, "Searching for " + query, Toast.LENGTH_SHORT).show();
        }

    }

    public void checkingCOnnection(View view) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(getApplicationContext(), "Connection ready",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Connection not ready",
                    Toast.LENGTH_SHORT).show();
        }
    }
}


