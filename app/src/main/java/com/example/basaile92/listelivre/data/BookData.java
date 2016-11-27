package com.example.basaile92.listelivre.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.basaile92.listelivre.database.MySQLHelper;
import com.example.basaile92.listelivre.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class BookData {
    
    private static final String LOG = "BookData";
    
    public static final String TABLE_BOOK = "book";
    
    public static final String KEY_ISBN = "isbn";
    public static final String KEY_TITLE = "title";
    public static final String KEY_COLLECTION = "collection";
    public static final String KEY_PUBLISHER = "publisher";
    public static final String KEY_YEAR = "year";
    public static final String KEY_SUMMARY = "summary";
    public static final String KEY_ISREAD = "isread";
    public static final String KEY_ISBORROWED = "isborrowed";
    public static final String KEY_BORROWER = "borrower";
    public static final String KEY_OWNER = "owner";
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_PHOTO = "photo";

    private MySQLHelper helper;
    
    public BookData(MySQLHelper helper) {
        this.helper = helper;
    }
    
    public Book fromCursor(Cursor c) {

        if(c.getCount() > 0) {
            String isbn = c.getString(c.getColumnIndex(KEY_ISBN));
            String title = c.getString(c.getColumnIndex(KEY_TITLE));
            String collection = c.getString(c.getColumnIndex(KEY_COLLECTION));
            String publisher = c.getString(c.getColumnIndex(KEY_PUBLISHER));
            String year = c.getString(c.getColumnIndex(KEY_YEAR));
            String summary = c.getString(c.getColumnIndex(KEY_SUMMARY));
            int isRead = c.getInt(c.getColumnIndex(KEY_ISREAD));
            int isBorrowed = c.getInt(c.getColumnIndex(KEY_ISBORROWED));
            String borrower = c.getString(c.getColumnIndex(KEY_BORROWER));
            String owner = c.getString(c.getColumnIndex(KEY_OWNER));
            String comment = c.getString(c.getColumnIndex(KEY_COMMENT));
            String photo = c.getString(c.getColumnIndex(KEY_PHOTO));
            Book entry = new Book(isbn, title, collection, publisher, year, summary, isRead, isBorrowed, borrower, owner, comment, photo);

            return entry;
        }
        return null;

    }
    
    public ContentValues toContentValues(Book arg) {
    
        ContentValues values = new ContentValues();
        
        values.put(KEY_ISBN, arg.getIsbn());
        values.put(KEY_TITLE, arg.getTitle());
        values.put(KEY_COLLECTION, arg.getCollection());
        values.put(KEY_PUBLISHER, arg.getPublisher());
        values.put(KEY_YEAR, arg.getYear());
        values.put(KEY_SUMMARY, arg.getSummary());
        values.put(KEY_ISREAD, arg.getIsRead());
        values.put(KEY_ISBORROWED, arg.getIsBorrowed());
        values.put(KEY_BORROWER, arg.getBorrower());
        values.put(KEY_OWNER, arg.getOwner());
        values.put(KEY_COMMENT, arg.getComment());
        values.put(KEY_PHOTO, arg.getPhoto());
        
        return values;
    }
    
    public long createBook(Book arg) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        return db.insert(TABLE_BOOK, null, values);
    }
    
    public List<Book> getAllBookByQuery(String query) {
        
        List<Book> list = new ArrayList<Book>();
            
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
 
        if (c.moveToFirst()) {
            do {
                Book entry = fromCursor(c);
 
                // adding to todo list
                list.add(entry);
            } while (c.moveToNext());
        }

        c.close();
 
        return list;
    }
    
    public Book getBookByQuery(String query) {
        
        SQLiteDatabase db = helper.getReadableDatabase();
        Log.e(LOG, query);
 
        Cursor c = db.rawQuery(query, null);
 
        if (c != null) {
            c.moveToFirst();
        }
        
        Book entry = fromCursor(c);

        c.close();
 
        return entry;
    }
    
    public Book getBookByIsbn(String isbn) {

        String selectQuery = "SELECT  * FROM " + TABLE_BOOK + " WHERE "
            + KEY_ISBN + " = " + "'" + isbn + "'"
            ;

        return getBookByQuery(selectQuery);
    }
    
    
    
    public List<Book> getAllBook() {
    
        String selectQuery = "SELECT  * FROM " + TABLE_BOOK;

        return getAllBookByQuery(selectQuery);
    }
    
    public long updateBookByIsbn(Book arg, String isbn) {
        SQLiteDatabase db = helper.getWritableDatabase();
 
        ContentValues values = toContentValues(arg);
 
        // updating row
        return db.update(TABLE_BOOK, values, KEY_ISBN + " = ?",
                new String[] { String.valueOf(isbn) });
    }
    
    public void deleteBookByIsbn(String isbn) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_BOOK, KEY_ISBN + " = ?",
                new String[] { String.valueOf(isbn) });
    }
}
