package com.example.newsapplicationlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SourceAdapter extends ArrayAdapter<Source> {
    public SourceAdapter(@NonNull Context context, int resource, @NonNull List<Source> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Source source = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.source_item ,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tv_channel = (TextView) convertView.findViewById(R.id.tv_channel);
            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder) convertView.getTag();
        }
        viewHolder.tv_channel.setText(source.name);

        return convertView;
    }

    private class ViewHolder {
        TextView tv_channel;

    }
}
