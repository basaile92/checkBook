package com.example.basaile92.listelivre.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.callback.BookLibraryFragmentCallBack;
import com.example.basaile92.listelivre.fragment.AddBookByIsbnDialogFragment;
import com.example.basaile92.listelivre.fragment.BookLibraryFragment;
import com.example.basaile92.listelivre.fragment.DisplayBookFragment;
import com.example.basaile92.listelivre.manager.BookManager;
import com.example.basaile92.listelivre.scanbook.ScanBook;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


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
        ImageView addBookButton = (ImageView) findViewById(R.id.addBookButton);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Change activity to add book library activity
                Intent intent = new Intent(BookLibraryActivity.this, AddBookActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Assignate function of add by scan isbn
        ImageView addBookScanIsbnButton = (ImageView) findViewById(R.id.addBookScanIsbnButton);
        addBookScanIsbnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Launch the scanner
                IntentIntegrator scanIntegrator = new IntentIntegrator(BookLibraryActivity.this);
                scanIntegrator.initiateScan();

            }
        });

        //Assignate gunction of add by isbn form
        ImageView addBookFormIsbnButton = (ImageView) findViewById(R.id.addBookFormIsbnButton);
        addBookFormIsbnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create the dialog form
                DialogFragment dialog = new AddBookByIsbnDialogFragment();
                dialog.show(getFragmentManager(), "AddBookByIsbnDialogFragment");

            }
        });

}
    // This function will get an ISBN and if it has 13 characters and it doesn't exist in the library, it will get the isbn in the google API.
    private void createBookListAddPopUp(String s) {

        if(s.length() == 13){

            BookManager bookManager = new BookManager(BookLibraryActivity.this);
            if(!bookManager.existIsbn(s)){

                FragmentManager fragmentManager = getSupportFragmentManager();
                BookLibraryFragment bookLibraryFragment = (BookLibraryFragment) fragmentManager.findFragmentById(R.id.bookLibraryFragment);
                ScanBook.showScanBook(s, bookLibraryFragment, findViewById(R.id.bookLibraryFragment) ,BookLibraryActivity.this);
            }else
            {
                Toast toast = Toast.makeText(BookLibraryActivity.this, R.string.bookAlreadyExist, Toast.LENGTH_SHORT);
                toast.show();
            }

        }else{

            Toast toast = Toast.makeText(BookLibraryActivity.this, R.string.isbnNotGoodFormat, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public void updateDisplayBookFragment(int position){

        LinearLayout displayBookLayout = (LinearLayout) findViewById(R.id.displayBookLayout);

        //if we are in Landscape mode
        if(displayBookLayout != null) {

            //We update the view of the displaybookfragment with the new position
            DisplayBookFragment displayBookFragment = (DisplayBookFragment) getSupportFragmentManager().findFragmentById(R.id.displayBookFragment);
            DisplayBookFragment newDisplayBookFragment = new DisplayBookFragment();
            View viewDisplay = findViewById(R.id.fragment_display_book);
            newDisplayBookFragment.updateView(position, viewDisplay);
            getSupportFragmentManager().beginTransaction().replace(R.id.displayBookLayout, newDisplayBookFragment).commit();

            displayBookLayout.setVisibility(View.VISIBLE);
        }else{

            //We change the activity and send the position by an intent to the DisplayBookActivity
            Intent intent = new Intent(BookLibraryActivity.this, DisplayBookActivity.class);
            intent.putExtra(BookLibraryFragment.POSITION, position);
            startActivity(intent);
            finish();
        }
    }

    //If the positive button of dialog is pushed
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String isbn) {

        createBookListAddPopUp( isbn);

        dialog.dismiss();
    }

    //If the negative button of dialog is pushed
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

        dialog.dismiss();
    }

    //If the back button is pressed, the fabtoolbar will hide.
    @Override
    public void onBackPressed() {
        FABToolbarLayout fabToolbarLayout = (FABToolbarLayout) findViewById(R.id.fabtoolbar);

        if(fabToolbarLayout.isToolbar()) {
            fabToolbarLayout.hide();
        }else{
            super.onBackPressed();
        }
    }

    // Get the result of the scanner
    @Override
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

                createBookListAddPopUp(scanContent);

            }else{

                Toast toast = Toast.makeText(BookLibraryActivity.this, R.string.scanFailed, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
