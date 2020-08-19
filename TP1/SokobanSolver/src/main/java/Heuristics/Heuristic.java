package Heuristics;

import game.GameState;

public interface Heuristic {
    int eval(GameState state);
    String getName();
}
