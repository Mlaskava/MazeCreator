import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class WelcomeWindowController {

    @FXML
    private TextField textField;

    @FXML
    private Button enterButton;

    @FXML
    private Label errorLabel;

    EventHandler<MouseEvent> buttonClicked = event -> Window.windowInstance.CreateMaze(textField.getText(), errorLabel);

    EventHandler<KeyEvent> enterPressed = event -> {
        if(event.getCode().equals(KeyCode.ENTER))
        Window.windowInstance.CreateMaze(textField.getText(), errorLabel);
    };


    @FXML
    public void initialize() {
        enterButton.addEventHandler(MouseEvent.MOUSE_CLICKED, buttonClicked);
        textField.addEventHandler(KeyEvent.KEY_PRESSED, enterPressed);
    }
}
