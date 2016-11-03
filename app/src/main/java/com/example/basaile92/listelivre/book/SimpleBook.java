package com.example.basaile92.listelivre.book;

/**
 * Created by Mercier on 27/10/2016.
 */

public class SimpleBook extends Book {

    protected String isbn;
    protected String description;
    protected Keywords keywords;

    /**
     * Constructor of a simple book
     * @param author
     * @param title
     * @param isbn
     * @param description
     */
    public SimpleBook(String isbn, String author, String title, String description){
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.description = description;
        this.keywords = new Keywords();
    }

    /**
     * getter isbn
     * @return a String which describes the isbn of the Book
     */
    public String getIsbn() {
        return this.isbn;
    }

    /**
     * setter isbn
     * @param isbn is the isbn title that you want to set.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * getter description
     * @return a String which gives more information about the Book
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * setter description
     * @param description
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Add a new keyword to the Book
     * @param keyword
     */
    public void addKeyword(String keyword){
        this.keywords.addKeyword(keyword);
    }

    /**
     * Remove the keyword from the book
     * @param keyword
     */
    public void removeKeyword(String keyword){
        this.keywords.removeKeyword(keyword);
    }

    @Override
    public boolean canContainBook(){

        return false;
    }

}
