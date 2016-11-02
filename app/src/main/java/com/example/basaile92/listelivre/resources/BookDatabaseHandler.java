package com.example.basaile92.listelivre.resources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Basile on 02/11/2016.
 */

public class BookDatabaseHandler extends SQLiteOpenHelper {

    public static final String bookNameDb = "Book";
    public static final String idBookDb = "id";
    public static final String isbnBookDb = "isbn";
    public static final String authorBookDb = "author";
    public static final String titleBookDb = "title";
    public static final String descriptionBookDb = "description";
    public static final String destroy_table_book = "DROP TABLE IF EXISTS " + bookNameDb + ";";


    public BookDatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Create the String which contains the SQL command to create a Book table in the database
     * @return the String which contains the SQL command to create a Book table in the database
     */
    private String createTableBook(){

        return ("CREATE TABLE "+bookNameDb+" ( "+idBookDb+" INTEGER PRIMARY KEY, "+isbnBookDb+" TEXT NOT NULL, "+authorBookDb+" TEXT NOT NULL, "+ titleBookDb +" TEXT NOT NULL, "+ descriptionBookDb+" TEXT);");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableBook());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(destroy_table_book);
        onCreate(db);

    }
}
