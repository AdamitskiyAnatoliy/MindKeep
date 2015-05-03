package com.anatoliyadamitskiy.a_adamitskiy_android_usersanddata;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by Anatoliy on 3/31/15.
 */
public class SignUpActivity extends ActionBarActivity {

    EditText username, password, passConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Parse.initialize(this, "mgZqRjcCPjoyOfCGv8bmwHENpehZYoSsnvgsMUpe",
                "u6aZbalHSzB79uxXR2AsQmYaZYcANA2n0rUiaxAv");

        username = (EditText) findViewById(R.id.usernameTextFieldForm);
        password = (EditText) findViewById(R.id.passwordTextFieldForm);
        passConfirm = (EditText) findViewById(R.id.verifyPasswordTextFieldForm);

        findViewById(R.id.signUpFormButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass1 = password.getText().toString();
                String pass2 = passConfirm.getText().toString();

                if (pass1.equals(pass2)) {
                    ParseUser user = new ParseUser();
                    user.setUsername(username.getText().toString());
                    user.setPassword(password.getText().toString());

                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                finish();

                            } else {
                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    password.setText("");
                    passConfirm.setText("");
                }
            }
        });

    }


}
