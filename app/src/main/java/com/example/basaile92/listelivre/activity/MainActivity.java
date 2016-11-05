package com.example.basaile92.listelivre.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.book.BookManager;
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


    public void updateModifyBookLibraryFragment(int position){

        LinearLayout modifyBookLibraryLayout = (LinearLayout) findViewById(R.id.modifyBookLibraryLayout);
        if(modifyBookLibraryLayout != null) {

            modifyBookLibraryLayout.setVisibility(View.VISIBLE);
            ModifyBookLibraryFragment modifyBookLibraryFragment = new ModifyBookLibraryFragment();
            Bundle args = new Bundle();
            args.putInt(ModifyBookLibraryFragment.POSITION, position);
            modifyBookLibraryFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.modifyBookLibraryLayout, modifyBookLibraryFragment).addToBackStack(null).commit();

        }else{

            Intent intent = new Intent(MainActivity.this, ModifyBookLibraryActivity.class);
            intent.putExtra(ModifyBookLibraryFragment.POSITION, position);
            startActivity(intent);
            finish();


        }

    }

    @Override
    public void updateBookLibraryFragment() {

        LinearLayout modifyBookLibraryLayout = (LinearLayout) findViewById(R.id.modifyBookLibraryLayout);
        if(modifyBookLibraryLayout != null) {

            BookLibraryFragment bookLibraryFragment = (BookLibraryFragment) getSupportFragmentManager().findFragmentById(R.id.bookLibraryFragment);

            bookLibraryFragment.updateView();

           // getSupportFragmentManager().beginTransaction().detach(bookLibraryFragment);
           // getSupportFragmentManager().beginTransaction().add(R.id.bookLibraryLayout, bookLibraryFragment).addToBackStack(null).commit();

        }

    }
}
