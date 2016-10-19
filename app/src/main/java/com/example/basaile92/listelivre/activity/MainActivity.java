package com.example.basaile92.listelivre.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import com.example.basaile92.listelivre.R;



public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLabel();
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


    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }


    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== R.id.deleteMenu){

        }
        return true;
    }

}
