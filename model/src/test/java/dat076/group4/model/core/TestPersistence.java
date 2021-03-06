package dat076.group4.model.core;

import dat076.group4.model.dao.IListCatalogue;
import dat076.group4.model.dao.IMovieCatalogue;
import dat076.group4.model.dao.IUserRegistry;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
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
import static org.junit.Assert.assertEquals;

/**
 * Testing the persistence layer.
 */
@RunWith(Arquillian.class)
public class TestPersistence {

    @EJB
    IMovieCatalogue movieCatalogue;
    @EJB
    IListCatalogue listCatalogue;
    @EJB
    IUserRegistry userRegistry;

    @Resource
    UserTransaction utx;

    @PersistenceContext(unitName = "model_pu")
    @Produces
    @Default
    EntityManager em;

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
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
    public void testCreateUser() throws Exception {
        User user = new User(new Long(1),"aaa");
        userRegistry.create(user);
        assertTrue(userRegistry.findAll().size() > 0);
    }

    @Test
    public void testAddMovieToList() throws Exception {
        User user = new User(new Long(1),"aaa");
        userRegistry.create(user);
        MovieList list1 = user.newList("List 1");
        list1.addMovie(new Movie(1111,"MovieA",2001,107));
        userRegistry.update(user);
        assertEquals(1, listCatalogue.findAll().size());
        assertEquals(1, movieCatalogue.findAll().size());
        MovieList list2 = user.newList("List 2");
        list2.addMovie(new Movie(1111,"MovieA",2001,107));
        userRegistry.update(user);
        assertEquals(2, listCatalogue.findAll().size());
        assertTrue(movieCatalogue.findAll().size() > 1);
    }

    @Test
    public void testMovieCreate() throws Exception {
        Movie p = new Movie(2000L, "Persist", 123);
        movieCatalogue.create(p);
        List<Movie> ps = movieCatalogue.findAll();
        assertTrue(ps.size() > 0);
        assertTrue(ps.get(0).getTitle().equals(p.getTitle()));
    }

    @Test
    public void testMovieGetByTitle() throws Exception {
        Movie p1 = new Movie(1000L, "GetByTitle", 123);
        Movie p2 = new Movie(2000L, "GetByTitle", 123);
        movieCatalogue.create(p1);
        movieCatalogue.create(p2);
        List<Movie> ps = movieCatalogue.getByTitle("GetByTitle");
        assertTrue(ps.size() == 2);
        assertTrue(ps.get(0).getTitle().equals(p1.getTitle()));
        assertTrue(ps.get(1).getTitle().equals(p2.getTitle()));
    }

    @Test
    public void testMovieGetByYear() throws Exception {
        Movie p1 = new Movie(1000L, "GetByYear", 123);
        Movie p2 = new Movie(2000L, "GetByYear", 123);
        movieCatalogue.create(p1);
        movieCatalogue.create(p2);
        List<Movie> ps = movieCatalogue.getByYear(123);
        assertTrue(ps.size() == 2);
        assertTrue(ps.get(0).getReleaseYear() == 123);
        assertTrue(ps.get(1).getReleaseYear() == 123);
    }

    @Test
    public void testMovieDelete() throws Exception {
        Movie p = new Movie(999L, "Delete", 123);
        movieCatalogue.create(p);
        movieCatalogue.delete(p.getId());
        assertTrue(movieCatalogue.findAll().isEmpty());
    }

    @Test
    public void testMovieUpdate() throws Exception {
        Movie p = new Movie(999L, "Update", 123);
        movieCatalogue.create(p);
        p.setTitle("Updated");
        movieCatalogue.update(p);
        Movie p2 = movieCatalogue.find(p.getId());
        assertTrue(p2.getTitle().equals(p.getTitle()));
    }

    @Test
    public void testMovieFind() throws Exception {
        Movie p = new Movie(999L, "Find", 123);
        movieCatalogue.create(p);
        assertTrue(movieCatalogue.find(p.getId()) != null);
    }

    @Test
    public void testMovieFindAll() throws Exception {
        int count = 5;
        for (int i = 0; i < count; i++) {
            Movie p = new Movie(10L * i, "FindAll", 123);
            movieCatalogue.create(p);
        }
        List<Movie> ps = movieCatalogue.findAll();
        assertTrue(ps.size() == count);
    }

