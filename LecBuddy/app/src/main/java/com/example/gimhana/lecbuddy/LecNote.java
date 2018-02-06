package com.example.gimhana.lecbuddy;

/**
 * Created by Gimhana on 11/5/2017.
 */

public class LecNote {

    private String image;
    private String moduleDiscription;
    private String notedate;

    public LecNote(String image, String moduleDiscription, String date) {
        this.setLecNoteImage(image);
        this.setNoteDiscription(moduleDiscription);
        this.setNoteDate(date);
    }


    public String getLecNoteImage() {
        return image;
    }

    public void setLecNoteImage(String image) {
        this.image = image;
    }

    public String getLecNoteDiscription() {
        return moduleDiscription;
    }

    public void setNoteDiscription(String moduleDiscription) {
        this.moduleDiscription = moduleDiscription;
    }

    public String getNoteDate() {
        return notedate;
    }

    public void setNoteDate(String date) {
        this.notedate = date;
    }
}
