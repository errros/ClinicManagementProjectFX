package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Patient;
import logic.WaitingRoom;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ModifyPatientController implements Initializable {
    @FXML
    private TextField fisrt_name_txtf;
    @FXML
    private TextField second_name_txtf;
    @FXML
    private TextField sex_txtf;
    @FXML
    private DatePicker date_of_birth_dp;
    @FXML
    private TextField phone_number_txtf;
    @FXML
    private TextField address_txtf;
    @FXML
    private Button add_btn;

    private LocalDate myDate = LocalDate.now();
    private Date dateSQL;
    private String dateformat;
    static public Patient selectedpatient;

    public void modify_Patient(ActionEvent event) throws IOException {
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

                selectedpatient.setFirst_name(fisrt_name_txtf.getText());
                selectedpatient.setSecond_name(second_name_txtf.getText());
                selectedpatient.setAdr(address_txtf.getText());
                selectedpatient.setSex(sex_txtf.getText());
                selectedpatient.setDateOfbirth(Date.valueOf(date_of_birth_dp.getValue().toString()));
                selectedpatient.setNumber(phone_number_txtf.getText());
                selectedpatient.modify();
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Patient added sucessfully!");
                alert.showAndWait();
            }
        }


    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Patient patient = selectedpatient;
        if (patient != null) {
            fisrt_name_txtf.setText(selectedpatient.getFirst_name());
            second_name_txtf.setText(selectedpatient.getSecond_name());
            sex_txtf.setText(selectedpatient.getSex());
           //TODO date_of_birth_dp.setValue();
            phone_number_txtf.setText(selectedpatient.getNumber());
            address_txtf.setText(selectedpatient.getAdr());
        }
        else System.out.println("null");


        myDate = LocalDate.now();
        date_of_birth_dp.setValue(myDate);
        dateformat = myDate.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
        dateSQL = Date.valueOf(myDate.toString());

    }
}
