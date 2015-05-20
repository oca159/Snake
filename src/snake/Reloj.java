/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author osvaldo
 */
public class Reloj extends Timer{
    private static Reloj instance = null;

    public Reloj(int delay, ActionListener listener) {
        super(delay, listener);
    }
    
    public static Reloj getInstance(int delay, ActionListener listener){
        if(instance == null){
            instance = new Reloj(delay, listener);
        }
        return instance;
    }
    
}
