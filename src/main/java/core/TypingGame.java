/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
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
    
    @EJB
    private WordHandler wordHandler;
    
    public TypingGame(){
       new Time();
       init();
    }
    
    public void createPlayer(String name){
       new Thread(new Player(name)).start();
        
    }
    
    public WordHandler getWordHandler() {
        return wordHandler;
    }
    
    private void openFile() {
        Scanner in = null;
        ArrayList<String> list;
        try {
            in = new Scanner(new File("src/main/words.txt"));
            list = new ArrayList<>();
            while (in.hasNext()){
                list.add(in.next());
                System.out.println(in.toString());
             }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) 
                in.close();
        }
    }
    
    @PostConstruct
    private void init(){
        Logger.getAnonymousLogger().log(Level.INFO, "Game alive");
        openFile();
        //createPlayer();
       
    }
}
