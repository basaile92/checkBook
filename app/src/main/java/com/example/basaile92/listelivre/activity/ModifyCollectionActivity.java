package com.example.basaile92.listelivre.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.basaile92.listelivre.R;

public class ModifyCollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_collection);

        setTitle(R.string.modifyCollectionActivity);


        //Assignate function to a collection. With a long click, we can edit the collection

    }
}
