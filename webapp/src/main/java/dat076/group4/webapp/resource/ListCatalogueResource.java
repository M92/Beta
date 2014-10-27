package dat076.group4.webapp.resource;

import dat076.group4.model.core.MovieList;
import dat076.group4.model.core.MovieList.Visibility;
import dat076.group4.model.dao.IListCatalogue;

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
import javax.ws.rs.core.Response.Status;

/**
 * REST resource for the (public) ListCatalogue.
 */
@Path("lists")
public class ListCatalogueResource {

    @EJB
    private IListCatalogue listCatalogue;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findAllPublic() {
        List<MovieList> lists = listCatalogue.getByVisibility(Visibility.PUBLIC);
        if (lists.isEmpty()) {
            return Response.noContent().build();
        } else {
            List<SimpleMovieListWrapper> listWrapper = new ArrayList<>();
            for (MovieList m : lists) {
                listWrapper.add(new SimpleMovieListWrapper(m));
            }
            return Response.ok(new GenericEntity<List<SimpleMovieListWrapper>>(listWrapper){}).build();
        }
    }

    @GET
    @Path(value = "{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findPublic(@PathParam("id") Long id) {
        MovieList list = listCatalogue.find(id);
        if (list != null && list.getVisibility() == Visibility.PUBLIC) {
            return Response.ok(new MovieListWrapper(list)).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
