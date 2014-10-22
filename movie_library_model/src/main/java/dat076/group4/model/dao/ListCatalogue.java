package dat076.group4.model.dao;

import dat076.group4.model.core.MovieList;
import dat076.group4.model.core.MovieList.Visibility;
import dat076.group4.model.core.User;
import dat076.group4.model.persistence.AbstractDAO;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * All movie lists
 */
@Stateless
public class ListCatalogue extends AbstractDAO<MovieList, Long>
    implements IListCatalogue {

    @PersistenceContext
    private EntityManager em;

    public ListCatalogue() {
        super(MovieList.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<MovieList> getByUser(User u) {
        List<MovieList> found = new ArrayList<>();
        for (MovieList list : findAll()) {
            if (list.getUser().equals(u)) {
                found.add(list);
            }
        }
        return found;
    }

    @Override
    public List<MovieList> getByVisibility(Visibility visibility) {
        List<MovieList> found = new ArrayList<>();
        for (MovieList list : findAll()) {
            if (list.getVisibility().equals(visibility)) {
                found.add(list);
            }
        }
        return found;
    }
}
