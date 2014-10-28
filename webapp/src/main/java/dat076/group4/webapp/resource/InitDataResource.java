package dat076.group4.webapp.resource;

import dat076.group4.model.core.Movie;
import dat076.group4.model.core.MovieList;
import dat076.group4.model.core.User;
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
    private IUserRegistry userRegistry;

    @GET
    @Produces("application/json")
    public Response getJson() {
        
        try {
            Movie movA1 = new Movie(11094,"The Fast and the Furious",2001,107);
            Movie movA2 = new Movie(155655113,"The Fast and the Furious: Tokyo Drift",2006,98);
            Movie movA3 = new Movie(771311994,"Need For Speed",2014,130);
            Movie movA4 = new Movie(17108,"Gone in 60 Seconds",2000,117);

            Movie movB1 = new Movie(12852,"Love Actually",2003,135);
            Movie movB2 = new Movie(771249048,"Love",2011,90);
            Movie movB3 = new Movie(351526458,"Love Annabelle",2006,77);

            Movie movC1 = new Movie(12989,"The Shawshank Redemption",1994,142);
            Movie movC2 = new Movie(12911,"The Godfather",1972,175);
            Movie movC3 = new Movie(13863,"Pulp Fiction",1994,154);
            Movie movC4 = new Movie(769959054,"The Dark Knight",2008,152);
            Movie movC5 = new Movie(12903,"Schindler's List",1993,196);

            User usr1 = addUser(new User(2845674219L, "elderbage"));
            User usr2 = addUser(new User(2845729306L, "userguden1"));
            User usr3 = addUser(new User(2845677567L, "viddeoh"));
            User usr4 = addUser(new User(2845679313L, "hejagoran"));
            User usr5 = addUser(new User(2845389945L, "linuxuser5"));
            User usr6 = addUser(new User(2845633529L, "webapp3"));

            MovieList usr1List1 = usr1.newList("Cars and stuff");
            usr1List1.addMovie(movA1);
            usr1List1.addMovie(movA2);
            usr1List1.addMovie(movA3);
            usr1List1.addMovie(movA4);
            usr1List1.setVisibility(MovieList.Visibility.PUBLIC);
            MovieList usr1List2 = usr1.newList("Love is in the air");
            usr1List2.addMovie(movB1);
            usr1List2.addMovie(movB2);
            usr1List2.addMovie(movB3);
            MovieList usr1List3 = usr1.newList("Top 5");
            usr1List3.addMovie(movC1);
            usr1List3.addMovie(movC2);
            usr1List3.addMovie(movC3);
            usr1List3.addMovie(movC4);
            usr1List3.addMovie(movC5);
            usr1List3.setVisibility(MovieList.Visibility.PUBLIC);
            userRegistry.update(usr1);

        } catch (Exception e) {
            return Response.ok(Json.createObjectBuilder().add("init", "Exception").build()).build();
        }
        return Response.ok(Json.createObjectBuilder().add("init", "OK").build()).build();
    }

    private User addUser(User u) throws Exception {
        User user = userRegistry.getByOAuth(u.getOAuth());
        if (user == null) {
            userRegistry.create(u);
            return u;
        } else {
            return user;
        }
    }
}
