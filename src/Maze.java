import javafx.util.Pair;

import java.util.Random;


public class Maze {

    public Pair<Integer, Integer> [][] cells;
    public Random random = new Random();
    public Maze(int columns, int rows){

        cells = new Pair[columns][rows];


        for(int i = 0; i < columns; ++i){
            for(int j = 0; j < rows; ++j){
                cells[i][j] = new Pair<>(30 + j * 20, 30 + i * 20);
            }
        }


    }

}
