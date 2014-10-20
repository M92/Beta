package edu.chl.library.core;


import edu.chl.library.core.Movie;
import edu.chl.library.persistence.AbstractEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * A single row in an Order
 *
 * @author hajo
 */
@Entity
public class Favorite extends AbstractEntity {
    
    @OneToOne(cascade = CascadeType.PERSIST)
    private Movie movie;
    private List<Movie> movieList = new ArrayList<>();
    

    public Favorite(){
        
    }
    public Favorite(Movie movie) {
        this.movie = movie;
        movieList.add(movie);
        
    }
    
    public Favorite(Long id, Movie movie) {
        super(id);
        this.movie = movie;
        movieList.add(movie);
    }

    public Movie getMovie() {
        return movie;
    }

    public List<Movie> getFavoriteList(){
        return movieList;
    }

    @Override
    public String toString() {
        return "Favorite{" + "movie=" + movie + '}';
    }
}
