package com.example.basaile92.listelivre.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;

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


    // Return view to obtain a view of book list
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //todo
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
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


}