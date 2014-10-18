/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javax.persistence.Entity;
import util.AbstractEntity;

/**
 *
 * @author Vivi
 */
@Entity
public class Word extends AbstractEntity {

    private String word;

    public Word() {
    }
    
    public Word(String word) {
        this.word = word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return "Word{" + "id=" + getId() + ", word=" + word + '}';
    }
}
