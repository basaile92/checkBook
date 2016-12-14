package com.example.basaile92.listelivre.scanbook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.entity.Author;
import com.example.basaile92.listelivre.entity.AuthorList;
import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.entity.SimpleBook;
import com.example.basaile92.listelivre.entity.Type;
import com.example.basaile92.listelivre.entity.TypeList;
import com.example.basaile92.listelivre.fragment.BookLibraryFragment;
import com.example.basaile92.listelivre.manager.BookManager;
import com.example.basaile92.listelivre.manager.ImageManager;
import com.example.basaile92.listelivre.manager.TypeManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dyment on 10/11/16.
 */


public class ScanBook{


        public static void showScanBook(final String isbn, final BookLibraryFragment bookLibraryFragment, final View view, final Context context){

        //We are getting the JSON thanks to the isbn that we got and with a JSON OBjectRequest (Method.GET)
        String bookSearchString = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.loading));
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,bookSearchString, null,  new Response.Listener<JSONObject>(){


            @Override
            public void onResponse(JSONObject response) {
                final BookLibrary bookLibrary = getBooks(response, isbn);

                if(bookLibrary.size()>0) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.addBookQuestion);
                    String[] stringOfBooks = new String[bookLibrary.size()];
                    int i = 0;

                    for (SimpleBook book : bookLibrary) {

                        stringOfBooks[i] = (book.toString());
                        i++;
                    }

                    builder.setItems(stringOfBooks, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {

                            final BookManager bookManager = new BookManager(context);
                            final TypeManager typeManager = new TypeManager(context);
                            final SimpleBook book = bookLibrary.get(which);
                            String url = book.getPhoto();
                            final String path = "";
                            new ImageManager.ImageDownloader(new ImageManager.DownloadImageListener() {
                                @Override
                                public void onDownload(Bitmap bitmap) {

                                    book.setPhoto(ImageManager.saveBitmap(context, bitmap));

                                    for(Type type: book.getTypes()){
                                        typeManager.saveType(type);
                                    }
                                    bookManager.saveSimpleBook(book);
                                    bookLibraryFragment.updateView(view);
                                    dialog.cancel();


                                }
                            }, context).execute(url);

                        }
                    });

                    progressDialog.dismiss();
                    builder.create();
                    builder.show();
                }else{

                    progressDialog.dismiss();
                    Toast toast = Toast.makeText(context, R.string.isbnNotFound, Toast.LENGTH_SHORT);
                    toast.show();
                }




            }
        }, new Response.ErrorListener(){


            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private static BookLibrary getBooks(JSONObject jsonObject, String isbn){

        BookLibrary list = new BookLibrary();

        if (jsonObject != null) {

            //If the JsonObject that we got isn't null
            try {
                JSONArray items = jsonObject.getJSONArray("items");
                // We get all field of JsonObject and we add it in a library.

                for (int i = 0; i < jsonObject.getInt("totalItems"); i++) {

                    JSONObject book = items.getJSONObject(i).getJSONObject("volumeInfo");
                    String summary = book.getString("description");
                    AuthorList authors = parseArrayAuthor(book.getJSONArray("authors"), isbn);
                    TypeList types = parseArrayType(book.getJSONArray("categories"));
                    String publisher = book.getString("publisher");
                    String year = parseYear(book.getString("publishedDate"));
                    String title = book.getString("title");
                    String photo = book.getJSONObject("imageLinks").getString("thumbnail");




                    SimpleBook simpleBook = new SimpleBook(isbn, authors, title, types, publisher, year, summary, false, false, "", photo);
                    list.addBook(simpleBook);
                }


            } catch (JSONException e) {

            }
        }

        return list;
    }

    //We parse the Date to have only the year.
    private static String parseYear(String publisherDate) {
        int i = 0;
        while(i < publisherDate.length() && publisherDate.charAt(i) != '-'){
            i++;
        }

        return (publisherDate.substring(0, i));

    }

    //We parse the JSONArray to get an AuthorList
    private static AuthorList parseArrayAuthor(JSONArray array, String isbn) throws JSONException {

        AuthorList res = new AuthorList();

        for (int i = 0; i < array.length(); i++) {
            res.addAuthor(new Author(array.getString(i), isbn));
        }

        return res;
    }

    //We parse the JSONArray to get a TypeList
    private static TypeList parseArrayType(JSONArray array) throws JSONException {

        TypeList res = new TypeList();



        for (int i = 0; i < array.length(); i++) {
            res.addType(new Type(array.getString(i)));
        }

        return res;
    }

}



