package com.snowlarks.classbox.Helper;

import android.widget.EditText;

/**
 * Created by Saswat on 12-08-2015.
 */
public class Extra {

    public static boolean comparePassword(EditText p, EditText cp){
        String password = p.getText().toString();
        String confirm_password = cp.getText().toString();
        password = password.trim();
        confirm_password = confirm_password.trim();

        boolean send = (password.equals(confirm_password)) && !password.isEmpty();

        return send;
    }
}
