package ui;

import game.Constants;
import game.GameLoop;
import game.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class MainCanvas extends Canvas implements KeyListener {

    private boolean repaintInProgress = false;
    private GameLoop gameLoop;
    private GameState gameState;

    MainCanvas(JFrame pane) {
        setIgnoreRepaint(true);
        addKeyListener(this);
        // read data and select map
        // read data and start with all the algorithms
        // for each solution, start a gamestate and do whatever it has to do
        gameState = new GameState(6, 3, Constants.map2);
        gameLoop = new GameLoop(gameState);
        Repainter repainter = new Repainter(this);
        new Timer(16, repainter).start();
    }

    public void doRepaint() {
        if (repaintInProgress) {
            return;
        }
        repaintInProgress = true;

        BufferStrategy strategy = getBufferStrategy();
        Graphics g = strategy.getDrawGraphics();
        gameLoop.loop(g, gameState);

        strategy.show();
        Toolkit.getDefaultToolkit().sync();
        repaintInProgress = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        GameState aux = null;

        if (key == KeyEvent.VK_DOWN) aux = gameState.move(0,1);
        else if (key == KeyEvent.VK_UP) aux = gameState.move(0,-1);
        else if (key == KeyEvent.VK_RIGHT) aux = gameState.move(1,0);
        else if (key == KeyEvent.VK_LEFT) aux = gameState.move(-1,0);

        if (aux != null) {
            gameState = aux;
            doRepaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public class Repainter implements ActionListener {
        MainCanvas canvas;

        Repainter(MainCanvas canvas) {
            this.canvas = canvas;
        }

        public void actionPerformed(ActionEvent event) {
            canvas.doRepaint();
        }
    }
}