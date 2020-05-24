package com.apkglobal.stories;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.internal.InternalTokenResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<ListData> listData;
    private Context context;

    public MyAdapter(Context context, List<ListData> listData) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ListData ld = listData.get(position);
        holder.txtid.setText(ld.getId());
        holder.txtname.setText(ld.getName());
        Picasso.get().load(ld.getMovie()).into(holder.ivprofile);
        holder.ivprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent web = new Intent(context, StoryActivity.class);
                web.putExtra("pdfurl", ld.getName());
                context.startActivity(web);
            }
        });

        //holder.ivprofile.setText(ld.getMovie());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtid, txtname;
        private ImageView ivprofile;

        public ViewHolder(View itemView) {
            super(itemView);
            txtid = itemView.findViewById(R.id.idtxt);
            txtname = itemView.findViewById(R.id.nametxt);
            ivprofile = itemView.findViewById(R.id.movietxt);
        }
    }
}