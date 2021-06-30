package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {
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

    @FXML
    private BorderPane mainPane;

    static public int user_id;
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private void loadAppointmentsPage(ActionEvent e) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("Apps");
        mainPane.setCenter(view);
    }
    @FXML
    private void loadWaitingRoomPage(ActionEvent e) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("WaitingRoom");
        mainPane.setCenter(view);
    }
    @FXML
    private void loadPatientsPage(ActionEvent e) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("PatientsScene");
        mainPane.setCenter(view);
    }
    @FXML
     void loadConsultationPage(ActionEvent e) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("AppCons");
        mainPane.setCenter(view);
    }

    void loadCons() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("AppCons");
        mainPane.setCenter(view);
    }
    public static void setUser_id(int user_id) {
        AppController.user_id = user_id;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}