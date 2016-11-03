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

    private BookLibraryFragmentCallBack mCallback;
    private BookManager bookManager;
    private EditText isbnEdit;
    private EditText authorEdit;
    private EditText titleEdit;
    private EditText descriptionEdit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_modify_book_library, container, false);
        initLabel(view, getActivity());

        Button modifyButton = (Button) view.findViewById(R.id.modifyButton);

        final int position = getPositionInListView();

        if(position == -1) {


            isbnEdit = (EditText) view.findViewById(R.id.isbnEdit);
            authorEdit = (EditText) view.findViewById(R.id.authorEdit);
            titleEdit = (EditText) view.findViewById(R.id.titleEdit);
            descriptionEdit = (EditText) view.findViewById(R.id.descriptionEdit);

            bookManager = new BookManager(getContext());
            SimpleBook book = bookManager.getSimpleBook(position);

            isbnEdit.setText(book.getIsbn());
            authorEdit.setText(book.getAuthor());
            titleEdit.setText(book.getTitle());
            descriptionEdit.setText(book.getDescription());


            modifyButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if (isbnEdit.getText().toString().equals("") || authorEdit.getText().toString().equals("") || titleEdit.getText().toString().equals("")) {

                        Toast toast = Toast.makeText(getActivity(), R.string.toastEmptyField, Toast.LENGTH_SHORT);
                        toast.show();

                    } else {
                        bookManager.modifyBook(new SimpleBook(isbnEdit.getText().toString(), authorEdit.getText().toString(), titleEdit.getText().toString(), descriptionEdit.getText().toString()), position);
                        mCallback.updateBookLibraryFragment();
                    }
                }
            });


        }

        return view;
    }


    private int getPositionInListView() {

        Bundle bundle = getArguments();

        if(bundle != null){
            return bundle.getInt(POSITION);
        }else
            return -1;

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

    @Override
    public void onAttach(Activity activity){

        super.onAttach(activity);

        try{

            mCallback = (BookLibraryFragmentCallBack) activity;

        }catch (ClassCastException e){

            throw new ClassCastException(activity.toString() + " must implement BookLibraryFragmentCallBack");
        }
    }

}
