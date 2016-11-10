package com.example.basaile92.listelivre.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.activity.AddBookLibraryActivity;
import com.example.basaile92.listelivre.activity.MainActivity;
import com.example.basaile92.listelivre.book.Book;
import com.example.basaile92.listelivre.book.BookAlreadyExistsException;
import com.example.basaile92.listelivre.book.BookLibrary;
import com.example.basaile92.listelivre.book.BookManager;
import com.example.basaile92.listelivre.book.SimpleBook;

public class ModifyBookLibraryFragment extends Fragment {

    public static final String POSITION = "itemPosition";

    private BookLibraryFragmentCallBack mCallback;
    private BookManager bookManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_modify_book_library, container, false);




        initLabel(view, getActivity());

        Bundle bundle = getArguments();
        int position;

        if (bundle != null) {
            position = bundle.getInt(POSITION);
        } else{
            position = 0;
        }


        updateView(position, view, view);

        return view;
    }

    public void updateView(final int position, View viewModif, View viewLibrary) {


        Button modifyButton = (Button) viewModif.findViewById(R.id.modifyButton);


        final EditText isbnEdit = (EditText) viewModif.findViewById(R.id.isbnEdit);
        final EditText authorEdit = (EditText) viewModif.findViewById(R.id.authorEdit);
        final EditText titleEdit = (EditText) viewModif.findViewById(R.id.titleEdit);
        final EditText descriptionEdit = (EditText) viewModif.findViewById(R.id.descriptionEdit);

        bookManager = new BookManager(viewLibrary.getContext());

        SimpleBook book = bookManager.getSimpleBook(position);

        Log.e("Salut", book.getIsbn()+" "+book.getAuthor()+" "+book.getTitle());

        isbnEdit.setText(book.getIsbn());
        authorEdit.setText(book.getAuthor());
        titleEdit.setText(book.getTitle());
        descriptionEdit.setText(book.getDescription());

        Log.e("Salut", isbnEdit.getText()+" "+authorEdit.getText()+" "+titleEdit.getText());


        modifyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (isbnEdit.getText().toString().equals("") || authorEdit.getText().toString().equals("") || titleEdit.getText().toString().equals("")) {

                    Toast toast = Toast.makeText(getActivity(), R.string.toastEmptyField, Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    try {
                        bookManager.modifyBook(new SimpleBook(isbnEdit.getText().toString(), authorEdit.getText().toString(), titleEdit.getText().toString(), descriptionEdit.getText().toString()), position);
                        Log.e("Salut2", mCallback.toString());
                        mCallback.updateBookLibraryFragment(view);

                    } catch (BookAlreadyExistsException e) {

                        Toast toast = Toast.makeText(getActivity(), R.string.bookAlreadyExist, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
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
    public void onAttach(Context context){

        super.onAttach(context);

        try{

            mCallback = (BookLibraryFragmentCallBack) context;

        }catch (ClassCastException e){

            throw new ClassCastException(context.toString() + " must implement BookLibraryFragmentCallBack");
        }
    }

}
