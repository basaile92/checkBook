package com.example.basaile92.listelivre.fragment;

    import android.content.DialogInterface;
    import android.content.Intent;
    import android.support.v4.app.Fragment;
    import android.support.v4.app.ListFragment;
    import android.support.v7.app.AlertDialog;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.Button;
    import android.widget.ListView;
    import android.widget.SimpleAdapter;

    import com.example.basaile92.listelivre.R;
    import com.example.basaile92.listelivre.activity.AddBookLibraryActivity;
    import com.example.basaile92.listelivre.activity.ModifyBookLibraryActivity;
    import com.example.basaile92.listelivre.resources.Book;
    import com.example.basaile92.listelivre.resources.BookLibrary;
    import com.example.basaile92.listelivre.resources.BookManager;

    import java.io.File;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class BookLibraryFragment extends ListFragment {

        private ListView bookList;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_book_library, container, false);

            File file = new File("/storage/emulated/0/Android/data/com.example.basaile92.listelivre/files/listeLivre.txt");
            bookList = (ListView) getView().findViewById(R.id.bookList);
            Button addBookButton = (Button) getView().findViewById(R.id.addBookButton);
            BookManager bookManager = new BookManager(file);
            bookManager.createFile();
            BookLibrary bookLibrary = bookManager.readBookLibrary();
            final List<Map<String, String>> listOfBook = new ArrayList<Map<String, String>>();

            addBookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), AddBookLibraryActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });

            bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    BookManager bookManager = new BookManager(new File("/storage/emulated/0/Android/data/com.example.basaile92.listelivre/files/listeLivre.txt"));
                    BookLibrary bookLibrary = bookManager.readBookLibrary();
                    Book book = bookLibrary.get(position);
                    Intent intent = new Intent(getActivity(), ModifyBookLibraryActivity.class);
                    intent.putExtra("id", position);
                    intent.putExtra("book", book);
                    startActivity(intent);
                    getActivity().finish();
                }
            });


            bookList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int position, long l) {

                    View deleteBookButton = adapterView.getChildAt(position).findViewById(R.id.deleteBookButton);
                    final int pos = position;
                    deleteBookButton.setVisibility(View.VISIBLE);
                    deleteBookButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(R.string.deleteQuestion);
                            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    BookManager bookManager = new BookManager(new File("/storage/emulated/0/Android/data/com.example.basaile92.listelivre/files/listeLivre.txt"));
                                    BookLibrary bookLibrary = bookManager.readBookLibrary();
                                    Book book = bookLibrary.get(pos);
                                    bookManager.deleteBook(pos);
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

                for (Book livre : bookLibrary) {

                    Map<String, String> bookMap = new HashMap<String, String>();
                    bookMap.put("authorBook", livre.getAuthor());
                    bookMap.put("titleBook", livre.getTitle());
                    bookMap.put("isbnBook", livre.getIsbn());
                    listOfBook.add(bookMap);
                }

                SimpleAdapter listAdapter = new SimpleAdapter(getActivity().getBaseContext(), listOfBook, R.layout.book, new String[]{"authorBook", "titleBook", "isbnBook"}, new int[]{R.id.authorBook, R.id.titleBook, R.id.isbnBook});
                bookList.setAdapter(listAdapter);
            }

            return view;
        }
    }