    @Test
    public void testMovieFindRange() throws Exception {
        int count = 5;
        for (int i = 0; i < count; i++) {
            Movie p = new Movie(10L * i, "FindRange", i);
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
            Movie p = new Movie(10L * i, "Count", 123);
            movieCatalogue.create(p);
        }
        assertTrue(count == movieCatalogue.count());
    }

    @Test
    public void testPersistCreateListCatalogue() throws Exception{
        List<Movie> items = new ArrayList<>();
        Movie m = new Movie(9873L, "hihih", 2);
        User user = new User(new Long(2),"bbb");
        
        movieCatalogue.create(m);
        userRegistry.create(user);
        
        items.add(m);
        MovieList movieList = new MovieList(user, "myList" ,items);
        // This should be persistent (cascade)
        listCatalogue.create(movieList);

        List<User> users = userRegistry.findAll();
        List<Movie> movies = movieCatalogue.findAll();
        List<MovieList> movieLists = listCatalogue.findAll();

        assertTrue(movieLists.size() > 0);
        assertEquals(users.get(0).getId(), user.getId() );
        assertEquals(movies.get(0).getId(), m.getId() );
    }

    @Test
    public void testPersistenceDeleteMovieList() throws Exception{
        List<Movie> items = new ArrayList<>();
        //Movie p, User c, Movie item must be cascaded (Must be persistent)
        //Ex in ProductCatalogue @ManyToOne(cascade = CascadeType.PERSIST)
        Movie m = new Movie(98L, "eee", 555);
        User user = new User(new Long(3),"ccc");
        movieCatalogue.create(m);
        userRegistry.create(user);

        items.add(m);
        MovieList movieList = new MovieList(user, "randomNaming",items);

        listCatalogue.create(movieList);
        listCatalogue.delete(movieList.getId());

        List<MovieList> movieLists = listCatalogue.findAll();
        assertTrue(movieLists.isEmpty());

        List<User> users = userRegistry.findAll();
        List<Movie> movies = movieCatalogue.findAll();
        // ... user and movieList should still be there
        assertEquals(1, users.size());
        assertEquals(1, movies.size());
    }

    @Test
    public void testPersistenceDeleteUser() throws Exception{
        List<Movie> items = new ArrayList<>();
        //Movie p, User c, Movie item must be cascaded (Must be persistent)
        //Ex in ProductCatalogue @ManyToOne(cascade = CascadeType.PERSIST)
        Movie m = new Movie(678L, "eee", 555);
        User user = new User(new Long(4),"ddd");
        movieCatalogue.create(m);
        userRegistry.create(user);

        items.add(m);
        MovieList movieList = new MovieList(user, "deleteUserListname",items);
        user.addList(movieList);

        listCatalogue.create(movieList);
        userRegistry.update(user);             //Updates the movieList
        userRegistry.delete(user.getId());

        List<User> users = userRegistry.findAll();
        assertTrue(users.isEmpty());

        List<MovieList> movieLists = listCatalogue.findAll();
        List<Movie> movies = movieCatalogue.findAll();
        // ... user and movieList should NOT be there
        assertEquals(1, movies.size());
        assertEquals(0, movieLists.size());
    }

    @Test
    public void testCascadeList() throws Exception{
        User user = new User(new Long(5),"eee");
        user.addList(new MovieList(user,"noNamingFantasy",new ArrayList<Movie>()));
        userRegistry.create(user);
        
        assertTrue(!listCatalogue.findAll().isEmpty());
        userRegistry.delete(user.getId());
        assertTrue(listCatalogue.findAll().isEmpty());        
    }

    @Test
    public void testCreateNewListUser() throws Exception{
        User user = new User(new Long(6),"fff");
        user.newList("Just create the list..");
        userRegistry.create(user);
        
        assertTrue(!listCatalogue.findAll().isEmpty());
        userRegistry.delete(user.getId());
        assertTrue(listCatalogue.findAll().isEmpty());    
    }

    private void clearAll() throws Exception {  
        utx.begin();  
        em.createQuery("DELETE FROM MovieList").executeUpdate();
        em.createQuery("DELETE FROM User").executeUpdate();
        em.createQuery("DELETE FROM Movie").executeUpdate();
        utx.commit();
    }
}
