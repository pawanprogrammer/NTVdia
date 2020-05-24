package com.kalkinemedia.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kalkinemedia.Helper.RealmController;
import com.kalkinemedia.POJO.Book;
import com.kalkinemedia.R;
import com.squareup.picasso.Picasso;

import io.realm.Realm;



public class BooksAdapter extends RealmRecyclerViewAdapter<Book> {

    final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public BooksAdapter(Context context) {

        this.context = context;
    }

    // create new views (invoked by the layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books, parent, false);
        return new CardViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        realm = RealmController.getInstance().getRealm();

        // get the article
        final Book book = getItem(position);
        // cast the generic view holder to our specific one
        final CardViewHolder holder = (CardViewHolder) viewHolder;

        // set the title and the snippet
        holder.textTitle.setText(book.getTitle());
        holder.textcontent.setText(book.getContent());
        holder.textby.setText(book.getBy());
        holder.textdate.setText(book.getDate());

        Typeface custom_font= Typeface.createFromAsset(holder.view_image.getContext().getAssets(),
                "font/titles.otf");
        Typeface content_font= Typeface.createFromAsset(holder.view_image.getContext().getAssets(),
                "font/myfonts.ttf");
        holder.textTitle.setTypeface(custom_font);
        holder.textcontent.setTypeface(content_font);

        Picasso.with(holder.view_image.getContext()).load(book.getImage())
                .placeholder(R.drawable.nv_icon).into(holder.view_image);
    }

    // return the size of your data set (invoked by the layout manager)
    public int getItemCount() {

        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public TextView textTitle, textcontent, textby, textdate;
        public ImageView view_image;
        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);
            textby = itemView.findViewById(R.id.view_by);
            textcontent = itemView.findViewById(R.id.view_content);
            textdate = itemView.findViewById(R.id.view_date);
            textTitle =itemView.findViewById(R.id.view_title);
            view_image = itemView.findViewById(R.id.view_image);
        }
    }
}
