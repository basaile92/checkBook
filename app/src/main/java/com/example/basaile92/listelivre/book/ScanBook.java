package com.example.basaile92.listelivre.book;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
    protected String jsonString;

    public ScanBook(String isbn, Context context){

        this.isbn = isbn;
        RequestQueue queue = Volley.newRequestQueue(context);
        String bookSearchString = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,bookSearchString, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {

                    jsonString =response;
                }
            }, new Response.ErrorListener(){


                @Override
                public void onErrorResponse(VolleyError error) {
                    jsonString = "";
                }
            });

        queue.add(stringRequest);
    }

    public String getJsonString(){

        return this.jsonString;

    }

    public BookLibrary getBooks(){

        BookLibrary list = new BookLibrary();

        try {
            JSONObject obj = new JSONObject(this.jsonString);
            JSONArray items = obj.getJSONArray("items");

            for(int i = 0; i < obj.getInt("totalItems"); i++){

                JSONObject book = items.getJSONObject(i);
                String isbn = this.isbn;
                String description = book.getString("description");
                String author = parseAuthor(book.getJSONArray("authors"));
                String title = book.getString("title");
                list.addBook(new SimpleBook(isbn, author, title, description, false, false, "", "", ""));
            }


        } catch (JSONException e) {
            e.printStackTrace();
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



