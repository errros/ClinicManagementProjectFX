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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.Appointment;
import logic.AppointmentSearchResult;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.function.Predicate;

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
    private TableColumn<AppointmentSearchResult, Void> col_num;
    @FXML
    private TextField search;
    @FXML
    private ComboBox<String> filter;
    @FXML
    private Label dateLabel;
    @FXML
    private DatePicker myDatePicker;
    private ObservableList<String> comboList = FXCollections.observableArrayList("All", "Ophtamologue", "Remplacant");
    private ObservableList<AppointmentSearchResult> patientsList;
    private FilteredList<AppointmentSearchResult> searchResultList =null;
    private Callback<TableColumn<AppointmentSearchResult, Void>, TableCell<AppointmentSearchResult, Void>> cellFactory1;
    private Callback<TableColumn<AppointmentSearchResult, Void>, TableCell<AppointmentSearchResult, Void>> cellFactory2;
    private LocalDate myDate;
    private String dayFormat;
    private Date dayFormatSql;
    int i=0,x=1,y=1,k=0,e=-1;

    public void addAppointements(ActionEvent event) throws IOException {
        FXMLLoader fxmlload = new FXMLLoader(getClass().getResource("addAppointements.fxml"));
        root = (Parent) fxmlload.load();
        addAppointmentController addAppointmentController = fxmlload.getController();
        addAppointmentController.getDate(myDate,0);
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void chooseDate(ActionEvent event) {
        myDate = myDatePicker.getValue();
        dayFormat = myDate.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
        dayFormatSql = Date.valueOf(myDate.toString());
        dateLabel.setText(dayFormat);
        getIt();
    }

    public void getIt() {
        int doc = 2;
        if (filter.getValue() == "Ophtamologue") {
            doc = 2;
        } else if (filter.getValue() == "Remplacant") {
            doc = 3;
        }
        if (filter.getValue() == null) {
            patientsList = Appointment.search(dayFormatSql, "");
        } else if (filter.getValue() == "All") {
            patientsList = Appointment.search(dayFormatSql, "");
        } else patientsList = Appointment.search(dayFormatSql, "", doc);
        searchResultList = new FilteredList<AppointmentSearchResult>(patientsList, createPredicate(search.getText()));
        k=searchResultList.size();
        appsTable.setItems(searchResultList);
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
    private boolean checkListLength() {
        boolean formule = (searchResultList.size() >= k);
        return formule;
    }
    public void setCellFactory1() {
        cellFactory1 = new Callback<TableColumn<AppointmentSearchResult, Void>, TableCell<AppointmentSearchResult, Void>>() {
            @Override
            public TableCell<AppointmentSearchResult, Void> call(final TableColumn<AppointmentSearchResult, Void> param) {
                final TableCell<AppointmentSearchResult, Void> cell = new TableCell<AppointmentSearchResult, Void>() {
                    Image imgd = new Image("gui/resources/DeleteBtn.png");
                    ImageView del = new ImageView(imgd);
                    private final Button dl = new Button("",del);

                    {

                        dl.setStyle(PatientsSceneController.style);
                        
                    }

                    HBox container = new HBox(5,  dl);
                    /*@Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        }
                        else if(e==-1 || !checkListLength()) {
                            if (!checkListLength()) {
                                k=searchResultList.size();
                                System.out.println("list length changed"+k);
                            }
                            e=0;
                        }
                        else if (e!=-1 && checkListLength()){
                            setGraphic(container);
                            int d=e;
                            dl.setOnAction((ActionEvent event) -> {
                                AppointmentSearchResult patient = searchResultList.get(d);
                                Appointment.delete(patient.getRdv_id());
                            });
                            e++;
                            System.out.println(searchResultList.size()+" "+e);
                            if (e>=k) {
                                e=-1;
                                System.out.println("Occurence "+e);
                            }
                        }
                    }
                };
                return cell;*/
                    {
                        dl.setOnAction((ActionEvent event) -> {
                            AppointmentSearchResult patient = appsTable.getSelectionModel().getSelectedItem();
                            if (patient == null) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "No Selected Patient.");
                                alert.showAndWait();
                            }
                            else Appointment.delete(patient.getRdv_id());
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
    public void setCellFactory2() {
        cellFactory2 = new Callback<TableColumn<AppointmentSearchResult, Void>, TableCell<AppointmentSearchResult, Void>>() {
            @Override
            public TableCell<AppointmentSearchResult, Void> call(final TableColumn<AppointmentSearchResult, Void> param) {
                final TableCell<AppointmentSearchResult, Void> cell = new TableCell<AppointmentSearchResult, Void>() {
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            if (i<searchResultList.size() && !empty) {
                                String chk = searchResultList.get(i).getDoctor();
                                if (chk=="Remplacant") {
                                    setText(""+y);
                                    setTextFill(Color.DEEPSKYBLUE);
                                    y++;
                                }
                                else if (chk=="Ophtamologue") {
                                    setText(""+x);
                                    setTextFill(Color.CRIMSON);
                                    x++;
                                }
                                i++;
                            }
                            if (i==searchResultList.size() && !empty) {
                                i=0;
                                x=1;
                                y=1;
                            }
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
        myDatePicker.setValue(myDate);
        dayFormat = myDate.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
        dayFormatSql = Date.valueOf(myDate.toString());
        dateLabel.setText(dayFormat);
        col_fname.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("secondName"));
        col_name.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("firstName"));
        col_adr.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("adr"));
        col_phoneN.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("num"));
        col_doc.setCellValueFactory(new PropertyValueFactory<AppointmentSearchResult, String>("doctor"));
        setCellFactory1();
        setCellFactory2();
        col_actions.setCellFactory(cellFactory1);
        col_num.setCellFactory(cellFactory2);
        getIt();
        search.setPromptText("Search ");
        appsTable.getSelectionModel().select(1);
        search.textProperty().addListener((Observable, oldValue, newValue) ->
                searchResultList.setPredicate(createPredicate(newValue)));
        setCellFactory1();
        setCellFactory2();
        filter.setItems(comboList);
    }
}