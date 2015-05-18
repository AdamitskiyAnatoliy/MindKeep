package com.anatoliyadamitskiy.mindkeep;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.dexafree.materialList.cards.BasicListCard;
import com.dexafree.materialList.cards.BigImageCard;
import com.dexafree.materialList.controller.OnDismissCallback;
import com.dexafree.materialList.controller.RecyclerItemClickListener;
import com.dexafree.materialList.model.Card;
import com.dexafree.materialList.model.CardItemView;
import com.dexafree.materialList.view.MaterialListView;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements Serializable {

    MaterialListView notesList;
    ArrayList<Note> notesArrayList;
    ArrayList<ParseObject> parseArrayList;
    ParseObject parseNote;
    com.getbase.floatingactionbutton.FloatingActionsMenu addNoteMenu;
    int listPosition;
    public static final String DELETE_POST = "com.anatoliyadamitskiy.mindkeep.DELETE_POST";
    public static final String UPDATE_POST = "com.anatoliyadamitskiy.mindkeep.UPDATE_POST";
    public static final String NEW_POST = "com.anatoliyadamitskiy.mindkeep.NEW_POST";

    Handler mHandler = new Handler();
    com.anatoliyadamitskiy.mindkeep.Network network =
            new com.anatoliyadamitskiy.mindkeep.Network(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        setTitle(" MindKeep");

        if (network.checkNetwork()) {
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "mgZqRjcCPjoyOfCGv8bmwHENpehZYoSsnvgsMUpe",
                    "u6aZbalHSzB79uxXR2AsQmYaZYcANA2n0rUiaxAv");
        } else {
            simpleSnackBar("Please Reconnect Network");
        }

        addNoteMenu = (FloatingActionsMenu) findViewById(R.id.addNoteMenu);
        notesList = (MaterialListView) findViewById(R.id.noteListView);
        parseArrayList = new ArrayList<>();

        final View darkBack = (View) findViewById(R.id.blackBackground);
        com.getbase.floatingactionbutton.FloatingActionsMenu addMenu =
                (com.getbase.floatingactionbutton.FloatingActionsMenu) findViewById(R.id.addNoteMenu);
        addNoteMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                darkBack.setVisibility(View.VISIBLE);
            }
            @Override
            public void onMenuCollapsed() {
                darkBack.setVisibility(View.INVISIBLE);
            }
        });

        darkBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNoteMenu.collapse();
                darkBack.setVisibility(View.INVISIBLE);
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton newNoteButton =
                (com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.newNoteButton);
        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (network.checkNetwork()) {
                    Intent intent = new Intent(getApplicationContext(), NewNoteActivity.class);
                    startActivity(intent);
                    addNoteMenu.collapse();
                } else {
                    addNoteMenu.collapse();
                    simpleSnackBar("Please Reconnect Network");
                }
            }
        });

