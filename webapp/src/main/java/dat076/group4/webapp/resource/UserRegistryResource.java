package dat076.group4.webapp.resource;

import dat076.group4.model.core.Movie;
import dat076.group4.model.core.MovieList;
import dat076.group4.model.core.User;
import dat076.group4.model.dao.IUserRegistry;
import dat076.group4.webapp.filter.ResourceFilterBinding;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * REST resource for the UserRegistry.
 * Protected by the ResourceFilter.
 */
@Path("users")
@ResourceFilterBinding
public class UserRegistryResource {

    @EJB
    private IUserRegistry userRegistry;

    @GET
    @Path(value = "{nickname}/lists")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findAllLists(@PathParam("nickname") String nickname) {

        User user = userRegistry.getByNickname(nickname);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<MovieList> lists = user.getLists();
        if (lists.isEmpty()) {
            return Response.noContent().build();
        }
        List<SimpleMovieListWrapper> wLists = new ArrayList<>();
        for (MovieList list : lists) {
            wLists.add(new SimpleMovieListWrapper(list));
        }
        return Response.ok(new GenericEntity<List<SimpleMovieListWrapper>>(wLists){}).build();
    }

    @GET
    @Path(value = "{nickname}/lists/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findList(@PathParam("nickname") String nickname,
                             @PathParam("id") long id) {

        User user = userRegistry.getByNickname(nickname);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<MovieList> lists = user.getLists();
        for (MovieList list : lists) {
            if (list.getId() == id) {
                return Response.ok(new GenericEntity<MovieListWrapper>(
                                   new MovieListWrapper(list)){}).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path(value = "{nickname}/lists")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response createNewList(@PathParam("nickname") String nickname, JsonObject json) {

        User user = userRegistry.getByNickname(nickname);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            user.newList(json.getString("listname"));
            userRegistry.update(user);
            return Response.ok().build();
        } catch (NullPointerException | ClassCastException e) {
            return Response.status(Status.BAD_REQUEST).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path(value = "{nickname}/lists/{id}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response addMovie(@PathParam("nickname") String nickname,
                             @PathParam("id") Long id, JsonObject json) {

        User user = userRegistry.getByNickname(nickname);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            long foreignId;
            try {
                foreignId = Long.parseLong(json.getString("id"));
            } catch (NullPointerException | ClassCastException | NumberFormatException e) {
                foreignId = json.getInt("id");
            }
            String title = json.getString("title");
            int year = json.getInt("year");
            int runtime = json.getInt("runtime");
            MovieList list = user.getList(id);
            list.addMovie(new Movie(foreignId, title, year, runtime));
            userRegistry.update(user);
            return Response.ok().build();
        } catch (NullPointerException | ClassCastException e) {
            return Response.status(Status.BAD_REQUEST).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path(value = "{nickname}/lists/{id}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response updateList(@PathParam("nickname") String nickname,
                               @PathParam("id") Long id, JsonObject json) {

        User user = userRegistry.getByNickname(nickname);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            MovieList list = user.getList(id);
            list.setListName(json.getString("listname"));
            switch (json.getString("visibility")) {
                case "PRIVATE":
                    list.setVisibility(MovieList.Visibility.PRIVATE);
                    break;
                case "PUBLIC":
                    list.setVisibility(MovieList.Visibility.PUBLIC);
                    break;
            }
            userRegistry.update(user);
            return Response.ok().build();
        } catch (NullPointerException | ClassCastException | NumberFormatException e) {
            return Response.status(Status.BAD_REQUEST).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path(value = "{nickname}/lists/{id}")
    public Response deleteList(@PathParam("nickname") String nickname,
                               @PathParam("id") Long id) {

        User user = userRegistry.getByNickname(nickname);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        user.deleteList(id);
        userRegistry.update(user);
        return Response.ok().build();
    }

    @DELETE
    @Path(value = "{nickname}/lists/{id}/{movie}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response removeMovie(@PathParam("nickname") String nickname,
                                @PathParam("id") Long id,
                                @PathParam("movie") Long movie) {

        User user = userRegistry.getByNickname(nickname);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            MovieList list = user.getList(id);
            list.removeMovie(movie);
            userRegistry.update(user);
            return Response.ok().build();
        } catch (NullPointerException | ClassCastException e) {
            return Response.status(Status.BAD_REQUEST).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
