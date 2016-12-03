package com.example.basaile92.listelivre.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.manager.CollectionManager;

public class ModifyCollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_collection);

        setTitle(R.string.modifyCollectionActivity);

        int position;
        Intent intent = getIntent();
        position = intent.getIntExtra("collectionID",0);

        Button deleteCollectionButton = (Button) findViewById(R.id.deleteCollectionButton);

        setDeleteCollectionButton(deleteCollectionButton, position);

    }


    public void setDeleteCollectionButton(Button deleteCollectionButton, final int position) {

        deleteCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyCollectionActivity.this);
                builder.setMessage(R.string.deleteCollectionQuestion);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        CollectionManager collectionManager = new CollectionManager(view.getContext());
                        collectionManager.deleteCollection(collectionManager.getCollectionAtPosition(position));

                        Intent intent = new Intent(ModifyCollectionActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }


    //When we push the back button, come back to the main activity
    public void onBackPressed()
    {
        Intent intent = new Intent(ModifyCollectionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
