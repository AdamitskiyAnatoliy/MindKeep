package com.anatoliyadamitskiy.mindkeep;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by Anatoliy on 5/3/15.
 */

public class LogInFragment extends Fragment {

    EditText username, password;
    com.anatoliyadamitskiy.mindkeep.Network network;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_login, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Parse.initialize(getActivity(), "mgZqRjcCPjoyOfCGv8bmwHENpehZYoSsnvgsMUpe",
                "u6aZbalHSzB79uxXR2AsQmYaZYcANA2n0rUiaxAv");

        network = new com.anatoliyadamitskiy.mindkeep.Network(getActivity());
        username = (EditText) getView().findViewById(R.id.usernameTextField);
        password = (EditText) getView().findViewById(R.id.passwordTextField);

        getView().findViewById(R.id.logInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (network.checkNetwork()) {
                    if (username.getText().toString().equals("") && password.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Please Fill Out All Fields", Toast.LENGTH_LONG).show();
                    } else if (password.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_LONG).show();
                    } else if (username.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Please Enter Username", Toast.LENGTH_LONG).show();
                    } else {

                        ParseUser.logInInBackground(username.getText().toString(),
                                password.getText().toString(), new LogInCallback() {
                                    public void done(ParseUser user, ParseException e) {
                                        if (user != null) {
                                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                            SharedPreferences.Editor editor = prefs.edit();
                                            editor.putBoolean("loggedIn", true);
                                            editor.commit();
                                            getActivity().finish();
                                        } else {
                                            Toast.makeText(getActivity(), "Invalid Login, Please Try Again", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                } else {
                    Toast.makeText(getActivity(), "Please Reconnect Network", Toast.LENGTH_LONG).show();
                }
            }
        });

        getView().findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (network.checkNetwork()) {
                    Intent intent = new Intent(getActivity(), SignUpActivity.class);
                    startActivityForResult(intent, 0);
                } else {
                    Toast.makeText(getActivity(), "Please Reconnect Network", Toast.LENGTH_LONG).show();
                }

            }
        });

        ScrollView scrollView = (ScrollView) getView().findViewById(R.id.logInScroll);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == SignUpActivity.RESULT_OK) {
            String user = data.getStringExtra("username");
            String pass = data.getStringExtra("password");
            ParseUser.logInInBackground(user, pass, new LogInCallback() {
                public void done(ParseUser user, ParseException e) {

                }
            });
            getActivity().finish();
        }
    }

}
