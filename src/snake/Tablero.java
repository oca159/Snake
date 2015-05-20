/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author osvaldo
 */
public class Tablero extends JPanel {

    private static Tablero instance = null;
    
    private final int ANCHO = 300;
    private final int ALTO = 300;
    private final int PUNTO_SIZE = 10;
    private final int RAND_POS = 25;
    private int RETRASO = 100;
    private int puntos;

    private final int TODOS_PUNTOS = 900;
    private int x[] = new int[TODOS_PUNTOS];
    private int y[] = new int[TODOS_PUNTOS];

    private int manzana_x;
    private int manzana_y;

    private Image bola;
    private Image manzana;
    private Image cabeza;

    private boolean izquierda;
    private boolean derecha;
    private boolean arriba;
    private boolean abajo;

    private boolean enJuego;
    private boolean movimiento = true;
    
    private final ActionListener listener;

    private Reloj timer;

    public Tablero() {
        this.setBackground(Color.BLACK);
        try {
            bola = ImageIO.read(getClass().getResource("iconos/bola.png"));
            manzana = ImageIO.read(getClass().getResource("iconos/manzana.png"));
            cabeza = ImageIO.read(getClass().getResource("iconos/cabeza.png"));
        } catch (IOException e) {
            System.out.println("No se encontraron los iconos");
        }
        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enJuego) {
                    checarManzana();
                    checarColision();
                    move();
                } else {
                    timer.stop();
                }
                Tablero t = Tablero.getInstance();
                t.repaint();
                setMovimiento(true);
            }
        };
        initGame();
    }

    public final void initGame() {
        izquierda = false;
        derecha = true;
        arriba = false;
        abajo = false;
        enJuego = true;
        //tamaño de la serpiente inicial
        puntos = 3;
        //posición de la serpiente
        for (int z = 0; z < puntos; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        ubicarManzana();
        timer = Reloj.getInstance(RETRASO,listener);
        timer.start();
    }

    //posicionar la manzana aleatoriamente
    private void ubicarManzana() {
        int r = (int) (Math.random() * RAND_POS);
        manzana_x = ((r * PUNTO_SIZE));
        r = (int) (Math.random() * RAND_POS);
        manzana_y = ((r * PUNTO_SIZE));
    }

    private void checarManzana() {
        // si la cabeza esta en la posición de la manzana
        // aumenta el número de puntos y vuelve a ubicar la manzana aleatoriamente
        if ((x[0] == manzana_x) && (y[0] == manzana_y)) {
            puntos++;
            if(puntos%2==0){
                timer.setDelay(RETRASO--);
            }
            ubicarManzana();
        }
    }

    private void move() {
        for (int z = puntos; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
        if (isIzquierda()) {
            x[0] -= PUNTO_SIZE;
        }
        if (isDerecha()) {
            x[0] += PUNTO_SIZE;
        }
        if (isArriba()) {
            y[0] -= PUNTO_SIZE;
        }
        if (isAbajo()) {
            y[0] += PUNTO_SIZE;
        }
    }

    private void checarColision() {
        // Checamos que no golpee con su cuerpo
        for (int z = puntos; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                enJuego = false;
            }
        }
        // Verificamos que no se salga del tablero
        if ((y[0] > ALTO-10) || (y[0] < 0) || (x[0] > ANCHO-10) || (x[0] < 0)) {
            enJuego = false;
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (enJuego) {
            pintarObjetos(g);
        } else {
            gameOver(g);
        }
    }

    private void pintarObjetos(Graphics g) {
        g.drawImage(manzana, manzana_x, manzana_y, this);
        for (int i = 0; i < puntos; i++) {
            if (i == 0) {
                g.drawImage(cabeza, x[i], y[i], this);
            } else {
                g.drawImage(bola, x[i], y[i], this);
            }
        }
    }

    private void gameOver(Graphics g) {
        String mensaje = "Game over";
        Font f = new Font("Helvetica", Font.BOLD, 16);
        g.setFont(f);
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics();
        int anchoMensaje = fm.stringWidth(mensaje);
        int ancho = this.getWidth();
        int alto = this.getHeight();
        g.drawString(mensaje, (ancho / 2) - (anchoMensaje / 2), alto / 2);
    }

    public boolean isDerecha() {
        return derecha;
    }

    public void setDerecha() {
        derecha = true;
        arriba = false;
        abajo = false;
    }

    public boolean isArriba() {
        return arriba;
    }

    public void setArriba() {
        arriba = true;
        derecha = false;
        izquierda = false;
    }

    public boolean isAbajo() {
        return abajo;
    }

    public void setAbajo() {
        abajo = true;
        derecha = false;
        izquierda = false;
    }

    public boolean isIzquierda() {
        return izquierda;
    }

    public void setIzquierda() {
        izquierda = true;
        arriba = false;
        abajo = false;
    }

    /**
     * @return the movimiento
     */
    public boolean isMovimiento() {
        return movimiento;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(boolean movimiento) {
        this.movimiento = movimiento;
    }
    
    public static Tablero getInstance(){
        if(instance == null){
            instance = new Tablero();
        }
        return instance;
    }

}
