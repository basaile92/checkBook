package com.example.basaile92.listelivre.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.manager.ImageManager;



/**
 * Class use to display Book
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private Context context;
    private BookLibrary books = new BookLibrary();


    //Constructor
    public BookAdapter(BookLibrary books) {

        this.books = books;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        //Set the layout for a book
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cardView = inflater.inflate(R.layout.book_portrait, parent, false);

        return new ViewHolder(cardView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Set the different information of a book : title, author and image

        TextView nameTextView = holder.getBookName();
        TextView authorsTextView = holder.getBookAuthors();

        nameTextView.setText(books.get(position).getBook().getTitle());
        authorsTextView.setText(books.get(position).getAuthors().get(0).getName());

        ImageView bookImageView = holder.bookImage;

        if(!books.get(position).getBook().getPhoto().equals("")) {
                bookImageView.setImageBitmap((ImageManager.getNewSizeBitmap(BitmapFactory.decodeFile(books.get(position).getBook().getPhoto()), 1000)));
        }

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

        public TextView getBookName() {
            return bookName;
        }

        public TextView getBookAuthors() {
            return bookAuthors;
        }
    }

}
