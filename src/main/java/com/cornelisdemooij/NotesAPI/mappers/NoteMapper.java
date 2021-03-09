package com.cornelisdemooij.NotesAPI.mappers;

import com.cornelisdemooij.NotesAPI.model.Note;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteMapper implements RowMapper<Note> {
    @Override
    public Note mapRow(ResultSet resultSet, int i) throws SQLException {
        Note note = new Note();

        note.id = resultSet.getLong("id");

        note.title = resultSet.getString("title");
        note.body = resultSet.getString("body");
        note.creation = resultSet.getTimestamp("creation");
        note.modified = resultSet.getTimestamp("modified");

        return note;
    }
}
