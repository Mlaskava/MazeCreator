import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;
import java.util.TimerTask;
import java.util.regex.PatternSyntaxException;

public class Window extends Application {

    public static Window windowInstance;
    public Stage primaryStage;
    public int cells = 0;

    public Window() {
        windowInstance = this;
    }

    public void CreateMaze(TextField textField) {

        boolean ableToMakeMove = true;

        try {

            cells = Integer.parseInt(textField.getText());

            if (cells > 35) {
                System.out.println("Przekroczono maksymalny rozmiar!");
            } else {

                Pane root = new Pane();


                Canvas canvas = new Canvas(cells * 20 + 40, cells * 20 + 40);
                GraphicsContext gc = canvas.getGraphicsContext2D();

                root.getChildren().add(canvas);

                primaryStage.setScene(new Scene(root));
                primaryStage.centerOnScreen();

                Maze maze = new Maze(cells, canvas);
                maze.drawMaze(canvas.getHeight(), canvas.getWidth());
                maze.start();
            }
        } catch (PatternSyntaxException e) {
            System.out.println("Nieprawidłowy format wejściowy!");
        } catch (NumberFormatException e) {
            System.out.println("Dane wejściowe nie są liczbami!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage stage) throws Exception {


        primaryStage = stage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("WelcomeWindow.fxml")));

        primaryStage.setTitle("MAZE");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();




    }


    public static void main(String[] args) {
        launch(args);
    }
}
