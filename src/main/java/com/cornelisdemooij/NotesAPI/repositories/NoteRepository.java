package com.cornelisdemooij.NotesAPI.repositories;

import com.cornelisdemooij.NotesAPI.mappers.NoteMapper;
import com.cornelisdemooij.NotesAPI.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class NoteRepository {
    private JdbcTemplate jdbcTemplate;

    private final String SQL_INSERT = "INSERT INTO note(title, body, creation, modified) VALUES (?,?,?,?)";
    private final String SQL_FIND = "SELECT * FROM note WHERE id = ?";
    private final String SQL_FIND_ALL = "SELECT * FROM note";
    private final String SQL_UPDATE = "UPDATE note SET title = ?, body = ?, creation = ?, modified = ? WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM `groups` WHERE id = ?";

    private final String SQL_FIND_BY_TITLE = "SELECT * FROM note WHERE title LIKE ?";
    private final String SQL_FIND_BY_BODY = "SELECT * FROM note WHERE body LIKE ?";
    private final String SQL_FIND_BY_TITLE_AND_BODY = "SELECT * FROM note WHERE title LIKE ? AND body LIKE ?";

    private final String SQL_FIND_BY_CREATION_AFTER = "SELECT * FROM note WHERE creation > ?";
    private final String SQL_FIND_BY_CREATION_BEFORE = "SELECT * FROM note WHERE creation < ?";
    private final String SQL_FIND_BY_CREATION_BETWEEN = "SELECT * FROM note WHERE creation > ? AND creation < ?";

    @Autowired
    public NoteRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public boolean save(Note note) {
        return jdbcTemplate.update(SQL_INSERT, note.title, note.body, note.creation, note.modified) > 0;
    }
    public Optional<Note> findById(long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND, new Object[] { id }, new NoteMapper()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    public List<Note> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new NoteMapper());
    }
    public boolean update(Note note) {
        return jdbcTemplate.update(SQL_UPDATE, note.title, note.body, note.creation, note.modified, note.id) > 0;
    }
    public boolean delete(Note note) {
        return jdbcTemplate.update(SQL_DELETE, note.id) > 0;
    }

    public List<Note> findByTitle(String title) {
        return jdbcTemplate.query(
                SQL_FIND_BY_TITLE,
                new Object[]{ "%" + title + "%" },
                new NoteMapper()
        );
    }
    public List<Note> findByBody(String body) {
        return jdbcTemplate.query(
                SQL_FIND_BY_BODY,
                new Object[]{ "%" + body + "%" },
                new NoteMapper()
        );
    }
    public List<Note> findByTitleAndBody(String title, String body) {
        return jdbcTemplate.query(
                SQL_FIND_BY_TITLE_AND_BODY,
                new Object[]{ "%" + title + "%", "%" + body + "%" },
                new NoteMapper()
        );
    }

    public List<Note> findByCreationAfter(Timestamp creationEarliest) {
        return jdbcTemplate.query(
                SQL_FIND_BY_CREATION_AFTER,
                new Object[] { creationEarliest },
                new int[] { Types.TIMESTAMP },
                new NoteMapper()
        );
    }
    public List<Note> findByCreationBefore(Timestamp creationLatest) {
        return jdbcTemplate.query(
                SQL_FIND_BY_CREATION_BEFORE,
                new Object[]{ creationLatest },
                new int[] { Types.TIMESTAMP },
                new NoteMapper()
        );
    }
    public List<Note> findByCreationBetween(Timestamp creationEarliest, Timestamp creationLatest) {
        return jdbcTemplate.query(
                SQL_FIND_BY_CREATION_BETWEEN,
                new Object[]{ creationEarliest, creationLatest },
                new int[] { Types.TIMESTAMP, Types.TIMESTAMP },
                new NoteMapper()
        );
    }
}
