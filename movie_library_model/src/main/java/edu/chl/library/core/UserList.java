package edu.chl.library.core;

import edu.chl.library.persistence.AbstractDAO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * All customers
 *
 * @author hajo
 */
@Stateless
public class UserList extends AbstractDAO<User, Long>
        implements IUser {

    @PersistenceContext
    private EntityManager em;

    
    // Factory method
  /* public static IUser newInstance() {
        return new UserList();
    }
*/
    public UserList() {
        super(User.class);
    }
    

   @Override
    public List<Movie> getByUser(User user) {
        List<Movie> found = new ArrayList<>();         
       found.addAll(em.createQuery("SELECT u FROM User u WHERE u.user = :user")
               .setParameter("user", user)
               .getResultList());
       return found;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
}
