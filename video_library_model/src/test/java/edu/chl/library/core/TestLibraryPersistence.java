package edu.chl.library.core;

import edu.chl.library.core.Library;
import edu.chl.library.core.Movie;
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
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Testing the persistence layer
 *
 * NOTE NOTE NOTE: JavaDB (Derby) must be running (not using an embedded
 * database) GlassFish not needed using embedded
 *
 * @author hajo
 */
@RunWith(Arquillian.class)
public class TestLibraryPersistence {

    @Inject
    Library library;

    @Inject
    UserTransaction utx;  // This is not an EJB so have to handle transactions

    //@Inject
    //CustomerRegistry customer;
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                // Add all classes
                .addPackage("edu.chl.library.core")
                // This will add test-persitence.xml as persistence.xml (renamed)
                // in folder META-INF, see Files > jpa_managing > target > arquillian
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                // Must have for CDI to work
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

    }

    @Before  // Run before each test
    public void before() throws Exception {
        clearAll();
    }

    @Test
    public void testPersistAMovie() throws Exception {
        Movie m = new Movie("Short Term 12", "Drama", 2013, 96);
        library.getMovieCatalogue().create(m);
        List<Movie> ps = library.getMovieCatalogue().findAll();
        assertTrue(ps.size() > 0);
        assertTrue(ps.get(0).getTitle().equals(m.getTitle()));

    }

    @Test
    public void testAddMovie() throws Exception {
        Movie p = new Movie("The Wolf of Wall Street", "Comedy", 2013, 180);
        library.getMovieCatalogue().create(p);
        assertNotNull(library.getMovieCatalogue().findAll());
    }

    @Test
    public void testFind() throws Exception {
        Movie p = new Movie("The Wolf of Wall Street", "Comedy", 2013, 180);
        library.getMovieCatalogue().create(p);
        assertTrue(p.equals(library.getMovieCatalogue().find(p.getId())));
    }

    @Test
    public void testDeleteMovie() throws Exception {
        Movie product1 = new Movie("The Wolf of Wall Street", "Comedy", 2013, 180);
        library.getMovieCatalogue().create(product1);

        library.getMovieCatalogue().delete(product1.getId());

        assertTrue(library.getMovieCatalogue().findAll().isEmpty());
    }

    @Test
    public void testUpdateMovieReleaseDate() throws Exception {
        Movie p3 = new Movie("The Wolf of Wall Street", "Comedy", 2013, 180);
        library.getMovieCatalogue().create(p3);

        p3.setReleaseDate(777777);
        library.getMovieCatalogue().update(p3);

        Movie p4 = library.getMovieCatalogue().find(p3.getId());
        assertEquals(p4.getReleaseDate(), p3.getReleaseDate());
    }

    @Test
    public void testUpdateMovieName() throws Exception {
        Movie p5 = new Movie("The Wolf of Wall Street", "Comedy", 2013, 180);
        library.getMovieCatalogue().create(p5);

        p5.setTitle("dddddddd");
        library.getMovieCatalogue().update(p5);

        Movie p6 = library.getMovieCatalogue().find(p5.getId());
        assertTrue(p6.getTitle().equals(p5.getTitle()));
    }
/*
    @Test
    public void testPersistASingleCustomer() throws Exception {
        User c = new User(new Address(), "random", "random2", "whatever");
        library.getCustomerRegistry().create(c);
        List<User> customers = library.getCustomerRegistry().findAll();
        assertTrue(customers.size() > 0);
        assertTrue(customers.get(0).getId().equals(c.getId()));
    }

    @Test
    public void testPersistAOrder() throws Exception {
        List<XXX> items = new ArrayList<>();
        Movie p = new Movie("hihih", 2);
        User c = new User();
        XXX item = new XXX(p, 2);

        items.add(item);
        FavoriteVideo order = new FavoriteVideo(c, items);
        // This should be persistent (cascade)
        shop.getUserList().create(order);

        List<User> customers = shop.getCustomerRegistry().findAll();
        List<Movie> products = shop.getMovieCatalogue().findAll();
        List<FavoriteVideo> orders = shop.getUserList().findAll();

        assertTrue(orders.size() > 0);
        assertTrue(customers.get(0).getId().equals(c.getId()));
        assertTrue(products.get(0).getId().equals(p.getId()));
    }

    @Test
    public void testPersistAndRemoveOrder() throws Exception {
        List<XXX> items = new ArrayList<>();
         //Product p, Customer c, OrderItem item must be cascaded (Must be persistent)
        //Ex in ProductCatalogue @ManyToOne(cascade = CascadeType.PERSIST)
        Movie p = new Movie("eee", 555);
        User c = new User(new Address("street1", 2, "Gbg"), "namee", "tttt", "blablaba");
        XXX item = new XXX(p, 1);

        items.add(item);
        FavoriteVideo order = new FavoriteVideo(c, items);

        shop.getUserList().create(order);
        shop.getUserList().delete(order.getId());

        List<FavoriteVideo> orders = shop.getUserList().findAll();
        assertTrue(orders.isEmpty());

        List<User> customers = shop.getCustomerRegistry().findAll();
        List<Movie> products = shop.getMovieCatalogue().findAll();
        // ... customer and product should still be there
        assertTrue(customers.get(0).getId().equals(c.getId()));
        assertTrue(products.get(0).getId().equals(p.getId()));
    }*/
