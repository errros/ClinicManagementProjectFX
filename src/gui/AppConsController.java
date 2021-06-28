package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.AppConsultationLogic;
import logic.Patient;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class AppConsController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        {
            Patient person = AppConsultationLogic.person;
            patien_fname.setText(person.getFirst_name() + " " + person.getSecond_name());
            sName.setText(person.getFirst_name());
            sFameName.setText(person.getSecond_name());
            sSex.setText(person.getSex());
            sPhone.setText(person.getNumber());
            sDateofbirth.setText(person.getDateOfbirth().toString());
            LocalDate date = person.getDateOfbirth().toLocalDate();
            Period diff = Period.between(date, LocalDate.now());
            int y = diff.getYears();
            sAge.setText(Integer.toString(y));
        }
    }

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
    private TextField sName;

    @FXML
    private TextField sFameName;

    @FXML
    private TextField sSex;

    @FXML
    private TextField sAge;

    @FXML
    private TextField sDateofbirth;

    @FXML
    private TextField sPhone;

    @FXML
    private Label patien_fname;

    @FXML
    private Button View_Medicaments;

    public void View_Medicaments(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Medicaments.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("cant load the window");
        }
    }

}
