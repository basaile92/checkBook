package com.example.basaile92.listelivre.book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.basaile92.listelivre.database.DAOBase;

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
    public static final String isReadDb = "isread";
    public static final String isBorrowedDb = "isborrowed";
    public static final String borrowerDb = "borrower";
    public static final String ownerDb = "owner";
    public static final String commentDb = "comment";
    public static final String photoDb = "photo";


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
        value.put(isReadDb, book.isRead());
        value.put(isBorrowedDb, book.isBorrowed());
        value.put(borrowerDb, book.getBorrower());
        value.put(ownerDb, book.getOwner());
        value.put(commentDb, book.getComment());
        value.put(photoDb, book.getPhoto());





        db.insert(bookNameDb, null, value);
    }

    private boolean dbExistSimpleBook(SimpleBook book){

        Cursor cursor = db.rawQuery("SELECT * FROM "+ bookNameDb + " WHERE " + isbnBookDb + " = ?", new String[]{ book.getIsbn()} );

        return cursor.getCount() != 0;
    }

    private boolean dbExistSimpleBook(SimpleBook book, long position){

        if(book.getIsbn().equals(getSimpleBook(position).getIsbn())){

            return false;
        }

        Cursor cursor = db.rawQuery("SELECT * FROM "+ bookNameDb + " WHERE " + isbnBookDb + " = ?", new String[]{ book.getIsbn()} );

        return cursor.getCount() != 0;


    }


    /**
     * Modify a book in the book save file.
     *
     * @param book is the new book that you want to put in the save file.
     */
    public void modifyBook(SimpleBook book, long position) throws BookAlreadyExistsException {

        if (dbExistSimpleBook(book, position)){

            throw new BookAlreadyExistsException();
        }


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
        value.put(isReadDb, book.isRead());
        value.put(isBorrowedDb, book.isBorrowed);
        value.put(borrowerDb, book.getBorrower());
        value.put(ownerDb, book.getOwner());
        value.put(commentDb, book.getComment());
        value.put(photoDb, book.getPhoto());

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
            boolean isRead = intToBool(cursor.getInt(5));
            boolean isBorrowed = intToBool(cursor.getInt(6));
            String borrower = cursor.getString(7);
            String owner = cursor.getString(8);
            String comment = cursor.getString(9);
            String photo = cursor.getString(10);

            bookLibrary.addBook(new SimpleBook(isbn, author, title, description, isRead, isBorrowed, borrower, owner, comment, photo));
        }
        cursor.close();
        return bookLibrary;


    }

    private boolean intToBool(int anInt) {
        if(anInt == 0) return false;

        return true;
    }

    public SimpleBook getSimpleBook(long position) {

        long pos = position;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ bookNameDb, null);

        while(cursor.moveToNext() && pos > 0){

            pos --;

        }

        String isbn = cursor.getString(1);
        String author = cursor.getString(2);
        String title = cursor.getString(3);
        String description = cursor.getString(4);
        boolean isRead = intToBool(cursor.getInt(5));
        boolean isBorrowed = intToBool(cursor.getInt(6));
        String borrower = cursor.getString(7);
        String owner = cursor.getString(8);
        String comment = cursor.getString(9);
        String photo = cursor.getString(10);

        cursor.close();
        return new SimpleBook(isbn, author, title, description, isRead, isBorrowed, borrower, owner, comment, photo);

    }
}