package edu.chl.library.core;

import edu.chl.library.persistence.AbstractDAO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * All products
 *
 * @author hajo
 */
@Stateless
public class MovieCatalogue extends AbstractDAO<Movie, Long>
        implements IMovieCatalogue {
    
    @PersistenceContext
    private EntityManager em;
    
    public MovieCatalogue(){
        super(Movie.class);
    }
  
    // Factory method
    public static IMovieCatalogue newInstance() {
        return new MovieCatalogue();
    }

    
    @Override
    public List<Movie> getByReleaseDate(int releaseDate) {
       List<Movie> found = new ArrayList<>();         
       found.addAll(em.createQuery("SELECT m FROM Movie m WHERE m.releaseDate = :date")
               .setParameter("date", releaseDate)
               .getResultList());
       return found;
       
    }

    @Override
    public List<Movie> getByGenre(String genre) {
        List<Movie> found = new ArrayList<>();         
       found.addAll(em.createQuery("SELECT m FROM Movie m WHERE m.genre = :genre")
               .setParameter("genre", genre)
               .getResultList());
       return found;
    }
    
    @Override
    public List<Movie> getByTitle(String title) {
       List<Movie> found = new ArrayList<>();         
       found.addAll(em.createQuery("SELECT m FROM Movie m WHERE m.title = :title")
               .setParameter("title", title)
               .getResultList());
       return found;
    }
    
    @Override
    public List<Movie> getById(Long id) {
        List<Movie> found = new ArrayList<>();        
        found.addAll(em.createQuery("SELECT m FROM Movie m WHERE p.id = :id")
               .setParameter("id", id)
               .getResultList());
       return found;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


}
