package com.example.basaile92.listelivre.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.adapter.BookLibraryAdapter;
import com.example.basaile92.listelivre.callback.BookLibraryFragmentCallBack;
import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.manager.BookManager;

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

            final RecyclerView bookList = (RecyclerView) view.findViewById(R.id.bookList);
            BookManager bookManager = new BookManager(view.getContext());
            final BookLibrary bookLibrary = bookManager.readBookLibrary();

            // if the booklibrary is well assignate
            if(bookLibrary != null) {

                bookList.setLayoutManager(new LinearLayoutManager(getActivity()));
                bookList.setAdapter(new BookLibraryAdapter(bookLibrary, mCallback));
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


