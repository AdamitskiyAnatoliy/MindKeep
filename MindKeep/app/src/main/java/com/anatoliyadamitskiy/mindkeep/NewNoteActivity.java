package com.anatoliyadamitskiy.mindkeep;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

/**
 * Created by Anatoliy on 3/31/15.
 */
public class NewNoteActivity extends ActionBarActivity {

    EditText noteTitle, noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteTitle = (EditText) findViewById(R.id.titleTextField);
        noteContent = (EditText) findViewById(R.id.noteTextField);

        FloatingActionButton doneButton = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.ic_action_done))
                .withButtonColor(Color.rgb(255, 193, 7))
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (noteTitle.getText().toString().equals("") || noteContent.getText().toString()
                        .equals("")) {
                    simpleSnackBar("Please Fill Out All Fields.");
                } else {

                    Intent intent = new Intent(MainActivity.NEW_POST);
                    intent.putExtra("title", noteTitle.getText().toString());
                    intent.putExtra("content", noteContent.getText().toString());
                    intent.putExtra("noteType", "text");
                    sendBroadcast(intent);
                    finish();
                }

            }
        });
    }

    public void simpleSnackBar(String text) {
        SnackbarManager.show(
                Snackbar.with(this)
                        .text(text)
                        .textColor(Color.rgb(255, 153, 51))
                        .color(Color.DKGRAY)
                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_done) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
