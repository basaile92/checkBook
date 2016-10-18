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
import com.example.basaile92.listelivre.resources.BookManager;

import java.io.File;

public class ModifyBookLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_book_library);
        initLabel();

        Button modifyButton = (Button) findViewById(R.id.modifyButton);

        Intent intent = getIntent();
        final Book book = (Book) intent.getSerializableExtra("book");
        final int id = intent.getIntExtra("id", -1);


        EditText isbnEdit = (EditText) findViewById(R.id.isbnEdit);
        EditText authorEdit = (EditText) findViewById(R.id.authorEdit);
        EditText titleEdit = (EditText) findViewById(R.id.titleEdit);

        isbnEdit.setText(book.getIsbn());
        authorEdit.setText(book.getAuthor());
        titleEdit.setText(book.getTitle());


        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BookManager bookManager = new BookManager(new File("/storage/emulated/0/Android/data/com.example.basaile92.listelivre/files/listeLivre.txt"));
                EditText isbnEdit = (EditText) findViewById(R.id.isbnEdit);
                EditText authorEdit = (EditText) findViewById(R.id.authorEdit);
                EditText titleEdit = (EditText) findViewById(R.id.titleEdit);

                if(isbnEdit.getText().toString().equals("")|| authorEdit.getText().toString().equals("") || titleEdit.getText().toString().equals("")){

                    Toast toast = Toast.makeText(ModifyBookLibraryActivity.this, R.string.toastEmptyField, Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    bookManager.modifyBook(new Book(authorEdit.getText().toString(), titleEdit.getText().toString(), isbnEdit.getText().toString()), id);
                    Intent intent = new Intent(ModifyBookLibraryActivity.this, MainActivity.class);
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
        Button modifyButton = (Button) findViewById(R.id.modifyButton);

        isbnText.setText(R.string.isbnText);
        authorText.setText(R.string.authorText);
        titleText.setText(R.string.titleText);
        addImageText.setText(R.string.addImageText);
        addImageButton.setText(R.string.addImageButton);
        modifyButton.setText(R.string.modifyButton);

        setTitle(R.string.modifyBookLibraryActivity);
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(ModifyBookLibraryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
