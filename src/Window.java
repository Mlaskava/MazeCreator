import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.Objects;


public class Window extends Application {

    public static Window windowInstance;
    public Stage primaryStage;
    Parent welcomeSceneRoot;

    public Window() {
        windowInstance = this;
    }

    public void CreateMaze(String text, Label errorLabel) {

        int size;

        try {

            size = Integer.parseInt(text);

            if (size > 35) {
                errorLabel.setText("Maximum size exceeded!");

            } else {

                Pane root = new Pane();


                Canvas canvas = new Canvas(size * 20 + 40, size * 20 + 40);
                root.getChildren().add(canvas);

                primaryStage.setScene(new Scene(root));
                primaryStage.centerOnScreen();

                Maze maze = new Maze(size, canvas.getGraphicsContext2D());
                maze.start();
            }
        }
        catch (NumberFormatException e) {
            errorLabel.setText("  Incorrect input!");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    @Override
    public void start(Stage stage) throws Exception {


        primaryStage = stage;
        welcomeSceneRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("WelcomeWindow.fxml")));

        primaryStage.setTitle("MAZE");
        primaryStage.setScene(new Scene(welcomeSceneRoot));
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();




    }


    public static void main(String[] args) {
        launch(args);
    }
}
