package dat076.group4.model.core;

import dat076.group4.model.dao.IListCatalogue;
import dat076.group4.model.dao.IMovieCatalogue;
import dat076.group4.model.dao.IUserRegistry;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;

/**
 * App is a container for other containers.
 */
@ApplicationScoped
public class App implements IApp {

    @EJB
    private IUserRegistry userRegistry;
    @EJB
    private IListCatalogue listCatalogue;
    @EJB
    private IMovieCatalogue movieCatalogue;

    public App() {
        Logger.getAnonymousLogger().log(Level.INFO, "app alive");
    }

    @Override
    public IUserRegistry getUserRegistry() {
        return userRegistry;
    }

    @Override
    public IListCatalogue getListCatalogue() {
        return listCatalogue;
    }

    @Override
    public IMovieCatalogue getMovieCatalogue() {
        return movieCatalogue;
    }
}
