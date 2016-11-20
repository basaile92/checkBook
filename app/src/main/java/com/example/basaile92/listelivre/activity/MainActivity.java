package com.example.basaile92.listelivre.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.fragment.BookLibraryFragment;
import com.example.basaile92.listelivre.fragment.BookLibraryFragmentCallBack;
import com.example.basaile92.listelivre.fragment.DisplayBookFragment;
import com.example.basaile92.listelivre.fragment.Fab;
import com.gordonwong.materialsheetfab.MaterialSheetFab;


public class MainActivity extends FragmentActivity implements BookLibraryFragmentCallBack{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Title of the Activity
        setTitle(R.string.mainActivity);

        // We define the Fab button right down
        Fab fab = (Fab) findViewById(R.id.fab);
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.brownie_top);
        int fabColor = getResources().getColor(R.color.brownie_top);
        MaterialSheetFab materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);

        LinearLayout displayBookLayout =(LinearLayout) findViewById(R.id.displayBookLayout);

        //If the modifyBookLibraryLayout is null, it means that you're in portrait view, and if it's not you're in landscape view
        if(displayBookLayout != null){
            // We add a new fragment in fragment manager
            DisplayBookFragment displayBookFragment = new DisplayBookFragment();
            getSupportFragmentManager().beginTransaction().add(displayBookLayout, displayBookFragment).commit();
        }

        // Assignate function of add button
        Button addBookButton = (Button) findViewById(R.id.addBookButton);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Change activity to add book library activity
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
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
            Intent intent = new Intent(MainActivity.this, DisplayBookActivity.class);
            intent.putExtra(BookLibraryFragment.POSITION, position);
            startActivity(intent);
            finish();
        }
    }

}
