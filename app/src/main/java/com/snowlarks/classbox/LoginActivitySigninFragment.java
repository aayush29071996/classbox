package com.snowlarks.classbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.snowlarks.classbox.Helper.AsyncLoginTask;


public class LoginActivitySigninFragment extends Fragment {


    Button signin_button;
    EditText email_id;
    EditText password;

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signin_button = (Button) view.findViewById(R.id.button_signin);
        email_id = (EditText) view.findViewById(R.id.signin_email);
        password = (EditText) view.findViewById(R.id.signin_password);

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncLoginTask Login = new AsyncLoginTask();
                Login.execute("login",email_id.getText().toString(),password.getText().toString());
            }
        });

    }
}
