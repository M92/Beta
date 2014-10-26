package dat076.group4.webapp.resource;

import dat076.group4.model.core.Movie;
import dat076.group4.model.core.MovieList;
import dat076.group4.model.core.MovieList.Visibility;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * This is needed because MovieList is immutable.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "MovieList", propOrder = {
    "id",
    "owner",
    "movies",
    "creationDate",
    "visibility"
})
public class MovieListWrapper {

    private MovieList movieList;

    protected MovieListWrapper() {}

    public MovieListWrapper(MovieList movieList) { 
        this.movieList = movieList; 
    }

    @XmlAttribute 
    public Long getId() {
        return movieList.getId();
    }

    @XmlElement
    public String getOwner() {
        return movieList.getUser().getNickname();
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
