package com.example.basaile92.listelivre.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.entity.SimpleBook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by merciert on 27/11/2016.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private Context context;
    private BookLibrary books = new BookLibrary();


    //Constructor
    public BookAdapter(Context context, BookLibrary books) {

        this.context = context;
        this.books = books;
    }


    @Override
    public RecyclerView.ViewHolder onCreatViewHolder(ViewGroup parent, int viewType) {

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

    }

}
