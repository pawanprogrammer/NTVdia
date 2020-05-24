package com.apgklobal.webpromo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    ArrayList<String> arrayList = new ArrayList<>();
    Context c;

    public MyAdapter(Context c, ArrayList<String> arrayList) {
        super();
        this.c = c;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.link, viewGroup,
                false);
        MyHolder myHolder = new MyHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        myHolder.link.setText(arrayList.get(i));
        myHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(arrayList.get(i)));
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView link;
        TextView btnopen;
        ConstraintLayout constraintLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            link = itemView.findViewById(R.id.link);
            btnopen = itemView.findViewById(R.id.btnopen);
            constraintLayout = itemView.findViewById(R.id.layout);
        }
    }
}
