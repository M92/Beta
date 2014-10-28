package dat076.group4.model.core;

import dat076.group4.model.persistence.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A movie
 */
@Entity
@Table(name = "MOVIES")
public class Movie extends AbstractEntity {

    @Column(name = "FOREIGN_ID",
            nullable = false)
    private long foreignId;

    @Column(nullable = false)
    private String title;

    @Column(name = "RELEASE_YEAR",
            nullable = false)
    private int releaseYear;

    @Column(name = "RUN_TIME")
    private int runTime;

    public Movie() {}

    public Movie(long foreignId, String title, int releaseYear) {
        this(foreignId, title, releaseYear, 0);
    }

    public Movie(long foreignId, String title, int releaseYear, int runTime) {
        this.foreignId = foreignId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.runTime = runTime;
    }

    public long getForeignId() {
        return foreignId;
    }

    public void setForeignId(long foreignId) {
        this.foreignId = foreignId;
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

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    @Override
    public String toString() {
        return "Movie{" + " id=" + getId()
                        + ", foreignId=" + foreignId
                        + ", title=" + title
                        + ", releaseYear=" + releaseYear
                        + ", runTime=" + runTime
                        + '}';
    }
}
