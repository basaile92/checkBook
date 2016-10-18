package com.example.basaile92.listelivre.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.resources.Book;
import com.example.basaile92.listelivre.resources.BookLibrary;
import com.example.basaile92.listelivre.resources.BookManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private ListView bookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLabel();
        setTitle(R.string.mainActivity);


        File file = new File("/storage/emulated/0/Android/data/com.example.basaile92.listelivre/files/listeLivre.txt");
        bookList = (ListView) findViewById(R.id.bookList);
        Button addBookButton = (Button) findViewById(R.id.addBookButton);
        BookManager bookManager = new BookManager(file);
        bookManager.createFile();
        BookLibrary bookLibrary = bookManager.readBookLibrary();
        final List<Map<String, String>> listOfBook = new ArrayList<Map<String, String>>();

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddBookLibraryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                BookManager bookManager = new BookManager(new File("/storage/emulated/0/Android/data/com.example.basaile92.listelivre/files/listeLivre.txt"));
                BookLibrary bookLibrary = bookManager.readBookLibrary();
                Book book = bookLibrary.get(position);
                Intent intent = new Intent(MainActivity.this, ModifyBookLibraryActivity.class);
                intent.putExtra("book", book);
                startActivity(intent);
                finish();
            }
        });


        bookList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int position, long l) {

                View deleteBookButton = adapterView.getChildAt(position).findViewById(R.id.deleteBookButton);
                final int pos = position;
                deleteBookButton.setVisibility(View.VISIBLE);
                deleteBookButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(R.string.deleteQuestion);
                        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                BookManager bookManager = new BookManager(new File("/storage/emulated/0/Android/data/com.example.basaile92.listelivre/files/listeLivre.txt"));
                                BookLibrary bookLibrary = bookManager.readBookLibrary();
                                Book book = bookLibrary.get(pos);
                                bookManager.deleteBook(book);
                                dialogInterface.cancel();
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
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

                return true;
            }
        });
        if(bookLibrary != null) {

            for (Book livre : bookLibrary) {

                Map<String, String> bookMap = new HashMap<String, String>();
                bookMap.put("authorBook", livre.getAuthor());
                bookMap.put("titleBook", livre.getTitle());
                bookMap.put("isbnBook", livre.getIsbn());
                listOfBook.add(bookMap);
            }

            SimpleAdapter listAdapter = new SimpleAdapter(this.getBaseContext(), listOfBook, R.layout.book, new String[]{"authorBook", "titleBook", "isbnBook"}, new int[]{R.id.authorBook, R.id.titleBook, R.id.isbnBook});
            bookList.setAdapter(listAdapter);
        }
    }

    public void initLabel(){

        Button addBookButton = (Button) findViewById(R.id.addBookButton);
        Button searchBookButton = (Button) findViewById(R.id.searchBookButton);
        Button myLibraryButton = (Button) findViewById(R.id.myLibraryButton);
        addBookButton.setText(R.string.addBookButton);
        searchBookButton.setText(R.string.searchBookButton);
        myLibraryButton.setText(R.string.myLibraryButton);
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