//        com.getbase.floatingactionbutton.FloatingActionButton newListNoteButton =
//                (com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.newListNoteButton);
//        newListNoteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (network.checkNetwork()) {
//                    Intent intent = new Intent(getApplicationContext(), NewListNoteActivity.class);
//                    startActivity(intent);
//                    addNoteMenu.collapse();
//                } else {
//                    addNoteMenu.collapse();
//                    simpleSnackBar("Please Reconnect Network");
//                }
//            }
//        });

        com.getbase.floatingactionbutton.FloatingActionButton newImageNoteButton =
                (com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.newImageNoteButton);
        newImageNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (network.checkNetwork()) {
                    Intent intent = new Intent(getApplicationContext(), NewImageNoteActivity.class);
                    startActivity(intent);
                    addNoteMenu.collapse();
                } else {
                    addNoteMenu.collapse();
                    simpleSnackBar("Please Reconnect Network");
                }
            }
        });

        notesList.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(CardItemView view, int position) {

                if (notesArrayList.get(position).getNoteType().equals("text")) {

                    listPosition = position;
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtra("Note", notesArrayList.get(position));
                    startActivity(intent);
                    addNoteMenu.collapse();
                } else if (notesArrayList.get(position).getNoteType().equals("image")) {

                    listPosition = position;
                    Intent intent = new Intent(getApplicationContext(), ImageDetailActivity.class);
                    //intent.putExtra("Note", notesArrayList.get(position));
                    //intent.putExtra("Note", notesArrayList.get(position));
                    intent.putExtra("title", notesArrayList.get(position).getTitle());
                    intent.putExtra("content", notesArrayList.get(position).getNote());
                    startActivity(intent);
                    addNoteMenu.collapse();
                }
            }

            @Override
            public void onItemLongClick(CardItemView view, int position) {

            }
        });

        notesList.setOnDismissCallback(new OnDismissCallback() {
            @Override
            public void onDismiss(Card card, int i) {
                if (network.checkNetwork()) {
                    //parseArrayList.get(i).deleteInBackground();
                    listPosition = i;
                    Intent intent = new Intent(DELETE_POST);
                    sendBroadcast(intent);

                }
            }
        });
    }

