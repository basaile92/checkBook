package com.example.basaile92.listelivre.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.activity.BookLibraryActivity;
import com.example.basaile92.listelivre.activity.DisplayBookActivity;
import com.example.basaile92.listelivre.callback.BookLibraryFragmentCallBack;
import com.example.basaile92.listelivre.entity.Author;
import com.example.basaile92.listelivre.entity.SimpleBook;
import com.example.basaile92.listelivre.entity.Type;
import com.example.basaile92.listelivre.manager.BookManager;

import java.util.ArrayList;

/**
 * Created by Basile on 20/11/2016.
 */
public class DisplayBookFragment extends Fragment{

    private BookLibraryFragmentCallBack mCallback;
    public static String SIMPLEBOOK = "SimpleBook";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle b) {
        View view = inflater.inflate(R.layout.fragment_display_book, container, false);

        //We get the position that was sent by the bundle
        Bundle bundle = getArguments();
        int position;

        if (bundle != null) {
            position = bundle.getInt(BookLibraryFragment.POSITION);
        } else{
            position = -1;
        }

        if(position != -1)
            updateView(position, view, view);

        return view;
    }

    //This function is used to be able to change the view from an other activity
    public void updateView(int position, View viewModif, View viewLibrary){

        TextView isbnText = (TextView) viewModif.findViewById(R.id.isbnText);
        TextView titleText = (TextView) viewModif.findViewById(R.id.titleText);
        ArrayList<Author> authors = new ArrayList<Author>();
        ArrayList<Type> types = new ArrayList<Type>();
        TextView collectionText = (TextView) viewModif.findViewById(R.id.collectionText);
        TextView publisherText = (TextView) viewModif.findViewById(R.id.publisherText);
        TextView yearText = (TextView) viewModif.findViewById(R.id.yearText);
        TextView summaryText = (TextView) viewModif.findViewById(R.id.summaryText);
        TextView isReadText = (TextView) viewModif.findViewById(R.id.isReadText);
        TextView isBorrowedText = (TextView) viewModif.findViewById(R.id.isBorrowedText);
        TextView borrowerText = (TextView) viewModif.findViewById(R.id.borrowerText);
        LinearLayout borrowedLayout = (LinearLayout) viewModif.findViewById(R.id.borrowedLayout);
        TextView ownerText = (TextView) viewModif.findViewById(R.id.ownerText);
        TextView commentsText = (TextView) viewModif.findViewById(R.id.commentsText);
        ImageView imageButton = (ImageView) viewModif.findViewById(R.id.imageButton);

        ImageView editButton = (ImageView) viewModif.findViewById(R.id.editButton);
        ImageView deleteButton = (ImageView) viewModif.findViewById(R.id.deleteButton);

        //We get the informations of the Simple Book
        BookManager bookManager = new BookManager(viewLibrary.getContext());
        SimpleBook book = bookManager.getSimpleBookAtPosition(position);

        //We set all field of the view with the informations of the book
        isbnText.setText(book.getIsbn());
        titleText.setText(book.getTitle());
        authors.addAll(book.getAuthors());
        types.addAll(book.getTypes());
        collectionText.setText(book.getCollection());
        publisherText.setText(book.getPublisher());
        yearText.setText(book.getYear());
        summaryText.setText(book.getSummary());
        setIsReadTextDisplay(isReadText, book.isRead());
        setBorrowerTextDisplay( borrowedLayout,isBorrowedText, book.isBorrowed(), borrowerText, book.getBorrower() );
        ownerText.setText(book.getOwner());
        commentsText.setText(book.getComment());
        setImageButton(imageButton, book.getPhoto());

        setButtonEdit(editButton, position);
        setButtonDelete(deleteButton, position);

    }

    private void setButtonEdit(ImageView editButton, final int position) {

        //We assignate the function of the editButton
        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //We send the book at the position in the intent to the modify book activity
                Intent intent = new Intent(getActivity(), DisplayBookActivity.class);
                BookManager bookManager = new BookManager(view.getContext());
                SimpleBook book = bookManager.getSimpleBookAtPosition(position);
                intent.putExtra(SIMPLEBOOK, book);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void setButtonDelete(ImageView deleteButton, final int position){

        //We assignate the function of the deleteButton
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                //We ask the confirmation of the user
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.deleteQuestion);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //if yes we come back to the main activity.
                        BookManager bookManager = new BookManager(view.getContext());
                        bookManager.deleteBook(bookManager.getSimpleBookAtPosition(position));

                        Intent intent = new Intent(getActivity(), BookLibraryActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });

                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override

                    // if no we just cancel the pop up
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void setIsReadTextDisplay(TextView isReadTextView, boolean isRead){

        if(isRead){

            isReadTextView.setText(R.string.read);
            isReadTextView.setTextColor(Color.RED);

        }else
        {
            isReadTextView.setText(R.string.notRead);
            isReadTextView.setTextColor(Color.GREEN);
        }
    }

    private void setBorrowerTextDisplay(LinearLayout borrowedLayout, TextView isBorrowedText, boolean isBorrowed, TextView borrowerText, String borrower ){

        if(isBorrowed){

            isBorrowedText.setText(R.string.borrowed);
            isBorrowedText.setTextColor(Color.RED);
            borrowerText.setText(borrower);
            borrowerText.setTextColor(Color.RED);
        }else{

            borrowedLayout.setVisibility(View.GONE);
        }
    }

    //TODO Import the picture from the library and set it
    private void setImageButton(ImageView imageButton, String path){


    }

    @Override
    public void onAttach(Context context){

        super.onAttach(context);

        try{

            mCallback = (BookLibraryFragmentCallBack) context;

        }catch (ClassCastException e){

            throw new ClassCastException(context.toString() + " must implement BookLibraryFragmentCallBack");
        }
    }

}
