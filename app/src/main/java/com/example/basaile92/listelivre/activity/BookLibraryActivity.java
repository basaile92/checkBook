package com.example.basaile92.listelivre.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.book.ScanBook;
import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.entity.SimpleBook;
import com.example.basaile92.listelivre.fragment.BookLibraryFragment;
import com.example.basaile92.listelivre.fragment.BookLibraryFragmentCallBack;
import com.example.basaile92.listelivre.fragment.DisplayBookFragment;
import com.example.basaile92.listelivre.manager.BookManager;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class BookLibraryActivity extends FragmentActivity implements BookLibraryFragmentCallBack{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_library);

        // Title of the Activity
        setTitle(R.string.mainActivity);

        final FABToolbarLayout fabToolbarLayout = (FABToolbarLayout) findViewById(R.id.fabtoolbar);
        View fab = findViewById(R.id.fabtoolbar_fab);

        //We assignate the function of the fab button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fabToolbarLayout.show();
            }
        });

        LinearLayout displayBookLayout =(LinearLayout) findViewById(R.id.displayBookLayout);

        //If the modifyBookLibraryLayout is null, it means that you're in portrait view, and if it's not you're in landscape view
        if(displayBookLayout != null){
            // We add a new fragment in fragment manager
            DisplayBookFragment displayBookFragment = new DisplayBookFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.displayBookLayout, displayBookFragment).commit();
        }

        // Assignate function of add button
        TextView addBookButton = (TextView) findViewById(R.id.addBookButton);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Change activity to add book library activity
                Intent intent = new Intent(BookLibraryActivity.this, AddBookActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView addBookScanIsbnButton = (TextView) findViewById(R.id.addBookScanIsbnButton);
        addBookScanIsbnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator scanIntegrator = new IntentIntegrator(BookLibraryActivity.this);
                scanIntegrator.initiateScan();
            }
        });

        TextView addBookFormIsbnButton = (TextView) findViewById(R.id.addBookFormIsbnButton);
        addBookFormIsbnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BookLibraryActivity.this);

                LayoutInflater factory = LayoutInflater.from(BookLibraryActivity.this);
                final View alertDialogView = factory.inflate(R.layout.addbookformisbn, null);

                builder.setTitle(R.string.addBookByIsbnText);
                builder.setView(R.layout.addbookformisbn);
                builder.setPositiveButton(R.string.findBook, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final EditText isbnEdit = (EditText) alertDialogView.findViewById(R.id.isbnEdit);

                        dialog.cancel();
                        createBookListAddPopUp(isbnEdit.getText().toString(), BookLibraryActivity.this);
                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                builder.create();
                builder.show();
            }
        });


}

private void createBookListAddPopUp(String s, Context context) {

    if(s.length() == 13){

        ScanBook scanBook = new ScanBook(s, BookLibraryActivity.this);
        final BookLibrary bookLibrary = scanBook.getBooks();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.addBookQuestion);
        String[] stringOfBooks = new String[bookLibrary.size()];
        int i = 0;

        for(SimpleBook book : bookLibrary){

            stringOfBooks[i] = (book.toString());
            i++;
        }

        builder.setItems(stringOfBooks, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                BookManager bookManager = new BookManager(BookLibraryActivity.this);
                bookManager.saveSimpleBook(bookLibrary.get(which));
                dialog.cancel();
            }
        });

    }else{

        Toast toast = Toast.makeText(context, R.string.isbnNotGoodFormat, Toast.LENGTH_SHORT);
        toast.show();
    }
}


public void updateDisplayBookFragment(int position, View viewLibrary){

        LinearLayout displayBookLayout = (LinearLayout) findViewById(R.id.displayBookLayout);

        //if we are in Landscape mode
        if(displayBookLayout != null) {

            //We update the view of the displaybookfragment with the new position
            DisplayBookFragment displayBookFragment = new DisplayBookFragment();
            View viewDisplay = findViewById(R.id.fragment_display_book);
            displayBookFragment.updateView(position, viewDisplay , viewLibrary);
        }else{

            //We change the activity and send the position by an intent to the DisplayBookActivity
            Intent intent = new Intent(BookLibraryActivity.this, DisplayBookActivity.class);
            intent.putExtra(BookLibraryFragment.POSITION, position);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        FABToolbarLayout fabToolbarLayout = (FABToolbarLayout) findViewById(R.id.fabtoolbar);

        if(fabToolbarLayout.isToolbar()) {
            fabToolbarLayout.hide();
        }else{
            super.onBackPressed();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve result of scanning - instantiate ZXing object
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        //check we have a valid result
        if (scanningResult != null) {
            //get content from Intent Result
            String scanContent = scanningResult.getContents();
            //get format name of data scanned
            String scanFormat = scanningResult.getFormatName();
            if (scanContent != null && scanFormat != null) {

                ScanBook scanBook = new ScanBook(scanContent, BookLibraryActivity.this);
                BookLibrary bookLibrary = scanBook.getBooks();
                createBookListAddPopUp(scanContent, BookLibraryActivity.this);

            }else{

                Toast toast = Toast.makeText(BookLibraryActivity.this, R.string.scanFailed, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
