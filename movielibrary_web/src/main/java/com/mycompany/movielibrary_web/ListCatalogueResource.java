package com.mycompany.movielibrary_web;

import dat076.group4.model.core.MovieList;
import dat076.group4.model.dao.IListCatalogue;
import dat076.group4.model.dao.IMovieCatalogue;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service for the movieList catalogue (an Adapter) We always use Response
 * as return value (probably easier to modify)
 *
 *
 * @author hajo
 */
@Path("movielists") // Leading trailing slash doesn't matter, see web.xml
public class ListCatalogueResource {

    @EJB
    IListCatalogue listCatalogue;
    // Helper class used to build URI's. Injected by container 
    @Context
    private UriInfo uriInfo;

 /*   @POST
    /* @Consumes(value = MediaType.APPLICATION_JSON)
     public Response create(JsonObject data) {
     MovieListmovie p = new MovieList(data.getString("title"), data.getInt("releaseYear"));
     */
 /*   @Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@FormParam("title") String title, @FormParam("releaseYear") int releaseYear) {
        MovieList p = new MovieList();
        try {
            listCatalogue.create(p);
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(p.getId())).build(p);
            return Response.created(uri).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }*/

        /*   try {
         MovieList p = new MovieList(title, releaseYear);
         listCatalogue.create(p);
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
            listCatalogue.delete(id);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
/*
    @PUT
    @Path(value = "{id}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response update(@PathParam(value = "id") Long id, JsonObject json) {
        try {
            MovieList p = new MovieList(json.getString("title"), json.getInt("releaseYear"));
            listCatalogue.update(p);
            MovieCatalogueWrapper pw = new MovieCatalogueWrapper(p);
            return Response.ok(pw).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        /*  try {
         MovieList p = new MovieList(json.getString("title"), json.getInt("releaseYear"));
         listCatalogue.update(p);
         return Response.ok(p).build();

         } catch (IllegalArgumentException e) {
         return Response.status(Status.INTERNAL_SERVER_ERROR).build();
         }
         
    }*/

    @GET
    @Path(value = "{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) {
        MovieList mL = listCatalogue.find(id);
        if (mL != null) {
            ListCatalogueWrapper lcw = new ListCatalogueWrapper(mL);
            return Response.ok(lcw).build();
        } else {
            return Response.noContent().build();
        }

        /*  MovieList p = listCatalogue.find(id);
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
        List<ListCatalogueWrapper> pwList = new ArrayList<>();
        List<MovieList> prodList = listCatalogue.findAll();
        for (MovieList p : prodList) {
            pwList.add(new ListCatalogueWrapper(p));
        }
        GenericEntity<List<ListCatalogueWrapper>> ge = new GenericEntity<List<ListCatalogueWrapper>>(pwList) {
        };
        return Response.ok(ge).build();
        /* GenericEntity<List<MovieList>> ge = new GenericEntity<List<MovieList>>(listCatalogue.findAll()) {
         };
         return Response.ok(ge).build();
         return Response.ok(ge).build();*/
    }

    @GET
    @Path("range")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findRange(@QueryParam("fst") int fst, @QueryParam("max") int n) {
        List<ListCatalogueWrapper> wrapList = new ArrayList<>();
        List<MovieList> pList = listCatalogue.findRange(fst, n);
        for (MovieList p : pList) {
            wrapList.add(new ListCatalogueWrapper(p));
        }
        GenericEntity<List<ListCatalogueWrapper>> ge = new GenericEntity<List<ListCatalogueWrapper>>(wrapList) {
        };
        return Response.ok(ge).build();

        /* GenericEntity<List<MovieList>> ge = new GenericEntity<List<MovieList>>(listCatalogue.findRange(fst, n)) {
         };
         return Response.ok(ge).build();*/
    }

    @GET
    @Path(value = "count")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response count() {
        int c = listCatalogue.count();
        JsonObject value = Json.createObjectBuilder().add("value", c).build();
        return Response.ok(value).build();
    }

}
