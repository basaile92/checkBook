package com.example.basaile92.listelivre.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.book.BookAlreadyExistsException;
import com.example.basaile92.listelivre.book.BookManager;
import com.example.basaile92.listelivre.book.SimpleBook;

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

                BookManager bookManager = new BookManager(getBaseContext());
                EditText isbnEdit = (EditText) findViewById(R.id.isbnEdit);
                EditText authorEdit = (EditText) findViewById(R.id.authorEdit);
                EditText titleEdit = (EditText) findViewById(R.id.titleEdit);
                EditText descriptionEdit = (EditText) findViewById(R.id.descriptionEdit);

                if(isbnEdit.getText().toString().equals("")|| authorEdit.getText().toString().equals("") || titleEdit.getText().toString().equals("")){

                    Toast toast = Toast.makeText(AddBookLibraryActivity.this, R.string.toastEmptyField, Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    try {
                        bookManager.saveSimpleBook(new SimpleBook(isbnEdit.getText().toString(), authorEdit.getText().toString(), titleEdit.getText().toString(), descriptionEdit.getText().toString()));
                        Intent intent = new Intent(AddBookLibraryActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (BookAlreadyExistsException e) {

                        Toast toast = Toast.makeText(AddBookLibraryActivity.this, R.string.bookAlreadyExist, Toast.LENGTH_SHORT);
                        toast.show();

                    }

                }
            }
        });
    }

    private void initLabel() {

        TextView isbnText = (TextView) findViewById(R.id.isbnText);
        TextView authorText = (TextView) findViewById(R.id.authorText);
        TextView titleText = (TextView) findViewById(R.id.titleText);
        TextView descriptionText = (TextView) findViewById(R.id.descriptionText);
        Button sendButton = (Button) findViewById(R.id.sendButton);

        isbnText.setText(R.string.isbnText);
        authorText.setText(R.string.authorText);
        titleText.setText(R.string.titleText);
        descriptionText.setText(R.string.descriptionText);
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
