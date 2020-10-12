package com.cornelisdemooij.NotesAPI.services;

import com.cornelisdemooij.NotesAPI.model.Note;
import com.cornelisdemooij.NotesAPI.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public Note save(Note note) {
        note.creation = Timestamp.from(Instant.now());
        note.modified = Timestamp.from(Instant.now());
        return noteRepository.save(note);
    }

    public Optional<Note> findById(Long id) {
        return noteRepository.findById(id);
    }
}
