package edu.chl.library.core;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * Shop is a container for other containers NOTE: Uses Java 7
 *
 * @author hajo
 */

@ApplicationScoped
public class Library implements IMovieLibrary {
    @EJB
    private IMovieCatalogue movieCatalogue;
    @EJB
    private IUser userList;// = OrderBook.newInstance();

    @PostConstruct
    private void init() {
        //initTestData();
        Logger.getAnonymousLogger().log(Level.INFO, "MovieLibrary alive");
    }

   

    @Override
    public IUser getUserList() {
        return userList;
    }

    @Override
    public IMovieCatalogue getMovieCatalogue() {
        return movieCatalogue;
    }

    // If we have no database we can use this
    private void initTestData() {

        // Add new data
        movieCatalogue.create(new Movie("Short Term 12", "Drama", 2013, 96));
        movieCatalogue.create(new Movie("The Wolf of Wall Street", "Comedy", 2013, 180));
        movieCatalogue.create(new Movie("X-men: Days of Future Past", "", 0 ,33));
        movieCatalogue.create(new Movie("Inglourious Basterds","", 2009, 0));
        
        movieCatalogue.create(new Movie("Hunger","", 2008, 96));
        movieCatalogue.create(new Movie("Crouching Tiger, Hidden Dragon","" ,2000, 120));
        movieCatalogue.create(new Movie("Eureka", "", 2000, 217));
        movieCatalogue.create(new Movie("Unbreakable", "", 2000, 106));
        
        movieCatalogue.create(new Movie("Rango", "Animation" ,2011, 0));
     
       /*

        userList.create(new Movie(""));
        userList.create(new User(
                "berit", "beritsson", "berit@gmail.com"));
        userList.create(new User(
                "cecilia", "ceciliasson", "cecila@gmail.com"));
       */
    }
}
