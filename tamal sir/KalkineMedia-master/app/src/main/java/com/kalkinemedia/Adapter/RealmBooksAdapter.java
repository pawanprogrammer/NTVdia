package com.kalkinemedia.Adapter;

import com.kalkinemedia.POJO.Book;

import io.realm.RealmResults;

public class RealmBooksAdapter extends RealmModelAdapter<Book> {

    public RealmBooksAdapter(RealmResults<Book> realmResults) {

        super( realmResults);
    }
}