package com.example.basaile92.listelivre.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.fragment.BookLibraryFragment;
import com.example.basaile92.listelivre.fragment.BookLibraryFragmentCallBack;
import com.example.basaile92.listelivre.fragment.DisplayBookFragment;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;


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

}
