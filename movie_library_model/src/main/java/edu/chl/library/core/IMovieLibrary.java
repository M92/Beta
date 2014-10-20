package edu.chl.library.core;

import javax.ejb.Local;

/**
 * Public interface for the shop
 * @author hajo
 */
@Local
public interface IMovieLibrary {

    public IUser getUserList();
    public IMovieCatalogue getMovieCatalogue();
}
