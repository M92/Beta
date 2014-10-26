package dat076.group4.webapp.resource;

import dat076.group4.model.dao.IMovieCatalogue;
import dat076.group4.model.dao.IUserRegistry;

import javax.ejb.EJB;
import javax.json.Json;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Used to populate the database with mock data.
 */
@Path("init")
public class InitDataResource {

    @EJB
    IMovieCatalogue movieCatalogue;
    @EJB
    IUserRegistry userRegistry;

    @GET
    @Produces("application/json")
    public Response getJson() {

        try {
            // init data
        } catch (Exception e) {
            return Response.ok(Json.createObjectBuilder().add("init", "Exception").build()).build();
        }
        return Response.ok(Json.createObjectBuilder().add("init", "OK").build()).build();
    }
}
