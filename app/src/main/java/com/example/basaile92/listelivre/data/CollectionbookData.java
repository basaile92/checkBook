package com.example.basaile92.listelivre.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.basaile92.listelivre.database.MySQLHelper;
import com.example.basaile92.listelivre.entity.Collection;
import com.example.basaile92.listelivre.entity.Collectionbook;

import java.util.ArrayList;
import java.util.List;

public class CollectionbookData {
    
    private static final String LOG = "CollectionbookData";
    
    public static final String TABLE_COLLECTIONBOOK = "collectionbook";
    
    public static final String KEY_ID = "id";
    public static final String KEY_NAMECOLLECTION = "namecollection";
    public static final String KEY_ISBN = "isbn";

    private MySQLHelper helper;
    
    public CollectionbookData(MySQLHelper helper) {
        this.helper = helper;
    }
    
    public Collectionbook fromCursor(Cursor c) {

        if (c.getCount() > 0) {

            Collectionbook entry = new Collectionbook();
            entry.setId(c.getLong(c.getColumnIndex(KEY_ID)));
            entry.setNamecollection(c.getString(c.getColumnIndex(KEY_NAMECOLLECTION)));
            entry.setIsbn(c.getString(c.getColumnIndex(KEY_ISBN)));
            return entry;
        }

        return null;
    }
    
    public ContentValues toContentValues(Collectionbook arg) {
    
        ContentValues values = new ContentValues();
        
        values.put(KEY_NAMECOLLECTION, arg.getNamecollection());
        values.put(KEY_ISBN, arg.getIsbn());
        
        return values;
    }
    
    public long createCollectionbook(Collectionbook arg) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        return db.insert(TABLE_COLLECTIONBOOK, null, values);
    }
    
    public List<Collectionbook> getAllCollectionbookByQuery(String query) {
        
        List<Collectionbook> list = new ArrayList<Collectionbook>();
            
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
 
        if (c.moveToFirst()) {
            do {
                Collectionbook entry = fromCursor(c);
 
                // adding to todo list
                list.add(entry);
            } while (c.moveToNext());
        }

        c.close();
 
        return list;
    }
    
    public Collectionbook getCollectionbookByQuery(String query) {
        
        SQLiteDatabase db = helper.getReadableDatabase();
        Log.e(LOG, query);
 
        Cursor c = db.rawQuery(query, null);
 
        if (c != null) {
            c.moveToFirst();
        }
        
        Collectionbook entry = fromCursor(c);

        c.close();
 
        return entry;
    }
    
    public Collectionbook getCollectionbookById(long id) {

        String selectQuery = "SELECT  * FROM " + TABLE_COLLECTIONBOOK + " WHERE "
            + KEY_ID + " = " + id
            ;

        return getCollectionbookByQuery(selectQuery);
    }
    
    
    public List<Collectionbook> getAllCollectionbookByNamecollection(String namecollection) {
        
        String selectQuery = "SELECT  * FROM " + TABLE_COLLECTIONBOOK + " WHERE "
            + KEY_NAMECOLLECTION + " = " + "'" + namecollection + "'"
            ;
 
        return getAllCollectionbookByQuery(selectQuery);
    }

    public Collectionbook getCollectionbook(String isbn, String name){

        String selectQuery = "SELECT  * FROM " + TABLE_COLLECTIONBOOK + " WHERE "
                + KEY_NAMECOLLECTION + " = " + "'" + name + "'" + " AND " + KEY_ISBN + " = " + "'" + isbn + "'"
                ;

        return getCollectionbookByQuery(selectQuery);
    }
    
    public List<Collectionbook> getAllCollectionbookByIsbn(String isbn) {
        
        String selectQuery = "SELECT  * FROM " + TABLE_COLLECTIONBOOK + " WHERE "
            + KEY_ISBN + " = " + "'" + isbn + "'"
            ;
 
        return getAllCollectionbookByQuery(selectQuery);
    }
    
    
    public List<Collectionbook> getAllCollectionbook() {
    
        String selectQuery = "SELECT  * FROM " + TABLE_COLLECTIONBOOK;

        return getAllCollectionbookByQuery(selectQuery);
    }
    
    public long updateCollectionbookById(Collectionbook arg, long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        // updating row
        return db.update(TABLE_COLLECTIONBOOK, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
    
    public void deleteCollectionbookById(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_COLLECTIONBOOK, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }


    public void deleteCollectionbook(String name, String isbn) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_COLLECTIONBOOK, KEY_NAMECOLLECTION + " = ?" + " AND " + KEY_ISBN + " = ?",
                new String[] { name, isbn });
    }
}
