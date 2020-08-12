package game;

public class ParentState {
    private GameState gameState;
    private Constants.Direction solutionDirection;

    public ParentState(GameState gameState, Constants.Direction solutionDirection) {
        this.gameState = gameState;
        this.solutionDirection = solutionDirection;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Constants.Direction getSolutionDirection() {
        return solutionDirection;
    }

    public void setSolutionDirection(Constants.Direction solutionDirection) {
        this.solutionDirection = solutionDirection;
    }
}
