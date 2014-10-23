package dat076.group4.model.core;

import dat076.group4.model.dao.IMovieCatalogue;
import dat076.group4.model.dao.MovieCatalogue;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
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
public class TestMovieLibraryPersistence {
    
    @EJB
    IMovieCatalogue movieCatalogue;

    @Resource
    UserTransaction utx;
    
    @PersistenceContext(unitName = "movie_library_pu")
    @Produces
    @Default
    EntityManager em;

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "shop.war")
            // Add all classes
            .addPackages(true, "dat076.group4.model")
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
    public void testMovieCreate() throws Exception {
        Movie p = new Movie("Persist", 123);
        movieCatalogue.create(p);
        List<Movie> ps = movieCatalogue.findAll();
        assertTrue(ps.size() > 0);
        assertTrue(ps.get(0).getTitle().equals(p.getTitle()));
    }

    @Test
    public void testMovieGetByTitle() throws Exception {
        Movie p1 = new Movie("GetByTitle", 123);
        Movie p2 = new Movie("GetByTitle", 123);
        movieCatalogue.create(p1);
        movieCatalogue.create(p2);
        List<Movie> ps = movieCatalogue.getByTitle("GetByTitle");
        assertTrue(ps.size() == 2);
        assertTrue(ps.get(0).getTitle().equals(p1.getTitle()));
        assertTrue(ps.get(1).getTitle().equals(p2.getTitle()));
    }

    @Test
    public void testMovieGetByYear() throws Exception {
        Movie p1 = new Movie("GetByYear", 123);
        Movie p2 = new Movie("GetByYear", 123);
        movieCatalogue.create(p1);
        movieCatalogue.create(p2);
        List<Movie> ps = movieCatalogue.getByYear(123);
        assertTrue(ps.size() == 2);
        assertTrue(ps.get(0).getReleaseYear() == 123);
        assertTrue(ps.get(1).getReleaseYear() == 123);
    }

    @Test
    public void testMovieDelete() throws Exception {
        Movie p = new Movie("Delete", 123);
        movieCatalogue.create(p);
        movieCatalogue.delete(p.getId());
        assertTrue(movieCatalogue.findAll().isEmpty());
    }

    @Test
    public void testMovieUpdate() throws Exception {
        Movie p = new Movie("Update", 123);
        movieCatalogue.create(p);
        p.setTitle("Updated");
        movieCatalogue.update(p);
        Movie p2 = movieCatalogue.find(p.getId());
        assertTrue(p2.getTitle().equals(p.getTitle()));
    }

    @Test
    public void testMovieFind() throws Exception {
        Movie p = new Movie("Find", 123);
        movieCatalogue.create(p);
        assertTrue(movieCatalogue.find(p.getId()) != null);
    }

    @Test
    public void testMovieFindAll() throws Exception {
        int count = 5;
        for (int i = 0; i < count; i++) {
            Movie p = new Movie("FindAll", 123);
            movieCatalogue.create(p);
        }
        List<Movie> ps = movieCatalogue.findAll();
        assertTrue(ps.size() == count);
    }

    @Test
    public void testMovieFindRange() throws Exception {
        int count = 5;
        for (int i = 0; i < count; i++) {
            Movie p = new Movie("FindRange", i);
            movieCatalogue.create(p);
        }
        List<Movie> ps = movieCatalogue.findRange(2, 2);
        assertTrue(ps.get(0).getReleaseYear() == 2 &&
                   ps.get(1).getReleaseYear() == 3);
    }

    @Test
    public void testMovieCount() throws Exception {
        int count = 5;
        for (int i = 0; i < count; i++) {
            Movie p = new Movie("Count", 123);
            movieCatalogue.create(p);
        }
        assertTrue(count == movieCatalogue.count());
    }

    @Test
    public void testMovieBulkUpdate() throws Exception {
        int count = 50;
        for (int i = 0; i < count; i++) {
            Movie p = new Movie("Bulk"+i, 100);
            movieCatalogue.create(p);
        }
        utx.begin();  
        int updateCount = em.createQuery(
                "UPDATE Movie m SET m.releaseYear = 200 WHERE m.releaseYear = 100")
                .executeUpdate();
        utx.commit();
        assertTrue(updateCount == count);
        assertTrue(movieCatalogue.getByYear(200).size() == count);
    }

    private void clearAll() throws Exception {  
        utx.begin();  
//        em.createQuery("DELETE FROM MovieList").executeUpdate();
//        em.createQuery("DELETE FROM User").executeUpdate();
        em.createQuery("DELETE FROM Movie").executeUpdate();
        utx.commit();
    }
}
