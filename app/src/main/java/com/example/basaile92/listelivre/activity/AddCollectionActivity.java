package com.example.basaile92.listelivre.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.manager.CollectionManager;

public class AddCollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);
        setTitle(R.string.addCollectionActivityTitle);

        final EditText nameEdit = (EditText) findViewById(R.id.collection_name);
        final Button createButton = (Button) findViewById(R.id.create_collection_button);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Test if the name of the new collection respects conditions
                if(checkName(nameEdit)) {

                    CollectionManager collectionManager = new CollectionManager(view.getContext());
                    //collectionManager.saveCollection(nameEdit.getText().toString());

                    Intent intent = new Intent(AddCollectionActivity.this, MainActivity.class);
                    startActivity(intent);

                    finish();
                }
            }
        });
    }


    private boolean checkName(EditText nameText) {

        String name = nameText.getText().toString();

        if(name.length() == 0) {

            Toast toast = Toast.makeText(AddCollectionActivity.this, R.string.collectionNameEmpty, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        else {

            return true;
        }
    }


    //When we push the back button, come back to the main activity
    public void onBackPressed()
    {
        Intent intent = new Intent(AddCollectionActivity.this, MainActivity.class);
        intent.setAction("tabHost.setCurrentTab(1);");
        startActivity(intent);
        finish();
    }
}
