package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import logic.Login;



/**
 * @author AyoubFRIHAOUI
 * @author WalidABDALLAOUI
 */
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
    //this invisible label is used only to retrieve username from button , whenever you click on a button this label will get the username text
    @FXML
    public Label invisibleUsernameLabel;


    public void user_button_1OnAction(ActionEvent event) {

        invisibleUsernameLabel.setText(user_button_1.getText());
        cleanButtonStyles(user_button_1);
        cleanButtonStyles(user_button_2);
        cleanButtonStyles(user_button_3);
        user_button_1.getStyleClass().add("userbuttonChecked");
        user_button_2.getStyleClass().add("userbutton");
        user_button_3.getStyleClass().add("userbutton");
    }

    public void user_button_2OnAction(ActionEvent event) {

        invisibleUsernameLabel.setText(user_button_2.getText());
        cleanButtonStyles(user_button_1);
        cleanButtonStyles(user_button_2);
        cleanButtonStyles(user_button_3);
        user_button_1.getStyleClass().add("userbutton");

        user_button_2.getStyleClass().add("userbuttonChecked");
        user_button_3.getStyleClass().add("userbutton");
    }

    public void user_button_3OnAction(ActionEvent event) {

        invisibleUsernameLabel.setText(user_button_3.getText());
        cleanButtonStyles(user_button_1);
        cleanButtonStyles(user_button_2);
        cleanButtonStyles(user_button_3);
        user_button_1.getStyleClass().add("userbutton");
        user_button_2.getStyleClass().add("userbutton");
        user_button_3.getStyleClass().add("userbuttonChecked");
    }

    //whenever the mouse is moved update the usernames from the db ,
    // this will allow us to have runtime synchironisation between the users db and login window
    public void back_paneOnMouseMoved(MouseEvent event) {
        logic.Login.initUsernameButtons(user_button_1, user_button_2, user_button_3);
    }


    public void submit_buttonOnAction(ActionEvent event) {
        String user = invisibleUsernameLabel.getText();
        String password = password_field.getText();
        int user_id = Login.login(user, password);
        //show error alert
        if (user_id == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The password entered is incorrect.");
            password_field.setText("");

            alert.showAndWait();
        } else {
           //show succes alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Logged in succesfuly!");
            alert.showAndWait();
        }

    }


    public void cleanButtonStyles(Button b) {
        b.getStyleClass().remove("userbutton");
        b.getStyleClass().remove("userbuttonChecked");

    }


}
