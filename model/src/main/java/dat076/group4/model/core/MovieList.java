package dat076.group4.model.core;

import dat076.group4.model.persistence.AbstractEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * A movie list
 */
@Entity
@Table(name = "LISTS")
public class MovieList extends AbstractEntity {

    public static enum Visibility {
        PRIVATE,
        PUBLIC,
    }  
    
    @Column(name = "LIST_NAME", nullable = false)
    private String listName;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Movie> movies;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATION_DATE", nullable = false)
    private final Date creationDate = new Date();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility = Visibility.PRIVATE;

    protected MovieList() {}

    public MovieList(User user, String listName) {
        this(user, listName, new ArrayList<Movie>());
    }
   
    public MovieList(User user, String listName, List<Movie> movies) {
        this(user, listName, movies, Visibility.PRIVATE);
    }

    public MovieList(User user, String listName, List<Movie> movies, Visibility visibility) {
        this.user = user;
        this.listName = listName;
        this.movies = movies;
        this.visibility = visibility;
    }

    public User getUser() {
        return user;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
    
    public List<Movie> getMovies() {
        return movies;
    }

    public void addMovie(Movie movie) {
        Long id = movie.getForeignId();
        for (Movie m : movies) {
            if (m.getForeignId() == id) {
                return;
            }
        }
        movies.add(movie);
    }

    public void removeMovie(Long foreignId) {
        for (Movie movie : movies) {
            if (movie.getForeignId() == foreignId) {
                movies.remove(movie);
                return;
            }
        }
    }
    
    public Date getCreationDate() {
        return creationDate;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(MovieList.Visibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return "MovieList{" + "id=" + getId()
                + ", user=" + user.getNickname()
                + ", movies=" + movies
                + ", creation-date=" + creationDate
                + ", visibility=" + visibility + '}';
    }
}
