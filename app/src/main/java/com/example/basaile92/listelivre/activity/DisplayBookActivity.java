package com.example.basaile92.listelivre.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.callback.BookLibraryFragmentCallBack;
import com.example.basaile92.listelivre.fragment.BookLibraryFragment;
import com.example.basaile92.listelivre.fragment.DisplayBookFragment;

public class DisplayBookActivity extends FragmentActivity implements BookLibraryFragmentCallBack{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_book);

        LinearLayout isPortrait = (LinearLayout) findViewById(R.id.isPortrait);



        //We catch the intent and get the position of the item that we want to display the informations
        Intent intent = getIntent();
        int itemId = intent.getIntExtra(BookLibraryFragment.POSITION, -1);

        if(isPortrait == null){

            Intent intent2 = new Intent(DisplayBookActivity.this, MainActivity.class);
            startActivity(intent2);
            finish();
        }


        //We put an intent and give it to the fragment and we add the fragment
        DisplayBookFragment displayBookFragment = new DisplayBookFragment();
        Bundle args = new Bundle();
        args.putInt(BookLibraryFragment.POSITION, itemId);
        displayBookFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_display_book, displayBookFragment).commit();

    }

    //When the back button is pressed we go back to the BookLibraryActivity
    public void onBackPressed()
    {
        Intent intent = new Intent(DisplayBookActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //This 3 functions are not used.
    @Override
    public void updateDisplayBookFragment(int position, View view) {

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String isbn) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
