package com.cornelisdemooij.NotesAPI.repositories;

import com.cornelisdemooij.NotesAPI.model.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
    public List<Note> findByTitle(String title);
}
