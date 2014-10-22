package dat076.group4.model.core;

import dat076.group4.model.dao.IListCatalogue;
import dat076.group4.model.dao.IMovieCatalogue;
import dat076.group4.model.dao.IUserRegistry;
import javax.ejb.Local;

/**
 * Public interface for the app
 */
@Local
public interface IApp {

    IUserRegistry getUserRegistry();

    IListCatalogue getListCatalogue();

    IMovieCatalogue getMovieCatalogue();
}
