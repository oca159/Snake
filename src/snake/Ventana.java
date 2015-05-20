/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;


/**
 *
 * @author osvaldo
 *
 * Bugs conocidos : El borde del titulo la ventana es de 30 px por eso
 * aumentamos en 30 la posición de la manzana y validamos que la posición en y
 * no sea menor a 30
 */
public class Ventana extends JFrame implements KeyListener {

    private Tablero panel;

    public Ventana() {
        this.setTitle("Snake básico");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(this);
        initVentana();
    }

    private void initVentana() {
        panel = Tablero.getInstance();
        panel.setSize(300, 300);
        panel.setLocation(100, 100);
        this.add(panel);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(panel.isMovimiento()){
            //izquierda
            if (key == 37 && !panel.isDerecha()) {
                panel.setIzquierda();
            }
            //derecha
            if (key == 39 && !panel.isIzquierda()) {
                panel.setDerecha();
            }
            //arriba
            if (key == 38 && !panel.isAbajo()) {
               panel.setArriba();
            }
            //abajo
            if (key == 40 && !panel.isArriba()) {
               panel.setAbajo();
            }
            panel.setMovimiento(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
