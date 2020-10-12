package com.cornelisdemooij.NotesAPI.endpoints;

import com.cornelisdemooij.NotesAPI.model.Note;
import com.cornelisdemooij.NotesAPI.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        Note result = noteService.save(note);
        if (result != null) {
            return Response.accepted(true).build();
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
}
