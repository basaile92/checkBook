package com.example.basaile92.listelivre.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dyment on 17/11/16.
 */

public class TypeBookDatabaseHandler extends SQLiteOpenHelper {

    public static final String typeBookNameDb = "TypeBook";
    public static final String idTypeBookDb = "id";
    public static final String idTypeDb = "idType";
    public static final String isbnBookDb = "isbn";




    public static final String destroy_table_book = "DROP TABLE IF EXISTS " + typeBookNameDb + ";";


    public TypeBookDatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Create the String which contains the SQL command to create a Book table in the database
     * @return the String which contains the SQL command to create a Book table in the database
     */
    private String createTableBook(){

        return ("CREATE TABLE "+typeBookNameDb+" ( "+idTypeBookDb+" INTEGER PRIMARY KEY, "+idTypeDb +" NUMERIC NOT NULL, "+ isbnBookDb  +"NUMERIC NOT NULL);");
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
