package dat076.group4.model.persistence;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * A container for entities that supports
 * common CRUD operations.
 *
 * @param <T> Type of items in container
 * @param <K> Primary key for items
 */
public abstract class AbstractDAO<T, K> implements IDAO<T, K> {
    
    private final Class<T> type;
    
    public AbstractDAO(Class<T> type) {
        this.type = type;
    }
    
    protected abstract EntityManager getEntityManager();
    
    @Override
    public void create(T t) {
        getEntityManager().persist(t);
    }
    
    @Override
    public void delete(K id) {
        T t = getEntityManager().getReference(type, id);
        getEntityManager().remove(t);
    }
    
    @Override
    public void update(T t) {
        getEntityManager().merge(t);
    }
    
    @Override
    public T find(K id) {
        return getEntityManager().find(type, id);
    }
    
    @Override
    public List<T> findAll() {
        return getEntityManager().createQuery(
               "select t from " + type.getSimpleName() + " t", type)
               .getResultList();
    }

    @Override
    public List<T> findRange(int first, int n) {
        TypedQuery<T> q = getEntityManager().createQuery(
                "select t from " + type.getSimpleName() + " t", type);
        q.setFirstResult(first);
        q.setMaxResults(n);
        return q.getResultList();
    }
    
    @Override
    public int count() {
        return getEntityManager().createQuery(
               "select count(t) from " + type.getSimpleName() + " t", Long.class)
               .getSingleResult().intValue();
    }
}
