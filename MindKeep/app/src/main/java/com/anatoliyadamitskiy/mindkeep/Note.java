package com.anatoliyadamitskiy.mindkeep;

import java.io.Serializable;

/**
 * Created by Anatoliy on 3/31/15.
 */
public class Note implements Serializable {

    private String note;
    private String title;
    private String noteType;
    private byte[] image;
    private int timeToComplete;

    public Note (String _note, String _title, String _type, byte[] _image, int _completion) {
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

    public byte[] getImage() {
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
