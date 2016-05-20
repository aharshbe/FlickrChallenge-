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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
import java.util.LinkedList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    LinkedList<String> items;
    ArrayAdapter<String> mAdapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new LinkedList<>();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(mAdapter);


        final AsyncHttpClient client = new AsyncHttpClient();


        client.get("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=cf930e4ef3a4a52af4ee0a6fe69b6b61&format=json&text=ugly&nojsoncallback=1&extras=url_l", new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                Toast.makeText(getApplicationContext(), "Process Successful",
                        Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = responseBody.getJSONObject("photos");
                    JSONArray jsonArray = jsonObject.getJSONArray("photo");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(9);
                    items.add(jsonObject1.getString("url_l"));
                    items.add(jsonArray.getJSONObject(1).getString("url_l"));
                    items.add(jsonArray.getJSONObject(2).getString("url_l"));
                    items.add(jsonArray.getJSONObject(3).getString("url_l"));
                    items.add(jsonArray.getJSONObject(4).getString("url_l"));
                    items.add(jsonArray.getJSONObject(5).getString("url_l"));
                    items.add(jsonArray.getJSONObject(6).getString("url_l"));
                    items.add(jsonArray.getJSONObject(7).getString("url_l"));
                    items.add(jsonArray.getJSONObject(8).getString("url_l"));
                    items.add(jsonArray.getJSONObject(9).getString("url_l"));
                    items.add(jsonArray.getJSONObject(10).getString("url_l"));
                    items.add(jsonArray.getJSONObject(11).getString("url_l"));
                    items.add(jsonArray.getJSONObject(12).getString("url_l"));
                    items.add(jsonArray.getJSONObject(13).getString("url_l"));
                    items.add(jsonArray.getJSONObject(14).getString("url_l"));
                    items.add(jsonArray.getJSONObject(15).getString("url_l"));
                    items.add(jsonArray.getJSONObject(16).getString("url_l"));
                    items.add(jsonArray.getJSONObject(17).getString("url_l"));
                    items.add(jsonArray.getJSONObject(18).getString("url_l"));
                    items.add(jsonArray.getJSONObject(19).getString("url_l"));
                    items.add(jsonArray.getJSONObject(20).getString("url_l"));
                    items.add(jsonArray.getJSONObject(21).getString("url_l"));
                    items.add(jsonArray.getJSONObject(22).getString("url_l"));
                    items.add(jsonArray.getJSONObject(23).getString("url_l"));
                    items.add(jsonArray.getJSONObject(24).getString("url_l"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
                myIntent.putExtra("position", position);
                String imageid = items.get(position);
                myIntent.putExtra("url", imageid);
                startActivity(myIntent);
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


