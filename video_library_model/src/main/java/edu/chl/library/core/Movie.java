package edu.chl.library.core;

import edu.chl.library.persistence.AbstractEntity;
import javax.persistence.Entity;

/**
 * A Product
 * @author hajo
 */
@Entity
public class Movie extends AbstractEntity {

    private String title;
    private String genre;
    private int releaseDate;
    private int playTime; //in minutes

    public Movie() {
    }
    
    protected Movie(String name, String genre, int releaseDate, int playTime) {
        this.title = name;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.playTime = playTime;
    }

    protected Movie(Long id, String name, String genre, int releaseDate, int playTime) {
        super(id);
        this.title = name;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.playTime = playTime;
    }

    public void setTitle(String title){
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setGenre(String genre){
        this.genre = genre;
    }
    
    public String getGenre() {
        return genre;
    }

    public void setReleaseDate(int releaseDate){
        this.releaseDate = releaseDate;
    }
    public int getReleaseDate() {
        return releaseDate;
    }
    
     public void setPlayTime(int playTime){
        this.playTime = playTime;
    }
    public int getPlayTime() {
        return playTime;
    }
     
    @Override
    public String toString() {
        return "Movie{" + "id=" + getId() + ", title=" + title + ", genre=" + genre + ", releaseDate=" + releaseDate + ", play time=" + playTime + '}';
    }
}
