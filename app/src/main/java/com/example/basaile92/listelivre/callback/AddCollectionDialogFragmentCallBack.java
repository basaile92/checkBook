package com.example.basaile92.listelivre.callback;

import com.example.basaile92.listelivre.fragment.AddCollectionDialogFragment;

/**
 * Created by basaile92 on 10/12/2016.
 */
public interface AddCollectionDialogFragmentCallBack {

    void onDialogPositiveClick(AddCollectionDialogFragment addCollectionDialogFragment, String nameEditForm);


    void onDialogNegativeClick(AddCollectionDialogFragment addCollectionDialogFragment);

}
