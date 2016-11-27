package com.example.basaile92.listelivre.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.callback.BookCollectionListFragmentCallBack;

public class ListBookCollectionActivity extends FragmentActivity implements BookCollectionListFragmentCallBack{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book_collection);

        // Set the title on the top of the screen
        setTitle(R.string.activityCollectionTitle);
    }

    public void updateDisplayCollectionFragment(int position, View viewLibrary){
        //todo
    }
}
