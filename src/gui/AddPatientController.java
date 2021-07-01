package gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.Patient;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddPatientController implements Initializable {
    @FXML
    private TextField fisrt_name_txtf;

    @FXML
    private TextField second_name_txtf;


    @FXML
    private DatePicker date_of_birth_dp;


    @FXML
    private TextField phone_number_txtf;

    @FXML
    private TextField address_txtf;

    @FXML
    private Button add_btn;

    @FXML
    private ComboBox sex_combo;
    Stage window;


    private LocalDate myDate = LocalDate.now();
    private Date dateSQL;
    private String dateformat;

    public void add_Patient(ActionEvent event) throws IOException {
        Patient patient;

        if (fisrt_name_txtf.getText().trim().isBlank() || second_name_txtf.getText().trim().isBlank()
                || date_of_birth_dp == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You must enter the first name, family name and date of birth");
            alert.showAndWait();
        } else {
            myDate = date_of_birth_dp.getValue();
            dateSQL = Date.valueOf(myDate.toString());
            String sdate =   myDate.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
            if (sdate.equals(dateformat)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You must enter the first name, family name and date of birth");
                alert.showAndWait();
            }
            else {

                patient = new Patient(fisrt_name_txtf.getText(), second_name_txtf.getText(),
                        dateSQL, sex_combo.getValue().toString(), address_txtf.getText(), phone_number_txtf.getText());
                patient.add();
                PatientsSceneController.newPatient=true;
                window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Patient added sucessfully!");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> sexItems = FXCollections.observableArrayList();
        sexItems.add("M");
        sexItems.add("F");
        sex_combo.setItems(sexItems);
        myDate = LocalDate.now();
        date_of_birth_dp.setValue(myDate);
        dateformat = myDate.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
        dateSQL = Date.valueOf(myDate.toString());
    }
}
