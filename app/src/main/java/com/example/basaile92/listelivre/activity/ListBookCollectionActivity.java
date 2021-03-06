package com.example.basaile92.listelivre.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.adapter.CollectionAdapter;
import com.example.basaile92.listelivre.callback.AddCollectionDialogFragmentCallBack;
import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.entity.Collection;
import com.example.basaile92.listelivre.entity.CollectionList;
import com.example.basaile92.listelivre.fragment.AddCollectionDialogFragment;
import com.example.basaile92.listelivre.manager.CollectionBookManager;
import com.example.basaile92.listelivre.manager.CollectionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListBookCollectionActivity extends FragmentActivity implements AddCollectionDialogFragmentCallBack{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book_collection);

        // Set the title on the top of the screen
        setTitle(R.string.activityCollectionTitle);


        //Add function to the button Create a new Collection
        FloatingActionButton createCollectionButton = (FloatingActionButton) findViewById(R.id.addCollectionButton);
        setAddCollectionButton(createCollectionButton);

        updateView();

    }

    public void updateView() {

        final ExpandableListView collectionListView = (ExpandableListView) findViewById(R.id.collectionList);

        CollectionManager collectionManager = new CollectionManager(ListBookCollectionActivity.this);

        // Load all collections inside a CollectionList
        CollectionList collectionList = collectionManager.readCollectionList();

        // To be able to click on each collection
        collectionListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                // We update the display book fragment with the good position
                ExpandableListView elv = (ExpandableListView) findViewById(R.id.collectionList);
                int pos, i;
                pos = position;
                for (i = 0 ; i < elv.getCount(); i++){

                    if(elv.isGroupExpanded(i) && position > i){
                        pos = position - 1;
                    }
                }

                Intent intent = new Intent(ListBookCollectionActivity.this, ModifyCollectionActivity.class);
                intent.putExtra("collectionID", pos);
                startActivity(intent);
                finish();
                return true;
            }
        });

        // Check if the list is initialized to add collections into a listView
        if(collectionList != null) {

            List<Map<String, String>> listOfCollections = new ArrayList<>();

            for (Collection collection : collectionList) {

                Map<String, String> collectionInfos = new HashMap<>();
                collectionInfos.put("name", collection.getName());

                listOfCollections.add(collectionInfos);
            }

            //Collect all collections from database
            for (int i = 0 ; i < collectionList.size() ; i++) {

                CollectionBookManager collectionBookManager = new CollectionBookManager(ListBookCollectionActivity.this);
                BookLibrary bookLibrary = collectionBookManager.getAllSimpleBooksFromCollection(collectionList.get(i));
                collectionList.get(i).setBooks(bookLibrary);
            }

            //Set the adapter
            CollectionAdapter mCollectionAdapter = new CollectionAdapter(ListBookCollectionActivity.this , collectionList);
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


    /**
     * Allows to add a new collection
     * @param createCollectionButton : Button which trigger the function
     */
    private void setAddCollectionButton(FloatingActionButton createCollectionButton) {

        //Set the eventClickListener
        createCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create the dialog to add a collection
                DialogFragment dialog = new AddCollectionDialogFragment();
                dialog.show(getFragmentManager(), "AddCollectionDialogFragment");
            }
        });
    }


    @Override
    public void onDialogPositiveClick(AddCollectionDialogFragment addCollectionDialogFragment, String nameEditForm) {
        CollectionManager collectionManager = new CollectionManager(addCollectionDialogFragment.getActivity());

        //Check if new collection's name is correct
        if(checkName(nameEditForm, collectionManager)) {

            //If yes, we save the collection in the database
            collectionManager.saveCollection(new Collection(nameEditForm, new BookLibrary()));

            updateView();
            addCollectionDialogFragment.dismiss();
        }

    }


    /**
     * Private function to check if a collection's name respect certain standards
     * @param nameEdit : where is the name to check
     * @param collectionManager : use to compare the name in the database
     * @return True if the name is correct
     *          False if not
     */
    private boolean checkName(String nameEdit, CollectionManager collectionManager) {

        //Verify if the name is not empty
        if(nameEdit.length() == 0) {

            Toast toast = Toast.makeText(ListBookCollectionActivity.this, R.string.collectionNameEmpty, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        //Verify if the name already exist in the database
        if(collectionManager.existCollection(nameEdit)){

            Toast toast = Toast.makeText(ListBookCollectionActivity.this, R.string.alreadyExistCollection, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }

    @Override
    public void onDialogNegativeClick(AddCollectionDialogFragment addCollectionDialogFragment) {

        addCollectionDialogFragment.dismiss();
    }
}
