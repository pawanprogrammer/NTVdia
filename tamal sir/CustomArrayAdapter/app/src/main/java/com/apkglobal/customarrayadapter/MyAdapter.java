package com.apkglobal.customarrayadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter<Pojo> {
    Context c;
    List<Pojo> list= new ArrayList<>();

    public MyAdapter(Context c, int item, List<Pojo> list ) {
        super(c, item, list);
        this.c = c;
        this.list = list;
    }


    @Override
    public int getCount() {
        return super.getCount();
    }


    @NonNull
    @Override
    public View getView(int position,  @Nullable View convertView,
                      @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        TextView title = convertView.findViewById(R.id.title);
        TextView body = convertView.findViewById(R.id.body);
        ImageView iv = convertView.findViewById(R.id.iv);

        title.setText(list.get(position).getTitle());
        body.setText(list.get(position).getBody());
        iv.setImageResource(list.get(position).getImage());

        return convertView;
    }
}
