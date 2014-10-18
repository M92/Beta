/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Vivi
 */
public class Time {
    private final Long startTime; //= System.nanoTime();
    private Long stopTime; // = System.nanoTime();
    
    public Time(){
        startTime = System.currentTimeMillis();
    }
    
    private Long getCurrentElaspedTime(){
       stopTime = System.currentTimeMillis();
        return stopTime - startTime;
    }
    
    public int getTimeDifference(){
        return getCurrentElaspedTime().intValue()/1000;
    }
}
