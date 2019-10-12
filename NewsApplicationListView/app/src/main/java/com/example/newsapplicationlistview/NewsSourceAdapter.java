package com.example.newsapplicationlistview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsSourceAdapter extends RecyclerView.Adapter<NewsSourceAdapter.ViewHolder> {
    ArrayList<NewsSource> newsData;

    public NewsSourceAdapter(ArrayList<NewsSource> newsData) {
        this.newsData = newsData;
    }

    @NonNull
    @Override
    public NewsSourceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nws_item, parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsSourceAdapter.ViewHolder holder, int position) {
        NewsSource nsObject =newsData.get(position);
        holder.tv_title.setText(nsObject.title);
        holder.tv_publishedAt.setText(nsObject.publishedAt);
        holder.tv_author.setText(nsObject.author);
         if(nsObject.getImageURL().equals("null") || nsObject.getImageURL().isEmpty()){
             holder.imageView.setImageResource(R.drawable.no_photo);
         }else{
             Picasso.get().load(nsObject.imageURL).into(holder.imageView);
         }
        holder.newsobj= nsObject;


    }

    @Override
    public int getItemCount() {
        return newsData.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tv_author,tv_title,tv_publishedAt;
        ImageView imageView;
        NewsSource newsobj;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_author =itemView.findViewById(R.id.tv_author);
            tv_publishedAt = itemView.findViewById(R.id.tv_publishedAt);
            tv_title = itemView.findViewById(R.id.tv_title);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("demo","hi"  + newsobj.webURL);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url",newsobj.webURL);
                    intent.putExtra("title",newsobj.title);
                    context.startActivity(intent);
                }
            });


        }
    }
}