/*
    @Test
    public void testFindRange() throws Exception {
        String[] names = "aaa, bbb, ccc, ddd, eee, fff, ggg, hhh".split(",");
        for (String s : names) {
            Movie p = new Movie(s, "Spooky", 333, 92);
            library.getMovieCatalogue().create(p);
        }
        List<Movie> products = library.getMovieCatalogue().findAll();
        assertTrue(products.size() == names.length);
        products = library.getMovieCatalogue().findRange(2, 2);
        assertTrue(products.get(0).getTitle().equals(names[2]));

    }

    @Test
    public void testCount() throws Exception {
        String[] names = "aaa, bbb, ccc, ddd, eee, fff, ggg, hhh".split(",");
        for (String s : names) {
            Movie p = new Movie(s, "Comedy" ,3333, 120);
           library.getMovieCatalogue().create(p);
        }
        int count = library.getMovieCatalogue().count();
        assertTrue(count == names.length);
    }

    @Test
    public void testGetByName() throws Exception {
        
        Movie p1 = new Movie("a","", 111, 0);
        Movie p2 = new Movie("a","",222,0 );
        Movie p3 = new Movie("a","", 333, 0);
       library.getMovieCatalogue().create(p1);
       library.getMovieCatalogue().create(p2);
        library.getMovieCatalogue().create(p3);
        List<Movie> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);

        assertEquals(library.getMovieCatalogue().getByTitle("a"), list);
    }

    @Test
    public void testGetByReleaseDate() throws Exception {
        Movie p1 = new Movie("a","", 111, 0);
        Movie p2 = new Movie("a","", 111, 0);
        Movie p3 = new Movie("a","", 111,0);
        library.getMovieCatalogue().create(p1);
        library.getMovieCatalogue().create(p2);
        library.getMovieCatalogue().create(p3);
        List<Movie> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        assertEquals(library.getMovieCatalogue().getByReleaseDate(111), list);
    }

    @Test
    public void testGetById() throws Exception {
        Movie p1 = new Movie("a", "", 111, 2);
        library.getMovieCatalogue().create(p1);
        List<Movie> list = new ArrayList<>();
        list.add(p1);
        assertEquals(library.getMovieCatalogue().getById(p1.getId()), list);
    }

    @Test
    public void testProductGetByName() throws Exception {
        Movie p = new Movie("aaa", "", 999, 22);
        library.getMovieCatalogue().create(p);
        List<Movie> ps = library.getMovieCatalogue().getByTitle("aaa");
        assertTrue(ps.size() > 0);
        assertTrue(ps.get(0).getTitle().equals(p.getTitle()));
    }
    
    @Test
    public void testBulkUpdate() throws Exception {
       
        String[] names = "aaa, bbb, ccc, ddd, eee, fff, ggg, hhh".split(",");
        int[] prices = {111,222,333,444,555,666,777,888,999,};
      
        utx.begin();
        //Create bunch of products with the same price
        for (String s : names) {
            Movie p = new Movie(s, "Horror", 6666, 445);
            library.getMovieCatalogue().create(p);
        }
        //Create bunch of products with the same name
        for (int price : prices) {
            Movie p = new Movie("invasion","",  price, 33);
            library.getMovieCatalogue().create(p);
        }
        //update the List of products with the same name to same prices
        List<Movie> ps = library.getMovieCatalogue().getByTitle("invasion");
        for (Movie p : ps){
            p.setReleaseDate(66666);
            library.getMovieCatalogue().update(p);
        }
        //update the List of products with the same price to same names
        List<Movie> pp = library.getMovieCatalogue().getByReleaseDate(6666);
        for (Movie p : pp){
            p.setTitle("sameNameEverywhere");
            library.getMovieCatalogue().update(p);
        }
        int sizeOfName1 = library.getMovieCatalogue().getByTitle("sameNameEverywhere").size();
        int sizeOfPrice1 = library.getMovieCatalogue().getByReleaseDate(6666).size();
        
        int sizeOfName2 = library.getMovieCatalogue().getByTitle("invasion").size();
        int sizeOfPrice2 = library.getMovieCatalogue().getByReleaseDate(66666).size();
        
        utx.commit();
        assertEquals(sizeOfName1,sizeOfPrice1);
        assertEquals(sizeOfName2,sizeOfPrice2);
    }
    */

    // Need a standalone em to remove testdata between tests
    // No em accessible from interfaces
    @PersistenceContext(unitName = "movie_library_pu")
    @Produces
    @Default
    EntityManager em;

    // Order matters
    private void clearAll() throws Exception {
        utx.begin();
        em.joinTransaction();
      //  em.createQuery("delete from User").executeUpdate();
        //em.createQuery("delete from Favorite").executeUpdate();
        em.createQuery("delete from Movie").executeUpdate();
        utx.commit();
    }

}
