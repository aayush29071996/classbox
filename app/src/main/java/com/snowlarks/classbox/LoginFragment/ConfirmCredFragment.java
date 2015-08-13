package com.snowlarks.classbox.LoginFragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snowlarks.classbox.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmCredFragment extends Fragment {


    public ConfirmCredFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_cred, container, false);
    }


}
