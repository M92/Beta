package dat076.group4.webapp.resource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * This resource is used by clients to determine if they
 * have an active session and what their username is.
 */
@Path("session")
public class SessionResource {

    @Context
    private HttpServletRequest request;

    @GET
    @Produces("application/json")
    public Response getSession() {

        HttpSession session = request.getSession(false);
        String username;
        JsonObject jsonObj;

        try {
            username = (String)session.getAttribute("username");
            jsonObj = Json.createObjectBuilder().add("username", username).build();
        } catch (Exception e) {
            return Response.noContent().build();
        }
        return Response.ok(jsonObj).build();
    }
}
