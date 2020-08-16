package searchMethods;

import game.GameState;

public interface Heuristic {
    int eval(GameState state);
    String getName();
}
