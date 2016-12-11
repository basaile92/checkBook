package com.example.basaile92.listelivre.manager;


import android.content.Context;

import com.example.basaile92.listelivre.data.AuthorData;
import com.example.basaile92.listelivre.data.BookData;
import com.example.basaile92.listelivre.data.CollectionbookData;
import com.example.basaile92.listelivre.data.TypebookData;
import com.example.basaile92.listelivre.database.DAOBase;
import com.example.basaile92.listelivre.entity.Author;
import com.example.basaile92.listelivre.entity.AuthorList;
import com.example.basaile92.listelivre.entity.Book;
import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.entity.Collection;
import com.example.basaile92.listelivre.entity.Collectionbook;
import com.example.basaile92.listelivre.entity.SimpleBook;
import com.example.basaile92.listelivre.entity.TypeList;
import com.example.basaile92.listelivre.entity.Typebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Class use to manipulate the collectionBook database
 */
public class CollectionBookManager extends DAOBase{


    // Constructor
    public CollectionBookManager (Context pContext) {

        super(pContext);
    }


    /**
     * Create a new collectionBook in the database
     * @param collection : collection to add
     * @param book : book to add
     */
    public void saveCollectionBook (Collection collection, SimpleBook book) {

        //Get the collection's name and the book's isbn
        String collectionName = collection.getName();
        String isbn = book.getIsbn();
        Collectionbook collectionbook = new Collectionbook(collectionName, isbn);

        //Create the collectionBook
        CollectionbookData collectionbookData = new CollectionbookData(handler);
        collectionbookData.createCollectionbook(collectionbook);
    }


    /**
     * Delete a collectionBook in the database
     * @param collection : collection to delete
     * @param book : book to delete
     */
    public void deleteCollectionBook (Collection collection, SimpleBook book) {

        //Get the collection's name and the book's isbn
        String collectionName = collection.getName();
        String isbn = book.getIsbn();

        //Delete the collectionBook
        CollectionbookData collectionbookData = new CollectionbookData(handler);
        collectionbookData.deleteCollectionbook(collectionName, isbn);
    }


    /**
     * Use to obtain all books from a collection
     * @param collection : collection which contains books
     * @return a list of books
     */
    private List<Book> getAllBooksFromCollection (Collection collection) {

        //Collect all books from the collection
        CollectionbookData collectionbookData = new CollectionbookData(handler);
        List<Collectionbook> collectionbooks = collectionbookData.getAllCollectionbookByNamecollection(collection.getName());
        List<Book> books = new ArrayList<>();
        BookData bookData = new BookData(handler);

        //Add them in a list
        for (Collectionbook collectionbook : collectionbooks) {
            books.add(bookData.getBookByIsbn(collectionbook.getIsbn()));
        }

        return books;
    }


    /**
     * Use to obtain all simpleBooks from a collection
     * @param collection : collection which contains SimpleBooks
     * @return a BookLibrary
     */
    public BookLibrary getAllSimpleBooksFromCollection (Collection collection) {

        List<Book> books = getAllBooksFromCollection(collection);
        BookLibrary bookLibrary = new BookLibrary();
        AuthorList authors;
        List<Typebook> typebooks;
        AuthorData authorData = new AuthorData(handler);
        TypebookData typebookData = new TypebookData(handler);


        // Collect authors, types and books and add them in a BookLibrary
        for (Book book : books) {
                authors = authorData.getAllAuthorByIsbn(book.getIsbn());
                typebooks = typebookData.getAllTypebookByIsbn(book.getIsbn());
                bookLibrary.add(new SimpleBook(book, authors, typebooks));
        }

        return bookLibrary;
    }


    /**
     * Check if a collectionBook already exist in the database
     * @param book : book to check
     * @param collection : collection to check
     * @return True if the collectionBook already exist
     *          False if not
     */
    public boolean existCollectionbook(SimpleBook book, Collection collection){

        boolean exist;

        String isbn = book.getIsbn();
        String name = collection.getName();
        CollectionbookData collectionbookData = new CollectionbookData(handler);
        exist = (collectionbookData.getCollectionbook(isbn, name) != null);

        return exist;
    }
}
