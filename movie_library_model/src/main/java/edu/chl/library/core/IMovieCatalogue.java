
package edu.chl.library.core;

import edu.chl.library.persistence.IDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;

/**
 * Interface to product catalogue
 * @author hajo
 */
@Local
public interface IMovieCatalogue extends IDAO<Movie, Long> {
    //@EJB
    public List<Movie> getByTitle(String title);
    public List<Movie> getByReleaseDate(int releaseDate);
    public List<Movie> getByGenre(String genre);
    public List<Movie> getById(Long id);
     
}
