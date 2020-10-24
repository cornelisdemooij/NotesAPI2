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

    public Iterable<Note> findByTitle(String title) { return noteRepository.findByTitle(title); }
    public Iterable<Note> findByBody(String body) { return noteRepository.findByBody(body); }
    public Iterable<Note> findByTitleAndBody(String title, String body) { return noteRepository.findByTitleAndBody(title, body); }

    public Iterable<Note> findAll() {
        return noteRepository.findAll();
    }

    public Optional<Note> updateById(Long id, Note newNote) throws Exception {
        Optional<Note> optionalOldNote = findById(id);
        if (optionalOldNote.isPresent()) {
            Note oldNote = optionalOldNote.get();
            newNote.id = oldNote.id;
            newNote.creation = oldNote.creation;
            newNote.modified = Timestamp.from(Instant.now());
            return Optional.of(noteRepository.save(newNote));
        } else {
            throw new Exception("Error: tried to update a note that does not exist.");
        }
    }

    public void deleteById(Long id) {
        noteRepository.deleteById(id);
    }
}
