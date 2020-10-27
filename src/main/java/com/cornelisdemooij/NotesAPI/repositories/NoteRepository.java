package com.cornelisdemooij.NotesAPI.repositories;

import com.cornelisdemooij.NotesAPI.model.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
    @Query("SELECT n FROM Note n WHERE n.title LIKE %?1%")
    public List<Note> findByTitle(String title);

    @Query("SELECT n FROM Note n WHERE n.body LIKE %?1%")
    public List<Note> findByBody(String body);

    @Query("SELECT n FROM Note n WHERE n.title LIKE %?1% AND n.body LIKE %?2%")
    public List<Note> findByTitleAndBody(String title, String body);

    public List<Note> findByCreationAfter(Timestamp creationEarliest);
}
