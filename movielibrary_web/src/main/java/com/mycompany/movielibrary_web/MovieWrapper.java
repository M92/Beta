package com.mycompany.movielibrary_web;


import dat076.group4.model.core.Movie;
import javax.xml.bind.annotation.*;

/**
 * Need this because we have an immutable class Product
 * (JAX-RS must have default constructor)
 * 
 * Possible other solution: Change in model to make Product mutable or 
 * http://blog.bdoughan.com/2010/12/jaxb-and-immutable-objects.html
 * @author hajo
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Product", propOrder = {
    "id",
    "name",
    "price"
})
public class MovieWrapper {

    private Movie movie;

    protected MovieWrapper() { // Must have
    }
   
    public MovieWrapper(Movie movie) { 
        this.movie = movie; 
    }
    
    @XmlElement
    public String getName() {
        return movie.getTitle();
    }

    @XmlElement //If serving XML we should use @XmlAttribute 
    public Long getId() {
        return movie.getId();
    }
/*
    @XmlElement
    public String getGenre() {
        return movie.getGenre();
    }
    
    @XmlElement
    public int getPlayTime() {
        return movie.getPlayTime();
    }
    */
}
