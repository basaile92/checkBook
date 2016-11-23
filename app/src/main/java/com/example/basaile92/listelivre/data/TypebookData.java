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
    
    public Typebook fromCursor(Cursor c) {
 
        Typebook entry = new Typebook();


        if(c.moveToFirst()) {
            entry.setId(c.getLong(c.getColumnIndex(KEY_ID)));
            entry.setNametype(c.getString(c.getColumnIndex(KEY_NAMETYPE)));
            entry.setIsbn(c.getString(c.getColumnIndex(KEY_ISBN)));
            return entry;

        }else{

            return null;
        }
    }
    
    public ContentValues toContentValues(Typebook arg) {
    
        ContentValues values = new ContentValues();
        
        values.put(KEY_NAMETYPE, arg.getNametype());
        values.put(KEY_ISBN, arg.getIsbn());
        
        return values;
    }

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


    public long createTypebook(Typebook arg) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        return db.insert(TABLE_TYPEBOOK, null, values);
    }
    
    public List<Typebook> getAllTypebookByQuery(String query) {
        
        List<Typebook> list = new ArrayList<Typebook>();
            
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
 
        if (c.moveToFirst()) {
            do {
                Typebook entry = fromCursor(c);
 
                // adding to todo list
                list.add(entry);
            } while (c.moveToNext());
        }

        c.close();
 
        return list;
    }
    
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
    
    public Typebook getTypebookById(long id) {

        String selectQuery = "SELECT  * FROM " + TABLE_TYPEBOOK + " WHERE "
            + KEY_ID + " = " + id
            ;

        return getTypebookByQuery(selectQuery);
    }
    
    
    public List<Typebook> getAllTypebookByNametype(String nametype) {
        
        String selectQuery = "SELECT  * FROM " + TABLE_TYPEBOOK + " WHERE "
            + KEY_NAMETYPE + " = " + "'" + nametype + "'"
            ;
 
        return getAllTypebookByQuery(selectQuery);
    }
    
    public List<Typebook> getAllTypebookByIsbn(String isbn) {
        
        String selectQuery = "SELECT  * FROM " + TABLE_TYPEBOOK + " WHERE "
            + KEY_ISBN + " = " + "'" + isbn + "'"
            ;
 
        return getAllTypebookByQuery(selectQuery);
    }
    
    
    public List<Typebook> getAllTypebook() {
    
        String selectQuery = "SELECT  * FROM " + TABLE_TYPEBOOK;

        return getAllTypebookByQuery(selectQuery);
    }
    
    public long updateTypebookById(Typebook arg, long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        // updating row
        return db.update(TABLE_TYPEBOOK, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public void updateAllTypeBookByIsbn(TypeList types, String isbn) {
        deleteTypebookByIsbn(isbn);
        createTypebook(isbn, types);
    }



    public void deleteTypebookById(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_TYPEBOOK, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public void deleteTypebookByIsbn(String isbn){

        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_TYPEBOOK, KEY_ISBN + " = ?",
                new String[] {isbn});
    }

}
