package com.mycompany.movielibrary_web;


import dat076.group4.model.core.Movie;
import javax.xml.bind.annotation.*;

/**
 * Need this because we have an immutable class Movie
 * (JAX-RS must have default constructor)
 * 
 * Possible other solution: Change in model to make Movie mutable or 
 * http://blog.bdoughan.com/2010/12/jaxb-and-immutable-objects.html
 * @author hajo
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Movie", propOrder = {
    "id",
    "title",
    "releaseYear"
})
public class MovieCatalogueWrapper {

    private Movie movie;

    protected MovieCatalogueWrapper() { // Must have
    }
   
    public MovieCatalogueWrapper(Movie movie) { 
        this.movie = movie; 
    }
   
    @XmlElement
    public String getTitle() {
        return movie.getTitle();
    }

    @XmlElement //If serving XML we should use @XmlAttribute 
    public Long getId() {
        return movie.getId();
    }

    @XmlElement
    public int getReleaseYear() {
        return movie.getReleaseYear();
    }
}
