/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import util.AbstractDAO;

/**
 *
 * @author Vivi
 */
@Stateless
public class WordHandler extends AbstractDAO<Word, Long> {

    private static final String FILE_PATH = "";
    @PersistenceContext
    private EntityManager em;

    public WordHandler() {
        super(Word.class);
    }

    //T t is the word we typed
    //K k is the id of the word in the databse
    public boolean compareWord(String s, Long id) {        
        Word w = em.createQuery("SELECT w FROM Word w WHERE w.id = :id", Word.class)
               .setParameter("id", id)
               .getSingleResult();
        return s.equals(w.getWord());
    }

    public void openFile() {
        InputStream in = null;
        BufferedReader buff = null;
        String s;
        try {
            File currFile = new File(getClass().getClassLoader().getResource("words.txt").getFile());
            in = new FileInputStream(currFile);
            buff = new BufferedReader(new InputStreamReader(in));
            while ((s = buff.readLine()) != null) {
                create(new Word(s));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WordHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (buff != null) {
                try {
                    buff.close();
                } catch (IOException e) {
                    Logger.getLogger(WordHandler.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Logger.getLogger(WordHandler.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }

        /*Scanner in = null;
         ArrayList<String> list;
         try {
         in = new Scanner(new File("src/main/words.txt"));
         list = new ArrayList<>();
         while (in.hasNext()){
         list.add(in.next());
         System.out.println(in.toString());
         }
         } catch (IOException e) {
         e.printStackTrace();
         } finally {
         if (in != null) 
         in.close();
         }*/
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
