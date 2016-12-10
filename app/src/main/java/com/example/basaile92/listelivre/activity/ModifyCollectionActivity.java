package com.example.basaile92.listelivre.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.entity.Collection;
import com.example.basaile92.listelivre.entity.SimpleBook;
import com.example.basaile92.listelivre.manager.BookManager;
import com.example.basaile92.listelivre.manager.CollectionBookManager;
import com.example.basaile92.listelivre.manager.CollectionManager;

import java.util.ArrayList;

public class ModifyCollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_collection);

        setTitle(R.string.modifyCollectionActivity);

        int position;
        Intent intent = getIntent();
        position = intent.getIntExtra("collectionID",0);

        Button deleteCollectionButton = (Button) findViewById(R.id.deleteCollectionButton);
        EditText editCollectionName = (EditText) findViewById(R.id.editCollectionName);
        Button saveCollectionModification = (Button) findViewById(R.id.saveCollectionModification);
        Button addBookToCollection = (Button) findViewById(R.id.addBookToCollectionButton);
        Button deleteBookFromCollectionButton = (Button) findViewById(R.id.deleteBookFromCollection);

        //Set function to the button Delete Collection
        setDeleteCollectionButton(deleteCollectionButton, position);

        //Set function to the EditText to have the default collection name
        setCollectionName(editCollectionName, position);

        //Set function to the button Save to save the new collection name in the database
        saveModification(saveCollectionModification, editCollectionName, position);

        //Set function to the button Add Book to add new books inside the collection
        addBookInCollection(addBookToCollection, position);

        //Set function to the button Delete Book to delete books inside the collection
        deleteBookFromCollection(deleteBookFromCollectionButton, position);

    }


    public void setDeleteCollectionButton(Button deleteCollectionButton, final int position) {

        deleteCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyCollectionActivity.this);
                builder.setMessage(R.string.deleteCollectionQuestion);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        CollectionManager collectionManager = new CollectionManager(view.getContext());
                        collectionManager.deleteCollection(collectionManager.getCollectionAtPosition(position));

                        Intent intent = new Intent(ModifyCollectionActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }


    public void setCollectionName(EditText editCollectionName, int position) {

        CollectionManager collectionManager = new CollectionManager(editCollectionName.getContext());
        Collection collection = collectionManager.getCollectionAtPosition(position);
        String collectionName = collection.getName();

        editCollectionName.setText(collectionName);
    }


    public void saveModification(final Button saveCollectionModification, final EditText editCollectionName, final int position) {

        saveCollectionModification.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View view) {

                CollectionManager collectionManager = new CollectionManager((view.getContext()));
                Collection collection = collectionManager.getCollectionAtPosition(position);
                String collectionName = collection.getName();
                collection.setName(editCollectionName.getText().toString());

                collectionManager.modifyCollectionName(collection, collectionName);

                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void addBookInCollection(final Button addBookToCollection, final int position){

        addBookToCollection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                final BookLibrary booksToAdd = new BookLibrary();

                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyCollectionActivity.this);
                builder.setTitle(R.string.selectBook);

                BookManager bookManager = new BookManager(view.getContext());
                final BookLibrary bookLibrary = bookManager.readBookLibrary();
                String[] booksDescription = new String[bookLibrary.size()];
                for(int i = 0 ; i<bookLibrary.size() ; i++) {

                    booksDescription[i] = (bookLibrary.get(i).toString());
                }

                builder.setMultiChoiceItems(booksDescription, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the book, add it to the selected books
                            booksToAdd.add(bookLibrary.get(which));
                        } else if (booksToAdd.contains(bookLibrary.get(which))) {
                            // Else, if the item is already in the array, we remove it
                            booksToAdd.remove(Integer.valueOf(which));
                        }
                    }
                });


                builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        CollectionManager collectionManager = new CollectionManager(view.getContext());
                        Collection collection = collectionManager.getCollectionAtPosition(position);
                        CollectionBookManager collectionBookManager = new CollectionBookManager(view.getContext());

                        for (int i = 0 ; i < booksToAdd.size() ; i++) {

                            SimpleBook book = booksToAdd.get(i);
                            collectionBookManager.saveCollectionBook(collection, book);
                        }
                    }
                });


                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }


    public void deleteBookFromCollection(Button deleteBookFromCollectionButton, final int position) {

        deleteBookFromCollectionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                final BookLibrary booksToDelete = new BookLibrary();

                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyCollectionActivity.this);
                builder.setTitle(R.string.selectBook);

                CollectionBookManager collectionBookManager = new CollectionBookManager(view.getContext());
                CollectionManager collectionManager = new CollectionManager(view.getContext());
                Collection collection = collectionManager.getCollectionAtPosition(position);
                final BookLibrary booksInCollection = collectionBookManager.getAllSimpleBooksFromCollection(collection);

                String[] booksDescription = new String[booksInCollection.size()];
                for(int i = 0 ; i<booksInCollection.size() ; i++) {

                    booksDescription[i] = (booksInCollection.get(i).toString());
                }


                builder.setMultiChoiceItems(booksDescription, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the book, add it to the selected books
                            booksToDelete.add(booksInCollection.get(which));
                        } else if (booksToDelete.contains(booksInCollection.get(which))) {
                            // Else, if the item is already in the array, we remove it
                            booksToDelete.remove(Integer.valueOf(which));
                        }
                    }
                });


                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        CollectionManager collectionManager = new CollectionManager(view.getContext());
                        Collection collection = collectionManager.getCollectionAtPosition(position);
                        CollectionBookManager collectionBookManager = new CollectionBookManager(view.getContext());

                        for (int i = 0 ; i < booksToDelete.size() ; i++) {

                            SimpleBook book = booksToDelete.get(i);
                            collectionBookManager.deleteCollectionBook(collection, book);
                        }
                    }
                });


                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });


                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    //When we push the back button, come back to the main activity
    public void onBackPressed()
    {
        Intent intent = new Intent(ModifyCollectionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
