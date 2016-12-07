package com.example.basaile92.listelivre.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.entity.BookLibrary;


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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService((context.LAYOUT_INFLATER_SERVICE));
        View cardView = inflater.inflate(R.layout.book_portrait, null, false);

        ViewHolder viewHolder = new ViewHolder(cardView);
        viewHolder.bookImage = (ImageView) cardView.findViewById(R.id.image_book);
        viewHolder.bookName = (TextView) cardView.findViewById(R.id.text_book_title);
        viewHolder.bookAuthors = (TextView) cardView.findViewById(R.id.text_book_author);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //ImageView bookImageView = (ImageView) holder.bookImage;
       // bookImageView.setImageResource(Integer.parseInt(books.get(position).getBook().getPhoto()));

        TextView nameTextView = (TextView) holder.bookName;
        nameTextView.setText(books.get(position).getBook().getTitle());

        TextView authorsTextView = (TextView) holder.bookAuthors;
        authorsTextView.setText(books.get(position).getAuthors().get(0).getName());
    }


    @Override
    public int getItemCount() {

        return books.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView bookImage;
        TextView bookName;
        TextView bookAuthors;

        //Constructor
        public ViewHolder(View bookView){

            super(bookView);
            bookImage = (ImageView) bookView.findViewById(R.id.image_book);
            bookName = (TextView) bookView.findViewById(R.id.text_book_title);
            bookAuthors = (TextView) bookView.findViewById(R.id.text_book_author);
        }

    }

}
