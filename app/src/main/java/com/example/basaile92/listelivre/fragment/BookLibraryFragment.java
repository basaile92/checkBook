package com.example.basaile92.listelivre.fragment;

    import android.app.Activity;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.database.Cursor;
    import android.support.v4.app.Fragment;
    import android.support.v4.widget.SimpleCursorAdapter;
    import android.support.v7.app.AlertDialog;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.ListView;
    import android.widget.SimpleAdapter;

    import com.example.basaile92.listelivre.R;
    import com.example.basaile92.listelivre.resources.Book;
    import com.example.basaile92.listelivre.resources.BookLibrary;
    import com.example.basaile92.listelivre.resources.BookManager;
    import com.example.basaile92.listelivre.resources.BookLibraryFragmentCallBack;
    import com.example.basaile92.listelivre.resources.SimpleBook;

    import java.io.File;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class BookLibraryFragment extends Fragment {

        private BookLibraryFragmentCallBack parent;
        int position = 0;
        private ListView bookList;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle b) {
            View view = inflater.inflate(R.layout.fragment_book_library, container, false);

            bookList = (ListView) view.findViewById(R.id.bookList);
            BookManager bookManager = new BookManager(getContext());
            BookLibrary bookLibrary = bookManager.readBookLibrary();

            bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                    position = pos;
                    parent.onItemSelected(position);
                }
            });


            bookList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int pos, long l) {

                    position = pos;
                    View deleteBookButton = adapterView.getChildAt(position).findViewById(R.id.deleteBookButton);
                    deleteBookButton.setVisibility(View.VISIBLE);
                    deleteBookButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(R.string.deleteQuestion);
                            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    BookManager bookManager = new BookManager(getContext());
                                    BookLibrary bookLibrary= bookManager.readBookLibrary();

                                    Book book = bookLibrary.get(position);

                                    bookManager.deleteBook(position);
                                    dialogInterface.cancel();
                                    Intent intent = new Intent(getActivity(), getActivity().getClass());
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            });

                            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    });

                    return true;
                }
            });
            if(bookLibrary != null) {



                Cursor cursor = bookManager.getDb().rawQuery("SELECT id as _id, author, title from Book;", new String[]{});

                SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(), R.layout.book, cursor ,new String[]{"author", "title"}, new int[]{R.id.authorBook, R.id.titleBook});
                bookList.setAdapter(listAdapter);
            }

            return view;
        }


        public void onAttach(Activity activity){
            super.onAttach(activity);

            parent = (BookLibraryFragmentCallBack) activity;

        }

    }


