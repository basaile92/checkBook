package com.example.basaile92.listelivre.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.basaile92.listelivre.R;

public class ModifyBookLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_book_library);

    }

    public void onBackPressed()
    {
        Intent intent = new Intent(ModifyBookLibraryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
