package com.cornelisdemooij.NotesAPI.unit;

import static org.assertj.core.api.Assertions.assertThat;

import com.cornelisdemooij.NotesAPI.model.Note;
import com.cornelisdemooij.NotesAPI.services.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;

@SpringBootTest
public class NoteServiceUnitTest {
    @Autowired
    private NoteService noteService;

    @Test
    public void contextLoads() throws Exception {
        assertThat(noteService).isNotNull();
    }

    @Test
    public void saveHelperTest() {
        // Given:
        Note note = new Note();
        note.title = "Test title";
        note.body = "Test body";
        note.id = 100;
        Timestamp now = Timestamp.from(Instant.now());

        // When:
        Note newNote = noteService.saveHelper(note);

        // Then:
        assertThat(newNote.title).isEqualTo(note.title);
        assertThat(newNote.body).isEqualTo(note.body);
        assertThat(newNote.id).isEqualTo(note.id);
        assertThat(newNote.creation).isCloseTo(now, 1000);
        assertThat(newNote.modified).isCloseTo(now, 1000);
    }

    @Test
    public void updateByIDHelperTest() {
        // Given:
        Note oldNote = new Note();
        oldNote.title = "Test title";
        oldNote.body = "Test body";
        oldNote.id = 101;
        oldNote.creation = Timestamp.from(Instant.now().minusSeconds(3600));
        oldNote.modified = oldNote.creation;

        Note newNote = new Note();
        newNote.title = "New title";
        newNote.body = "New body";

        Timestamp now = Timestamp.from(Instant.now());

        // When:
        Note newNewNote = noteService.updateByIdHelper(oldNote, newNote);

        // Then:
        assertThat(newNewNote.title).isEqualTo(newNote.title);
        assertThat(newNewNote.body).isEqualTo(newNote.body);
        assertThat(newNewNote.id).isEqualTo(oldNote.id);
        assertThat(newNewNote.creation).isCloseTo(oldNote.creation, 1000);
        assertThat(newNewNote.modified).isCloseTo(now, 1000);
    }
}
