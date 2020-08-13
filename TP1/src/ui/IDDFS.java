package ui;

import game.GameState;
import game.cell.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static game.Constants.HEIGHT;
import static game.Constants.WIDTH;

public class IDDFS {
        public static final int down = 0;
        public static final int up = 1;
        public static final int left = 2;
        public static final int right = 3;
        GameState game;
        HashSet<String> history = new HashSet<>();
        int finished = 0; //0 not, 1 won, 2 lose

        public IDDFS(GameState game) {
            this.game = game;
        }

        public ArrayList<Integer> iddfs(ArrayList<ArrayList<Integer>> solutions, ArrayList<Integer> solution){
            GameState auxiliarGame = new GameState();
            completeBoard(solution,auxiliarGame,history);
            //System.out.printf("Player x:%d y:%d. %s\n\n",game.player.x,game.player.y,auxiliarGame.player.x,auxiliarGame.player.y,code);
            iddfsStep(solutions,solution,auxiliarGame);
            if(finished == 1){
                return solution;
            }
            solutions.remove(solution);
            if(finished == 2){
                finished = 0;
                return null;
            }
            auxiliarGame.moveDown();
            //System.out.printf("%s AFTER DOWN %s\n",code,boardHash(auxiliarGame));
            if(!boardCompare(auxiliarGame.board, game.board)){
                if(!history.contains(boardHash(auxiliarGame))) {
                    //System.out.printf("MOVE DOWN AND HAS BEEN A CHANGE. Player x1:%d y1:%d x2:%d y2:%d.\n\n", game.player.x, game.player.y, auxiliarGame.player.x, auxiliarGame.player.y);
                    solution.add(down);
                    history.add(boardHash(auxiliarGame));
                    iddfsStep(solutions, solution, game);
                    if (finished == 1)
                        return solution;
                    solution.remove(solution.size()-1);
                }
                    completeBoard(solution,auxiliarGame,history);

            }
            auxiliarGame.moveLeft();
            //chequeo que haya habido un cambio == se pudo realizar la acción
            if(!boardCompare(auxiliarGame.board, game.board)){
                //System.out.printf("%s --> AFTER left %s\n",code,boardHash(auxiliarGame));
                if(!history.contains(boardHash(auxiliarGame))) {
                    solution.add(left);
                    history.add(boardHash(auxiliarGame));
                    iddfsStep(solutions, solution, game);
                    if (finished == 1)
                        return solution;
                    solution.remove(solution.size()-1);

                }
                completeBoard(solution,auxiliarGame,history);
            }
            auxiliarGame.moveUp();
            //System.out.printf("%s --> AFTER up %s\n",code,boardHash(auxiliarGame));
            //chequeo que haya habido un cambio == se pudo realizar la acción
            if(!boardCompare(auxiliarGame.board, game.board)){
                if(!history.contains(boardHash(auxiliarGame))) {
                    solution.add(up);
                    history.add(boardHash(auxiliarGame));
                    iddfsStep(solutions, solution, game);
                    if (finished == 1)
                        return solution;
                    solution.remove(solution.size()-1);

                }
                    completeBoard(solution,auxiliarGame,history);
            }
            auxiliarGame.moveRight();
            //System.out.printf("%s --> AFTER right %s\n",code,boardHash(auxiliarGame));
            //chequeo que haya habido un cambio == se pudo realizar la acción
            if(!boardCompare(auxiliarGame.board, game.board)){
                if(!history.contains(boardHash(auxiliarGame))) {
                    solution.add(right);
                    history.add(boardHash(auxiliarGame));
                    iddfsStep(solutions, solution, game);
                    if (finished == 1)
                        return solution;
                    solution.remove(solution.size()-1);

                }
                    completeBoard(solution,auxiliarGame,history);
            }
            finished = 0;
            return solution;
        }
        String boardHash(GameState game){
            return Integer.toString(game.player.x) + Integer.toString(game.player.y) + '-' +
                    Integer.toString(game.bags.get(0).x) + Integer.toString(game.bags.get(0).y) + '-' +
                    Integer.toString(game.bags.get(1).x) + Integer.toString(game.bags.get(1).y)  ;
        }
        void completeBoard(ArrayList<Integer> solution, GameState auxiliarGame, HashSet<String> history){
            auxiliarGame.resetGame();
            for (int dir:solution) {
                switch (dir){
                    case up:{
                        auxiliarGame.moveUp();
                        break;
                    }
                    case down:{
                        auxiliarGame.moveDown();
                        break;
                    }
                    case left:{
                        auxiliarGame.moveLeft();
                        break;
                    }
                    case right:{
                        auxiliarGame.moveRight();
                        break;
                    }
                }
            }
        }
        public ArrayList<Integer> getSolution() {
            HashSet<String> history = new HashSet<>();
            history.add(boardHash(game));
            return iddfsController();
        }

    private ArrayList<Integer> iddfsController() {
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        ArrayList<Integer> solution = new ArrayList<>();
        solution = iddfs(solutions,solution);
        int count = 0;
        while (finished!=1){
            System.out.printf("%d\n\n",count++);
            ArrayList<ArrayList<Integer>> auxiliar = (ArrayList<ArrayList<Integer>>) solutions.clone();
            for(int i=0;i<auxiliar.size() && finished!=1;i++){
                solution = iddfs(solutions,auxiliar.get(i));
            }
        }
        return solution;
    }
    void iddfsStep(ArrayList<ArrayList<Integer>> solutions, ArrayList<Integer> solution, GameState auxiliarGame){
        if(auxiliarGame.won()){
            //System.out.println("WON!");
            finished = 1;
        }else if(!auxiliarGame.isMovable()){
            //System.out.println("NOT MOVABLE!");
            finished = 2;
        } else if(!solutions.contains(solution)){
            ArrayList<Integer> copy = (ArrayList<Integer>) solution.clone();
            solutions.add(copy);
        }
    }
    boolean boardCompare(Cell[][] b1, Cell[][] b2){

            for(int width=0; width<WIDTH;width++){
                for(int height=0; height<HEIGHT;height++){
                    Cell c1 = b1[width][height];
                    Cell c2 = b2[width][height];
                    if(!c1.equals(c2)){
                        return false;
                    }
                }
            }
            return true;
        }


}
