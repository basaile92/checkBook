package com.example.basaile92.listelivre.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.basaile92.listelivre.database.MySQLHelper;
import com.example.basaile92.listelivre.entity.Author;
import com.example.basaile92.listelivre.entity.AuthorList;

/**
 * Database for Authors.
 * Each Author contains an id, a name and a isbn number
 */
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

    /**
     * @param c a cursor
     * @return the Author who is designated by the cursor
     */
    public Author fromCursor(Cursor c) {

        Author entry = new Author();
        entry.setId(c.getLong(c.getColumnIndex(KEY_ID)));
        entry.setName(c.getString(c.getColumnIndex(KEY_NAME)));
        entry.setIsbn(c.getString(c.getColumnIndex(KEY_ISBN)));
        return entry;
    }

    /**
     * Create a ContentValue with an author information
     * @param arg an author which contains information
     * @return a ContentValue
     */
    public ContentValues toContentValues(Author arg) {
    
        ContentValues values = new ContentValues();
        
        values.put(KEY_NAME, arg.getName());
        values.put(KEY_ISBN, arg.getIsbn());
        
        return values;
    }

    /**
     * Create a new Authors in the database
     * @param arg Author to add in the database
     * @return the id
     */
    public long createAuthor(Author arg) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        return db.insert(TABLE_AUTHOR, null, values);
    }

    /**
     * Create all authors in the database from a list of authors
     * @param authors an AuthorList contains all authors to add
     */
    public void createAllAuthor(AuthorList authors) {

        SQLiteDatabase db = helper.getWritableDatabase();

        for(Author author: authors){

            ContentValues values = toContentValues(author);

            db.insert(TABLE_AUTHOR, null, values);
        }
    }

    /**
     * @param query the condition
     * @return an AuthorList which contains all authors from the database who respects the query
     */
    public AuthorList getAllAuthorByQuery(String query) {
        
        AuthorList list = new AuthorList();
            
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
 
        if (c.moveToFirst()) {
            do {
                Author entry = fromCursor(c);
 
                list.addAuthor(entry);
            } while (c.moveToNext());
        }

        c.close();
 
        return list;
    }

    /**
     * @param query the condition
     * @return an Author from the database who respect the query
     */
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


    /**
     * @param id
     * @return an Author from the database with the id 'id'
     */
    public Author getAuthorById(long id) {

        String selectQuery = "SELECT  * FROM " + TABLE_AUTHOR + " WHERE "
            + KEY_ID + " = " + id
            ;

        return getAuthorByQuery(selectQuery);
    }


    /**
     * @param isbn
     * @return an AuthorList which contains Authors from the database with the isbn 'isbn'
     */
    public AuthorList getAllAuthorByIsbn(String isbn) {
        
        String selectQuery = "SELECT  * FROM " + TABLE_AUTHOR + " WHERE "
            + KEY_ISBN + " = " + "'" + isbn + "'"
            ;
 
        return getAllAuthorByQuery(selectQuery);
    }


    /**
     * @return all authors from the database
     */
    public AuthorList getAllAuthor() {
    
        String selectQuery = "SELECT  * FROM " + TABLE_AUTHOR;

        return getAllAuthorByQuery(selectQuery);
    }


    /**
     * Modify information of the author have the id 'id' in the database
     * @param arg new Author
     * @param id
     * @return the id
     */
    public long updateAuthorById(Author arg, long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        // updating row
        return db.update(TABLE_AUTHOR, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    /**
     * Modifiy information of all authors have the isbn 'isbn' in the database
     * @param authors new authors
     * @param isbn
     */
    public void updateAllAuthorByIsbn(AuthorList authors, String isbn){

        deleteAllAuthorByIsbn(isbn);
        createAllAuthor(authors);
    }


    /**
     * Delete an author from the database who have the id 'id'
     * @param id
     */
    public void deleteAuthorById(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_AUTHOR, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }


    /**
     * Delete all authors from the database who have the isbn 'isbn'
     * @param isbn
     */
    public void deleteAllAuthorByIsbn(String isbn){

        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_AUTHOR, KEY_ISBN + " = ?",
                new String[]{ isbn});
    }
}
