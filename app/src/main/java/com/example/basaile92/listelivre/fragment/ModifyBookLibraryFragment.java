package com.example.basaile92.listelivre.fragment;

import android.app.Activity;
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
import com.example.basaile92.listelivre.resources.Book;
import com.example.basaile92.listelivre.resources.BookLibrary;
import com.example.basaile92.listelivre.resources.BookLibraryFragmentCallBack;
import com.example.basaile92.listelivre.resources.BookManager;

import java.io.File;

public class ModifyBookLibraryFragment extends Fragment {

    private EditText isbnEdit;
    private EditText authorEdit;
    private EditText titleEdit;
    private BookLibraryFragmentCallBack parent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_modify_book_library, container, false);
        initLabel(view, getActivity());

        Button modifyButton = (Button) view.findViewById(R.id.modifyButton);




        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BookManager bookManager = new BookManager(new File("/storage/emulated/0/Android/data/com.example.basaile92.listelivre/files/listeLivre.txt"));
                EditText isbnEdit = (EditText) view.findViewById(R.id.isbnEdit);
                EditText authorEdit = (EditText) view.findViewById(R.id.authorEdit);
                EditText titleEdit = (EditText) view.findViewById(R.id.titleEdit);

                if(isbnEdit.getText().toString().equals("")|| authorEdit.getText().toString().equals("") || titleEdit.getText().toString().equals("")){

                    Toast toast = Toast.makeText(getActivity(), R.string.toastEmptyField, Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    bookManager.modifyBook(new Book(authorEdit.getText().toString(), titleEdit.getText().toString(), isbnEdit.getText().toString()), parent.getId());
                }
            }
        });

        return view;
    }

    private void initLabel(View view, Activity thisActivity) {

        TextView isbnText = (TextView) view.findViewById(R.id.isbnText);
        TextView authorText = (TextView) view.findViewById(R.id.authorText);
        TextView titleText = (TextView) view.findViewById(R.id.titleText);
        TextView addImageText = (TextView) view.findViewById(R.id.addImageText);
        Button addImageButton = (Button) view.findViewById(R.id.addImageButton);
        Button modifyButton = (Button) view.findViewById(R.id.modifyButton);

        isbnText.setText(R.string.isbnText);
        authorText.setText(R.string.authorText);
        titleText.setText(R.string.titleText);
        addImageText.setText(R.string.addImageText);
        addImageButton.setText(R.string.addImageButton);
        modifyButton.setText(R.string.modifyButton);

        thisActivity.setTitle(R.string.modifyBookLibraryActivity);
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);

        parent = (BookLibraryFragmentCallBack) activity;

    }

    public void updateView(int itemId) {

        BookManager bookManager = new BookManager(new File("/storage/emulated/0/Android/data/com.example.basaile92.listelivre/files/listeLivre.txt"));
        BookLibrary bookLibrary = bookManager.readBookLibrary();

        Book book;
        if(parent.getId() == -1){

            book =  bookLibrary.get(0);
        }else {

            book = bookLibrary.get(itemId);
        }
        isbnEdit.setText(book.getIsbn());
        authorEdit.setText(book.getAuthor());
        titleEdit.setText(book.getTitle());


    }
}
