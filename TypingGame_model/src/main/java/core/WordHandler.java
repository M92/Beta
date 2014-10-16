/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import util.AbstractDAO;

/**
 *
 * @author Vivi
 */
@Stateless
public class WordHandler extends AbstractDAO<Word, Long> {
    
    @PersistenceContext
    private EntityManager em;
    
    public WordHandler() {
        super(Word.class);
    }

    //T t is the word we typed
    //K k is the id of the word in the databse
    public boolean compareWord(String s, Long id) {
        /*
         found.addAll(em.createQuery("SELECT p FROM Product p WHERE p.price = :price")
         .setParameter("price", price)
         .getResultList());
         */
   //     TypedQuery<Long> query = em.createQuery(
        // "SELECT COUNT(c) FROM Country c", Long.class);
        //long countryCount = query.getSingleResult();
        String word = em.createQuery("SELECT w FROM Word w WHERE w.id = :id")
                .setParameter("id", id)
                .getSingleResult().toString();        
        return s.equals(word);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
