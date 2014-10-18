/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import core.TypingGame;
import core.Word;
import java.util.List;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Vivi
 */
@RunWith(Arquillian.class)
public class TestTypingGame {

    @Inject
    TypingGame game;

    @Inject
    UserTransaction utx;  // This is not an EJB so have to handle transactions

    //@Inject
    //CustomerRegistry customer;
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                // Add all classes
                .addPackage("core")
                // This will add test-persitence.xml as persistence.xml (renamed)
                // in folder META-INF, see Files > jpa_managing > target > arquillian
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                // Must have for CDI to work
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

    }

    @Test
    @InSequence(1)
    public void testPersistAWord() throws Exception {
        Word p = new Word();
        p.setWord("aaa");
        game.getWordHandler().create(p);
        List<Word> ps = game.getWordHandler().findAll();
        assertTrue(ps.size() > 0);
        assertTrue(ps.get(0).getWord().equals(p.getWord()));

    }

    @Test
    @InSequence(2)
    public void testAddWord() throws Exception {
        Word p = new Word();
        p.setWord("bbb");
        game.getWordHandler().create(p);
        assertNotNull(game.getWordHandler().findAll());
    }

    @Test
    @InSequence(3)
    public void testFind() throws Exception {
        Word p = new Word();
        game.getWordHandler().create(p);
        assertTrue(p.equals(game.getWordHandler().find(p.getId())));
    }

    @Test
    @InSequence(4)
    public void testDeleteWord() throws Exception {
        Word product1 = new Word();
        game.getWordHandler().create(product1);

        game.getWordHandler().delete(product1.getId());

        assertTrue(game.getWordHandler().findAll().isEmpty());
    }

    @Test
    @InSequence(5)
    public void testUpdateWord() throws Exception {
        Word p3 = new Word();
        game.getWordHandler().create(p3);

        p3.setWord("updated");
        game.getWordHandler().update(p3);

        Word p4 = game.getWordHandler().find(p3.getId());
        assertEquals(p4.getWord(), p3.getWord());
    }

    @Test
    @InSequence(6)
    public void testFindRange() throws Exception {
        String[] names = "aaa, bbb, ccc, ddd, eee, fff, ggg, hhh".split(",");
        for (String s : names) {
            Word p = new Word();
            p.setWord(s);
            game.getWordHandler().create(p);
        }
        List<Word> products = game.getWordHandler().findAll();
        assertTrue(products.size() == names.length);
        products = game.getWordHandler().findRange(2, 2);
        assertTrue(products.get(0).getWord().equals(names[2]));

    }

    @Test
    @InSequence(7)
    public void testCount() throws Exception {
        String[] names = "aaa, bbb, ccc, ddd, eee, fff, ggg, hhh".split(",");
        for (String s : names) {
            Word p = new Word();
            p.setWord(s);
            game.getWordHandler().create(p);
        }
        int count = game.getWordHandler().count();
        assertTrue(count == names.length);
    }
    
    @Test
    @InSequence(8)
    public void testCompareWord() throws Exception{
        Word w = new Word("wordToTest");
        game.getWordHandler().create(w);
        assertTrue(game.getWordHandler().compareWord("wordToTest", w.getId()));
    }

    @Test
    @InSequence(9)
    public void testReadTextFile() throws Exception{
        game.getWordHandler().openFile();
        assertTrue(!(game.getWordHandler().findAll().isEmpty()));
    }
    
    @Before
    public void setUp() throws Exception {
        clearAll();
    }

    // Order matters
    @PersistenceContext(unitName = "com.mycompany_TypingGame_model_jar_1.0-SNAPSHOTPU")
    @Produces
    @Default
    EntityManager em;

    private void clearAll() throws Exception {
        utx.begin();
        em.joinTransaction();
        em.createQuery("delete from Word").executeUpdate();
        utx.commit();
    }

}
