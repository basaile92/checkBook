package com.example.basaile92.listelivre.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.fragment.BookLibraryFragmentCallBack;
import com.example.basaile92.listelivre.fragment.ModifyBookLibraryFragment;

public class ModifyBookLibraryActivity extends AppCompatActivity implements BookLibraryFragmentCallBack{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_book_library);


        Intent intent = getIntent();
        int itemId = intent.getIntExtra(ModifyBookLibraryFragment.POSITION, -1);

        ModifyBookLibraryFragment modifyBookLibraryFragment = new ModifyBookLibraryFragment();
        Bundle args = new Bundle();
        args.putInt(ModifyBookLibraryFragment.POSITION, itemId);
        modifyBookLibraryFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_modify_book_library, modifyBookLibraryFragment).commit();

    }

    public void onBackPressed()
    {
        Intent intent = new Intent(ModifyBookLibraryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateModifyBookLibraryFragment(int position, View view) {

    }

    @Override
    public void updateBookLibraryFragment(View view) {

        Intent intent = new Intent(ModifyBookLibraryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
