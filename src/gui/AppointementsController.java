package gui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.ResourceBundle;

public class AppointementsController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;



    @FXML
    private TableView<AppointmentSearchResult> appsTable;
    @FXML
    private TableColumn<AppointmentSearchResult, Void> col_actions;
    @FXML
    private TableColumn<AppointmentSearchResult, String> col_fname;

    @FXML
    private TableColumn<AppointmentSearchResult, String> col_name;

    @FXML
    private TableColumn<AppointmentSearchResult, String> col_doc;

    @FXML
    private TableColumn<AppointmentSearchResult, String> col_adr;

    @FXML
    private TableColumn<AppointmentSearchResult, String> col_phoneN;

    @FXML
    private TextField search;

    @FXML
    private ComboBox<String> filter;

    @FXML
    private Label dateLabel;

    @FXML
    private DatePicker myDatePicker;

    int index = -1;
    private ObservableList<String> comboList = FXCollections.observableArrayList("All","Ophtamologue","Remplacant");
    private ObservableList<AppointmentSearchResult> patientsList;
    private FilteredList<AppointmentSearchResult> searchResultList;
    private SortedList<AppointmentSearchResult> searchResultSortedList;
    private LocalDate myDate;
    private String dayFormat;
    private Date dayFormatSql;
    Connection cn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    public void addAppointements(ActionEvent event) throws IOException {
        FXMLLoader fxmlload = new FXMLLoader(getClass().getResource("addAppointements.fxml"));
        root = (Parent) fxmlload.load();
        addAppointmentController addAppointmentController = fxmlload.getController();
        addAppointmentController.getDate(myDate);
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void refresh() {
        getThemAll();
    }

    public void chooseDate(ActionEvent event) {
        myDate = myDatePicker.getValue();
        dayFormat = myDate.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
        dayFormatSql = Date.valueOf(myDate.toString());
        dateLabel.setText(dayFormat);
        getThemAll();
    }
    public void chooseDoctor(ActionEvent event) {
        getThemAll();
    }

    public void getThemAll() {
        int doc = 2;
        if (filter.getValue() == "Ophtamologue") {
            doc = 2;
        }
        else if (filter.getValue() == "Remplacant") {
            doc = 3;
        }
        if  (filter.getValue() == null) {
            patientsList = Appointment.search(dayFormatSql,"");
        }
        else if (filter.getValue() == "All" ) {
            patientsList = Appointment.search(dayFormatSql,"");
        }
        else patientsList = Appointment.search(dayFormatSql,"", doc);
        searchResultList = new FilteredList<>(patientsList, b -> true);
        search.textProperty().addListener((Observable, oldValue, newValue) -> {
            searchResultList.setPredicate(patient ->  {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (patient.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                }
                else if (patient.getSecondName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else return false;

            });
        });
        searchResultSortedList = new SortedList<>(searchResultList);
        searchResultSortedList.comparatorProperty().bind(appsTable.comparatorProperty());
        appsTable.setItems(searchResultSortedList);
        Callback<TableColumn<AppointmentSearchResult, Void>, TableCell<AppointmentSearchResult, Void>>cellFactory = new Callback<TableColumn<AppointmentSearchResult, Void>, TableCell<AppointmentSearchResult, Void>>() {
            @Override
            public TableCell<AppointmentSearchResult, Void> call(final TableColumn<AppointmentSearchResult, Void> param) {
                final TableCell<AppointmentSearchResult, Void> cell = new TableCell<AppointmentSearchResult, Void>() {

                    private final Button vw = new Button ("View");
                    private final Button dl = new Button("Delete");


                    {
                        dl.setOnAction((ActionEvent event) -> {
                            AppointmentSearchResult Patient = appsTable.getSelectionModel().getSelectedItem();


                        });
                    }

                    HBox container = new HBox(5,vw,dl);

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
        col_actions.setCellFactory(cellFactory);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myDate = LocalDate.now();
        myDatePicker.setValue(myDate);
        dayFormat = myDate.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
        dayFormatSql = Date.valueOf(myDate.toString());
        dateLabel.setText(dayFormat);
        col_fname.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("secondName"));
        col_name.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("firstName"));
        col_adr.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("adr"));
        col_phoneN.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("num"));
        col_doc.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("doctor"));
        getThemAll();
        filter.setItems(comboList);
    }
}

