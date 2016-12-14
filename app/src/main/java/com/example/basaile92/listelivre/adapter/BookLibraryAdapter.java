package com.example.basaile92.listelivre.adapter;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.callback.BookLibraryFragmentCallBack;
import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.manager.ImageManager;

/**
 * Created by basaile92 on 09/12/2016.
 */

public class BookLibraryAdapter extends RecyclerView.Adapter<BookLibraryAdapter.ViewHolder>{

    private BookLibrary books = new BookLibrary();
    private BookLibraryFragmentCallBack mCallBack;

    public BookLibraryAdapter(BookLibrary books, BookLibraryFragmentCallBack mCallBack){

        this.books = books;
        this.mCallBack = mCallBack;

    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.book, parent, false);

        final BookLibraryFragmentCallBack mCallBack2 = this.mCallBack;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int itemPosition = ((RecyclerView)parent).getChildLayoutPosition(view);
                mCallBack2.updateDisplayBookFragment(itemPosition);

            }
        });

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TextView author = holder.getAuthor();
        TextView title = holder.getTitle();
        ImageView photo = holder.getPhoto();

        author.setText(books.get(position).getAuthors().toString());
        title.setText(books.get(position).getBook().getTitle());

        if(!books.get(position).getBook().getPhoto().equals("")) {
                photo.setImageBitmap((ImageManager.getNewSizeBitmap(BitmapFactory.decodeFile(books.get(position).getBook().getPhoto()), 1000)));
        }

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView author;
        private TextView title;
        private ImageView photo;

        public ViewHolder(View view)  {
            super(view);
            author = ((TextView) view.findViewById(R.id.authorBook));
            title = ((TextView) view.findViewById(R.id.titleBook));
            photo = ((ImageView) view.findViewById(R.id.imageBook));

        }

        public TextView getAuthor() {
            return author;
        }

        public TextView getTitle() {
            return title;
        }

        public ImageView getPhoto() {
            return photo;
        }
    }
}
