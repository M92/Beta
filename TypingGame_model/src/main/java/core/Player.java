/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package core;


/**
 *
 * @author Vivi
 */
public class Player implements Runnable {

    private final String name;
    private String word;
    private WordHandler handler;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        handler = new WordHandler();
        //Receives the word typed
        //compare with database

        throw new UnsupportedOperationException("Not supported yet.");
//To change body of generated methods, choose Tools | Templates.
    }

}
