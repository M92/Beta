package dat076.group4.model.core;

import dat076.group4.model.persistence.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A movie
 */
@Entity
@Table(name = "Movies")
public class Movie extends AbstractEntity {

    //@Column(nullable = false)
    private String title;
    
    //@Column(nullable = false)
    private int releaseYear;

    public Movie() {}

    public Movie(String title, int releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "Movie{" + "id=" + getId() + ", title=" + title + ", releaseYear=" + releaseYear + '}';
    }
}
