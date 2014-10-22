package dat076.group4.model.dao;

import dat076.group4.model.core.User;
import dat076.group4.model.persistence.AbstractDAO;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * All users
 */
@Stateless
public class UserRegistry extends AbstractDAO<User, Long>
    implements IUserRegistry {
    
    @PersistenceContext
    private EntityManager em;

    public UserRegistry() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<User> getByName(String name) {
        List<User> found = new ArrayList<>();
        for (User u : findAll()) {
            if (u.getNickname().equals(name)) {
                found.add(u);
            }
        }
        return found;
    }
}
