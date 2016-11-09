package com.example.basaile92.listelivre.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.fragment.BookLibraryFragment;
import com.example.basaile92.listelivre.fragment.BookLibraryFragmentCallBack;
import com.example.basaile92.listelivre.fragment.ModifyBookLibraryFragment;


public class MainActivity extends FragmentActivity implements BookLibraryFragmentCallBack{

    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLabel();


        LinearLayout modifyBookLibraryLayout =(LinearLayout) findViewById(R.id.modifyBookLibraryLayout);
        if(modifyBookLibraryLayout != null){
            ModifyBookLibraryFragment modifyBookLibraryFragment = new ModifyBookLibraryFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.modifyBookLibraryLayout, modifyBookLibraryFragment).commit();
        }

        Button addBookButton = (Button) findViewById(R.id.addBookButton);


        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddBookLibraryActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void initLabel(){

        Button addBookButton = (Button) findViewById(R.id.addBookButton);
        Button searchBookButton = (Button) findViewById(R.id.searchBookButton);
        Button myLibraryButton = (Button) findViewById(R.id.myLibraryButton);
        addBookButton.setText(R.string.addBookButton);
        searchBookButton.setText(R.string.searchBookButton);
        myLibraryButton.setText(R.string.myLibraryButton);

        setTitle(R.string.mainActivity);
    }


    public void updateModifyBookLibraryFragment(int position, View viewLibrary){

        LinearLayout modifyBookLibraryLayout = (LinearLayout) findViewById(R.id.modifyBookLibraryLayout);
        if(modifyBookLibraryLayout != null) {

            LinearLayout layout = (LinearLayout) findViewById(R.id.modifyBookLibraryLayout);
            layout.setVisibility(View.VISIBLE);
            ModifyBookLibraryFragment modifyBookLibraryFragment = new ModifyBookLibraryFragment();
            View viewModif = findViewById(R.id.fragment_modify_book_library);
            modifyBookLibraryFragment.updateView(position, viewModif, viewLibrary);
        }else{

            Intent intent = new Intent(MainActivity.this, ModifyBookLibraryActivity.class);
            intent.putExtra(ModifyBookLibraryFragment.POSITION, position);
            startActivity(intent);
            finish();


        }

    }

    @Override
    public void updateBookLibraryFragment(View view) {

        LinearLayout modifyBookLibraryLayout = (LinearLayout) findViewById(R.id.modifyBookLibraryLayout);
        if(modifyBookLibraryLayout != null) {

            BookLibraryFragment bookLibraryFragment = (BookLibraryFragment) getSupportFragmentManager().findFragmentById(R.id.bookLibraryFragment);

            bookLibraryFragment.updateView(view);

        }

    }



}
