package com.example.basaile92.listelivre.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.book.BookAlreadyExistsException;
import com.example.basaile92.listelivre.book.BookLibrary;
import com.example.basaile92.listelivre.book.BookManager;
import com.example.basaile92.listelivre.book.ScanBook;
import com.example.basaile92.listelivre.book.SimpleBook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class AddBookLibraryActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_library);
        initLabel();

        Button sendButton = (Button) findViewById(R.id.sendButton);
        ImageButton getBookInfosButton = (ImageButton) findViewById(R.id.getBookInfosButton);
        CheckBox isBorrowedCheckBox = (CheckBox) findViewById(R.id.isBorrowedCheckBox);
        ImageView imageButton = (ImageView) findViewById(R.id.imageButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BookManager bookManager = new BookManager(getBaseContext());
                EditText isbnEdit = (EditText) findViewById(R.id.isbnEdit);
                EditText authorEdit = (EditText) findViewById(R.id.authorEdit);
                EditText titleEdit = (EditText) findViewById(R.id.titleEdit);
                EditText descriptionEdit = (EditText) findViewById(R.id.descriptionEdit);
                CheckBox isReadCheckBox = (CheckBox) findViewById(R.id.isReadCheckBox);
                CheckBox isBorrowedCheckBox = (CheckBox) findViewById(R.id.isBorrowedCheckBox);
                EditText borrowerEdit = (EditText) findViewById(R.id.borrowerEdit);
                EditText ownerEdit = (EditText) findViewById(R.id.ownerEdit);
                EditText commentsEdit = (EditText) findViewById(R.id.commentsEdit);
                ImageView imageButton = (ImageView) findViewById(R.id.imageButton);



                if(isbnEdit.getText().toString().equals("")|| authorEdit.getText().toString().equals("") || titleEdit.getText().toString().equals("")){

                    Toast toast = Toast.makeText(AddBookLibraryActivity.this, R.string.toastEmptyField, Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    try {

                        bookManager.saveSimpleBook(new SimpleBook(isbnEdit.getText().toString(), authorEdit.getText().toString(), titleEdit.getText().toString(), descriptionEdit.getText().toString(), isReadCheckBox.isChecked(), isBorrowedCheckBox.isChecked(), borrowerEdit.getText().toString(), ownerEdit.getText().toString(), commentsEdit.getText().toString(),"FAIRE ICI LA PHOTO"));
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

        isBorrowedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EditText borrowerEdit = (EditText) findViewById(R.id.borrowerEdit);

                if(isChecked){
                    borrowerEdit.setVisibility(View.VISIBLE);
                }else{
                    borrowerEdit.setVisibility(View.INVISIBLE);
                }

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }

            }
        });
        getBookInfosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText isbnEdit = (EditText) findViewById(R.id.isbnEdit);
                EditText authorEdit = (EditText) findViewById(R.id.authorEdit);
                EditText titleEdit = (EditText) findViewById(R.id.titleEdit);
                EditText descriptionEdit = (EditText) findViewById(R.id.descriptionEdit);
                ImageView imageButton = (ImageView) findViewById(R.id.imageButton);

                ScanBook scanBook = new ScanBook(isbnEdit.getText().toString(), v.getContext());

                BookLibrary bookLibrary = scanBook.getBooks();
                if(bookLibrary.size() > 0) {
                    SimpleBook book = scanBook.getBooks().get(0);

                    authorEdit.setText(book.getAuthor());
                    titleEdit.setText(book.getTitle());
                    descriptionEdit.setText(book.getDescription());
                    /*
                    //TODO: Faire l'import de photos par ISBN
                    try
                    {
                        Log.e("Salut", book.getPhoto());
                        InputStream is = (InputStream) new URL(book.getPhoto()).getContent();
                        Drawable drawable = Drawable.createFromStream(is, "src name");
                        imageButton.setImageDrawable(drawable);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                */
                }else {

                    Toast toast = Toast.makeText(AddBookLibraryActivity.this, R.string.isbnCantComplet, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            if(data!=null){
                if(data.hasExtra("data")){

                    Bundle extras = data.getExtras();
                    Bitmap thumbnail = (Bitmap) extras.get("data");
                    ImageView imageButton = (ImageView) findViewById(R.id.imageButton);
                    imageButton.setImageBitmap(thumbnail);

                }

            }
        }
    }

    private void initLabel() {
        setTitle(R.string.addBookLibraryActivity);
    }
    public void onBackPressed()
    {
        Intent intent = new Intent(AddBookLibraryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
