package com.example.basaile92.listelivre.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.activity.MainActivity;
import com.example.basaile92.listelivre.book.Book;
import com.example.basaile92.listelivre.book.BookLibrary;
import com.example.basaile92.listelivre.book.BookManager;
import com.example.basaile92.listelivre.book.SimpleBook;

public class ModifyBookLibraryFragment extends Fragment {

    public static final String POSITION = "itemPosition";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_modify_book_library, container, false);
        initLabel(view, getActivity());

        Button modifyButton = (Button) view.findViewById(R.id.modifyButton);

        //TODO Récuper Bundle et Intent
        Intent intent = ;
        final int position = getPositionInListView();


        modifyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                BookManager bookManager = new BookManager(getContext());
                EditText isbnEdit = (EditText) view.findViewById(R.id.isbnEdit);
                EditText authorEdit = (EditText) view.findViewById(R.id.authorEdit);
                EditText titleEdit = (EditText) view.findViewById(R.id.titleEdit);
                EditText descriptionEdit = (EditText) view.findViewById(R.id.descriptionEdit);



                if(isbnEdit.getText().toString().equals("")|| authorEdit.getText().toString().equals("") || titleEdit.getText().toString().equals("")){

                    Toast toast = Toast.makeText(getActivity(), R.string.toastEmptyField, Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    bookManager.modifyBook(new SimpleBook(authorEdit.getText().toString(), titleEdit.getText().toString(), isbnEdit.getText().toString(), descriptionEdit.getText().toString() ), position);
                }
            }
        });




        return view;
    }


    private int getPositionInListView() {

        Intent intent =


    }

    private void initLabel(View view, Activity thisActivity) {

        TextView isbnText = (TextView) view.findViewById(R.id.isbnText);
        TextView authorText = (TextView) view.findViewById(R.id.authorText);
        TextView titleText = (TextView) view.findViewById(R.id.titleText);
        TextView descriptionText = (TextView) view.findViewById(R.id.descriptionText);
        Button modifyButton = (Button) view.findViewById(R.id.modifyButton);

        isbnText.setText(R.string.isbnText);
        authorText.setText(R.string.authorText);
        titleText.setText(R.string.titleText);
        modifyButton.setText(R.string.modifyButton);
        descriptionText.setText(R.string.descriptionText);

        thisActivity.setTitle(R.string.modifyBookLibraryActivity);
    }

}
