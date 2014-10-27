package dat076.group4.webapp.resource;

import dat076.group4.model.core.MovieList;
import dat076.group4.model.core.User;
import dat076.group4.model.dao.IUserRegistry;
import dat076.group4.webapp.auth.UserFilterBinding;

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
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * REST resource for the UserRegistryResource.
 */
@Path("users")
public class UserRegistryResource {

    @EJB
    private IUserRegistry userRegistry;

    @GET
    @UserFilterBinding
    @Path(value = "{nickname}/lists")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findAllLists(@PathParam("nickname") String nickname) {

        User user = userRegistry.getByNickname(nickname);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
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
    @UserFilterBinding
    @Path(value = "{nickname}/lists/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findList(@PathParam("nickname") String nickname,
                             @PathParam("id") long id) {

        User user = userRegistry.getByNickname(nickname);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
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
    @UserFilterBinding
    @Path(value = "{nickname}/lists")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response createNewList(@PathParam("nickname") String nickname, JsonObject json) {

        User user = userRegistry.getByNickname(nickname);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
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

    @DELETE
    @UserFilterBinding
    @Path(value = "{nickname}/lists/{id}")
    public Response deleteList(@PathParam("nickname") String nickname,
                               @PathParam("id") Long id) {

        User user = userRegistry.getByNickname(nickname);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        user.deleteList(id);
        userRegistry.update(user);
        return Response.ok().build();
    }
}
