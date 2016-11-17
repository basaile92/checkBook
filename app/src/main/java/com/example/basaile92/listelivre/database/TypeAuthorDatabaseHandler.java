package com.example.basaile92.listelivre.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dyment on 17/11/16.
 */

public class TypeAuthorDatabaseHandler extends SQLiteOpenHelper {

    public static final String typeAuthorNameDb = "TypeAuthor";
    public static final String idTypeAuthorDb = "id";
    public static final String idTypeDb = "idType";
    public static final String idAuthorDb = "idAuthor";




    public static final String destroy_table_book = "DROP TABLE IF EXISTS " + typeAuthorNameDb + ";";


    public TypeAuthorDatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Create the String which contains the SQL command to create a Book table in the database
     * @return the String which contains the SQL command to create a Book table in the database
     */
    private String createTableBook(){

        return ("CREATE TABLE "+typeAuthorNameDb+" ( "+idTypeAuthorDb+" INTEGER PRIMARY KEY, "+idTypeDb +" NUMERIC NOT NULL, "+ idAuthorDb  +"NUMERIC NOT NULL);");
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
