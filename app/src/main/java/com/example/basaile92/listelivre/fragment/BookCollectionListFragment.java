package com.example.basaile92.listelivre.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.adapter.CollectionAdapter;
import com.example.basaile92.listelivre.callback.BookCollectionListFragmentCallBack;
import com.example.basaile92.listelivre.entity.Author;
import com.example.basaile92.listelivre.entity.AuthorList;
import com.example.basaile92.listelivre.entity.Book;
import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.entity.Collection;
import com.example.basaile92.listelivre.entity.CollectionList;
import com.example.basaile92.listelivre.entity.SimpleBook;
import com.example.basaile92.listelivre.entity.Type;
import com.example.basaile92.listelivre.entity.TypeList;
import com.example.basaile92.listelivre.entity.Typebook;
import com.example.basaile92.listelivre.manager.CollectionBookManager;
import com.example.basaile92.listelivre.manager.CollectionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BookCollectionListFragment extends Fragment {

    private BookCollectionListFragmentCallBack mCallback;
    private CollectionAdapter mCollectionAdapter;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle b) {

        View view = inflater.inflate(R.layout.fragment_book_collection_list, container, false);

        updateView(view);
        return view;
    }

    public void updateView(View view) {

        final ExpandableListView collectionListView = (ExpandableListView) view.findViewById(R.id.collectionList);

        CollectionManager collectionManager = new CollectionManager(getContext());

        // Load all collections inside a CollectionList
        CollectionList collectionList = collectionManager.readCollectionList();

        // To be able to click on each collection
        collectionListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                // We update the display book fragment with the good position
                mCallback.updateDisplayCollectionFragment(position, getView());
                return true;
            }
        });

        // Check if the list is initialized to add collections into a listView
        if(collectionList != null) {

            List<Map<String, String>> listOfCollections = new ArrayList<Map<String, String>>();

            for (Collection collection : collectionList) {

                Map<String, String> collectionInfos = new HashMap<String, String>();
                collectionInfos.put("name", collection.getName());

                listOfCollections.add(collectionInfos);
            }

            for (int i = 0 ; i < collectionList.size() ; i++) {

                CollectionBookManager collectionBookManager = new CollectionBookManager(view.getContext());
                BookLibrary bookLibrary = collectionBookManager.getAllSimpleBooksFromCollection(collectionList.get(i));
                collectionList.get(i).setBooks(bookLibrary);
            }

            mCollectionAdapter = new CollectionAdapter(getContext(),collectionList);
            collectionListView.setAdapter(mCollectionAdapter);

            collectionListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                int previousGroup = -1;

                @Override
                public void onGroupExpand(int groupPosition) {
                    if ((previousGroup != -1) && (groupPosition != previousGroup)) {
                        collectionListView.collapseGroup(previousGroup);
                    }
                    previousGroup = groupPosition;
                }
            });

        }
    }

    @Override
    public void onAttach(Context context){

        super.onAttach(context);

        try{

            mCallback = (BookCollectionListFragmentCallBack) context;

        }catch (ClassCastException e){

            throw new ClassCastException(context.toString() + " must implement BookCollectionListFragmentCallBack");
        }
    }
}
