package com.example.basaile92.listelivre.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basaile92.listelivre.R;
import com.example.basaile92.listelivre.entity.Collection;
import com.example.basaile92.listelivre.entity.CollectionList;

/**
 * Created by merciert on 24/11/16.
 */

public class CollectionAdapter implements ExpandableListAdapter {

    private Context context;
    private CollectionList collectionList;


    //Constructor of a CollectionAdapter
    public CollectionAdapter(Context context, CollectionList collectionList) {
        this.context = context;
        this.collectionList = collectionList;
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    // Return number of collection inside the list
    @Override
    public int getGroupCount() {
        return this.collectionList.size();
    }


    // Return the number of list of books the collection contains
    @Override
    public int getChildrenCount(int groupPosition) {
        // return 1 because a collection have only one list of books
        return 1;
    }


    // Return the book at the position groupPosition
    @Override
    public Object getGroup(int groupPosition) {
        return collectionList.get(groupPosition);
    }

    // Return the book at the position groupPosition
    // childPosition is not use because, a collection have one list of books
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return collectionList.get(groupPosition);
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }


    // Return a view to obtain the collection view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ParentHolder parentHolder = null;

        Collection collection = (Collection) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.collection, null);
            convertView.setHorizontalScrollBarEnabled(true);

            parentHolder = new ParentHolder();
            convertView.setTag(parentHolder);
        } else {
            parentHolder = (ParentHolder) convertView.getTag();
        }

        //Set the collection name
        parentHolder.collectionName = (TextView) convertView.findViewById(R.id.collection_title);
        parentHolder.collectionName.setText(collection.getName());

        //Set the indicator to see or not books
        parentHolder.indicator = (ImageView) convertView.findViewById(R.id.image_indicator);

        //Test if the indicator is expanded or not
        if(isExpanded) {
            //If the indicator is expanded, the arrow is up
            parentHolder.indicator.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
        } else {
            //If the indicator is not expanded, the arrow is down
            parentHolder.indicator.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
        }

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildHolder childHolder = null;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_books_collection, parent, false);

            childHolder = new ChildHolder();
            convertView.setTag(childHolder);
        } else {

            childHolder = (ChildHolder) convertView.getTag();
        }

        childHolder.horizontalListView = (RecyclerView) convertView.findViewById(R.id.booksOfCollection);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        childHolder.horizontalListView.setLayoutManager(layoutManager);

        //Add a bookAdapter to display all books inside the collection
        BookAdapter bookAdapter = new BookAdapter(context, collectionList.get(groupPosition).books);
        childHolder.horizontalListView.setAdapter(bookAdapter);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {

        return false;
    }

    @Override
    public boolean isEmpty() {

        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {

        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {

        return 0;
    }


    // Class for the list of books
    private static class ChildHolder {
        static RecyclerView horizontalListView;
    }

    // Class for the collection
    private static class ParentHolder {
        TextView collectionName;
        ImageView indicator;
    }

}