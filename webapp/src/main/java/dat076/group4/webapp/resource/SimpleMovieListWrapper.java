package dat076.group4.webapp.resource;

import dat076.group4.model.core.MovieList;
import dat076.group4.model.core.MovieList.Visibility;

import java.util.Date;
import javax.xml.bind.annotation.*;

/**
 * This is needed because MovieList is immutable.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "MovieList", propOrder = {
    "id",
    "owner",
    "name",
    "movies",
    "creationDate",
    "visibility"
})
public class SimpleMovieListWrapper {

    private MovieList movieList;

    protected SimpleMovieListWrapper() {}

    public SimpleMovieListWrapper(MovieList movieList) { 
        this.movieList = movieList; 
    }

    @XmlAttribute
    public long getId() {
        return movieList.getId();
    }

    @XmlElement
    public String getOwner() {
        return movieList.getUser().getNickname();
    }

    @XmlElement
    public String getName() {
        return movieList.getListName();
    }

    @XmlElement
    public int getMovies() {
        return movieList.getMovies().size();
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
