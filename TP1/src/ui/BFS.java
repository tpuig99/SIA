package ui;

import game.Constants;
import game.GameState;
import game.ParentState;

import java.util.*;

public class BFS {
    private GameState solution;
    private Queue<GameState> search;
    private Set<String> visited;

    public void findSolutionBFS(GameState start) {
        start.setParent(null);
        search = new LinkedList<>();
        visited = new HashSet<>();

        visited.add(start.getBoardHash());
        search.offer(start);

        while (!search.isEmpty()) {
            GameState current = search.poll(); // poll the first gamestate of the queue

            if (current.won()) { // if the gamestate is a win one, solution found
                solution = current;
                return;
            }

            for (Constants.Direction direction : Constants.Direction.values()) { // for each direction
                GameState auxiliarGame = current.getCopy();
                System.out.printf("Board before going %s\n", direction);
                auxiliarGame.printBoard();

                auxiliarGame.moveInDirection(direction);

                System.out.printf("Board after going %s\n", direction);
                auxiliarGame.printBoard();

                if (!(auxiliarGame.getBoardHash().equals(current.getBoardHash()))) {        // if the movement was successful
                    System.out.println("Board changed!");
                    if(!visited.contains(auxiliarGame.getBoardHash())) {   // if the state was nos previously visited
                        System.out.printf("Board not visited and after going %s\n", direction);
                        visited.add(auxiliarGame.getBoardHash());
                        auxiliarGame.setParent(new ParentState(current, direction));
                        search.offer(auxiliarGame);         // add next node to the search graph
                    }
                }
                System.out.println("///////////Finished Iter///////////");
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
