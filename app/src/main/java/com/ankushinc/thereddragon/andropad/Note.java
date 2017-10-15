package com.ankushinc.thereddragon.andropad;

/**
 * Created by Ankush on 8/12/2017.
 */

public class Note {
    private String id,title,note;

    public Note(String id, String title, String note) {
        this.setId(id);
        this.setTitle(title);
        this.setNote(note);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
