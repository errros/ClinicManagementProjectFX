package gui;

import javafx.collections.FXCollections;
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
import logic.Appointment;
import logic.AppointmentSearchResult;
import logic.WaitingRoom;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class WaitingRoomController implements Initializable {


    public WaitingRoom wr;
    @FXML
    private TableView<AppointmentSearchResult> table;

    @FXML
    private TableColumn<AppointmentSearchResult, Void> col_actions;

    @FXML
    private TableColumn<AppointmentSearchResult, Void> col_ordre;

    @FXML
    private TableColumn<AppointmentSearchResult,String> col_fname;

    @FXML
    private TableColumn<AppointmentSearchResult,String> col_name;

    @FXML
    private TableColumn<AppointmentSearchResult, String> col_doctor;

    @FXML
    private TableColumn<AppointmentSearchResult, String> col_adr;

    @FXML
    private TableColumn<AppointmentSearchResult, String> col_num;

    @FXML
    private TextField search;

    @FXML
    private ComboBox<String> filter;
    @FXML
    private Label current;

    private ObservableList<String> comboList = FXCollections.observableArrayList("All","Ophtamologue","Remplacant");
    private ObservableList<AppointmentSearchResult> patientsList;
    private FilteredList<AppointmentSearchResult> searchResultList;
    private Callback<TableColumn<AppointmentSearchResult, Void>, TableCell<AppointmentSearchResult, Void>> cellFactory1;
    private Parent root;
    private Stage stage;
    private Scene scene;
    int e=0;

    public void addAppointements(ActionEvent event) throws IOException {
        FXMLLoader fxmlload = new FXMLLoader(getClass().getResource("addAppointements.fxml"));
        root = (Parent) fxmlload.load();
        addAppointmentController addAppointmentController = fxmlload.getController();
        addAppointmentController.getDate(null,1);
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void getIt() {
        int doc = 2;
        AppConsController.initWaitingRoomStaticFields();
        if (filter.getValue() == "Ophtamologue") {
            doc = 2;
            if (WaitingRoom.currentPatient1==null) {
                current.setText("Empty");
            }
            else current.setText(WaitingRoom.currentPatient1.getFirst_name()+" "+WaitingRoom.currentPatient1.getSecond_name());
        }
        else if (filter.getValue() == "Remplacant") {
            doc = 3;
            if (WaitingRoom.currentPatient2==null) {
                current.setText("Empty");
            }
            else current.setText(WaitingRoom.currentPatient2.getFirst_name()+" "+WaitingRoom.currentPatient2.getSecond_name());
        }
        if  (filter.getValue() == null) {
            patientsList = wr.search("");
            if (WaitingRoom.currentPatient2==null&&WaitingRoom.currentPatient1==null) {
                current.setText("Empty / Empty");
            }
            else if(WaitingRoom.currentPatient2==null||WaitingRoom.currentPatient1==null) {
                if (WaitingRoom.currentPatient2==null) {
                    current.setText(WaitingRoom.currentPatient1.getFirst_name()+" "+WaitingRoom.currentPatient1.getSecond_name()+" / Empty");
                }
                else current.setText("Empty / "+WaitingRoom.currentPatient2.getFirst_name()+" "+WaitingRoom.currentPatient2.getSecond_name());
            }
            else current.setText(WaitingRoom.currentPatient1.getFirst_name()+" "+WaitingRoom.currentPatient1.getSecond_name()+" / "+WaitingRoom.currentPatient2.getFirst_name()+" "+WaitingRoom.currentPatient2.getSecond_name());
        }
        else if (filter.getValue() == "All" ) {
            patientsList = wr.search("");
            if (WaitingRoom.currentPatient2==null&&WaitingRoom.currentPatient1==null) {
                current.setText("Empty / Empty");
            }
            else if(WaitingRoom.currentPatient2==null||WaitingRoom.currentPatient1==null) {
                if (WaitingRoom.currentPatient2==null) {
                    current.setText(WaitingRoom.currentPatient1.getFirst_name()+" "+WaitingRoom.currentPatient1.getSecond_name()+" / Empty");
                }
                else current.setText("Empty / "+WaitingRoom.currentPatient2.getFirst_name()+" "+WaitingRoom.currentPatient2.getSecond_name());
            }
            else current.setText(WaitingRoom.currentPatient1.getFirst_name()+" "+WaitingRoom.currentPatient1.getSecond_name()+" / "+WaitingRoom.currentPatient2.getFirst_name()+" "+WaitingRoom.currentPatient2.getSecond_name());
        }
        else patientsList = wr.search("", doc);
        searchResultList = new FilteredList<AppointmentSearchResult>(patientsList, createPredicate(search.getText()));
        table.setItems(searchResultList);
    }
    private Predicate<AppointmentSearchResult> createPredicate(String searchText) {
        return patient -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = searchText.toLowerCase();
            if (patient.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true;
            } else if (patient.getSecondName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true;
            } else return false;
        };
    }
    public void setCellFactory1() {
        cellFactory1 = new Callback<TableColumn<AppointmentSearchResult, Void>, TableCell<AppointmentSearchResult, Void>>() {
            @Override
            public TableCell<AppointmentSearchResult, Void> call(final TableColumn<AppointmentSearchResult, Void> param) {
                final TableCell<AppointmentSearchResult, Void> cell = new TableCell<AppointmentSearchResult, Void>() {
                    private final Button vw = new Button("View");
                    private final Button dl = new Button("Delete");
                    private final Button push = new Button("Push");
                    HBox container = new HBox(5, vw, dl, push);
                    {
                        dl.setOnAction((ActionEvent event) -> {
                            AppointmentSearchResult patient = table.getSelectionModel().getSelectedItem();
                            if (patient == null) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "No Selected Patient.");
                                alert.showAndWait();
                            }
                            else WaitingRoom.delete(patient.getRdv_id());
                        });
                        push.setOnAction((ActionEvent event) -> {
                            AppointmentSearchResult patient = table.getSelectionModel().getSelectedItem();
                            if (patient == null) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "No Selected Patient.");
                                alert.showAndWait();
                            }
                            else {
                                int a;
                                if (patient.getDoctor() == "Ophtamologue" ) {
                                    a=1;
                                }
                                else if(patient.getDoctor() == "Remplacant") {
                                    a=2;
                                }
                                else a=0;
                                WaitingRoom.addCurrentPatient(patient.getPatientId(),a);
                                AppConsController.initWaitingRoomStaticFields();
                            }
                        });
                    }
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
        search.setPromptText("Search ");
        wr = new WaitingRoom();
        wr.initialize();
        getIt();
        search.textProperty().addListener((Observable, oldValue, newValue) ->
                searchResultList.setPredicate(createPredicate(newValue)));
        setCellFactory1();
        col_fname.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("secondName"));
        col_name.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("firstName"));
        col_adr.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("adr"));
        col_num.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("num"));
        col_doctor.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("doctor"));
        col_actions.setCellFactory(cellFactory1);
        filter.setItems(comboList);
    }
}
