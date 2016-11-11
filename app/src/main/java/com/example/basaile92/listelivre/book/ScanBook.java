package com.example.basaile92.listelivre.book;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dyment on 10/11/16.
 */


public class ScanBook{

    protected String isbn;
    protected static JSONObject jsonObject;
    protected static boolean status = false;

    public ScanBook(String isbn, Context context){

        this.isbn = isbn;
        String bookSearchString = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,bookSearchString, null,  new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                status = true;
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

        if(this.status) {

            try {
                JSONArray items = jsonObject.getJSONArray("items");

                for (int i = 0; i < jsonObject.getInt("totalItems"); i++) {

                    JSONObject book = items.getJSONObject(i).getJSONObject("volumeInfo");
                    String isbn = this.isbn;
                    String description = book.getString("description");
                    String author = parseAuthor(book.getJSONArray("authors"));
                    String title = book.getString("title");
                    String photo = book.getJSONObject("imageLinks").getString("thumbnail");

                    list.addBook(new SimpleBook(isbn, author, title, description, false, false, "", "", "", photo));
                }


            } catch (JSONException e) {

            }
        }
        return list;
    }

    private String parseAuthor(JSONArray authors) throws JSONException {

        String res = authors.getString(0);



        for (int i = 1; i < authors.length(); i++) {
            res += ", " + authors.getString(i);
        }

        return res;
    }

}