//    public Runnable updater = new Runnable() {
//        @Override
//        public void run() {
//            refreshNoteList();
//            mHandler.postDelayed(updater,900000);
//        }
//    };
//
//    void startRepeating()
//    {
//        updater.run();
//    }
//
//    void stopRepeating()
//    {
//        mHandler.removeCallbacks(updater);
//    }

    @Override
    protected void onResume() {
        super.onResume();

        if (network.checkNetwork()) {
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
//                stopRepeating();
//                startRepeating();
            } else {
                Intent intent = new Intent(this, LogInActivity.class);
                startActivity(intent);
            }
        } else {
            // No Network, Pull from Local

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            if (prefs.getBoolean("loggedIn", false) == false) {
                Intent intent = new Intent(this, LogInActivity.class);
                startActivity(intent);
            }
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(DELETE_POST);
        filter.addAction(UPDATE_POST);
        filter.addAction(NEW_POST);
        this.registerReceiver(broadcastReceiver, filter);

        notesArrayList = new ArrayList<>();
        refreshNoteList();

    }

    private void fillArray() {
        notesList.clear();
        for (int i = 0; i < parseArrayList.size(); i++) {
            Card card = addCards(i);
            notesList.add(card);
        }
    }

    private Card addCards(int i){

        if (notesArrayList.get(i).getNoteType().equals("text")) {

            VerySimpleCard card = new VerySimpleCard(this);
            card.setTitle(notesArrayList.get(i).getTitle());
            card.setDescription(notesArrayList.get(i).getNote());
            card.setTag("VERY_BASIC_CARD");
            card.setDismissible(false);
            return card;

        } else if (notesArrayList.get(i).getNoteType().equals("list")) {

            BasicListCard card = new BasicListCard(this);
            card.setDividerVisible(true);
            card.setTitle(notesArrayList.get(i).getTitle());
            card.setDescription(notesArrayList.get(i).getNote());
            BasicListAdapter adapter = new BasicListAdapter(this);
            adapter.add("Text1");
            adapter.add("Text2");
            adapter.add("Text3");
            card.setTag("LIST_CARD");
            ((BasicListCard) card).setAdapter(adapter);
            ((BasicListCard) card).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Do what ever you want...
                    //Toast.makeText(getApplicationContext(), position, Toast.LENGTH_SHORT).show();
                    //boolean checked = card.isItemChecked(card.getItems().get(position));
                    //card.setItemChecked(position, !checked);
                }
            });
            card.setDismissible(false);

            return card;

        } else if (notesArrayList.get(i).getNoteType().equals("image")) {

            final BigImageCard card = new BigImageCard(this);
            card.setTitle(notesArrayList.get(i).getTitle());
            card.setDescription(notesArrayList.get(i).getNote());
            byte[] image = notesArrayList.get(i).getImage();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap takenImage = BitmapFactory.decodeByteArray(image, 0, image.length, options);

            Drawable d = new BitmapDrawable(getResources(), takenImage);

            card.setDrawable(d);

            card.setTag("BIG_IMAGE_CARD");
            card.setDismissible(false);

            return card;
        }
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void refreshNoteList() {

        if (network.checkNetwork()) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
            query.findInBackground(new FindCallback<ParseObject>() {

                @Override
                public void done(List<ParseObject> postList, ParseException e) {
                    if (e == null) {
                        notesArrayList.clear();
                        parseArrayList.clear();
                        for (ParseObject post : postList) {
                            parseNote = post;
                            parseArrayList.add(post);
                            Note note = null;
                            if (post.getString("noteType").equals("text")) {
                                note = new Note(post.getString("content"), post.getString("title"),
                                        post.getString("noteType"), null, Integer.parseInt(post.getString("hours")));
                            } else if (post.getString("noteType").equals("list")) {
                                note = new Note(post.getString("content"), post.getString("title"),
                                        post.getString("noteType"), null, Integer.parseInt(post.getString("hours")));
                            } else if (post.getString("noteType").equals("image")) {
                                try {
                                    note = new Note(post.getString("content"), post.getString("title"),
                                            post.getString("noteType"), post.getParseFile("image").getData(), Integer.parseInt(post.getString("hours")));
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            notesArrayList.add(note);
                        }
                        fillArray();
                    } else {
                        Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                    }
                }
            });
        } else {
            // Refresh Note List when no Network

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //stopRepeating();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {

            if (intent.getAction().equals(DELETE_POST)) {
                parseArrayList.get(listPosition).deleteInBackground();
                refreshNoteList();
            } else if (intent.getAction().equals(UPDATE_POST)) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
                query.getInBackground(parseArrayList.get(listPosition).getObjectId(), new GetCallback<ParseObject>() {
                    public void done(ParseObject note, ParseException e) {
                        if (e == null) {
                            note.put("title", intent.getStringExtra("title"));
                            note.put("hours", intent.getStringExtra("hours"));
                            note.put("content", intent.getStringExtra("content"));
                            note.saveInBackground();
                        }
                    }
                });

                simpleSnackBar("Updated Successfully");
                refreshNoteList();
            } else if (intent.getAction().equals(NEW_POST)) {

                if (intent.getStringExtra("noteType").equals("text")) {

                    ParseObject note = new ParseObject("Note");
                    note.put("title", intent.getStringExtra("title"));
                    note.put("content", intent.getStringExtra("content"));
                    note.put("hours", "0");
                    note.put("noteType", "text");
                    note.setACL(new ParseACL(ParseUser.getCurrentUser()));
                    note.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                simpleSnackBar("Saved Successfully");
                            } else {
                                simpleSnackBar("Save Failure");
                            }
                        }
                    });

                    refreshNoteList();
                } else if (intent.getStringExtra("noteType").equals("image")) {

                    if (intent.getStringExtra("completed").equals("yes")) {
                        simpleSnackBar("Saved Successfully");
                    } else {
                        simpleSnackBar("Save Failure");
                    }

                    refreshNoteList();
                } else if (intent.getStringExtra("noteType").equals("list")) {


                }
            }
            //refreshNoteList();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {

            if (network.checkNetwork()) {
                //stopRepeating();

                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();

                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
            } else {
                // Manually log user out

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("loggedIn", false);
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
            }
            return true;
        } else if (id == R.id.action_refresh) {
            if (network.checkNetwork()) {
                fillArray();
                refreshNoteList();
            } else {
                simpleSnackBar("Please Reconnect Network");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void simpleSnackBar(String text) {
        SnackbarManager.show(
                Snackbar.with(this)
                        .text(text)
                        .textColor(Color.rgb(255, 153, 51))
                        .color(Color.DKGRAY)
                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT));
    }
}
