package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AppController {
    static  private int user_id;


    public static void setUser_id(int user_id) {

            AppController.user_id = user_id;
        }

}
