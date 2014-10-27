package dat076.group4.webapp.resource;

import dat076.group4.model.core.MovieList;
import dat076.group4.model.core.User;
import dat076.group4.model.dao.IUserRegistry;
import dat076.group4.webapp.auth.UserFilterBinding;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST resource for the UserRegistryResource.
 */
@Path("users")
public class UserRegistryResource {

    @EJB
    IUserRegistry userRegistry;

    @GET
    @UserFilterBinding
    @Path(value = "{nickname}/lists")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findAllLists(@PathParam(value = "nickname") String nickname) {

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
}
