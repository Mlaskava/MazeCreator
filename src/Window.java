import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.regex.PatternSyntaxException;

public class Window extends Application {

    public static Window windowInstance;
    public Stage primaryStage;
    public int height = 0;
    public int width = 0;

    public Window(){
        windowInstance = this;
    }

    public void CreateMaze(TextField textField){
        String text = textField.getText();
        String[] size;


        if(text.contains("x") || text.contains("X")) {
            try {
                size = text.split("x");

                height = Integer.parseInt(size[0]);
                width = Integer.parseInt(size[1]);


                if(height > 740 || width > 840){
                    System.out.println("Przekroczono maksymalny rozmiar!");
                }



                else {

                    Pane root = new Pane();
                    Maze maze = new Maze(width, height);
                    Canvas canvas = new Canvas(width * 20 + 40, height * 20 + 40);
                    root.getChildren().add(canvas);
                    GraphicsContext gc = canvas.getGraphicsContext2D();

                    gc.setFill(Color.WHITE);
                    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());



                    for(int position = 20; position < canvas.getHeight(); position += 20){
                        gc.strokeLine(20, position, canvas.getWidth() - 20, position);
                        gc.strokeLine(position, 20, position, canvas.getHeight() - 20);
                    }


                    gc.strokeLine(canvas.getWidth() - 20, 20, canvas.getWidth() - 20, canvas.getHeight() - 20);
                    gc.strokeLine(20, canvas.getHeight() - 20, canvas.getWidth() - 20, canvas.getHeight() - 20);
                    gc.setStroke(Color.YELLOW);
                    gc.setLineWidth(19);
                    gc.strokeLine(30, 30, 50, 30);
                    gc.strokeLine(90, 30, 90, 50);



                    primaryStage.setScene(new Scene(root));
                    primaryStage.centerOnScreen();
                }
            }
            catch (PatternSyntaxException e) {
                System.out.println("Nieprawidłowy format wejściowy!");
            }
            catch (NumberFormatException e) {
                System.out.println("Dane wejściowe nie są liczbami!");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Należy podać 2 liczby, oddzielone x!");
        }
    }


    @Override
    public void start(Stage stage) throws Exception{
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("WelcomeWindow.fxml"));

        primaryStage.setTitle("MAZE");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();





    }


    public static void main(String[] args) {
        launch(args);
    }
}
