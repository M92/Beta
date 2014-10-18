/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import util.Time;

/**
 *
 * @author Vivi
 */
@ApplicationScoped
public class TypingGame {
    
    private Time time;
  
    @EJB
    private WordHandler wordHandler;
    
    public TypingGame(){
       time = new Time();
      // init();
    }
    
    public void createPlayer(String name){
       new Thread(new Player(name)).start();
        
    }
    
    public WordHandler getWordHandler() {
        return wordHandler;
    }
    
   
    
    private boolean isTimedOut(){
        return time.getTimeDifference() == 30;  
    }
    
    
    @PostConstruct
    private void init(){
        Logger.getAnonymousLogger().log(Level.INFO, "Game alive");
        //openFile();
        //createPlayer();
       
    }
}
