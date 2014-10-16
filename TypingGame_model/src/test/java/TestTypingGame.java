/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import core.TypingGame;
import core.Word;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
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
    
    public TestTypingGame() {
    }
    
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
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // Must have for CDI to work
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

    }

  @Test
    public void testPersistAProduct() throws Exception {
        Word p = new Word();
        p.setWord("aaa");
        game.getWordHandler().create(p);
        List<Word> ps = game.getWordHandler().findAll();
        assertTrue(ps.size() > 0);
        assertTrue(ps.get(0).getWord().equals(p.getWord()));

    }
/*
    @Test
    public void testAddProduct() throws Exception {
        Product p = new Product("ppp", 1111);
        shop.getProductCatalogue().create(p);
        assertNotNull(shop.getProductCatalogue().findAll());
    }

    @Test
    public void testFind() throws Exception {
        Product p = new Product("fifi", 222);
        shop.getProductCatalogue().create(p);
        assertTrue(p.equals(shop.getProductCatalogue().find(p.getId())));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Product product1 = new Product("bbb", 888);
        shop.getProductCatalogue().create(product1);

        shop.getProductCatalogue().delete(product1.getId());

        assertTrue(shop.getProductCatalogue().findAll().isEmpty());
    }

    @Test
    public void testUpdateProductPrice() throws Exception {
        Product p3 = new Product("ccc", 777);
        shop.getProductCatalogue().create(p3);

        p3.setPrice(777777);
        shop.getProductCatalogue().update(p3);

        Product p4 = shop.getProductCatalogue().find(p3.getId());
        assertEquals(p4.getPrice(), p3.getPrice(), 0.00);
    }

    @Test
    public void testUpdateProductName() throws Exception {
        Product p5 = new Product("ddd", 666);
        shop.getProductCatalogue().create(p5);

        p5.setName("dddddddd");
        shop.getProductCatalogue().update(p5);

        Product p6 = shop.getProductCatalogue().find(p5.getId());
        assertTrue(p6.getName().equals(p5.getName()));
    }
*/
    
    
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
