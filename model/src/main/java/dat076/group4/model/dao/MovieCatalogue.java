package dat076.group4.model.dao;

import dat076.group4.model.core.Movie;
import dat076.group4.model.persistence.AbstractDAO;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * All movies
 */
@Stateless
public class MovieCatalogue extends AbstractDAO<Movie, Long>
    implements IMovieCatalogue {

    @PersistenceContext
    private EntityManager em;

    public MovieCatalogue() {
        super(Movie.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Movie> getByTitle(String title) {
        List<Movie> found = new ArrayList<>();
        for (Movie m : findAll()) {
            if (m.getTitle().equals(title)) {
                found.add(m);
            }
        }
        return found;
    }

    @Override
    public List<Movie> getByYear(Integer year) {
        List<Movie> found = new ArrayList<>();
        for (Movie m : findAll()) {
            if (m.getReleaseYear() == year) {
                found.add(m);
            }
        }
        return found;
    }
}
