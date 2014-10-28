package dat076.group4.model.dao;

import dat076.group4.model.core.MovieList;
import dat076.group4.model.core.MovieList.Visibility;
import dat076.group4.model.core.User;
import dat076.group4.model.persistence.IDAO;

import java.util.List;
import javax.ejb.Local;

/**
 * Interface to movie list catalogue.
 */
@Local
public interface IListCatalogue extends IDAO<MovieList, Long> {

    List<MovieList> getByUser(User user);

    List<MovieList> getByVisibility(Visibility visibility);
}
