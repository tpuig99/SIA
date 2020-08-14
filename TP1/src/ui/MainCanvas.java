package ui;

import com.sun.org.apache.bcel.internal.classfile.ConstantString;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import game.Constants;
import game.GameLoop;
import game.GameState;
import searchMethods.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Stack;

public class MainCanvas extends Canvas implements KeyListener {

    private boolean repaintInProgress = false;
    private GameLoop gameLoop;
    private Stack<Constants.Direction> steps;
    private GameState gameState;

    MainCanvas(JFrame pane) {
        setIgnoreRepaint(true);
        addKeyListener(this);

        steps = executeSearchMethod(SearchMethodName.IDDFS);
        // read data and select map
        // read data and start with all the algorithms
        // for each solution, start a gamestate and do whatever it has to do
        gameState = new GameState(15, 10, Constants.map1);
        gameLoop = new GameLoop(gameState);
        Repainter repainter = new Repainter(this);
        new Timer(16, repainter).start();
    }

    private Stack<Constants.Direction> executeSearchMethod(SearchMethodName name) {
        GameState solution = null;
        SearchMethod searchMethod = null;
        switch (name) {
            case BFS:
                searchMethod = new BFS();
                solution = searchMethod.run(new GameState(15, 10, Constants.map1));
                break;
            case DFS:
                searchMethod = new DFS();
                solution = searchMethod.run(new GameState(15, 10, Constants.map1));
                break;
            case IDDFS:
                searchMethod = new IDDFS();
                solution = searchMethod.run(new GameState(15, 10, Constants.map1));
                break;
            default:
                break;
        }
        return retrieveSteps(solution, searchMethod);
    }

    private Stack<Constants.Direction> retrieveSteps (GameState solution, SearchMethod searchMethod) {
        GameState curr = solution;
        int moves = 0;

        Stack<Constants.Direction> steps = new Stack<>();
        while (curr != null) {
            if (curr.getParent() != null) {
                steps.push(curr.getDirectionFromParent());
            }
            curr = curr.getParent();
            moves++;
        }

        String status = "ÉXITO";
        if(searchMethod.getTimeOut())
            status = "ABORTADO POR TIEMPO";
        else if(solution == null)
            status = "SOLUCIÓN NO ENCONTRADA";
        System.out.printf("Algoritmo de búsqueda: %s [%s]\n", searchMethod.getName(), status);
        System.out.printf("Tiempo total: %.2fs\n", searchMethod.getTotalTimeMillis() / 1000.0);
        System.out.printf("Tiempo invertido en cálculo de la heurística: %.2fs\n", searchMethod.getHeuristicTimeMillis() / 1000.0);
        System.out.printf("Tiempo invertido en el método: %.2fs\n", (searchMethod.getTotalTimeMillis() - searchMethod.getHeuristicTimeMillis()) / 1000.0);
        System.out.printf("Cantidad total de nodos expandidos: %d\n", searchMethod.getExpandedNodes());
        System.out.printf("Cantidad de nodos en la frontera al final la ejecución: %d\n", searchMethod.getFrontierCount());
        if (solution != null) {
            System.out.println("Costo de la solución: " + moves);
            System.out.println("Profundidad de la solución: " + moves);
        }
        return steps;
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

    public void performSteps (Stack<Constants.Direction> steps) {
        while (!steps.empty()) {
            GameState aux = null;
            switch (steps.pop()) {
                case up: aux = gameState.moveInDirection(Constants.Direction.up); break;
                case down: aux = gameState.moveInDirection(Constants.Direction.down); break;
                case left: aux = gameState.moveInDirection(Constants.Direction.left); break;
                case right: aux = gameState.moveInDirection(Constants.Direction.right); break;
            }
            gameState = aux;
            doRepaint();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            performSteps(steps);
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