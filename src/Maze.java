import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.Random;
import java.util.Stack;


public class Maze extends Thread{

    public boolean[][] visited;
    public Random random = new Random();
    private final double [] column;
    private final double [] row;
    private int nextColumn;
    private int nextRow;
    private int currentRow;
    private int currentColumn;
    private final int startingRow;
    private int size;
    private Stack<Pair<Integer, Integer>> path = new Stack<>();

    private final Canvas canvas;
    private GraphicsContext graphicsContext;

    public Maze(int cells, Canvas canvas) {
        this.canvas = canvas;
        size = cells;
        startingRow = random.nextInt(size);
        nextRow = startingRow;
        nextColumn = 0;
        currentColumn = 0;
        currentRow = nextRow;

        column = new double[size];
        row = new double[size];
        visited = new boolean[size][size];
        graphicsContext = canvas.getGraphicsContext2D();


        for(int i = 0; i < size; ++i){
            column[i] = 30 + i * 20;
            row[i] = 30 + i * 20;
        }


    }

    @Override
    public void run(){
        boolean ableToMakeMove = true;
        while (ableToMakeMove) {
try {
    ableToMakeMove = move();
    Thread.sleep(2);
}
catch (Exception e){
    e.printStackTrace();
}
        }
    }


    void drawMaze(double height, double width) {

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, width, height);



        for(int position = 20; position < height; position += 20){
            graphicsContext.strokeLine(20, position, width - 20, position);
            graphicsContext.strokeLine(position, 20, position, height - 20);
        }


        graphicsContext.strokeLine(width - 20, 20, width - 20, height - 20);
        graphicsContext.strokeLine(20, height - 20, width - 20, height - 20);
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.strokeLine(20, row[startingRow] - 9, 20, row[startingRow] + 9);
        graphicsContext.strokeLine(width - 20, row[startingRow] - 9, width - 20, row[startingRow] + 9);

        graphicsContext.setStroke(Color.YELLOW);
        graphicsContext.setLineWidth(19);

    }

    boolean checkEnvironment() {
        boolean flag1 = (currentRow + 1 < size && !visited[currentColumn][currentRow + 1]);
        boolean flag2 = (currentRow - 1 > -1 && !visited[currentColumn][currentRow - 1]);
        boolean flag3 = (currentColumn + 1 < size && !visited[currentColumn + 1][currentRow]);
        boolean flag4 = (currentColumn - 1 > -1 && !visited[currentColumn - 1][currentRow]);
        //System.out.println("OLD: " + currentColumn + " " + currentRow + flag1 + " " + flag2 + " " + flag3 + " " + flag4);
        return (flag1 || flag2 || flag3 || flag4);

    }

    void pickNewCoordinates() {

        int repeats = 0;

            do {
                repeats++;
                nextColumn = currentColumn;
                nextRow = currentRow;
                int direction = random.nextInt(4);


                switch (direction) {
                    case 0 -> nextRow++;
                    case 1 -> nextColumn++;
                    case 2 -> nextRow--;
                    case 3 -> nextColumn--;
                }

//                if(repeats % 10000 == 0){
//                    System.out.println("NEW: " + nextColumn + " " + nextRow);
//                }


            }while (nextRow >= size || nextColumn >= size || nextRow < 0 || nextColumn < 0 || visited[nextColumn][nextRow]);
            //System.out.println(visited[nextColumn][nextRow]);
            visited[nextColumn][nextRow] = true;

        }



    boolean move() {



            visited[currentColumn][currentRow] = true;
            if(checkEnvironment()){
                pickNewCoordinates();
                graphicsContext.strokeLine(column[currentColumn], row[currentRow], column[nextColumn], row[nextRow]);
                currentRow = nextRow;
                currentColumn = nextColumn;
                graphicsContext = canvas.getGraphicsContext2D();
                path.push(new Pair<>(nextColumn, nextRow));
            }
            else{
//                System.out.print("COMING BACK FROM " + currentColumn + " " + currentRow);
                if(path.isEmpty()){
                    return false;
                }
                currentColumn = path.peek().getKey();
                currentRow = path.peek().getValue();
//                System.out.println(" TO " + currentColumn + " " + currentRow);
                path.pop();
            }


            return true;




    }
}

