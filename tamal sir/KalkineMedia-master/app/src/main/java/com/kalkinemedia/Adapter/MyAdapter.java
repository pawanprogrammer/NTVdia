package com.kalkinemedia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kalkinemedia.Dashboard.DashboardActivity;
import com.kalkinemedia.POJO.Book;
import com.kalkinemedia.POJO.Mydata;
import com.kalkinemedia.R;
import com.kalkinemedia.realm.RealmController;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;
import retrofit2.Callback;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    String[] post_image;
    String[] post_title;
    String[] post_by;
    String[] post_date;
    String [] format_date;
    public static String[] post_content;
    Context context;
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    Typeface custom_font;
    public MyAdapter(Context c) {
        this.context = c;
    }
    boolean b = false; //toolbar is hide
    private Realm realm;

    public MyAdapter(Callback<List<Mydata>> callback, String[] post_image, String[] post_title, String[] post_by,
                     String[] post_date, String[] post_content) {
        this.post_by = post_by;
        this.post_content = post_content;
        this.post_date = post_date;
        this.post_image = post_image;
        this.post_title = post_title;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_items, viewGroup, false);

        custom_font= Typeface.createFromAsset(viewGroup.getContext().getAssets(),
                "font/myfonts.ttf");
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {
        realm.init(myHolder.iv_bookmark.getContext());
        realm = Realm.getDefaultInstance();
        myHolder.post_title.setText(post_title[i]);
        myHolder.post_date.setText(post_date[i]);
        myHolder.post_content.setText(post_content[i]);
        myHolder.post_by.setText(post_by[i]);
        myHolder.post_title.setTypeface(custom_font);
        myHolder.post_title.setAllCaps(true);
        myHolder.iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT, "");
                share.setType("text/plain");
                myHolder.iv_share.getContext().startActivity(share);
                myHolder.iv_share.setImageResource(R.drawable.ic_share_red_24dp);
            }
        });
        myHolder.iv_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(myHolder.iv_bookmark.getContext(), "Bookmark Added", Toast.LENGTH_SHORT).show();
                //to store the data locally
                Book book = new Book();
                //book.setId(RealmController.getInstance().getBooks().size() + 1);
                book.setId(i);
                book.setTitle(post_title[i]);
                book.setBy(post_by[i]);
                book.setContent(post_content[i]);
                book.setImage(post_image[i]);
                book.setDate(post_date[i]);

                // Persist your data easily
                realm.beginTransaction();
                realm.copyToRealm(book);
                realm.commitTransaction();

                myHolder.iv_bookmark.setImageResource(R.drawable.ic_bookmark_red_24dp);
            }
        });
        /*myHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (b == false)
                {
                    DashboardActivity.toolbar.setVisibility(View.VISIBLE);
                    b = true;
                }
                else {
                    DashboardActivity.toolbar.setVisibility(View.GONE);
                    b = false;
                }
            }
        });*/

        //myHolder.post_content.setTypeface(custom_font);
        Picasso.with(myHolder.post_image.getContext()).load(post_image[i]).placeholder(R.drawable.nv_icon).into(myHolder.post_image);
        /*myHolder.btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to send the manually data using android intent
                Intent intent = new Intent(myHolder.btn_read.getContext(),
                        DetailActivity.class);
                intent.putExtra("post_image", post_image);
                intent.putExtra("post_title", post_title);
                intent.putExtra("post_date", post_date);
                intent.putExtra("post_by", post_by);
                intent.putExtra("position", i);
                //intent.putExtra("post_content", post_content);
                myHolder.btn_read.getContext().startActivity(intent);

                //to send the large amount of data using android parceable
                *//*Mydata mydata = new Mydata(post_title, post_by, post_date, post_content,post_image);
                Intent intent = new Intent(myHolder.btn_read.getContext(), DetailActivity.class);
                intent.putExtra("news data", mydata);
                myHolder.btn_read.getContext().startActivity(intent);*//*
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return post_title.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder  {
        TextView post_title, post_by, post_date, post_content;
        ImageView post_image, iv_bookmark, iv_share;
        TextView btn_read;
        ConstraintLayout viewForeground;
        CardView cardView;

        public MyHolder(@NonNull View v) {
            super(v);
            cardView = v.findViewById(R.id.card_view);
            iv_bookmark = v.findViewById(R.id.iv_bookmark);
            iv_share = v.findViewById(R.id.iv_share);
            post_by = v.findViewById(R.id.view_by);
            post_content = v.findViewById(R.id.view_content);
            post_date = v.findViewById(R.id.view_date);
            post_image = v.findViewById(R.id.view_image);
            post_title = v.findViewById(R.id.view_title);
            //btn_read = v.findViewById(R.id.btn_read);
            viewForeground=v.findViewById(R.id.contain);
        }
    }
}


    /*private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = iv_digital;
    private boolean isLoaderVisible = false;

    //private List<PostItem> mPostItems;

    *//*public PostRecyclerAdapter(List<PostItem> postItems) {
        this.mPostItems = postItems;
    }*//*


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.news_items, parent, false));
            case VIEW_TYPE_LOADING:
                return new FooterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == mPostItems.size() - iv_digital ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return mPostItems == null ? 0 : mPostItems.size();
    }

    public void add(PostItem response) {
        mPostItems.add(response);
        notifyItemInserted(mPostItems.size() - iv_digital);
    }

    public void addAll(List<PostItem> postItems) {
        for (PostItem response : postItems) {
            add(response);
        }
    }


    private void remove(PostItem postItems) {
        int position = mPostItems.indexOf(postItems);
        if (position > -iv_digital) {
            mPostItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoading() {
        isLoaderVisible = true;
        add(new PostItem());
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = mPostItems.size() - iv_digital;
        PostItem item = getItem(position);
        if (item != null) {
            mPostItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    PostItem getItem(int position) {
        return mPostItems.get(position);
    }


    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.textViewTitle)
        TextView textViewTitle;
        @BindView(R.id.textViewDescription)
        TextView textViewDescription;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);
            PostItem item = mPostItems.get(position);
            textViewTitle.setText(item.getTitle());
            textViewDescription.setText(item.getDescription());
        }
    }

    public class FooterHolder extends BaseViewHolder {

        ProgressBar mProgressBar;
        FooterHolder(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.progressBar);
        }

        @Override
        protected void clear() {

        }
    }

}
*/