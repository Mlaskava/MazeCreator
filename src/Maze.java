import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.Random;
import java.util.Stack;


public class Maze extends Thread {

    private final boolean[][] visited;
    private final double[] column;
    private final double[] row;

    private int nextColumn;
    private int nextRow;
    private int currentRow;
    private int currentColumn;
    private final int startingRow;
    private final int startingColumn;
    private final int mazeSize;

    private final Random random = new Random();
    private final GraphicsContext graphicsContext;

    private final Stack<Pair<Integer, Integer>> pathFromBegin = new Stack<>();


    public Maze(int size, GraphicsContext context) {

        graphicsContext = context;
        mazeSize = size;

        int startPosition = random.nextInt(2);

        startingRow = startPosition == 0 ? random.nextInt(mazeSize) : 0;
        startingColumn = startPosition == 1 ? random.nextInt(mazeSize) : 0;

        currentColumn = startingColumn;
        currentRow = startingRow;

        column = new double[mazeSize];
        row = new double[mazeSize];
        visited = new boolean[mazeSize][mazeSize];



        for (int i = 0; i < this.mazeSize; ++i) {
            column[i] = 30 + i * 20;
            row[i] = column[i];
        }


    }

    @Override
    public void run() {

        boolean ableToMakeMove;
        drawBaseForMaze(mazeSize * 20 + 40);

        do{
            ableToMakeMove = tryToMove();
            try {
                Thread.sleep(10);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }while (ableToMakeMove);
    }


    void drawBaseForMaze(double size) {

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, size, size);


        for (int position = 20; position < size; position += 20) {
            graphicsContext.strokeLine(20, position, size - 20, position);
            graphicsContext.strokeLine(position, 20, position, size - 20);
        }


        graphicsContext.strokeLine(size - 20, 20, size - 20, size - 20);
        graphicsContext.strokeLine(20, size - 20, size - 20, size - 20);
        graphicsContext.setStroke(Color.GREENYELLOW);

        if(startingRow != 0) {
            graphicsContext.strokeLine(20, row[startingRow] - 9, 20, row[startingRow] + 9);
            graphicsContext.strokeLine(size - 20, row[startingRow] - 9, size - 20, row[startingRow] + 9);
        }
        else{
            graphicsContext.strokeLine(column[startingColumn] - 9, 20, column[startingColumn] + 9, 20);
            graphicsContext.strokeLine(column[startingColumn] - 9, size - 20, column[startingColumn] + 9, size - 20);
        }

        graphicsContext.setLineWidth(19);

    }

    boolean ableToPerformMove() {
        boolean canGoUp = (currentRow + 1 < mazeSize && !visited[currentColumn][currentRow + 1]);
        boolean canGoDown = (currentRow - 1 >= 0 && !visited[currentColumn][currentRow - 1]);
        boolean canGoRight = (currentColumn + 1 < mazeSize && !visited[currentColumn + 1][currentRow]);
        boolean canGoLeft = (currentColumn - 1 >= 0 && !visited[currentColumn - 1][currentRow]);
        return (canGoUp || canGoDown || canGoRight || canGoLeft);

    }

    void pickNewCoordinates() {

        do {
            nextColumn = currentColumn;
            nextRow = currentRow;
            int direction = random.nextInt(4);

            switch (direction) {
                case 0 -> nextRow++;
                case 1 -> nextColumn++;
                case 2 -> nextRow--;
                case 3 -> nextColumn--;
            }

        } while (nextRow >= mazeSize || nextColumn >= mazeSize || nextRow < 0 || nextColumn < 0 || visited[nextColumn][nextRow]);

    }


    boolean tryToMove() {

        visited[currentColumn][currentRow] = true;

        if (ableToPerformMove()) {

            pathFromBegin.push(new Pair<>(currentColumn, currentRow));
            pickNewCoordinates();
            graphicsContext.strokeLine(column[currentColumn], row[currentRow], column[nextColumn], row[nextRow]);
            currentRow = nextRow;
            currentColumn = nextColumn;

        }
        else if (!pathFromBegin.isEmpty()) {

                currentColumn = pathFromBegin.peek().getKey();
                currentRow = pathFromBegin.peek().getValue();
                pathFromBegin.pop();

            return currentRow != startingRow || currentColumn != startingColumn;

        }

        return true;
    }
}

