package com.cornelisdemooij.NotesAPI.services;

import com.cornelisdemooij.NotesAPI.model.Note;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class NoteService {

    public Optional<Note> findById(Long id) {
        Note note = new Note();
        note.id = id;
        note.creation = Timestamp.from(Instant.now());
        note.modified = Timestamp.from(Instant.now());
        return Optional.of(note);
    }
}
