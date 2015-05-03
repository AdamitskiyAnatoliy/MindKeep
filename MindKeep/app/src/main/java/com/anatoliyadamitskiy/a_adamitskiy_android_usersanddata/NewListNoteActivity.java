package com.anatoliyadamitskiy.a_adamitskiy_android_usersanddata;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Anatoliy on 4/26/15.
 */
public class NewListNoteActivity extends ActionBarActivity {

    Note noteObject;
    EditText noteTitle, noteContent, noteHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list_note);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteTitle = (EditText) findViewById(R.id.titleTextFieldList);
        noteContent = (EditText) findViewById(R.id.noteTextFieldList);
        noteHours = (EditText) findViewById(R.id.hoursTextFieldList);

        FloatingActionButton doneButton = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.ic_action_done))
                .withButtonColor(Color.rgb(0, 255, 0))
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (noteTitle.getText().toString().equals("") || noteContent.getText().toString()
                        .equals("") || noteHours.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Fill Out All Fields", Toast.LENGTH_LONG).show();
                } else {

                    ParseObject note = new ParseObject("Note");
                    note.put("title", noteTitle.getText().toString());
                    note.put("content", noteContent.getText().toString());
                    note.put("hours", noteHours.getText().toString());
                    note.put("noteType", "list");
                    note.setACL(new ParseACL(ParseUser.getCurrentUser()));
                    note.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                finish();
                                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                                Log.d(getClass().getSimpleName(), "User update error: " + e);
                            }
                        }
                    });
                }

            }
        });
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
