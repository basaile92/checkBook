package com.example.basaile92.listelivre.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.fragment.BookLibraryFragment;
import com.example.basaile92.listelivre.fragment.ModifyBookLibraryFragment;
import com.example.basaile92.listelivre.resources.Book;
import com.example.basaile92.listelivre.resources.BookLibrary;
import com.example.basaile92.listelivre.resources.BookManager;
import com.example.basaile92.listelivre.resources.BookLibraryFragmentCallBack;
import com.example.basaile92.listelivre.resources.ModifyBookLibraryFragmentCallBack;

import java.io.File;


public class MainActivity extends FragmentActivity implements BookLibraryFragmentCallBack, ModifyBookLibraryFragmentCallBack {

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


    public int getId(){

        return this.id;
    }

    public void setId(int id){

        this.id = id;
    }
    @Override
    public void onItemSelected(int itemId) {

        ModifyBookLibraryFragment modifyBookLibraryFragment = (ModifyBookLibraryFragment) getSupportFragmentManager().findFragmentById(R.id.modifyBookLibraryFragment);
                if (modifyBookLibraryFragment != null) {

                    ModifyBookLibraryFragment newModifyBookLibraryFragment = new ModifyBookLibraryFragment();
                    Bundle args = new Bundle();
                    args.putInt("id", id);
                    newModifyBookLibraryFragment.setArguments(args);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.modifyBookLibraryLayout, newModifyBookLibraryFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {

                    ModifyBookLibraryFragment newModifyBookLibraryFragment = new ModifyBookLibraryFragment();
                    Bundle args = new Bundle();
                    args.putInt("id", id);
                    newModifyBookLibraryFragment.setArguments(args);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.mainLibraryLayout, newModifyBookLibraryFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

    }


    @Override
    public void modified() {

        ModifyBookLibraryFragment modifyBookLibraryFragment = (ModifyBookLibraryFragment) getSupportFragmentManager().findFragmentById(R.id.modifyBookLibraryFragment);
        if (modifyBookLibraryFragment != null) {

            Fragment emptyFragment = new Fragment();
            Bundle args = new Bundle();
            args.putInt("id", id);
            emptyFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.modifyBookLibraryLayout, emptyFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {

            BookLibraryFragment bookLibraryFragment = new BookLibraryFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.mainLibraryLayout, bookLibraryFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
