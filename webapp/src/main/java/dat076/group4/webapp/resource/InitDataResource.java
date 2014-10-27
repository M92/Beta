package dat076.group4.webapp.resource;

import dat076.group4.model.core.Movie;
import dat076.group4.model.core.MovieList;
import dat076.group4.model.core.User;
import dat076.group4.model.dao.IMovieCatalogue;
import dat076.group4.model.dao.IUserRegistry;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Used to populate the database with mock data.
 */
@Path("init")
public class InitDataResource {

    private static final Logger LOG =  Logger.getLogger(InitDataResource.class.getName());

    @EJB
    IMovieCatalogue movieCatalogue;
    @EJB
    IUserRegistry userRegistry;

    @GET
    @Produces("application/json")
    public Response getJson() {
        
        try {
            for (User u : userRegistry.findAll())
                userRegistry.delete(u.getId());
            
            User a = new User(2845110303L , "adamlindberg076");
            User b = new User(2845389945L , "linuxuser5");
            User c = new User(2845633529L , "webapp3");
            User d = new User(2845674219L , "elderbage");
            User e = new User(2845729306L , "userguden1");				
            User f = new User(2845677567L , "viddeoh");
            User g = new User(2845679313L , "hejagoran");
            
            String[] titles = "aaa, bbb, ccc, ddd, eee, fff, ggg, hhh".split(",");
            List<Movie> listOfMovies = new ArrayList<>();
            for(String s : titles){
                Movie m = new Movie(s, 2000);
                listOfMovies.add(m);
                movieCatalogue.create(m);
            }
            MovieList ml = new MovieList(a, "c00l list", listOfMovies);
            a.addList(ml);
            
            //LOG.log(Level.INFO, ml);
            userRegistry.create(a);
            //LOG.log(Level.INFO, a);
            userRegistry.create(b);
            userRegistry.create(c);
            userRegistry.create(d);
            userRegistry.create(e);
            userRegistry.create(f);
            userRegistry.create(g);
            
            MovieList mlist = a.newList("hejhejhej");
            mlist.setVisibility(MovieList.Visibility.PUBLIC);
            userRegistry.update(a);
            

        } catch (Exception e) {
            return Response.ok(Json.createObjectBuilder().add("init", "Exception").build()).build();
        }
        return Response.ok(Json.createObjectBuilder().add("init", "OK").build()).build();
    }
}
