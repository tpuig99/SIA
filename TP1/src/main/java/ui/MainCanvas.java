package ui;


import Heuristics.*;
import game.Constants;
import game.GameLoop;
import game.GameState;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import searchMethods.*;
import searchMethods.IDDFS;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MainCanvas extends Canvas implements KeyListener {

    private boolean repaintInProgress = false;
    private GameLoop gameLoop;
    private Queue<Stack<Constants.Direction>> queue;
    private GameState gameState;
    private int width;
    private int height;
    private FileWriter file;

    private String map;
    private List<SearchMethodName> searchMethod;
    private List<HeuristicName> heuristic;

    public MainCanvas(JFrame pane) {
        setIgnoreRepaint(true);
        addKeyListener(this);

        getConfigInfo();
        getSizes(map);
        queue = new LinkedList<>();
        try{
        file = new FileWriter("./results.txt");
        }catch (IOException e){}

        for (SearchMethodName name : searchMethod) {
            if (name == SearchMethodName.A_STAR || name == SearchMethodName.G_GREEDY || name == SearchMethodName.IDA_STAR) {
                for (HeuristicName hname : heuristic) {
                    queue.offer(executeSearchMethod(name, hname, map));
                }
            }
            else {
                queue.offer(executeSearchMethod(name, map));
            }
        }

        gameState = new GameState(width, height, map);
        gameLoop = new GameLoop(gameState);
//        gameState = new GameState(width, height, map);
//        steps = executeSearchMethod(searchMethod.get(0),map);
//        gameLoop = new GameLoop(gameState);
        try{
        file.close();
        }catch (IOException e){}
        Repainter repainter = new Repainter(this);
        new Timer(16, repainter).start();
    }

    private void getConfigInfo() {
        JSONParser jsonParser = new JSONParser();
        JSONObject configObj = null;
        try {
            // configObj = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/config.json"));
            configObj = (JSONObject) jsonParser.parse(new FileReader("./config.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (configObj != null) {
            map = processMap(((Long) configObj.get("map")).intValue());
            searchMethod = processSearchMethod(((String) configObj.get("searchMethod")).toLowerCase());
            heuristic = processHeuristic(((String) configObj.get("heuristic")).toLowerCase());
        }
    }

    private List<HeuristicName> processHeuristic (String input) {
        List<HeuristicName> heuristicNameList = null;
        if (searchMethod.contains(SearchMethodName.G_GREEDY) || searchMethod.contains(SearchMethodName.A_STAR)
        || searchMethod.contains(SearchMethodName.IDA_STAR)) {
            heuristicNameList = new ArrayList<>();
            switch (input) {
                case "all":
                    heuristicNameList.add(HeuristicName.H1);
                    heuristicNameList.add(HeuristicName.H2);
                    heuristicNameList.add(HeuristicName.H3);
                    break;
                case "h1":
                    heuristicNameList.add(HeuristicName.H1);
                    break;
                case "h2":
                    heuristicNameList.add(HeuristicName.H2);
                    break;
                case "h3":
                    heuristicNameList.add(HeuristicName.H3);
                    break;
                default:
                    break;
            }
        }
        return heuristicNameList;
    }

    private List<SearchMethodName> processSearchMethod (String input) {
        List<SearchMethodName> searchMethodNameList = new ArrayList<>();
        switch (input) {
            case "all":
                searchMethodNameList.add(SearchMethodName.BFS);
                searchMethodNameList.add(SearchMethodName.DFS);
                searchMethodNameList.add(SearchMethodName.IDDFS);
                searchMethodNameList.add(SearchMethodName.G_GREEDY);
                searchMethodNameList.add(SearchMethodName.A_STAR);
                searchMethodNameList.add(SearchMethodName.IDA_STAR);
                break;
            case "dfs":
                searchMethodNameList.add(SearchMethodName.DFS);
                break;
            case "bfs":
                searchMethodNameList.add(SearchMethodName.BFS);
                break;
            case "iddfs":
                searchMethodNameList.add(SearchMethodName.IDDFS);
                break;
            case "g_greedy":
                searchMethodNameList.add(SearchMethodName.G_GREEDY);
                break;
            case "a_star":
                searchMethodNameList.add(SearchMethodName.A_STAR);
                break;
            case "ida_star":
                searchMethodNameList.add(SearchMethodName.IDA_STAR);
                break;
            default:
                searchMethodNameList = null;
                break;
        }
        return searchMethodNameList;
    }

    private String processMap (int mapNumber) {
        String toReturn = null;
        switch (mapNumber) {
            case 1:
                toReturn = Constants.easy;
                break;
            case 2:
                toReturn = Constants.medium;
                break;
            case 3:
                toReturn = Constants.hard;
                break;
            default:
                break;
        }
        return toReturn;
    }

    private void getSizes(String map) {
        String[] lines = map.split("\n");
        height = lines.length;
        width = lines[0].length();
    }

    private Stack<Constants.Direction> executeSearchMethod(SearchMethodName name, String map) {
        return executeSearchMethod(name, null, map);
    }

    private Stack<Constants.Direction> executeSearchMethod(SearchMethodName name, HeuristicName hname, String map) {
        GameState solution = null;
        SearchMethod searchMethod = null;
        Heuristic heuristic = null;
        if (hname != null) {
            switch (hname) {
                case H1:
                    heuristic = new H1();
                    break;
                case H2:
                    heuristic = new H2();
                    break;
                case H3:
                    heuristic = new H3();
                    break;
                default:
                    break;
            }
        }

        switch (name) {
            case BFS:
                searchMethod = new BFS();
                solution = searchMethod.run(new GameState(width, height, map));
                break;
            case DFS:
                searchMethod = new DFS();
                solution = searchMethod.run(new GameState(width, height,map));
                break;
            case IDDFS:
                searchMethod = new IDDFS();
                solution = searchMethod.run(new GameState(width, height, map));
                break;
            case G_GREEDY:
                searchMethod = new GlobalGreedy(heuristic);
                solution = searchMethod.run(new GameState(width, height, map));
                break;
            case A_STAR:
                searchMethod = new AStar(heuristic);
                solution = searchMethod.run(new GameState(width, height, map));
                break;
            case IDA_STAR:
                searchMethod = new IDAStar(heuristic);
                solution = searchMethod.run(new GameState(width, height, map));
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
        try{
        String status = "ÉXITO";
        if(searchMethod.getTimeOut())
            status = "ABORTADO POR TIEMPO";
        else if(solution == null)
            status = "SOLUCIÓN NO ENCONTRADA";
        file.write(String.format("Algoritmo de búsqueda: %s [%s]\n", searchMethod.getName(), status));
        System.out.printf("Algoritmo de búsqueda: %s [%s]\n", searchMethod.getName(), status);
        file.write(String.format("Tiempo total: %.2fs\n", searchMethod.getTotalTimeMillis() / 1000.0));
        System.out.printf("Tiempo total: %.2fs\n", searchMethod.getTotalTimeMillis() / 1000.0);
        file.write(String.format("Tiempo invertido en cálculo de la heurística: %.2fs\n", searchMethod.getHeuristicTimeMillis() / 1000.0));
        System.out.printf("Tiempo invertido en cálculo de la heurística: %.2fs\n", searchMethod.getHeuristicTimeMillis() / 1000.0);
        file.write(String.format("Tiempo invertido en el método: %.2fs\n", (searchMethod.getTotalTimeMillis() - searchMethod.getHeuristicTimeMillis()) / 1000.0));
        System.out.printf("Tiempo invertido en el método: %.2fs\n", (searchMethod.getTotalTimeMillis() - searchMethod.getHeuristicTimeMillis()) / 1000.0);
        file.write(String.format("Cantidad total de nodos expandidos: %d\n", searchMethod.getExpandedNodes()));
        System.out.printf("Cantidad total de nodos expandidos: %d\n", searchMethod.getExpandedNodes());
        file.write(String.format("Cantidad de nodos en la frontera al final la ejecución: %d\n", searchMethod.getFrontierCount()));
        System.out.printf("Cantidad de nodos en la frontera al final la ejecución: %d\n", searchMethod.getFrontierCount());
        if (solution != null) {
            file.write(String.format("Costo de la solución: " + moves + '\n'));
            System.out.println("Costo de la solución: " + moves);
            file.write(String.format("Profundidad de la solución: " + moves + '\n'));
            System.out.println("Profundidad de la solución: \n" + moves);
        }
        file.flush();
        } catch (IOException e){
            System.out.println("An error has occured");
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
                Thread.sleep(15);
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
            if (!queue.isEmpty()) {
                gameState = new GameState(width, height, map);
                gameLoop = new GameLoop(gameState);
                performSteps(queue.poll());
            }
            else {
                System.out.println("No more elements to show");
            }
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