package com.example.basaile92.listelivre.book;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.basaile92.listelivre.entity.Author;
import com.example.basaile92.listelivre.entity.AuthorList;
import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.entity.SimpleBook;
import com.example.basaile92.listelivre.entity.Type;
import com.example.basaile92.listelivre.entity.TypeList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dyment on 10/11/16.
 */


public class ScanBook{

    protected String isbn;
    protected static JSONObject jsonObject;

    public ScanBook(String isbn, Context context){

        //We are getting the JSON thanks to the isbn that we got and with a JSON OBjectRequest (Method.GET).

        this.isbn = isbn;
        String bookSearchString = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,bookSearchString, null,  new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                jsonObject = response;
            }
        }, new Response.ErrorListener(){


            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public BookLibrary getBooks(){

        BookLibrary list = new BookLibrary();

        //If the JsonObject that we got isn't null
        if(jsonObject != null) {

            try {
                JSONArray items = jsonObject.getJSONArray("items");

                // We get all field of JsonObject and we add it in a library.
                for (int i = 0; i < jsonObject.getInt("totalItems"); i++) {

                    JSONObject book = items.getJSONObject(i).getJSONObject("volumeInfo");
                    String isbn = this.isbn;
                    String summary = book.getString("description");
                    AuthorList authors = parseArrayAuthor(book.getJSONArray("authors"), this.isbn);
                    TypeList types = parseArrayType(book.getJSONArray("categories"));
                    String publisher = book.getString("publisher");
                    String year = parseYear(book.getString("publisherDate"));
                    String title = book.getString("title");
                    String photo = book.getJSONObject("imageLinks").getString("thumbnail");

                    list.addBook(new SimpleBook(isbn, authors, title, "", types, publisher, year,  summary, false, false, "", "", "", photo));
                }


            } catch (JSONException e) {

            }
        }
        return list;
    }

    //We parse the Date to have only the year.
    private String parseYear(String publisherDate) {
        int i = 0;
        while(publisherDate.charAt(i) != '-'){
            i++;
        }

        String res = publisherDate.substring(0, 4);
        return res;

    }

    //We parse the JSONArray to get an AuthorList
    private AuthorList parseArrayAuthor(JSONArray array, String isbn) throws JSONException {

        AuthorList res = new AuthorList();



        for (int i = 0; i < array.length(); i++) {
            res.addAuthor(new Author(array.getString(i), isbn));
        }

        return res;
    }

    //We parse the JSONArray to get a TypeList
    private TypeList parseArrayType(JSONArray array) throws JSONException {

        TypeList res = new TypeList();



        for (int i = 0; i < array.length(); i++) {
            res.addType(new Type(array.getString(i)));
        }

        return res;
    }


}



