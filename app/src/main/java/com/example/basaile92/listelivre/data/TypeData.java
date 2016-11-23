package com.example.basaile92.listelivre.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.basaile92.listelivre.database.MySQLHelper;
import com.example.basaile92.listelivre.entity.Type;
import com.example.basaile92.listelivre.entity.TypeList;

public class TypeData {
    
    private static final String LOG = "TypeData";
    
    public static final String TABLE_TYPE = "type";
    
    public static final String KEY_NAME = "name";

    private MySQLHelper helper;
    
    public TypeData(MySQLHelper helper) {
        this.helper = helper;
    }
    
    public Type fromCursor(Cursor c) {
 
        Type entry = new Type();
        entry.setName(c.getString(c.getColumnIndex(KEY_NAME)));
        return entry;
    }
    
    public ContentValues toContentValues(Type arg) {
    
        ContentValues values = new ContentValues();
        
        values.put(KEY_NAME, arg.getName());
        
        return values;
    }
    
    public long createType(Type arg) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        return db.insert(TABLE_TYPE, null, values);
    }
    
    public TypeList getAllTypeByQuery(String query) {
        
        TypeList list = new TypeList();
            
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
 
        if (c.moveToFirst()) {
            do {
                Type entry = fromCursor(c);
 
                // adding to todo list
                list.add(entry);
            } while (c.moveToNext());
        }

        c.close();
 
        return list;
    }

    public Type getTypeByQuery(String query) {
        
        SQLiteDatabase db = helper.getReadableDatabase();
        Log.e(LOG, query);
 
        Cursor c = db.rawQuery(query, null);
 
        if (c != null) {
            c.moveToFirst();
        }
        
        Type entry = fromCursor(c);

        c.close();
 
        return entry;
    }
    
    public Type getTypeByName(String name) {

        String selectQuery = "SELECT  * FROM " + TABLE_TYPE + " WHERE "
            + KEY_NAME + " = " + "'" + name + "'"
            ;

        return getTypeByQuery(selectQuery);
    }
    
    
    
    public TypeList getAllType() {
    
        String selectQuery = "SELECT  * FROM " + TABLE_TYPE;

        return getAllTypeByQuery(selectQuery);
    }
    
    public long updateTypeByName(Type arg, String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        // updating row
        return db.update(TABLE_TYPE, values, KEY_NAME + " = ?",
                new String[] { String.valueOf(name) });
    }
    
    public void deleteTypeByName(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_TYPE, KEY_NAME + " = ?",
                new String[] { String.valueOf(name) });
    }



}
