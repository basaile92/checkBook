package com.example.basaile92.listelivre.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.database.MySQLHelper;
import com.example.basaile92.listelivre.manager.BookManager;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        String bookLibraryTitle = getString(R.string.bookLibraryTitle);
        TabHost.TabSpec spec = tabHost.newTabSpec(bookLibraryTitle);
        spec.setIndicator(bookLibraryTitle);
        Intent intent = new Intent(this, BookLibraryActivity.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        String collectionTitle = getString(R.string.collectionTitle);
        TabHost.TabSpec spec1 = tabHost.newTabSpec(collectionTitle);
        spec1.setIndicator(collectionTitle);
        spec1.setContent(new Intent(this, ListBookCollectionActivity.class));
        tabHost.addTab(spec1);

        Intent intentLocation = getIntent();
        String tabHostLocation = (String) intentLocation.getSerializableExtra("tabHostLocation");
        if(tabHostLocation != null) {
            if (tabHostLocation.equals("collection")) {
                tabHost.setCurrentTab(1);
            }
        }
    }
}
