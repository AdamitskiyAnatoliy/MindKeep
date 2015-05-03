package com.anatoliyadamitskiy.a_adamitskiy_android_usersanddata;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by Anatoliy on 3/31/15.
 */
public class LogInActivity extends ActionBarActivity {

    EditText username, password;

    com.anatoliyadamitskiy.a_adamitskiy_android_usersanddata.Network network =
            new com.anatoliyadamitskiy.a_adamitskiy_android_usersanddata.Network(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Parse.initialize(this, "mgZqRjcCPjoyOfCGv8bmwHENpehZYoSsnvgsMUpe",
                "u6aZbalHSzB79uxXR2AsQmYaZYcANA2n0rUiaxAv");

        username = (EditText) findViewById(R.id.usernameTextField);
        password = (EditText) findViewById(R.id.passwordTextField);

        findViewById(R.id.logInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (network.checkNetwork()) {
                    if (username.getText().toString().equals("") && password.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Fill Out All Fields", Toast.LENGTH_LONG).show();
                    } else if (password.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_LONG).show();
                    } else if (username.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Enter Username", Toast.LENGTH_LONG).show();
                    } else {

                        ParseUser.logInInBackground(username.getText().toString(),
                                password.getText().toString(), new LogInCallback() {
                                    public void done(ParseUser user, ParseException e) {
                                        if (user != null) {
                                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                            SharedPreferences.Editor editor = prefs.edit();
                                            editor.putBoolean("loggedIn", true);
                                            editor.commit();
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Invalid Login, Please Try Again", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Reconnect Network", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (network.checkNetwork()) {
                    Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Reconnect Network", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
