package com.cornelisdemooij.NotesAPI.model;

import java.sql.Timestamp;

public class Note {
    public long id;

    public String title;
    public String body;
    public Timestamp creation;
    public Timestamp modified;
}
