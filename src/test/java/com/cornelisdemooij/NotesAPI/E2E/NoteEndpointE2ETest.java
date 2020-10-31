package com.cornelisdemooij.NotesAPI.E2E;

import com.cornelisdemooij.NotesAPI.endpoints.NoteEndpoint;
import com.cornelisdemooij.NotesAPI.model.Note;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NoteEndpointE2ETest {
    @Autowired
    private NoteEndpoint noteEndpoint;

    @Test
    public void postNoteSuccessTest() {
        // Given:
        Note noteToPost = new Note();
        noteToPost.title = "Test title";
        noteToPost.body = "Test body";
        Timestamp now = Timestamp.from(Instant.now());

        // When:
        Response responsePostedNote = noteEndpoint.postNote(noteToPost);
        Note postedNote = (Note)responsePostedNote.getEntity();

        // Then:
        assertThat(postedNote.title).isEqualTo(noteToPost.title);
        assertThat(postedNote.body).isEqualTo(noteToPost.body);
        assertThat(postedNote.id).isNotNull();
        assertThat(postedNote.creation).isCloseTo(now, 1000);
        assertThat(postedNote.modified).isCloseTo(now, 1000);
    }

    @Test
    public void postNoteFailureTest() {
        // Given:
        Note noteToPost = new Note();
        noteToPost.title = "Test title that is too big: ............................................................." +
                "...................................................................................................." +
                ".....................................................................................................";
        noteToPost.body = "Test body";

        // When:
        Response responsePostedNote = noteEndpoint.postNote(noteToPost);

        // Then:
        assertThat(responsePostedNote.getStatus()).isEqualTo(500);
    }
}
