package com.example.newsapplicationlistview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Source> channelList;

    SourceAdapter adapter;
    ProgressBar progressbar;
    public final static String SOURCE_KEY = "name";
    public final static String url_KEY = "name";
    public final static int REQ_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("News Channels");
        progressbar = (ProgressBar) findViewById(R.id.progressBar);
        if (isConnected() == true) {
            Toast.makeText(MainActivity.this, "Connected to wifi and phone", Toast.LENGTH_SHORT).show();
            new GetDataAsync().execute("https://newsapi.org/v2/sources?apiKey=39f746c057f2488eb3876c9be74d9837");
        } else {
            Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || (networkInfo.getType() != connectivityManager.TYPE_WIFI &&
                networkInfo.getType() != connectivityManager.TYPE_MOBILE)) {
            return false;
        }

        return true;
    }

    private class GetDataAsync extends AsyncTask<String, Void, ArrayList<Source>> {
        @Override
        protected void onPreExecute() {
            progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Source> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            String json = null;
            channelList = new ArrayList<>();
            try {
                URL urls = new URL(strings[0]);
                connection = (HttpURLConnection) urls.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    json = IOUtils.toString(connection.getInputStream(), "utf-8");
                    JSONObject root = new JSONObject(json);
                    JSONArray channels = root.getJSONArray("sources");
                    for (int i = 0; i < channels.length(); i++) {
                        JSONObject channelJSON = channels.getJSONObject(i);
                        Source source = new Source();
                        source.setId(channelJSON.getString("id"));
                        source.setName(channelJSON.getString("name"));
                        channelList.add(source);
                    }

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return channelList;
        }


        @Override
        protected void onPostExecute(final ArrayList<Source> sources) {
            progressbar.setVisibility(View.GONE);

            adapter = new SourceAdapter(MainActivity.this, R.layout.source_item, sources);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                    Log.i("demo", "adapter data" +adapter.getItem(position));
                    intent.putExtra(SOURCE_KEY, adapter.getItem(position));
                    startActivityForResult(intent, REQ_CODE);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Home Page", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Home Page, request cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
