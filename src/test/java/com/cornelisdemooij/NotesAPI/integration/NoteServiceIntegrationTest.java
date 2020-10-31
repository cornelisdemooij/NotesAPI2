package com.cornelisdemooij.NotesAPI.integration;

import com.cornelisdemooij.NotesAPI.model.Note;
import com.cornelisdemooij.NotesAPI.repositories.NoteRepository;
import com.cornelisdemooij.NotesAPI.services.NoteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class NoteServiceIntegrationTest {
    @Autowired
    private NoteService noteService;

    @MockBean
    private NoteRepository noteRepository;

    @Test
    public void saveTest() {
        // Set up mock response:
        Note mockNote = new Note();
        mockNote.title = "Mock note title";
        Mockito.when(noteRepository.save(Mockito.any(Note.class))).thenReturn(mockNote);

        // Given:
        Note noteToSave = new Note();
        noteToSave.title = "Doesn't matter";

        // When:
        Note savedNote = noteService.save(noteToSave);

        // Then:
        assertThat(savedNote.title).isEqualTo(mockNote.title);
        Mockito.verify(noteRepository, Mockito.times(1))
                .save(Mockito.any(Note.class));
    }

    @Test
    public void updateByIdThatExistsTest() {
        // Set up mock response:
        Note mockNote1 = new Note();
        mockNote1.title = "Mock note 1 title";
        Mockito.when(noteRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockNote1));

        Note mockNote2 = new Note();
        mockNote2.title = "Mock note 2 title";
        Mockito.when(noteRepository.save(Mockito.any(Note.class))).thenReturn(mockNote2);

        // Given:
        Note noteToUpdate = new Note();
        noteToUpdate.title = "Doesn't matter";

        // When:
        Optional<Note> optionalSavedNote = null;
        try {
            optionalSavedNote = noteService.updateById((long) 999, noteToUpdate);
        } catch(Exception e) {
            fail("No exception should be thrown by noteService.updateById in updateByIdThatExistsTest");
        }

        // Then:
        assertThat(optionalSavedNote).isNotNull();
        assertThat(optionalSavedNote.isPresent()).isTrue();
        Note savedNote = optionalSavedNote.get();
        assertThat(savedNote.title).isEqualTo(mockNote2.title);
        Mockito.verify(noteRepository, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(noteRepository, Mockito.times(1))
                .save(Mockito.any(Note.class));
    }

    @Test
    public void updateByIdThatDoesNotExistTest() {
        // Set up mock response:
        Mockito.when(noteRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // Given:
        Note noteToUpdate = new Note();
        noteToUpdate.title = "Doesn't matter";

        // When & Then:
        assertThatThrownBy(() -> {
            noteService.updateById((long) 999, noteToUpdate);   // When.
        }).isInstanceOf(Exception.class)
          .hasMessage("Error: tried to update a note that does not exist.");

        Mockito.verify(noteRepository, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(noteRepository, Mockito.times(0))
                .save(Mockito.any(Note.class));
    }
}
