package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AppAssistController implements Initializable {
    @FXML
    private BorderPane mainPane;
    @FXML
    private AnchorPane anchor_tabs_container;
    @FXML
    private AnchorPane dash_pane;
    @FXML
    private VBox vb_dashboard;
    @FXML
    private Button btn_home;
    @FXML
    private HBox hb_home;
    @FXML
    private Label lab_home;
    @FXML
    private Button btn_watingroom;
    @FXML
    private HBox hb_waitingroom;
    @FXML
    private Button btn_patients;
    @FXML
    private HBox hb_patients;
    @FXML
    private Button btn_accounting;
    @FXML
    private HBox hb_accounting;
    @FXML
    private Button btn_appointments;
    @FXML
    private HBox hb_appointments;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void cleanHboxStyles(Button b) {
        b.getStyleClass().remove("dash_buttons");
        b.getStyleClass().remove("dash_buttons_selected");
    }

    @FXML
    private void loadAccountingPage(ActionEvent e) {
        cleanHboxStyles(btn_accounting);
        cleanHboxStyles(btn_appointments);
        cleanHboxStyles(btn_home);
        cleanHboxStyles(btn_patients);
        cleanHboxStyles(btn_watingroom);
        btn_appointments.getStyleClass().add("dash_buttons");
        btn_accounting.getStyleClass().add("dash_buttons_selected");
        btn_home.getStyleClass().add("dash_buttons");
        btn_patients.getStyleClass().add("dash_buttons");
        btn_watingroom.getStyleClass().add("dash_buttons");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("Bill");
        mainPane.setCenter(view);

    }

    @FXML
    private void loadHomePage(ActionEvent e) {
        cleanHboxStyles(btn_accounting);
        cleanHboxStyles(btn_appointments);
        cleanHboxStyles(btn_home);
        cleanHboxStyles(btn_patients);
        cleanHboxStyles(btn_watingroom);
        btn_appointments.getStyleClass().add("dash_buttons");
        btn_accounting.getStyleClass().add("dash_buttons");
        btn_home.getStyleClass().add("dash_buttons_selected");
        btn_patients.getStyleClass().add("dash_buttons");
        btn_watingroom.getStyleClass().add("dash_buttons");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("Home");
        mainPane.setCenter(view);

    }

    @FXML
    private void loadAppointmentsPage(ActionEvent e) {
        cleanHboxStyles(btn_accounting);
        cleanHboxStyles(btn_appointments);
        cleanHboxStyles(btn_home);
        cleanHboxStyles(btn_patients);
        cleanHboxStyles(btn_watingroom);
        btn_appointments.getStyleClass().add("dash_buttons_selected");
        btn_accounting.getStyleClass().add("dash_buttons");
        btn_home.getStyleClass().add("dash_buttons");
        btn_patients.getStyleClass().add("dash_buttons");
        btn_watingroom.getStyleClass().add("dash_buttons");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("Apps");
        mainPane.setCenter(view);

    }
    @FXML
    private void loadWaitingRoomPage(ActionEvent e) {
        cleanHboxStyles(btn_accounting);
        cleanHboxStyles(btn_appointments);
        cleanHboxStyles(btn_home);
        cleanHboxStyles(btn_patients);
        cleanHboxStyles(btn_watingroom);
        btn_appointments.getStyleClass().add("dash_buttons");
        btn_accounting.getStyleClass().add("dash_buttons");
        btn_home.getStyleClass().add("dash_buttons");
        btn_patients.getStyleClass().add("dash_buttons");
        btn_watingroom.getStyleClass().add("dash_buttons_selected");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("WaitingRoom");
        mainPane.setCenter(view);
    }
    @FXML
    private void loadPatientsPage(ActionEvent e) {
        cleanHboxStyles(btn_accounting);
        cleanHboxStyles(btn_appointments);
        cleanHboxStyles(btn_home);
        cleanHboxStyles(btn_patients);
        cleanHboxStyles(btn_watingroom);
        btn_appointments.getStyleClass().add("dash_buttons");
        btn_accounting.getStyleClass().add("dash_buttons");
        btn_home.getStyleClass().add("dash_buttons");
        btn_patients.getStyleClass().add("dash_buttons_selected");
        btn_watingroom.getStyleClass().add("dash_buttons");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("PatientsScene");
        mainPane.setCenter(view);
    }
    @FXML
    void loadConsultationPage(ActionEvent e) {
        cleanHboxStyles(btn_accounting);
        cleanHboxStyles(btn_appointments);

        cleanHboxStyles(btn_home);
        cleanHboxStyles(btn_patients);
        cleanHboxStyles(btn_watingroom);
        btn_appointments.getStyleClass().add("dash_buttons");
        btn_accounting.getStyleClass().add("dash_buttons");
        btn_home.getStyleClass().add("dash_buttons");
        btn_patients.getStyleClass().add("dash_buttons");
        btn_watingroom.getStyleClass().add("dash_buttons");
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
        cleanHboxStyles(btn_accounting);
        cleanHboxStyles(btn_appointments);
        cleanHboxStyles(btn_home);
        cleanHboxStyles(btn_patients);
        cleanHboxStyles(btn_watingroom);
        btn_appointments.getStyleClass().add("dash_buttons");
        btn_accounting.getStyleClass().add("dash_buttons");
        btn_home.getStyleClass().add("dash_buttons_selected");
        btn_patients.getStyleClass().add("dash_buttons");
        btn_watingroom.getStyleClass().add("dash_buttons");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("Home");
        mainPane.setCenter(view);

    }
}
