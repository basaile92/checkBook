package com.example.basaile92.listelivre.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.callback.BookCollectionListFragmentCallBack;
import com.example.basaile92.listelivre.entity.BookLibrary;
import com.example.basaile92.listelivre.entity.Collection;
import com.example.basaile92.listelivre.fragment.AddCollectionDialogFragment;
import com.example.basaile92.listelivre.manager.CollectionManager;

public class ListBookCollectionActivity extends FragmentActivity implements BookCollectionListFragmentCallBack{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book_collection);

        // Set the title on the top of the screen
        setTitle(R.string.activityCollectionTitle);


        //Add function to the button Create a new Collection
        FloatingActionButton createCollectionButton = (FloatingActionButton) findViewById(R.id.addCollectionButton);
        setAddCollectionFunction(createCollectionButton);


    }

    private void setAddCollectionFunction(FloatingActionButton createCollectionButton) {

        createCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment dialog = new AddCollectionDialogFragment();
                dialog.show(getFragmentManager(), "AddCollectionDialogFragment");
            }
        });
    }

    public void updateDisplayCollectionFragment(int position, View viewLibrary){

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
    }


    public void onDialogPositiveClick(DialogFragment dialog, String nameEdit) {

        CollectionManager collectionManager = new CollectionManager(dialog.getActivity());

        if(checkName(nameEdit, collectionManager)) {

            collectionManager.saveCollection(new Collection(nameEdit, new BookLibrary()));

            Intent intent = new Intent(ListBookCollectionActivity.this, MainActivity.class);
            intent.putExtra("tabHostLocation", "collection");
            startActivity(intent);

            finish();
        }
    }


    private boolean checkName(String nameEdit, CollectionManager collectionManager) {

        if(nameEdit.length() == 0) {

            Toast toast = Toast.makeText(ListBookCollectionActivity.this, R.string.collectionNameEmpty, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if(collectionManager.existCollection(nameEdit)){

            Toast toast = Toast.makeText(ListBookCollectionActivity.this, R.string.alreadyExistCollection, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }

    public void onDialogNegativeClick(DialogFragment dialog) {

        dialog.dismiss();
    }
}
