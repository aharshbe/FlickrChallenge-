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

public class API {
    LinkedList<String> items;
    ArrayAdapter<String> mAdapter;
    EditText editText;


    public void dorequest() {


        final AsyncHttpClient client = new AsyncHttpClient();

        final String searchVariable = editText.getText().toString();

        client.get(
                "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=cf930e4ef3a4a52af4ee0a6fe69b6b61&format=json&text="
                        + searchVariable + "&nojsoncallback=1&extras=url_l",
                null,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                        try {
                            JSONObject jsonObject = response.getJSONObject("photos");
                            JSONArray jsonArray = jsonObject.getJSONArray("photo");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject photo = jsonArray.getJSONObject(i);
                                if (!photo.has("url_l")) continue;
                                items.add(photo.getString("url_l"));
                            }
                            mAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                });

    }


}


