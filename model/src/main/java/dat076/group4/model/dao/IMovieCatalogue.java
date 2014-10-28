package dat076.group4.model.dao;

import dat076.group4.model.core.Movie;
import dat076.group4.model.persistence.IDAO;

import java.util.List;
import javax.ejb.Local;

/**
 * Interface to movie catalogue.
 */
@Local
public interface IMovieCatalogue extends IDAO<Movie, Long> {

    Movie getByForeignId(long id);

    List<Movie> getByTitle(String name);

    List<Movie> getByYear(int year);
}
