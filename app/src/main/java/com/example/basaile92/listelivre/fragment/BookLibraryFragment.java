package com.example.basaile92.listelivre.fragment;

    import android.app.Activity;
    import android.content.Context;
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

    import com.example.basaile92.listelivre.R;
    import com.example.basaile92.listelivre.book.Book;
    import com.example.basaile92.listelivre.book.BookLibrary;
    import com.example.basaile92.listelivre.book.BookManager;

public class BookLibraryFragment extends Fragment {

        private BookLibraryFragmentCallBack mCallback;
        private ListView bookList;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle b) {
            View view = inflater.inflate(R.layout.fragment_book_library, container, false);

            updateView(view);


            return view;
        }

        public void updateView(final View view){


            bookList = (ListView) view.findViewById(R.id.bookList);
            BookManager bookManager = new BookManager(getContext());
            BookLibrary bookLibrary = bookManager.readBookLibrary();

            bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    mCallback.updateModifyBookLibraryFragment(position, getView());
                }
            });


            bookList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {

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



                Cursor cursor = bookManager.getDb().rawQuery("SELECT id as _id, author, title, photo from Book;", new String[]{});

                SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(), R.layout.book, cursor ,new String[]{"author", "title"}, new int[]{R.id.authorBook, R.id.titleBook});
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


