package com.example.basaile92.listelivre.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.entity.Collection;
import com.example.basaile92.listelivre.entity.CollectionList;
import com.example.basaile92.listelivre.manager.CollectionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BookCollectionListFragment extends Fragment {

    private BookCollectionListFragmentCallBack mCallback; // TODO

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

        ListView collectionListView = (ListView) view.findViewById(R.id.collectionList);
        CollectionManager collectionManager = new CollectionManager(getContext());

        // Load all collections inside a CollectionList
        CollectionList collectionList = collectionManager.readCollectionList();

        // To be able to click on each Book collection
        collectionListView.setOnClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // We update the display book fragment with the good position
                mCallback.updateDisplayCollectionFragment(position, getView()); //TODO
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

            //TODO collection.xml
            SimpleAdapter listAdapter = new SimpleAdapter(view.getContext(), listOfCollections, R.layout.collection, new String[]{"name"}, new int[]{R.id.collectionName});
            collectionListView.setAdapter(listAdapter);
        }
    }
}
