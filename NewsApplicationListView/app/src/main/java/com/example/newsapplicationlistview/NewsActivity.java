package com.example.newsapplicationlistview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    ArrayList<NewsSource> newsList;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressBar progressbars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        progressbars = (ProgressBar) findViewById(R.id.progressBar);

        if(getIntent() != null && getIntent().getExtras() != null ){
            Source source= (Source) getIntent().getExtras().get(MainActivity.SOURCE_KEY);
            Log.i("demo", "v " +source.name );
            setTitle(source.name);
            new GetNEWSDataAsync().execute("https://newsapi.org/v2/top-headlines?sources="+source.id+"&apiKey=39f746c057f2488eb3876c9be74d9837");
            Intent intent = new Intent();
            setResult(RESULT_OK,intent);

        } else{
            Intent intent = new Intent();
            setResult(RESULT_CANCELED,intent);
        }

    }

    private class GetNEWSDataAsync extends AsyncTask<String, Void, ArrayList<NewsSource>> {
        String data;
        @Override
        protected void onPreExecute() {
            progressbars.setVisibility(View.VISIBLE);
            newsList = new ArrayList<>();
        }

        @Override
        protected ArrayList<NewsSource> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            String json = null;


            try {
                URL urls = new URL(strings[0]);
                connection = (HttpURLConnection) urls.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    data = IOUtils.toString(connection.getInputStream(), "utf-8");
                    JSONObject root = new JSONObject(data);
                    JSONArray articles = root.getJSONArray("articles");
                    for(int i=0; i<articles.length(); i++){
                        JSONObject sourceJSON = articles.getJSONObject(i);
                        NewsSource newsSourceObject = new NewsSource();
                        if(sourceJSON.getString("author").equals("null")){
                            newsSourceObject.setAuthor("No Author Information");
                        } else{
                            newsSourceObject.setAuthor(sourceJSON.getString("author"));
                        }
                        if(sourceJSON.getString("title").equals("null")){
                            newsSourceObject.setTitle("No Title Information");
                        } else{
                            newsSourceObject.setTitle(sourceJSON.getString("title"));
                        }
                        if(sourceJSON.getString("publishedAt").equals("null")){
                            newsSourceObject.setPublishedAt("No Published Information");
                        } else{
                            newsSourceObject.setPublishedAt(sourceJSON.getString("publishedAt"));
                        }
                        newsSourceObject.setImageURL(sourceJSON.getString("urlToImage"));
                        newsSourceObject.setWebURL(sourceJSON.getString("url"));

                        newsList.add(newsSourceObject);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return newsList ;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsSource> newsList) {
            progressbars.setVisibility(View.INVISIBLE);
            layoutManager = new LinearLayoutManager(NewsActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recAdapter = new NewsSourceAdapter(newsList);
            recyclerView.setAdapter(recAdapter);

        }
    }


}
