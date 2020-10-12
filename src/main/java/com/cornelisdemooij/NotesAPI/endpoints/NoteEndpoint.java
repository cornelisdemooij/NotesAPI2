package com.cornelisdemooij.NotesAPI.endpoints;

import com.cornelisdemooij.NotesAPI.model.Note;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("note")
@Component
public class NoteEndpoint {
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNote(@PathParam("id") long id) {
        Note note = new Note();
        note.id = id;
        return Response.ok(note).build();
    }
}
