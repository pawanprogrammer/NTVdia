package com.kalkinemedia.NavigationView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kalkinemedia.Adapter.BooksAdapter;
import com.kalkinemedia.Adapter.RealmBooksAdapter;
import com.kalkinemedia.Helper.RealmController;
import com.kalkinemedia.POJO.Book;
import com.kalkinemedia.R;
import io.realm.Realm;
import io.realm.RealmResults;

public class ViewBookmarkActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Realm realm;
    private BooksAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookmark);
        getSupportActionBar().setTitle("View Bookmarks");
        recyclerView = findViewById(R.id.view_recyclerview);
        //get realm instance
        this.realm = RealmController.with(this).getRealm();
        setupRecycler();

        setRealmAdapter(RealmController.with(this).getBooks());
    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // create an empty adapter and add it to the recycler view
        adapter = new BooksAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void setRealmAdapter(RealmResults<Book> books) {
        RealmBooksAdapter realmAdapter = new RealmBooksAdapter(books);
        // Set the data and tell the RecyclerView to draw
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }
}
