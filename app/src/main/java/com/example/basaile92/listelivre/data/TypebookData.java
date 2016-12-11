package com.example.basaile92.listelivre.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.basaile92.listelivre.database.MySQLHelper;
import com.example.basaile92.listelivre.entity.Type;
import com.example.basaile92.listelivre.entity.TypeList;
import com.example.basaile92.listelivre.entity.Typebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Database for TypeBook.
 * Each TypeBook contains an id, a nameType and an isbn.
 */
public class TypebookData {
    
    private static final String LOG = "TypebookData";
    
    public static final String TABLE_TYPEBOOK = "typebook";
    
    public static final String KEY_ID = "id";
    public static final String KEY_NAMETYPE = "nametype";
    public static final String KEY_ISBN = "isbn";

    private MySQLHelper helper;
    
    public TypebookData(MySQLHelper helper) {
        this.helper = helper;
    }

    /**
     * @param c a cursor
     * @return the TypeBook which is designated by the cursor
     */
    public Typebook fromCursor(Cursor c) {
 
        Typebook entry = new Typebook();
        entry.setId(c.getLong(c.getColumnIndex(KEY_ID)));
        entry.setNametype(c.getString(c.getColumnIndex(KEY_NAMETYPE)));
        entry.setIsbn(c.getString(c.getColumnIndex(KEY_ISBN)));
        return entry;
    }

    /**
     * Create a ContentValue with a TypeBook information
     * @param arg a TypeBook which contains information
     * @return a ContentValue
     */
    public ContentValues toContentValues(Typebook arg) {
    
        ContentValues values = new ContentValues();
        
        values.put(KEY_NAMETYPE, arg.getNametype());
        values.put(KEY_ISBN, arg.getIsbn());
        
        return values;
    }

    /**
     * Create new TypeBooks in the database
     * @param isbn isbn of typeBooks to add
     * @param types names of typeBooks to add
     */
    public void createTypebook(String isbn, TypeList types) {
        SQLiteDatabase db = helper.getWritableDatabase();

        for(Type type : types){

            Typebook typebook = new Typebook();
            typebook.setNametype(type.getName());
            typebook.setIsbn(isbn);

            ContentValues values = toContentValues(typebook);

            db.insert(TABLE_TYPEBOOK, null, values);
        }
    }

    /**
     * Create a new Collection in the database
     * @param arg Collection to add in the database
     * @return the id
     */
    public long createTypebook(Typebook arg) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        return db.insert(TABLE_TYPEBOOK, null, values);
    }

    /**
     * @param query the condition
     * @return a list of TypeBook which contains all TypeBooks from the database which respects the query
     */
    public List<Typebook> getAllTypebookByQuery(String query) {
        
        List<Typebook> list = new ArrayList<Typebook>();
            
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
 
        if (c.moveToFirst()) {
            do {
                Typebook entry = fromCursor(c);
 
                list.add(entry);
            } while (c.moveToNext());
        }

        c.close();
 
        return list;
    }

    /**
     * @param query the condition
     * @return a TypeBook from the database which respects the query
     */
    public Typebook getTypebookByQuery(String query) {
        
        SQLiteDatabase db = helper.getReadableDatabase();
        Log.e(LOG, query);
 
        Cursor c = db.rawQuery(query, null);
 
        if (c != null) {
            c.moveToFirst();
        }
        
        Typebook entry = fromCursor(c);

        c.close();
 
        return entry;
    }

    /**
     * @param id
     * @return a TypeBook from the database with the id 'id'
     */
    public Typebook getTypebookById(long id) {

        String selectQuery = "SELECT  * FROM " + TABLE_TYPEBOOK + " WHERE "
            + KEY_ID + " = " + id
            ;

        return getTypebookByQuery(selectQuery);
    }

    /**
     * @param nametype
     * @return a list of TypeBooks from the database with the name 'nametype'
     */
    public List<Typebook> getAllTypebookByNametype(String nametype) {
        
        String selectQuery = "SELECT  * FROM " + TABLE_TYPEBOOK + " WHERE "
            + KEY_NAMETYPE + " = " + "'" + nametype + "'"
            ;
 
        return getAllTypebookByQuery(selectQuery);
    }

    /**
     * @param isbn
     * @return a list of TypeBooks from the database with the isbn 'isbn'
     */
    public List<Typebook> getAllTypebookByIsbn(String isbn) {
        
        String selectQuery = "SELECT  * FROM " + TABLE_TYPEBOOK + " WHERE "
            + KEY_ISBN + " = " + "'" + isbn + "'"
            ;
 
        return getAllTypebookByQuery(selectQuery);
    }


    /**
     * @return all TypeBooks from the database
     */
    public List<Typebook> getAllTypebook() {
    
        String selectQuery = "SELECT  * FROM " + TABLE_TYPEBOOK;

        return getAllTypebookByQuery(selectQuery);
    }


    /**
     * Modify information of the TypeBook have the id 'id' in the database
     * @param arg new Collection
     * @param id
     * @return the id
     */
    public long updateTypebookById(Typebook arg, long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        // updating row
        return db.update(TABLE_TYPEBOOK, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    /**
     * Modify information of all TypeBooks have the isbn 'isbn' from the TypeList 'types"
     * @param types new TypesBooks
     * @param isbn
     */
    public void updateAllTypeBookByIsbn(TypeList types, String isbn) {
        deleteTypebookByIsbn(isbn);
        createTypebook(isbn, types);
    }


    /**
     * Delete a TypeBook from the database which have the id 'id'
     * @param id
     */
    public void deleteTypebookById(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_TYPEBOOK, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    /**
     * Delete a TypeBook from the database which have the isbn 'isbn'
     * @param isbn
     */
    public void deleteTypebookByIsbn(String isbn){

        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_TYPEBOOK, KEY_ISBN + " = ?",
                new String[] {isbn});
    }

}
