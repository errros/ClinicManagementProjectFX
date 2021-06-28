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

    private ObservableList<String> comboList = FXCollections.observableArrayList("All","Ophtamologue","Remplacant");
    private ObservableList<AppointmentSearchResult> patientsList;
    private FilteredList<AppointmentSearchResult> searchResultList;
    private Callback<TableColumn<AppointmentSearchResult, Void>, TableCell<AppointmentSearchResult, Void>> cellFactory;
    private Parent root;
    private Stage stage;
    private Scene scene;

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
        if (filter.getValue() == "Ophtamologue") {
            doc = 2;
        }
        else if (filter.getValue() == "Remplacant") {
            doc = 3;
        }
        if  (filter.getValue() == null) {
            patientsList = wr.search("");
        }
        else if (filter.getValue() == "All" ) {
            patientsList = wr.search("");
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
    public void setCellFactory() {
        cellFactory = new Callback<TableColumn<AppointmentSearchResult, Void>, TableCell<AppointmentSearchResult, Void>>() {
            @Override
            public TableCell<AppointmentSearchResult, Void> call(final TableColumn<AppointmentSearchResult, Void> param) {
                final TableCell<AppointmentSearchResult, Void> cell = new TableCell<AppointmentSearchResult, Void>() {
                    private final Button vw = new Button("View");
                    private final Button dl = new Button("Delete");

                    {
                        dl.setOnAction((ActionEvent event) -> {
                            AppointmentSearchResult Patient = table.getSelectionModel().getSelectedItem();
                            WaitingRoom.delete(Patient.getRdv_id());
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
        wr = new WaitingRoom();
        wr.initialize();
        getIt();
        search.textProperty().addListener((Observable, oldValue, newValue) ->
                searchResultList.setPredicate(createPredicate(newValue)));
        setCellFactory();
        col_fname.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("secondName"));
        col_name.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("firstName"));
        col_adr.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("adr"));
        col_num.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("num"));
        col_doctor.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("doctor"));
        col_actions.setCellFactory(cellFactory);
        filter.setItems(comboList);
    }
}
