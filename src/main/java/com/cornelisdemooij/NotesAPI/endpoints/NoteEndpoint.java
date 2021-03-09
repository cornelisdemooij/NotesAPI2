package com.cornelisdemooij.NotesAPI.endpoints;

import com.cornelisdemooij.NotesAPI.model.Note;
import com.cornelisdemooij.NotesAPI.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.Optional;

@Path("note")
@Component
public class NoteEndpoint {
    @Autowired
    private NoteService noteService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response postNote(@RequestBody Note note) {
        boolean result = noteService.save(note);
        if (result) {
            return Response.accepted().build();
        } else {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNote(@PathParam("id") long id) {
        Optional<Note> optionalNote = noteService.findById(id);
        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            return Response.ok(note).build();
        } else {
            return Response.status(404, "No note found for id " + Long.toString(id)).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotes(
            @QueryParam("title") String title,
            @QueryParam("body") String body,
            @QueryParam("creation_earliest") Timestamp creationEarliest,
            @QueryParam("creation_latest") Timestamp creationLatest
            ) {
        Iterable<Note> notes;
        if (creationEarliest != null && creationLatest != null) {
            notes = noteService.findByCreationBetween(creationEarliest, creationLatest);
        } else if (creationEarliest != null) {
            notes = noteService.findByCreationAfter(creationEarliest);
        } else if (creationLatest != null) {
            notes = noteService.findByCreationBefore(creationLatest);
        } else if (title != null && body != null) {
            notes = noteService.findByTitleAndBody(title, body);
        } else if (title != null) {
            notes = noteService.findByTitle(title);
        } else if (body != null) {
            notes = noteService.findByBody(body);
        } else {
            notes = noteService.findAll();
        }
        return Response.ok(notes).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response putNote(@PathParam("id") long id, @RequestBody Note note) {
        try {
            boolean result = noteService.updateById(id, note);
            if (result) {
                return Response.ok().build();
            } else {
                return Response.serverError().build();
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return Response.status(404, "No note found for id " + Long.toString(id)).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteNote(@PathParam("id") long id) {
        try {
            boolean result = noteService.deleteById(id);
            if (result) {
                return Response.ok().build();
            } else {
                return Response.serverError().build();
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return Response.status(404, "No note found for id " + Long.toString(id)).build();
        }
    }
}
