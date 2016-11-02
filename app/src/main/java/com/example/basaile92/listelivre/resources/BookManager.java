package com.example.basaile92.listelivre.resources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.database.DatabaseUtilsCompat;

/**
 * Created by basaile92 on 06/10/2016.
 */

public class BookManager extends DAOBase {

    public static final String bookNameDb = "Book";
    public static final String idBookDb = "id";
    public static final String isbnBookDb = "isbn";
    public static final String authorBookDb = "author";
    public static final String titleBookDb = "title";
    public static final String descriptionBookDb = "description";


    public BookManager(Context pContext) {
        super(pContext);
    }


    /**
     * Add a book in the book save file.
     *
     * @param book is the book that you want to add in the save file.
     */
    public void saveSimpleBook(SimpleBook book) throws BookAlreadyExistsException {

        if (dbExistSimpleBook(book)) {

            throw new BookAlreadyExistsException();
        }

        ContentValues value = new ContentValues();
        value.put(isbnBookDb, book.getIsbn());
        value.put(authorBookDb, book.getAuthor());
        value.put(titleBookDb, book.getTitle());
        value.put(descriptionBookDb, book.getDescription());
        db.insert(bookNameDb, null, value);
    }

    private boolean dbExistSimpleBook(SimpleBook book){

        Cursor cursor = db.rawQuery("SELECT * FROM "+ bookNameDb + " WHERE " + isbnBookDb + " = ?", new String[]{ book.getIsbn()} );

        return cursor.getCount() != 0;
    }

    /**
     * Modify a book in the book save file.
     *
     * @param book is the new book that you want to put in the save file.
     */
    public void modifyBook(Book book, long position) {

        long pos = position;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ bookNameDb, null);

        while(cursor.moveToNext() && pos > 0){

            pos --;

        }
        long id = cursor.getLong(0);

        ContentValues value = new ContentValues();
        value.put(isbnBookDb, book.getIsbn());
        value.put(authorBookDb, book.getAuthor());
        value.put(titleBookDb, book.getTitle());
        value.put(descriptionBookDb, book.getDescription());
        db.update(bookNameDb, value, idBookDb + " = ? ", new String[]{String.valueOf(id)});
    }

    /**
     * Delete a book in the book save file.
     * @param position is the position in the database
     */
    public void deleteBook(long position) {


        long pos = position;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ bookNameDb, null);

        while(cursor.moveToNext() && pos > 0){

            pos --;

        }

        long id = cursor.getLong(0);
        db.delete(bookNameDb, idBookDb + " = ?", new String[]{String.valueOf(id)});
    }

    /**
     * Read the Book Library in the book save file.
     *
     * @return the booklibrary
     */
    public BookLibrary readBookLibrary() {


        BookLibrary bookLibrary = new BookLibrary();
        Cursor cursor = db.rawQuery("SELECT * FROM " + bookNameDb, null);

        while (cursor.moveToNext()) {

            String isbn = cursor.getString(1);
            String author = cursor.getString(2);
            String title = cursor.getString(3);
            String description = cursor.getString(4);

            bookLibrary.addBook(new SimpleBook(isbn, author, title, description));
        }
        cursor.close();
        return bookLibrary;


    }
}
