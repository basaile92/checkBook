package com.example.basaile92.listelivre.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.resources.Book;
import com.example.basaile92.listelivre.resources.BookAlreadyExistsException;
import com.example.basaile92.listelivre.resources.BookManager;

import java.io.File;

public class AddBookLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_library);
        initLabel();

        Button sendButton = (Button) findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BookManager bookManager = new BookManager(new File("/storage/emulated/0/Android/data/com.example.basaile92.listelivre/files/listeLivre.txt"));
                EditText isbnEdit = (EditText) findViewById(R.id.isbnEdit);
                EditText authorEdit = (EditText) findViewById(R.id.authorEdit);
                EditText titleEdit = (EditText) findViewById(R.id.titleEdit);

                if(isbnEdit.getText().toString().equals("")|| authorEdit.getText().toString().equals("") || titleEdit.getText().toString().equals("")){

                    Toast toast = Toast.makeText(AddBookLibraryActivity.this, R.string.toastEmptyField, Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    try {
                        bookManager.saveBook(new Book(authorEdit.getText().toString(), titleEdit.getText().toString(), isbnEdit.getText().toString()));
                    } catch (BookAlreadyExistsException e) {

                        Toast toast = Toast.makeText(AddBookLibraryActivity.this, R.string.bookAlreadyExist, Toast.LENGTH_SHORT);
                        toast.show();

                    }
                    Intent intent = new Intent(AddBookLibraryActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void initLabel() {

        TextView isbnText = (TextView) findViewById(R.id.isbnText);
        TextView authorText = (TextView) findViewById(R.id.authorText);
        TextView titleText = (TextView) findViewById(R.id.titleText);
        TextView addImageText = (TextView) findViewById(R.id.addImageText);
        Button addImageButton = (Button) findViewById(R.id.addImageButton);
        Button sendButton = (Button) findViewById(R.id.sendButton);

        isbnText.setText(R.string.isbnText);
        authorText.setText(R.string.authorText);
        titleText.setText(R.string.titleText);
        addImageText.setText(R.string.addImageText);
        addImageButton.setText(R.string.addImageButton);
        sendButton.setText(R.string.sendButton);

        setTitle(R.string.addBookLibraryActivity);

    }
    public void onBackPressed()
    {
        Intent intent = new Intent(AddBookLibraryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
