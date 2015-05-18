package com.anatoliyadamitskiy.mindkeep;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.EventListener;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;



/**
 * Created by Anatoliy on 3/31/15.
 */

public class SignUpActivity extends ActionBarActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
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

        findViewById(R.id.cancelFormButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.signUpFormButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass1 = password.getText().toString();
                String pass2 = passConfirm.getText().toString();

                if (pass1.equals(pass2)) {

                    showSnackBar("Checking Username Availability", Snackbar.SnackbarDuration.LENGTH_INDEFINITE);
                    ParseUser user = new ParseUser();
                    user.setUsername(username.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {

                                SnackbarManager.show(
                                        Snackbar.with(SignUpActivity.this)
                                                .text("Sign Up Successful")
                                                .color(Color.DKGRAY)
                                                .textColor(Color.rgb(255, 153, 51))
                                                .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                                                .eventListener(new EventListener() {
                                                    @Override
                                                    public void onShow(Snackbar snackbar) {
                                                        Log.i(TAG, String.format(
                                                                "Snackbar will show. Width: %d Height: %d Offset: %d",
                                                                snackbar.getWidth(), snackbar.getHeight(),
                                                                snackbar.getOffset()));
                                                    }

                                                    @Override
                                                    public void onShowByReplace(Snackbar snackbar) {
                                                        Log.i(TAG, String.format(
                                                                "Snackbar will show by replace. Width: %d Height: %d Offset: %d",
                                                                snackbar.getWidth(), snackbar.getHeight(),
                                                                snackbar.getOffset()));
                                                    }

                                                    @Override
                                                    public void onShown(Snackbar snackbar) {
                                                        Log.i(TAG, String.format(
                                                                "Snackbar shown. Width: %d Height: %d Offset: %d",
                                                                snackbar.getWidth(), snackbar.getHeight(),
                                                                snackbar.getOffset()));
                                                    }

                                                    @Override
                                                    public void onDismiss(Snackbar snackbar) {
                                                        Log.i(TAG, String.format(
                                                                "Snackbar will dismiss. Width: %d Height: %d Offset: %d",
                                                                snackbar.getWidth(), snackbar.getHeight(),
                                                                snackbar.getOffset()));
                                                    }

                                                    @Override
                                                    public void onDismissByReplace(Snackbar snackbar) {
                                                        Log.i(TAG, String.format(
                                                                "Snackbar will dismiss by replace. Width: %d Height: %d Offset: %d",
                                                                snackbar.getWidth(), snackbar.getHeight(),
                                                                snackbar.getOffset()));
                                                        SnackbarManager.dismiss();
                                                    }

                                                    @Override
                                                    public void onDismissed(Snackbar snackbar) {

                                                        Intent intent = new Intent();
                                                        intent.putExtra("username", username.getText().toString());
                                                        intent.putExtra("password", username.getText().toString());
                                                        setResult(RESULT_OK, intent);
                                                        finish();
                                                    }
                                                }));
                            } else {
                                showSnackBar("Username Not Available", Snackbar.SnackbarDuration.LENGTH_LONG);
                            }
                        }
                    });

                } else {
                    showSnackBar("Passwords Do Not Match", Snackbar.SnackbarDuration.LENGTH_SHORT);
                    password.setText("");
                    passConfirm.setText("");
                }
            }
        });

        ScrollView scrollView = (ScrollView) findViewById(R.id.signUpScroll);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

    }

    public void showSnackBar (String message, Snackbar.SnackbarDuration duration) {

        SnackbarManager.show(
                Snackbar.with(SignUpActivity.this)
                        .text(message)
                        .color(Color.DKGRAY)
                        .textColor(Color.rgb(255, 153, 51))
                        .duration(duration)
                        .eventListener(new EventListener() {
                            @Override
                            public void onShow(Snackbar snackbar) {
                                Log.i(TAG, String.format(
                                        "Snackbar will show. Width: %d Height: %d Offset: %d",
                                        snackbar.getWidth(), snackbar.getHeight(),
                                        snackbar.getOffset()));
                            }

                            @Override
                            public void onShowByReplace(Snackbar snackbar) {
                                Log.i(TAG, String.format(
                                        "Snackbar will show by replace. Width: %d Height: %d Offset: %d",
                                        snackbar.getWidth(), snackbar.getHeight(),
                                        snackbar.getOffset()));
                            }

                            @Override
                            public void onShown(Snackbar snackbar) {
                                Log.i(TAG, String.format(
                                        "Snackbar shown. Width: %d Height: %d Offset: %d",
                                        snackbar.getWidth(), snackbar.getHeight(),
                                        snackbar.getOffset()));
                            }

                            @Override
                            public void onDismiss(Snackbar snackbar) {
                                Log.i(TAG, String.format(
                                        "Snackbar will dismiss. Width: %d Height: %d Offset: %d",
                                        snackbar.getWidth(), snackbar.getHeight(),
                                        snackbar.getOffset()));
                            }

                            @Override
                            public void onDismissByReplace(Snackbar snackbar) {
                                Log.i(TAG, String.format(
                                        "Snackbar will dismiss by replace. Width: %d Height: %d Offset: %d",
                                        snackbar.getWidth(), snackbar.getHeight(),
                                        snackbar.getOffset()));
                            }

                            @Override
                            public void onDismissed(Snackbar snackbar) {

                            }
                        }));

    }

}
