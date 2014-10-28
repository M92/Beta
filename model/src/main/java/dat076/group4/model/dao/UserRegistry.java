package dat076.group4.model.dao;

import dat076.group4.model.core.User;
import dat076.group4.model.persistence.AbstractDAO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * DAO for users.
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
    public User getByNickname(String name) {
        for (User u : findAll()) {
            if (u.getNickname().equals(name)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public User getByOAuth(long oauth) {
         for (User u : findAll()) {
            if (u.getOAuth() == oauth) {
                return u;
            }
        }
        return null;
    }
}
