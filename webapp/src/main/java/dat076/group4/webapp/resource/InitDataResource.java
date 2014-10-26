package dat076.group4.webapp.resource;

import dat076.group4.model.core.User;
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
    
            User a = new User(2845110303L , "adamlindberg076");
            User b = new User(2845389945L , "linuxuser5");
            User c = new User(2845633529L , "webapp3");
            User d = new User(2845674219L , "elderbage");
            User e = new User(2845729306L , "userguden1");				
            User f = new User(2845677567L , "viddeoh");
            User g = new User(2845679313L , "hejagoran");


            userRegistry.create(a);
            userRegistry.create(b);
            userRegistry.create(c);
            userRegistry.create(d);
            userRegistry.create(e);
            userRegistry.create(f);
            userRegistry.create(g);
            
            
        } catch (Exception e) {
            return Response.ok(Json.createObjectBuilder().add("init", "Exception").build()).build();
        }
        return Response.ok(Json.createObjectBuilder().add("init", "OK").build()).build();
    }
}
