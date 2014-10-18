/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Reads the input and compare the string
 * @author Vivi
 * @param <T>
 * @param <K>
 */
public abstract class AbstractDAO<T, K> implements IDAO<T, K> {
    
    private final Class<T> clazz;
    
    protected abstract EntityManager getEntityManager();
    
    protected AbstractDAO(Class<T> clazz){
        this.clazz = clazz;
    }
    
    
   @Override
    public void create(T t) {
        getEntityManager().persist(t);
    }

    @Override
    public void delete(K id) {
        T t = getEntityManager().getReference(clazz, id);
        getEntityManager().remove(t);

    }

    @Override
    public void update(T t) {
        getEntityManager().merge(t);
    }

    @Override
    public T find(K id) {
        return getEntityManager().find(clazz, id);
    }

    @Override
    public List<T> findAll() {
        return get(true, -1, -1);
    }
    
    //Might not need this method
    @Override
    public List<T> findRange(int first, int n) {
        return get(false, first, n);
    }

    @Override
    public int count() {
        EntityManager em = getEntityManager();
        // Warning because typename not found in string (clazz.getSimpleName())
        // Criteria API better, possible misstakes in String, NOTE space before t      
        Long n = em.createQuery("select count(t) from " + clazz.getSimpleName() + " t", Long.class)
                .getSingleResult();
        return n.intValue();
    }

    
     private List<T> get(boolean all, int first, int n) {
        EntityManager em = getEntityManager();
        List<T> found = new ArrayList<>();
        // Warning because typename not found in string (clazz.getSimpleName())
        // Criteria API better, possible misstakes in String, NOTE space before t
        TypedQuery<T> q = em.createQuery("select t from " + clazz.getSimpleName() + " t", clazz);
        if (!all) {
            q.setFirstResult(first);
            q.setMaxResults(n);
        }
        found.addAll(q.getResultList());
        return found;
    }
    
}
