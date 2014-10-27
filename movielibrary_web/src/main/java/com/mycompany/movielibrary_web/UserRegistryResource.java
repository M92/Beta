package com.mycompany.movielibrary_web;

import dat076.group4.model.core.Movie;
import dat076.group4.model.core.MovieList;
import dat076.group4.model.core.User;
import dat076.group4.model.dao.IUserRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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

    //Ta bort usern - ok, listorna som användaren har tas även bort
    @DELETE 
    @Path(value = "{nickname}")
    public Response deleteUser(@PathParam("nickname") String nickname) {
        try {
            User user = userRegistry.getByNickname(nickname);
            userRegistry.delete(user.getId());
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Skapa en ny lista i listkatalogen - Fungerar så länge namnet listName tilldelas
    @POST 
    @Path(value = "{nickname}/lists")
    @Consumes(value = MediaType.APPLICATION_JSON)
    //@Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response createNewList(@PathParam("nickname") String nickname, JsonObject json) {
        User u = userRegistry.getByNickname(nickname);
        try {
            u.newList(json.getString("listName"));
            userRegistry.update(u);
            UserRegistryWrapper uw = new UserRegistryWrapper(u);
            return Response.ok(uw).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    //DEL - Ta bort den specifika listan i listkatalogen - ok men tar inte bort filmer i movies-tabellen (behövs den ens?)
    @DELETE
    @Path(value = "{nickname}/lists/{listId}")
    public Response deleteList(@PathParam(value = "nickname") String nickname, @PathParam(value = "listId") Long id) {
        try {
            User u = userRegistry.getByNickname(nickname);
            u.deleteMovieList(id);
            userRegistry.update(u);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Hämta alla listor usern har - testat ok
    @GET
    @Path(value = "{nickname}/lists")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findAll(@PathParam(value = "nickname") String nickname) {
        List<UserRegistryWrapper> urw = new ArrayList<>();
        List<User> userList = userRegistry.findAll();
        for (User u : userList) {
            urw.add(new UserRegistryWrapper(u));
        }
        GenericEntity<List<UserRegistryWrapper>> ge = new GenericEntity<List<UserRegistryWrapper>>(urw) {
        };
        return Response.ok(ge).build();
    }

    //Lägg till en ny film i listan - Fungerar men visar andra listor usern har
    @PUT 
    @Path(value = "{nickname}/lists/{listId}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON})
    //@Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
    public Response addMovie(@PathParam("nickname") String nickname, @PathParam("listId") Long id, JsonObject json) {
    //public Response addMovie(@PathParam("nickname") String nickname, @PathParam("listId") Long id, @FormParam("title") String title, @FormParam("releaseYear") int releaseYear) {
        User u = userRegistry.getByNickname(nickname);
        try {
            MovieList movieList = u.getList(id);
            movieList.addMovie(new Movie(json.getString("title"), json.getInt("releaseYear")));

            //movieList.addMovie(new Movie(title, releaseYear));
            userRegistry.update(u);
            UserRegistryWrapper uw = new UserRegistryWrapper(u);
            return Response.ok(uw).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    //DEL - Ta bort den specifika filmen i listan - ok men tas inte bort i movies-tabellen
    @DELETE
    @Path(value = "{nickname}/lists/{listId}/{movieId}")
    public Response deleteMovie(@PathParam(value = "nickname") String nickname, @PathParam(value = "listId") Long listId, @PathParam(value = "movieId") Long movieId) {
        try {
            User u = userRegistry.getByNickname(nickname);
            MovieList movieList = u.getList(listId);
            movieList.deleteMovie(movieId);
            userRegistry.update(u);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    //hämta filmen {id} listan - Ger mig user's listor istället?
    /*@GET
    @Path(value = "{nickname}/lists/{listId}/{movieId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findMovie(@PathParam(value = "nickname") String nickname, @PathParam(value = "listId") Long listId, @PathParam(value = "movieId") Long movieId) {
        User user = userRegistry.getByNickname(nickname);
        if (user != null) {
            MovieList movieList = user.getList(listId);
            for (Movie m : movieList.getMovies()) {
                if (m.getId().equals(movieId)) {
                    UserRegistryWrapper mw = new UserRegistryWrapper(user);
                    return Response.ok(mw).build();
                }
            }
        }
        return Response.noContent().build();
    }*/
    
    
    //Hitta alla filmer i listan - ok
    @GET
    @Path(value = "{nickname}/lists/{listId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findAllMovies(@PathParam(value = "nickname") String nickname, @PathParam(value = "listId") Long listId) {
        List<MovieCatalogueWrapper> pwList = new ArrayList<>();
        User user = userRegistry.getByNickname(nickname);
        MovieList movieList = user.getList(listId);

        for (Movie m : movieList.getMovies()) {
            pwList.add(new MovieCatalogueWrapper(m));
        }
        GenericEntity<List<MovieCatalogueWrapper>> ge = new GenericEntity<List<MovieCatalogueWrapper>>(pwList) {
        };
        return Response.ok(ge).build();
    }

    /*
     @GET
     @Path("{nickname}/lists/range")
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
     }*/
    /*
     @GET
     @Path(value = "{nickname}/lists/{listId}/count")
     @Produces(value = {MediaType.APPLICATION_JSON})
     public Response count(@PathParam(value = "nickname") String nickname, @PathParam(value = "listId") Long id) {
     User user = userRegistry.getByNickname(nickname);
     MovieList movieList = user.getList(id);
     int c = movieList.size();
     JsonObject value = Json.createObjectBuilder().add("value", c).build();
     return Response.ok(value).build();
     }
     */
}