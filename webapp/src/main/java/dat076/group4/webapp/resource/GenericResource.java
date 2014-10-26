package dat076.group4.webapp.resource;

import dat076.group4.webapp.auth.UserFilterBinding;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * REST Web Service
 */
@Path("generic")
public class GenericResource {

    private static final Logger LOG = Logger.getLogger(GenericResource.class.getName());

    @Context
    private UriInfo context;

    @GET
    @UserFilterBinding
    @Path(value = "{var1}/lists/{var2}")
    @Produces("application/json")
    public JsonObject getJson2(@PathParam(value = "var1") String user, @PathParam(value = "var2") Long list) {
        LOG.log(Level.INFO, "MyProtectedResource getJson2");
        LOG.log(Level.INFO, "user: {0}", user);
        LOG.log(Level.INFO, "list: {0}", list);
        JsonObject model = Json.createObjectBuilder().add("success", "sucess").build();
        return model;
    }
}
