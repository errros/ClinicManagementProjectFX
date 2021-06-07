package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController {
    @FXML
    private AnchorPane anchor_tabs_container;

    @FXML
    private AnchorPane dash_pane;

    @FXML
    private VBox vb_dashboard;

    @FXML
    private HBox hb_home;

    @FXML
    private Label lab_home;

    @FXML
    private HBox hb_waitingroom;

    @FXML
    private HBox hb_patients;

    @FXML
    private Label lab_patients;

    @FXML
    private HBox hb_consultation;

    @FXML
    private Label lab_consultation;

    @FXML
    private HBox hb_accounting;

    @FXML
    private Label lab_accounting;

    @FXML
    private HBox hb_appointments;

    @FXML
    private Label lab_appointments;
    static  private int user_id;
    private Stage stage;
    private Scene scene;
    private Parent root;


    public static void setUser_id(int user_id) {

            AppController.user_id = user_id;
        }
  /* public void openAppointementsWindow(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Appointements.fxml"));
        //scene.getStylesheets().add(getClass().getResource("AppStyling.css").toExternalForm());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();
} */
}
