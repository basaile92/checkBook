package com.example.basaile92.listelivre.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.callback.BookLibraryFragmentCallBack;
import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.manager.BookManager;
import com.example.basaile92.listelivre.entity.SimpleBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookLibraryFragment extends Fragment {

        public static final String POSITION = "itemId";
        private BookLibraryFragmentCallBack mCallback;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle b) {
            View view = inflater.inflate(R.layout.fragment_book_library, container, false);

            //We create the view of the book library
            updateView(view);
            return view;
        }

        public void updateView(final View view){

            ListView bookList = (ListView) view.findViewById(R.id.bookList);
            BookManager bookManager = new BookManager(getContext());
            BookLibrary bookLibrary = bookManager.readBookLibrary();

            // Assignate the click function of each items of the bookList.
            bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    // We update the display book fragment with the good position
                    mCallback.updateDisplayBookFragment(position, getView());
                }
            });

            // if the booklibrary is well assignate
            if(bookLibrary != null) {

                List<Map<String,String>> listOfBook = new ArrayList<Map<String, String>>();
                for(SimpleBook book : bookLibrary){

                    // We fill the 4 fields for each view of the list view.
                    Map<String, String> bookMap = new HashMap<String, String>();
                    //TODO add the photo here
                    bookMap.put("img", "");
                    bookMap.put("authors", book.getAuthors().toString());
                    bookMap.put("title", book.getTitle());
                    listOfBook.add(bookMap);
                }

                //We assignate the listview
                SimpleAdapter listAdapter = new SimpleAdapter(view.getContext(), listOfBook, R.layout.book, new String[]{"img", "authors", "title"}, new int[]{R.id.imageButton, R.id.authorBook, R.id.titleBook});
                bookList.setAdapter(listAdapter);
            }
        }

        @Override
        public void onAttach(Context context){

            super.onAttach(context);

            try{

                mCallback = (BookLibraryFragmentCallBack) context;

            }catch (ClassCastException e){

                throw new ClassCastException(context.toString() + " must implement BookLibraryFragmentCallBack");
            }
        }



    }


