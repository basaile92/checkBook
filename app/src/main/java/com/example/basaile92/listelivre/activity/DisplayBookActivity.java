package com.example.basaile92.listelivre.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.fragment.BookLibraryFragment;
import com.example.basaile92.listelivre.fragment.DisplayBookFragment;

public class DisplayBookActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_book);

        //We catch the intent and get the position of the item that we want to display the informations
        Intent intent = getIntent();
        int itemId = intent.getIntExtra(BookLibraryFragment.POSITION, -1);

        //We put an intent and give it to the fragment and we add the fragment
        DisplayBookFragment displayBookFragment = new DisplayBookFragment();
        Bundle args = new Bundle();
        args.putInt(DisplayBookFragment.POSITION, itemId);
        displayBookFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_display_book, displayBookFragment).commit();

    }

    //When the back button is pressed we go back to the MainActivity
    public void onBackPressed()
    {
        Intent intent = new Intent(DisplayBookActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
