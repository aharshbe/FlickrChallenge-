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
    TextView button;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        searchView = (SearchView) findViewById(R.id.search);

        button = (TextView) findViewById(R.id.button);

        listView = (ListView) findViewById(R.id.listView);




        final AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=cf930e4ef3a4a52af4ee0a6fe69b6b61&format=json&text=pretty", new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(), "Process Successful",
                        Toast.LENGTH_SHORT).show();


//                try {
//                    String jsonStr = new String(responseBody, "UTF-8");
//                    Log.e("Tag ","jsonStr "+jsonStr);
//
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                InputStream is = null;
//                String contentAsString = null;
//                try {
//                    contentAsString = readIt(is);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                String parsingData = null;
//                try {
//                    parsingData = parseJson(contentAsString);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

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



    public String readIt(InputStream stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String read;

        while ((read = br.readLine()) != null) {
            sb.append(read);
        }
        return sb.toString();
    }

    private String parseJson(String contentAsString) throws JSONException {
        String postList = "";
        JSONArray array = new JSONArray(contentAsString);
        for (int i = 0; i < array.length(); i++) {
            JSONObject post = array.getJSONObject(i);
            String postTitle = post.getString("pages");

            postList += postTitle;
        }
        return postList;
    }




}


