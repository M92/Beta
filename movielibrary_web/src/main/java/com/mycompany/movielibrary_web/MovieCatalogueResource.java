package com.mycompany.movielibrary_web;


import dat076.group4.model.core.App;
import dat076.group4.model.core.Movie;
import dat076.group4.model.dao.IMovieCatalogue;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
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
 * REST Web Service for the movie catalogue (an Adapter) We always use
 * Response as return value (probably easier to modify)
 *
 *
 * @author hajo
 */
//@Path("movies") // Leading trailing slash doesn't matter, see web.xml
@Path ("/cond")
public class MovieCatalogueResource {
   IMovieCatalogue movieCatalogue;
    // Helper class used to build URI's. Injected by container 
    @Context
    private UriInfo uriInfo;
    
    @Inject
    App app;
    
    public MovieCatalogueResource(){
        movieCatalogue = app.getMovieCatalogue();
    }
    
 

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response create(JsonObject data) {
        Movie p = new Movie(data.getString("title"), data.getInt("releaseDate"));
        try {
            movieCatalogue.create(p);
            MovieWrapper pw = new MovieWrapper(p);           
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(pw.getId())).build(pw);
            return Response.created(uri).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        
        
        /*try {
            Movie p = new Movie(name, price);
            shop.getMovieCatalogue().create(p);
            MovieWrapper pw = new MovieWrapper(p);
            return Response.ok(pw).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }*/

    }

    @DELETE
    @Path(value = "{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            movieCatalogue.delete(id);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path(value = "{id}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response update(@PathParam(value = "id") Long id, JsonObject json, @Context Request request) {
        try {
            Movie p = new Movie(json.getString("name"), json.getInt("releaseDate"));
            movieCatalogue.update(p);
            MovieWrapper pw = new MovieWrapper(p);
            return Response.ok(pw).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        /*
            Movie p = shop.getMovieCatalogue().find(id);
        String s = ETagGenerator.getETagFor(new MovieWrapper(p));
        EntityTag tag = new EntityTag(s);

        Response.ResponseBuilder builder = request.evaluatePreconditions(tag);
        if (builder != null) {  // Precondition NOT met (If-Match false)
            // Send 412 "precondition failed"     
            return builder.build();
        } else {
            try {
                Movie old = shop.getMovieCatalogue().find(id);
                shop.getMovieCatalogue().update(new Movie(id, json.getString("name"), json.getInt("price")));
                // Send Ok 200
                
                return Response.ok(new MovieWrapper(old)).build();
            } catch (IllegalArgumentException e) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).build();
            }
        }*/
    }

    @GET
    @Path(value = "{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id, @Context Request request) {
        Movie p = movieCatalogue.find(id);
        if (p != null) {
            MovieWrapper pw = new MovieWrapper(p);
            return Response.ok(pw).build();
        } else {
            return Response.noContent().build();
        }

        /*
         Movie p = shop.getMovieCatalogue().find(id);
        if (p == null) {
            // return Response.noContent().build();
        }
        MovieWrapper pw = new MovieWrapper(p);
        String s = ETagGenerator.getETagFor(pw);
        EntityTag tag = new EntityTag(s);

        // Etag Will be evaluted agaist  If-None-Match
        // header in request. 
        // Returnvalues: 
        // --- null if preconditions ARE met
        // --- if NOT create a RespondeBuilder with appropriate status
        Response.ResponseBuilder builder = request.evaluatePreconditions(tag);

        CacheControl cc = new CacheControl();
        cc.setMaxAge(10);  // sec.

        if (builder != null) {  // Precondition NOT met (If-None-Match is false)
          // LOG.log(Level.INFO, "Builder NOT null");
            builder.cacheControl(cc);
            // Send 304 "Not Modified"      
            return builder.build();
        } else {
           // LOG.log(Level.INFO, "Builder null");
            // Send representation of current data
            return Response.ok(pw).tag(tag).cacheControl(cc).build();
        }
*/
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findAll() {
        List<MovieWrapper> pwList = new ArrayList<>();
        List<Movie> prodList = movieCatalogue.findAll();
        for (Movie p : prodList) {
            pwList.add(new MovieWrapper(p));
        }
        GenericEntity<List<MovieWrapper>> ge = new GenericEntity<List<MovieWrapper>>(pwList) {
        };
        return Response.ok(ge).build();
    }

    @GET
    @Path("range")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findRange(@QueryParam("fst") int fst, @QueryParam("max") int n) {
        List<MovieWrapper> wrapList = new ArrayList<>();
        List<Movie> pList = movieCatalogue.findRange(fst, n);
        for (Movie p : pList) {
            wrapList.add(new MovieWrapper(p));
        }
        GenericEntity<List<MovieWrapper>> ge = new GenericEntity<List<MovieWrapper>>(wrapList) {
        };
        return Response.ok(ge).build();
    }

    @GET
    @Path(value = "count")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response count() {
        int c = movieCatalogue.count();
        JsonObject value = Json.createObjectBuilder().add("value", c).build();
        return Response.ok(value).build();
    }

}
