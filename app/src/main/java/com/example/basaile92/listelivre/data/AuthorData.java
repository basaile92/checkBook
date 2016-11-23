package com.example.basaile92.listelivre.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.basaile92.listelivre.database.MySQLHelper;
import com.example.basaile92.listelivre.entity.Author;
import com.example.basaile92.listelivre.entity.AuthorList;

public class AuthorData {
    
    private static final String LOG = "AuthorData";
    
    public static final String TABLE_AUTHOR = "author";
    
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_ISBN = "isbn";

    private MySQLHelper helper;
    
    public AuthorData(MySQLHelper helper) {
        this.helper = helper;
    }
    
    public Author fromCursor(Cursor c) {

        if(c.moveToFirst()) {

            Author entry = new Author();
            entry.setId(c.getLong(c.getColumnIndex(KEY_ID)));
            entry.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            entry.setIsbn(c.getString(c.getColumnIndex(KEY_ISBN)));
            return entry;
        }else{

            return null;
        }
    }
    
    public ContentValues toContentValues(Author arg) {
    
        ContentValues values = new ContentValues();
        
        values.put(KEY_NAME, arg.getName());
        values.put(KEY_ISBN, arg.getIsbn());
        
        return values;
    }
    
    public long createAuthor(Author arg) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        return db.insert(TABLE_AUTHOR, null, values);
    }

    public void createAllAuthor(AuthorList authors) {

        SQLiteDatabase db = helper.getWritableDatabase();

        for(Author author: authors){

            ContentValues values = toContentValues(author);

            db.insert(TABLE_AUTHOR, null, values);
        }
    }
    
    public AuthorList getAllAuthorByQuery(String query) {
        
        AuthorList list = new AuthorList();
            
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
 
        if (c.moveToFirst()) {
            do {
                Author entry = fromCursor(c);
 
                // adding to todo list
                list.addAuthor(entry);
            } while (c.moveToNext());
        }

        c.close();
 
        return list;
    }
    
    public Author getAuthorByQuery(String query) {
        
        SQLiteDatabase db = helper.getReadableDatabase();
        Log.e(LOG, query);
 
        Cursor c = db.rawQuery(query, null);
 
        if (c != null) {
            c.moveToFirst();
        }
        
        Author entry = fromCursor(c);

        c.close();
 
        return entry;
    }
    
    public Author getAuthorById(long id) {

        String selectQuery = "SELECT  * FROM " + TABLE_AUTHOR + " WHERE "
            + KEY_ID + " = " + id
            ;

        return getAuthorByQuery(selectQuery);
    }
    
    
    public AuthorList getAllAuthorByIsbn(String isbn) {
        
        String selectQuery = "SELECT  * FROM " + TABLE_AUTHOR + " WHERE "
            + KEY_ISBN + " = " + "'" + isbn + "'"
            ;
 
        return getAllAuthorByQuery(selectQuery);
    }
    
    
    public AuthorList getAllAuthor() {
    
        String selectQuery = "SELECT  * FROM " + TABLE_AUTHOR;

        return getAllAuthorByQuery(selectQuery);
    }
    
    public long updateAuthorById(Author arg, long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        // updating row
        return db.update(TABLE_AUTHOR, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public void updateAllAuthorByIsbn(AuthorList authors, String isbn){

        deleteAllAuthorByIsbn(isbn);
        createAllAuthor(authors);
    }

    public void deleteAuthorById(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_AUTHOR, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public void deleteAllAuthorByIsbn(String isbn){

        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_AUTHOR, KEY_ISBN + " = ?",
                new String[]{ isbn});
    }
}
