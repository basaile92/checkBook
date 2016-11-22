package com.example.basaile92.listelivre.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.manager.BookManager;
import com.example.basaile92.listelivre.entity.AuthorList;
import com.example.basaile92.listelivre.entity.SimpleBook;
import com.example.basaile92.listelivre.entity.TypeList;
import com.example.basaile92.listelivre.fragment.DisplayBookFragment;

public class ModifyBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_book);
        setTitle(R.string.modifyBookLibraryActivity);

        final EditText isbnEdit = (EditText) findViewById(R.id.isbnEdit);
        final EditText titleEdit = (EditText) findViewById(R.id.titleEdit);
        final EditText collectionEdit = (EditText) findViewById(R.id.collectionEdit);
        final EditText publisherEdit = (EditText) findViewById(R.id.publisherEdit);
        final EditText yearEdit = (EditText) findViewById(R.id.yearEdit);
        final EditText summaryEdit = (EditText) findViewById(R.id.summaryEdit);
        final CheckBox isReadCheckBox = (CheckBox) findViewById(R.id.isReadCheckBox);
        final CheckBox isBorrowedCheckBox = (CheckBox) findViewById(R.id.isBorrowedCheckBox);
        final EditText borrowerEdit = (EditText) findViewById(R.id.borrowerEdit);
        final EditText ownerEdit = (EditText) findViewById(R.id.ownerEdit);
        final EditText commentsEdit = (EditText) findViewById(R.id.commentsEdit);
        final ImageView imageButton = (ImageView) findViewById(R.id.imageButton);
        final Button sendButton = (Button) findViewById(R.id.sendButton);

        final ListView authorsListView = (ListView) findViewById(R.id.authorsListView);
        final EditText addAuthorsEdit = (EditText) findViewById(R.id.addAuthorsEdit);
        final ImageView addAuthorsButton = (ImageView) findViewById(R.id.addAuthorsButton);

        final AuthorList authorsList = new AuthorList();

        ListView typeListView = (ListView) findViewById(R.id.typeListView);
        Spinner addTypesSpinner = (Spinner) findViewById(R.id.addTypesSpinner);
        ImageView addTypesButton = (ImageView) findViewById(R.id.addTypesButton);
        final TypeList typesList = new TypeList();

        ImageView typeEditButton = (ImageView) findViewById(R.id.typeEditButton);

        //we get the simple book with the Intent
        Intent intent = getIntent();
        SimpleBook book = (SimpleBook) intent.getSerializableExtra(DisplayBookFragment.SIMPLEBOOK);

        //if book is not null we assignate all field with the book information
        if(book != null) {

            isbnEdit.setText(book.getIsbn());
            titleEdit.setText(book.getTitle());
            collectionEdit.setText(book.getCollection());
            publisherEdit.setText(book.getPublisher());
            yearEdit.setText(book.getYear());
            summaryEdit.setText(book.getSummary());
            isReadCheckBox.setChecked(book.isRead());
            isBorrowedCheckBox.setChecked(book.isBorrowed());
            borrowerEdit.setText(book.getBorrower());
            ownerEdit.setText(book.getOwner());
            commentsEdit.setText(book.getComment());
            //TODO set image and the author list and the type list



        }

        setBorrowingFieldDisplay(isBorrowedCheckBox, borrowerEdit);
        setPhotoButton(imageButton);
        setAuthorsListView(authorsListView, addAuthorsEdit, addAuthorsButton);
        setTypeListView(typeListView, addTypesSpinner, addTypesButton);
        setTypeEditButton(typeEditButton);

        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // If the form is correctly fill
                if(checkForm(isbnEdit, titleEdit,authorsList, view.getContext())) {

                    // We save the book in the Database
                    BookManager bookManager = new BookManager(view.getContext());
                    bookManager.saveSimpleBook(new SimpleBook(isbnEdit.getText().toString(), authorsList, titleEdit.getText().toString(), collectionEdit.getText().toString(), typesList, publisherEdit.getText().toString(), yearEdit.getText().toString(), summaryEdit.getText().toString(), isReadCheckBox.isChecked(), isBorrowedCheckBox.isChecked(), borrowerEdit.getText().toString(), ownerEdit.getText().toString(), commentsEdit.getText().toString(), ""));
                    Intent intent = new Intent(ModifyBookActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    //TODO create a Fragment with the add and delete of the type
    private void setTypeEditButton(ImageView typeEditButton) {
    }

    //TODO initialize the ListView and update it when a new element is added
    private void setTypeListView(ListView typeListView, Spinner addTypesSpinner, ImageView addTypesButton) {
    }

    //TODO initialize the listView and update it when a new element is added
    private void setAuthorsListView(ListView authorsListView, EditText addAuthorsEdit, ImageView addAuthorsButton) {
    }

    //TODO change activity to take a photo
    private void setPhotoButton(ImageView imageButton) {
    }


    //To make visible the borrowerEdit when the isBorrowed checkbox is checked or not
    private void setBorrowingFieldDisplay(CheckBox isBorrowedCheckBox, final EditText borrowerEdit){

        isBorrowedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    borrowerEdit.setVisibility(View.VISIBLE);
                }else{
                    borrowerEdit.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    //Check the different field of the form
    private boolean checkForm(EditText isbnEdit, EditText titleEdit, AuthorList authorsList, Context context){

        // We check that the main fields has a value.
        if(isbnEdit.getText().toString().equals("")||authorsList.size()==0||titleEdit.getText().toString().equals("")){

            // Error Toast if it hasn't
            Toast toast=Toast.makeText(ModifyBookActivity.this,R.string.toastEmptyField,Toast.LENGTH_SHORT);
            toast.show();
        }else{

            //We check that the ISBN has 13 characters.
            if(isbnEdit.getText().toString().length()!=13){

                //Error Toast if it hasn't
                Toast toast=Toast.makeText(ModifyBookActivity.this,R.string.isbnNotGoodFormat,Toast.LENGTH_SHORT);
                toast.show();
            }else{

                //We check that the ISBN doesn't alread exist in database
                BookManager bookManager = new BookManager(context);
                if(bookManager.existIsbn(isbnEdit.getText().toString())){

                    return true;
                }else{

                    //Error Toast if it does
                    Toast toast = Toast.makeText(ModifyBookActivity.this, R.string.bookAlreadyExist, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
        return false;
    }



    //TODO: Get the photo
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}

