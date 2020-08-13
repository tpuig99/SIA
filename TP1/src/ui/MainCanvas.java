package ui;

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
    private boolean active = false;

    MainCanvas(JFrame pane) {
        setIgnoreRepaint(true);
        addKeyListener(this);

        gameState = new GameState();
        gameLoop = new GameLoop(gameState);
        DFS search = new DFS(gameState);
        //IDDFS search = new IDDFS(gameState);
        ArrayList<Integer> solution = search.getSolution();

        RepainterSearch repainter = new RepainterSearch(this,solution);
        new Timer(50, repainter).start();
    }

    public void doRepaint() {
        if (repaintInProgress) {
            return;
        }
        repaintInProgress = true;

        BufferStrategy strategy = getBufferStrategy();
        Graphics g = strategy.getDrawGraphics();
        gameLoop.loop(g);

        strategy.show();
        Toolkit.getDefaultToolkit().sync();
        repaintInProgress = false;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_SPACE)
            active = true;
        if (key == KeyEvent.VK_DOWN) gameState.moveDown();
        else if (key == KeyEvent.VK_UP) gameState.moveUp();
        else if (key == KeyEvent.VK_RIGHT) gameState.moveRight();
        else if (key == KeyEvent.VK_LEFT) gameState.moveLeft();

        if (key == KeyEvent.VK_SPACE && gameState.isOver()) gameState.resetGame();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
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

    public class RepainterSearch implements ActionListener {
        MainCanvas canvas;
        ArrayList<Integer> solution;
        int index = 0;

        RepainterSearch(MainCanvas canvas,ArrayList<Integer> solution) {
            this.canvas = canvas;
            this.solution = solution;
        }

        public void actionPerformed(ActionEvent event) {
            if(index<solution.size() && active) {
                switch (solution.get(index++)) {
                    case 0: {
                        gameState.moveDown();
                        break;
                    }
                    case 1: {
                        gameState.moveUp();
                        break;
                    }
                    case 2: {
                        gameState.moveLeft();
                        break;
                    }
                    case 3: {
                        gameState.moveRight();
                        break;
                    }
                }
            }
            canvas.doRepaint();
        }
    }
}