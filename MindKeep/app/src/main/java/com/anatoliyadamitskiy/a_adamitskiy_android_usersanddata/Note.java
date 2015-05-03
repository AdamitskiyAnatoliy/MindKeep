package com.anatoliyadamitskiy.a_adamitskiy_android_usersanddata;

import com.parse.ParseFile;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Anatoliy on 3/31/15.
 */
public class Note implements Serializable {

    private String note;
    private String title;
    private String noteType;
    private ParseFile image;
    private int timeToComplete;

    public Note (String _note, String _title, String _type, ParseFile _image, int _completion) {
        note = _note;
        title = _title;
        noteType = _type;
        image = _image;
        timeToComplete = _completion;
    }

    public String getNote() {
        return note;
    }

    public String getTitle() {
        return title;
    }

    public String getNoteType() {
        return noteType;
    }

    public ParseFile getImage() {
        return image;
    }

    public int getTimeToComplete() {
        return timeToComplete;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
