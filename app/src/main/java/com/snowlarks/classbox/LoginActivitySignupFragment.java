package com.snowlarks.classbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.snowlarks.classbox.Helper.AsyncLoginTask;
import com.snowlarks.classbox.Helper.Extra;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivitySignupFragment extends Fragment {

    private final String LOG_TAG = LoginActivitySignupFragment.class.getSimpleName();

    Button signup_button;
    EditText email_id;
    EditText password;
    EditText confirm_password;

    public static  LoginActivitySignupFragment getInstance(){
        LoginActivitySignupFragment instance = new LoginActivitySignupFragment();
        return instance;
    }

    public LoginActivitySignupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_signup, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signup_button = (Button) view.findViewById(R.id.button_signup);
        email_id = (EditText) view.findViewById(R.id.signup_email);
        password = (EditText) view.findViewById(R.id.signup_password);
        confirm_password = (EditText) view.findViewById(R.id.signup_confirm_password);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncLoginTask Login = new AsyncLoginTask();

                if(Extra.comparePassword(password,confirm_password)){
                    Login.execute("register",
                            email_id.getText().toString(),
                            password.getText().toString());
                }

            }
        });
    }

}
