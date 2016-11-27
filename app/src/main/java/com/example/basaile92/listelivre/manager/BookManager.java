package com.example.basaile92.listelivre.manager;

import android.content.Context;

import com.example.basaile92.listelivre.exception.BookNotInDatabaseException;
import com.example.basaile92.listelivre.data.AuthorData;
import com.example.basaile92.listelivre.data.BookData;
import com.example.basaile92.listelivre.data.TypebookData;
import com.example.basaile92.listelivre.database.DAOBase;
import com.example.basaile92.listelivre.entity.AuthorList;
import com.example.basaile92.listelivre.entity.Book;
import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.entity.SimpleBook;
import com.example.basaile92.listelivre.entity.Typebook;

import java.util.List;

/**
 * Created by basaile92 on 06/10/2016.
 */

public class BookManager extends DAOBase {


    public BookManager(Context pContext) {
        super(pContext);

    }


    /**
     * Add a book in the book save file.
     *
     * @param book is the book that you want to add in the save file.
     */
    public void saveSimpleBook(SimpleBook book) {

        BookData bookData = new BookData(handler);

        bookData.createBook(book.getBook());

        AuthorData authorData = new AuthorData(handler);

        authorData.createAllAuthor(book.getAuthors());

        TypebookData typebookData = new TypebookData(handler);

        typebookData.createTypebook(book.getIsbn(), book.getTypes());


    }

    public boolean existIsbn(String isbn){

        BookData bookData = new BookData(handler);
        boolean res = bookData.getBookByIsbn(isbn) != null;

        return res;
    }

    /**
     * Modify a book in the book save file.
     *
     * @param book is the new book that you want to put in the save file.
     */
    public void modifyBook(SimpleBook book, SimpleBook newBook) {

        BookData bookData = new BookData(handler);
        AuthorData authorData = new AuthorData(handler);
        TypebookData typebookData = new TypebookData(handler);

        bookData.updateBookByIsbn(newBook.getBook(), book.getIsbn());
        authorData.updateAllAuthorByIsbn(newBook.getAuthors(), book.getIsbn());
        typebookData.updateAllTypeBookByIsbn(newBook.getTypes(), book.getIsbn());
    }



    public int getSimpleBookPosition(SimpleBook book) throws BookNotInDatabaseException {

        BookData bookData = new BookData(handler);
        List<Book> books = bookData.getAllBook();
        int res = 0;

        while(!books.get(res).equals(book.getBook()) || res < books.size() ){

            res ++;
        }

        if(res == books.size()){
            throw new BookNotInDatabaseException();
        }

        return res;

    }

    public SimpleBook getSimpleBookAtPosition(int position){

        BookData bookData = new BookData(handler);
        List<Book> books = bookData.getAllBook();
        Book book = books.get(position);

        AuthorData authorData = new AuthorData(handler);
        AuthorList authors = authorData.getAllAuthorByIsbn(book.getIsbn());

        TypebookData typebookData = new TypebookData(handler);
        List<Typebook> typebooks = typebookData.getAllTypebookByIsbn(book.getIsbn());


        return new SimpleBook(book, authors, typebooks);

    }

    /**
     * Delete a book in the book save file.
     * @param book is the book that you want to delete
     */
    public void deleteBook(SimpleBook book) {

        BookData bookData = new BookData(handler);
        bookData.deleteBookByIsbn(book.getIsbn());
    }

    /**
     * Read the Book Library in the book save file.
     *
     * @return the booklibrary
     */
    public BookLibrary readBookLibrary() {


        BookLibrary bookLibrary = new BookLibrary();

        BookData bookData = new BookData(handler);
        AuthorData authorData = new AuthorData(handler);
        TypebookData typebookData = new TypebookData(handler);

        List<Book> books = bookData.getAllBook();

        for(Book book: books) {

            AuthorList authors = authorData.getAllAuthorByIsbn(book.getIsbn());
            List<Typebook> typebooks = typebookData.getAllTypebookByIsbn(book.getIsbn());
            bookLibrary.add(new SimpleBook(book, authors, typebooks));
        }
        return bookLibrary;
    }

}
