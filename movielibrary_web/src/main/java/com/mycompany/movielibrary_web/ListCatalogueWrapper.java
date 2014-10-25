package com.mycompany.movielibrary_web;


import dat076.group4.model.core.MovieList;
import javax.xml.bind.annotation.*;

/**
 * Need this because we have an immutable class MovieList
 * (JAX-RS must have default constructor)
 * 
 * Possible other solution: Change in model to make MovieList mutable or 
 * http://blog.bdoughan.com/2010/12/jaxb-and-immutable-objects.html
 * @author hajo
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "MovieList", propOrder = {
    "id",
    "title",
    "releaseYear"
})
public class ListCatalogueWrapper {

    private MovieList movieList;

    protected ListCatalogueWrapper() { // Must have
    }
   
    public ListCatalogueWrapper(MovieList movieList) { 
        this.movieList = movieList; 
    }
    /*
    @XmlElement
    public MovieList getMovie() {
        return movieList.getMovies();
    }

    @XmlElement //If serving XML we should use @XmlAttribute 
    public Long getId() {
        return movieList.getId();
    }

    @XmlElement
    public int getReleaseYear() {
        return movieList.getReleaseYear();
    }*/
}
