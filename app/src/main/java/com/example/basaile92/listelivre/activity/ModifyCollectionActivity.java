package com.example.basaile92.listelivre.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.entity.Collection;
import com.example.basaile92.listelivre.entity.SimpleBook;
import com.example.basaile92.listelivre.manager.BookManager;
import com.example.basaile92.listelivre.manager.CollectionBookManager;
import com.example.basaile92.listelivre.manager.CollectionManager;


public class ModifyCollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_collection);

        setTitle(R.string.modifyCollectionActivity);

        int position;
        Intent intent = getIntent();
        position = intent.getIntExtra("collectionID",0);

        ImageView deleteCollectionButton = (ImageView) findViewById(R.id.deleteCollectionButton);
        EditText editCollectionName = (EditText) findViewById(R.id.editCollectionName);
        ImageView saveCollectionModification = (ImageView) findViewById(R.id.saveCollectionModification);
        ImageView addBookToCollection = (ImageView) findViewById(R.id.addBookToCollectionButton);
        ImageView deleteBookFromCollectionButton = (ImageView) findViewById(R.id.deleteBookFromCollection);

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

    /**
     * This function allows the ImageView to delete a collection at position 'position' on the list
     * @param deleteCollectionButton : ImageView which will receive the function
     * @param position : position of the collection on the list
     */
    public void setDeleteCollectionButton(ImageView deleteCollectionButton, final int position) {

        //Set the evenClickListener
        deleteCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //Create a dialog to confirm if the user really wants to delete the collection
                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyCollectionActivity.this);
                builder.setMessage(R.string.deleteCollectionQuestion);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //If the user choose yes, we delete the collection from the database
                        CollectionManager collectionManager = new CollectionManager(view.getContext());
                        collectionManager.deleteCollection(collectionManager.getCollectionAtPosition(position));

                        //Return to the MainActivity
                        Intent intent = new Intent(ModifyCollectionActivity.this, MainActivity.class);
                        intent.putExtra("tabHostLocation", "collection");
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialogInterface, int i) {

                        //If the user choose no, we just cancel the dialog
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }


    /**
     * Pre-filled the EditText with the name of collection at position 'position'
     * @param editCollectionName : EditText to pre-filled
     * @param position : position of the collection on the list
     */
    public void setCollectionName(EditText editCollectionName, int position) {

        //Select the collection in the database
        CollectionManager collectionManager = new CollectionManager(editCollectionName.getContext());
        Collection collection = collectionManager.getCollectionAtPosition(position);
        String collectionName = collection.getName();

        //Filled the EditText
        editCollectionName.setText(collectionName);
    }


    /**
     *  Save the change of name for a collection
     * @param saveCollectionModification : Button to confirm the change
     * @param editCollectionName : EditText which contains the new name
     * @param position : position of the collection which receive the new name
     */
    public void saveModification(final ImageView saveCollectionModification, final EditText editCollectionName, final int position) {

        //Set the eventClickListener
        saveCollectionModification.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View view) {

                //Select the collection in the database
                CollectionManager collectionManager = new CollectionManager((view.getContext()));
                Collection collection = collectionManager.getCollectionAtPosition(position);
                String collectionName = collection.getName();
                //Collect the new name
                String newCollectionName = editCollectionName.getText().toString();

                //Check if the new name is correct, or not the same
                if(!collectionName.equals(newCollectionName) && checkName(newCollectionName, collectionManager)) {

                    //If the name is correct, we modify the collection in the database
                    collection.setName(newCollectionName);
                    collectionManager.modifyCollectionName(collection, collectionName);

                    //Return to MainActivity
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.putExtra("tabHostLocation", "collection");
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    /**
     * Private function to check if a collection's name respect certain standards
     * @param nameEdit : where is the name to check
     * @param collectionManager : use to compare the name in the database
     * @return True if the name is correct
     *          False if not
     */
    private boolean checkName(String nameEdit, CollectionManager collectionManager) {

        //Verify if the name is not empty
        if(nameEdit.length() == 0) {

            Toast toast = Toast.makeText(ModifyCollectionActivity.this, R.string.collectionNameEmpty, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        //Verify if the name already exist in the database
        if(collectionManager.existCollection(nameEdit)){

            Toast toast = Toast.makeText(ModifyCollectionActivity.this, R.string.alreadyExistCollection, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }


    /**
     * Allows to add books inside a collection
     * @param addBookToCollection : Button to trigger the function
     * @param position : position of the collection on the list
     */
    public void addBookInCollection(final ImageView addBookToCollection, final int position){

        //Set the eventClickListener
        addBookToCollection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                final BookLibrary booksToAdd = new BookLibrary();

                // Create a multi choice dialog which contains all the library's books
                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyCollectionActivity.this);
                builder.setTitle(R.string.selectBook);

                //Collect all the books from the library
                BookManager bookManager = new BookManager(view.getContext());
                final BookLibrary allBookLibrary = bookManager.readBookLibrary();

                final CollectionManager collectionManager = new CollectionManager(view.getContext());
                final Collection collection = collectionManager.getCollectionAtPosition(position);
                final CollectionBookManager collectionBookManager = new CollectionBookManager(view.getContext());
                final BookLibrary bookLibrary = new BookLibrary();

                //We remove all books already exist in the collection
                for(SimpleBook book : allBookLibrary){

                    if (!collectionBookManager.existCollectionbook(book, collection)){
                        bookLibrary.addBook(book);
                    }
                }

                //String[] is use to add book in the dialog
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
                            booksToAdd.remove(which);
                        }
                    }
                });


                builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        //Add all the checked books in the collection

                        for (int i = 0 ; i < booksToAdd.size() ; i++) {

                            SimpleBook book = booksToAdd.get(i);
                            collectionBookManager.saveCollectionBook(collection, book);
                        }
                    }
                });


                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        //Just close the dialog
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }


    /**
     * Allows to delete books from a collection
     * @param deleteBookFromCollectionButton : Button will receive the function
     * @param position : position of the collection on the list
     */
    public void deleteBookFromCollection(ImageView deleteBookFromCollectionButton, final int position) {

        //Set the eventClickListener
        deleteBookFromCollectionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                final BookLibrary booksToDelete = new BookLibrary();

                //Create a multi choice dialog which contains all the collection's books
                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyCollectionActivity.this);
                builder.setTitle(R.string.selectBook);

                //Collect all the books from the collection
                CollectionBookManager collectionBookManager = new CollectionBookManager(view.getContext());
                CollectionManager collectionManager = new CollectionManager(view.getContext());
                Collection collection = collectionManager.getCollectionAtPosition(position);
                final BookLibrary booksInCollection = collectionBookManager.getAllSimpleBooksFromCollection(collection);

                //String[] is use to add book in the dialog
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
                            booksToDelete.remove(which);
                        }
                    }
                });


                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        //Delete all the checked book from the collection
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

                        //Just close the dialog
                        dialog.cancel();
                    }
                });


                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }


    /**
     * When we push the back button, come back to the main activity
     */
    public void onBackPressed()
    {
        Intent intent = new Intent(ModifyCollectionActivity.this, MainActivity.class);
        intent.putExtra("tabHostLocation", "collection");
        startActivity(intent);
        finish();
    }
}
