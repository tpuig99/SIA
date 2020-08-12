package ui;

import game.Constants;
import game.GameState;
import game.ParentState;

import java.util.*;

public class BFS {
    private GameState solution;
    private Queue<GameState> search;
    private Set<GameState> visited;

    public void findSolutionBFS(GameState start) {
        start.setParent(null);
        search = new LinkedList<>();
        visited = new HashSet<>();

        visited.add(start);
        search.offer(start);

        while (!search.isEmpty()) {
            GameState current = search.poll(); // poll the first gamestate of the queue

            if (current.won()) { // if the gamestate is a win one, solution found
                solution = current;
                return;
            }

            for (Constants.Direction direction : Constants.Direction.values()) { // for each direction
                GameState auxiliarGame = current.getCopy();
                auxiliarGame.moveInDirection(direction);
                if (!auxiliarGame.equals(current)) {        // if the movement was successful
                    if(!visited.contains(auxiliarGame)) {   // if the state was nos previously visited
                        System.out.printf("Player: (%s,%s). Bags: %s%n",
                                auxiliarGame.player.x, auxiliarGame.player.y, auxiliarGame.bags.toString());
                        visited.add(auxiliarGame);
                        auxiliarGame.setParent(new ParentState(current, direction));
                        search.offer(auxiliarGame);         // add next node to the search graph
                    }
                }
            }
        }
    }

    public Stack<Constants.Direction> getSolution() {
        if (solution == null) {
            throw new RuntimeException("No solution was called");
        }
        Stack<Constants.Direction> directions = new Stack<>();
        ParentState aux = solution.getParent();
        while (aux != null) {
            directions.push(aux.getSolutionDirection());
            aux = aux.getGameState().getParent();
        }
        return directions;
    }


}
