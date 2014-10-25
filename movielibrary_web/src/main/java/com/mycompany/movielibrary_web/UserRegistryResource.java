package com.mycompany.movielibrary_web;

import dat076.group4.model.core.User;
import dat076.group4.model.dao.IUserRegistry;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service for the user catalogue (an Adapter) We always use Response
 * as return value (probably easier to modify)
 *
 *
 * @author hajo
 */
@Path("users") // Leading trailing slash doesn't matter, see web.xml
public class UserRegistryResource {

    @EJB
    IUserRegistry userRegistry;
    // Helper class used to build URI's. Injected by container 
    @Context
    private UriInfo uriInfo;
/*
    @POST
    /* @Consumes(value = MediaType.APPLICATION_JSON)
     public Response create(JsonObject data) {
     User p = new User(data.getString("title"), data.getInt("releaseYear"));
     */
    /*
    @Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@FormParam("title") String title, @FormParam("releaseYear") int releaseYear) {
        User p = new User(title, releaseYear);
        try {
            userRegistry.create(p);
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(p.getId())).build(p);
            return Response.created(uri).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }*/
        /*   try {
         User p = new User(title, releaseYear);
         userRegistry.create(p);
         MovieWrapper pw = new MovieWrapper(p);
         return Response.ok(pw).build();
         } catch (IllegalArgumentException e) {
         return Response.status(Status.INTERNAL_SERVER_ERROR).build();
         }
         
    }*/

    @DELETE
    @Path(value = "{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            userRegistry.delete(id);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path(value = "{id}/lists/{id2}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response update(@PathParam(value = "id") Long id, JsonObject json) {
        try {
            User u = userRegistry.find(id);
          //  u.setEmail(json.getString("email"));
            json.getJsonObject(String.valueOf(id));
            userRegistry.update(u);
            UserRegistryWrapper uw = new UserRegistryWrapper(u);
            return Response.ok(uw).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        /*  try {
         User p = new User(json.getString("title"), json.getInt("releaseYear"));
         userRegistry.update(p);
         return Response.ok(p).build();

         } catch (IllegalArgumentException e) {
         return Response.status(Status.INTERNAL_SERVER_ERROR).build();
         }
         */
    }

    @GET
    @Path(value = "{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) {
        User m = userRegistry.find(id);
        if (m != null) {
            UserRegistryWrapper mw = new UserRegistryWrapper(m);
            return Response.ok(mw).build();
        } else {
            return Response.noContent().build();
        }

        /*  User p = userRegistry.find(id);
         if (p != null) {
         return Response.ok(p).build();
         } else {
         return Response.noContent().build();
         }

         */
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findAll() {
        List<UserRegistryWrapper> pwList = new ArrayList<>();
        List<User> prodList = userRegistry.findAll();
        for (User p : prodList) {
            pwList.add(new UserRegistryWrapper(p));
        }
        GenericEntity<List<UserRegistryWrapper>> ge = new GenericEntity<List<UserRegistryWrapper>>(pwList) {
        };
        return Response.ok(ge).build();
        /* GenericEntity<List<User>> ge = new GenericEntity<List<User>>(userRegistry.findAll()) {
         };
         return Response.ok(ge).build();
         return Response.ok(ge).build();*/
    }

    @GET
    @Path("range")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findRange(@QueryParam("fst") int fst, @QueryParam("max") int n) {
        List<UserRegistryWrapper> wrapList = new ArrayList<>();
        List<User> pList = userRegistry.findRange(fst, n);
        for (User p : pList) {
            wrapList.add(new UserRegistryWrapper(p));
        }
        GenericEntity<List<UserRegistryWrapper>> ge = new GenericEntity<List<UserRegistryWrapper>>(wrapList) {
        };
        return Response.ok(ge).build();

        /* GenericEntity<List<User>> ge = new GenericEntity<List<User>>(userRegistry.findRange(fst, n)) {
         };
         return Response.ok(ge).build();*/
    }

    @GET
    @Path(value = "count")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response count() {
        int c = userRegistry.count();
        JsonObject value = Json.createObjectBuilder().add("value", c).build();
        return Response.ok(value).build();
    }

}
