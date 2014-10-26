package com.mycompany.movielibrary_web;


import dat076.group4.model.core.Movie;
import dat076.group4.model.core.MovieList;
import dat076.group4.model.core.MovieList.Visibility;
import dat076.group4.model.core.User;
import java.util.Date;
import java.util.List;
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
    "user",
    "movies",
    "creationDate",
    "visibility"
})
public class ListCatalogueWrapper {

    private MovieList movieList;

    protected ListCatalogueWrapper() { // Must have
    }
   
    public ListCatalogueWrapper(MovieList movieList) { 
        this.movieList = movieList; 
    }
    
//    @XmlElement
//    public User getUser() {
//        return movieList.getUser();
//    }

    @XmlAttribute //If serving XML we should use @XmlAttribute 
    public Long getId() {
        return movieList.getId();
    }

    @XmlElement
    public List<Movie> getMovies() {
        return movieList.getMovies();
    }
  
    @XmlElement
    public Date getCreationDate() {
        return movieList.getCreationDate();
    }
    
    @XmlElement
    public Visibility getVisibility() {
        return movieList.getVisibility();
    }
}
