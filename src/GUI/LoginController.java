package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class LoginController {
    @FXML
    public AnchorPane back_pane;
    @FXML
    public AnchorPane left_pane;
    @FXML
    public Circle back_circle;
    @FXML
    public Label select_user_label;
    @FXML
    public Button user_button_1;
    @FXML
    public Button user_button_2;
    @FXML
    public Label login_label;
    @FXML
    public Button user_button_3;
    @FXML
    public Label password_label;
    @FXML
    public PasswordField password_field;
    @FXML
    public Button submit_button;

}
