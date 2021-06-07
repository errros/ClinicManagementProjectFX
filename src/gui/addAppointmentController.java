package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.Appointment;
import logic.AppointmentSearchResult;
import logic.Patient;
import logic.Patient;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class addAppointmentController implements Initializable {
    @FXML
    private TextField search;

    @FXML
    private TableView<Patient> patientsTable;

    @FXML
    private TableColumn<Patient, String> col_fname;

    @FXML
    private TableColumn<Patient, String> col_name;

    @FXML
    private TableColumn<Patient, Date> col_bdate;

    @FXML
    private ComboBox<String> filter;

    @FXML
    private Button addButton;

    private ObservableList<Patient> patientsList;
    private FilteredList<Patient> searchResultList;
    private SortedList<Patient> searchResultSortedList;
    private LocalDate myDate;
    private ObservableList<String> comboList = FXCollections.observableArrayList("Ophtamologue","Remplacant");
    public void getDate (LocalDate Date) {
        myDate = Date;
    }
    public void addAppointment(ActionEvent event) throws IOException {
        Patient patient = null;
        patient = patientsTable.getSelectionModel().getSelectedItem();
        if (patient == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No Selected Patient.");
            alert.showAndWait();
        }
        else {
            int doc = 2;
            if (filter.getValue() == "Ophtamologue") {
                doc = 2;
            }
            else if (filter.getValue() == "Remplacant") {
                doc = 3;
            }
            Appointment Appoint = new Appointment(Date.valueOf(myDate.toString()), patient.getPatientId(),doc);
            Appoint.add();

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment added sucessfully!");
            alert.showAndWait();

            FXMLLoader fxmlload = new FXMLLoader(getClass().getResource("Apps.fxml"));
            Parent root = fxmlload.load();
            AppointementsController AppointmentRefresh = fxmlload.getController();
            AppointmentRefresh.refresh();

        }
    }
    public void getThemAll() {
        patientsList = Patient.search("",1);
        searchResultList = new FilteredList<>(patientsList, b -> true);
        search.textProperty().addListener((Observable, oldValue, newValue) -> {
            searchResultList.setPredicate(patient ->  {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (patient.getFirst_name().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                }
                else if (patient.getSecond_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else return false;

            });
        });
        searchResultSortedList = new SortedList<>(searchResultList);
        searchResultSortedList.comparatorProperty().bind(patientsTable.comparatorProperty());
        patientsTable.setItems(searchResultSortedList);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_fname.setCellValueFactory(new PropertyValueFactory<Patient, String>("second_name"));
        col_name.setCellValueFactory(new PropertyValueFactory<Patient, String>("first_name"));
        col_bdate.setCellValueFactory(new PropertyValueFactory<Patient, Date>("dateOfbirth"));
        getThemAll();
        filter.setItems(comboList);
        filter.setValue("Ophtamologue");
    }
}
