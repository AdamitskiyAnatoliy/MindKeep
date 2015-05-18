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
import android.widget.Toast;

/**
 * Created by Anatoliy on 3/31/15.
 */
public class DetailActivity extends ActionBarActivity {

    EditText title, content;
    String mainTitle;
    Note note;

    Network network = new Network(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (EditText) findViewById(R.id.titleTextFieldDetail);
        content = (EditText) findViewById(R.id.noteTextFieldDetail);

        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("Note");

        title.setText(note.getTitle());
        content.setText(note.getNote());
        mainTitle = note.getTitle();
        setTitle(mainTitle);

        FloatingActionButton updateButton = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.ic_action_done))
                .withButtonColor(Color.rgb(255, 153, 51))
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (network.checkNetwork()) {
                    Intent intent = new Intent(MainActivity.UPDATE_POST);
                    intent.putExtra("title", title.getText().toString());
                    intent.putExtra("content", content.getText().toString());
                    intent.putExtra("hours", "0");
                    getApplicationContext().sendBroadcast(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Reconnect Network", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {

            if (network.checkNetwork()) {
                Intent intent = new Intent(MainActivity.DELETE_POST);
                getApplicationContext().sendBroadcast(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Please Reconnect Network", Toast.LENGTH_LONG).show();
            }

            return true;
        } else if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
