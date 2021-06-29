package gui;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.Patient;
import logic.WaitingRoom;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PatientsSceneController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField searchField;
    @FXML
    private Label DateTimeLab;
    @FXML
    private TableView<Patient> PatientsTableView;
    @FXML
    private TableColumn<Patient, Void> col_actions;
    @FXML
    private TableColumn<Patient, String> col_familyname;
    @FXML
    private TableColumn<Patient, String> col_firstname;
    @FXML
    private TableColumn<Patient, String> col_sex;
    @FXML
    private TableColumn<Patient, Date> col_bdate;
    @FXML
    private TableColumn<Patient, String> col_phonenumber;
    @FXML
    private TableColumn<Patient, String> col_address;

    private ObservableList<Patient> patientsList;
    private FilteredList<Patient> searchResultList;
    private Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>> cellFactory;
    private String dayFormat;
    private LocalDate myDate;


    public void addPatient(ActionEvent event) throws IOException {
        FXMLLoader fxmlload = new FXMLLoader(getClass().getResource("addPatient.fxml"));
        root = (Parent) fxmlload.load();
        //any information needing to be copied to the addPatientPage;
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void getIt() {
        patientsList = Patient.search("",1);
        searchResultList = new FilteredList<>(patientsList, createPredicate(searchField.getText()));
        PatientsTableView.setItems(searchResultList);
    }
    private Predicate<Patient> createPredicate(String searchText) {
        return patient -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = searchText.toLowerCase();
            if (patient.getFirst_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true;
            } else if (patient.getSecond_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true;
            } else return false;
        };
    }
    public void setCellFactory() {
        cellFactory = new Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>>() {
            @Override
            public TableCell<Patient, Void> call(final TableColumn<Patient, Void> param) {
                final TableCell<Patient, Void> cell = new TableCell<Patient, Void>() {
                    private final Button vw = new Button("View");
                    private final Button md = new Button("Modify");
                    private final Button dl = new Button("Delete");

                    {
                        dl.setOnAction((ActionEvent event) -> {
                            Patient patient = PatientsTableView.getSelectionModel().getSelectedItem();
                            if (patient == null) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "No Selected Patient.");
                                alert.showAndWait();
                            }
                            else patient.delete();
                        });

                        vw.setOnAction((ActionEvent event) -> {
                            Patient patient = PatientsTableView.getSelectionModel().getSelectedItem();
                            if (patient == null) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "No Selected Patient.");
                                alert.showAndWait();
                            }
                            else {
                                WaitingRoom.currentPatient1 = patient;
                                WaitingRoom.currentPatient2 = patient;
                            }
                        });

                    }

                    HBox container = new HBox(5, vw, dl);

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(container);
                        }
                    }
                };
                return cell;
            }
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myDate = LocalDate.now();
        dayFormat = myDate.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
        DateTimeLab.setText(dayFormat);
        searchField.textProperty().addListener((Observable, oldValue, newValue) ->
                searchResultList.setPredicate(createPredicate(newValue)));
        setCellFactory();
        col_familyname.setCellValueFactory(new PropertyValueFactory<Patient, String>("second_name"));
        col_firstname.setCellValueFactory(new PropertyValueFactory<Patient, String>("first_name"));
        col_address.setCellValueFactory(new PropertyValueFactory<Patient, String>("adr"));
        col_phonenumber.setCellValueFactory(new PropertyValueFactory<Patient, String>("number"));
        col_bdate.setCellValueFactory(new PropertyValueFactory<Patient, Date>("dateOfbirth"));
        col_sex.setCellValueFactory(new PropertyValueFactory<Patient, String>("sex"));
        col_actions.setCellFactory(cellFactory);
        getIt();
    }
}

