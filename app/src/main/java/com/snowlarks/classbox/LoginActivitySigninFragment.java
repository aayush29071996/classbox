package com.snowlarks.classbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class LoginActivitySigninFragment extends Fragment {


    public static LoginActivitySigninFragment getInstance(){
        LoginActivitySigninFragment instance = new LoginActivitySigninFragment();
        return instance;
    }

    public LoginActivitySigninFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_signin, container, false);
    }


}
