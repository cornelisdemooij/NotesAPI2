package com.cornelisdemooij.NotesAPI.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String title;
    public String body;
    public Timestamp creation;
    public Timestamp modified;
}
