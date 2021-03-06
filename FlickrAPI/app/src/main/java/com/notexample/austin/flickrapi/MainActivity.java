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
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void clickingSearch(View view) {

        items = new LinkedList<>();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(mAdapter);
        editText = (EditText) findViewById(R.id.editText);


        final AsyncHttpClient client = new AsyncHttpClient();

        final String searchVariable = editText.getText().toString();

        // Not sure why this boolean isn't working but the objective was to make it so that if a user enter's nothing, the search doesn't happen

        if (editText.getText().toString() == " ") {
            Toast.makeText(getApplicationContext(), "No result",
                    Toast.LENGTH_SHORT).show();

        } else {


            client.get("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=cf930e4ef3a4a52af4ee0a6fe69b6b61&format=json&text=" + searchVariable + "&nojsoncallback=1&extras=url_l", new JsonHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {



                    Toast.makeText(getApplicationContext(), "Successfully searched for" + " " + editText.getText().toString(),
                            Toast.LENGTH_SHORT).show();


                    try {
                        JSONObject jsonObject = responseBody.getJSONObject("photos");
                        JSONArray jsonArray = jsonObject.getJSONArray("photo");



                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject photo = jsonArray.getJSONObject(i);
                            if (!photo.has("url_l")) continue;
                            items.add("Photo" + " " + i + " " + "of search for:" + " " + searchVariable);
                            items.add(photo.getString("url_l"));
                        }
                        mAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Toast.makeText(getApplicationContext(), "Process Not Successful",
                            Toast.LENGTH_SHORT).show();
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
                    myIntent.putExtra("position", position);
                    String imageid = items.get(position);
                    String picasso = items.get(position+1);
                    myIntent.putExtra("url", imageid);
                    myIntent.putExtra("url2", picasso);
                    startActivity(myIntent);
                }
            });


        }

    }

}


