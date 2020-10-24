package com.cornelisdemooij.NotesAPI.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    public long id;

    public String title;
    public String body;
    public Timestamp creation;
    public Timestamp modified;
}
