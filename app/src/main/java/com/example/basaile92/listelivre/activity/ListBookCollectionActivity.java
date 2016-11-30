package com.example.basaile92.listelivre.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.callback.BookCollectionListFragmentCallBack;

public class ListBookCollectionActivity extends FragmentActivity implements BookCollectionListFragmentCallBack{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book_collection);

        // Set the title on the top of the screen
        setTitle(R.string.activityCollectionTitle);


        //Add function to the button Create a new Collection
        FloatingActionButton createCollectionButton = (FloatingActionButton) findViewById(R.id.addCollectionButton);

        createCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ListBookCollectionActivity.this, AddCollectionActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }

    public void updateDisplayCollectionFragment(int position, View viewLibrary){
        //todo
    }
}
