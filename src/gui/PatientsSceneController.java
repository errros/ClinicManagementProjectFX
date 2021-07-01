package gui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    @FXML
    private Button add;

    private ObservableList<Patient> patientsList;
    private FilteredList<Patient> searchResultList;
    private Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>> cellFactory;
    private String dayFormat;
    private LocalDate myDate;
    public static final String style = "-fx-border-radius: 20;" +
            "-fx-background-radius: 20;" +
            "-fx-pref-height: 18;" +
            "-fx-pref-width: 18;" +
            "-fx-border-width: 0;" ;
    static boolean newPatient=false;
    boolean deleted=false;

    public void addPatient(ActionEvent event) throws IOException {
        add.setDisable(true);
        FXMLLoader fxmlload = new FXMLLoader(getClass().getResource("addPatient.fxml"));
        root = (Parent) fxmlload.load();
        //any information needing to be copied to the addPatientPage;
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void getIt() {
        patientsList = Patient.search("",AppController.user_id);
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
                    Image imgv = new Image("gui/resources/ViewBtn.png");
                    ImageView view = new ImageView(imgv);
                    Image imgd = new Image("gui/resources/DeleteBtn.png");
                    ImageView del = new ImageView(imgd);
                    Image imgm = new Image("gui/resources/ModifyBtn.png");
                    ImageView mod = new ImageView(imgm);
                    private final Button vw = new Button("",view);
                    private final Button md = new Button("",mod);
                    private final Button dl = new Button("",del);

                    {
                        vw.setStyle(PatientsSceneController.style);
                        dl.setStyle(PatientsSceneController.style);
                        md.setStyle(PatientsSceneController.style);
                    }


                    {
                        dl.setOnAction((ActionEvent event) -> {
                            Patient patient = PatientsTableView.getSelectionModel().getSelectedItem();
                            if (patient == null) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "No Selected Patient.");
                                alert.showAndWait();
                            }
                            else {Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete " +patient.getSecond_name()+" "+patient.getFirst_name());
                                if (alert.showAndWait().get() == ButtonType.OK) {
                                    patient.delete();
                                    deleted=true;
                                }

                            }
                        });

                        vw.setOnAction((ActionEvent event) -> {
                            Patient patient = PatientsTableView.getSelectionModel().getSelectedItem();
                            if (patient == null) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "No Selected Patient.");
                                alert.showAndWait();
                            }
                            else {
                                if (AppController.user_id == 2) {
                                    WaitingRoom.patientPushedFromPatientsScene = patient;
                                    Parent root = null;
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
                                    try {
                                        root = loader.load();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    AppController con = loader.getController();
                                    con.loadCons();
                                    Stage stage;
                                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                                    Scene scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.show();

                                } else if (AppController.user_id == 3) {
                                    WaitingRoom.patientPushedFromPatientsScene = patient;
                                    Parent root = null;

                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
                                    try {
                                        root = loader.load();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    AppController con = loader.getController();
                                    con.loadCons();
                                    Stage stage;
                                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                                    Scene scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.show();

                                } else if (AppController.user_id == 1) {
                                    ViewPatientController.selectedpatient = patient;
                                    FXMLLoader fxmlload = new FXMLLoader(getClass().getResource("ViewPatient.fxml"));
                                    try {
                                        root = (Parent) fxmlload.load();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    ViewPatientController viewcon = fxmlload.getController();
                                    System.out.println(ViewPatientController.selectedpatient.toString());
                                    stage = new Stage();
                                    scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.show();

                                }
                            }
                        });

                        md.setOnAction((ActionEvent event) -> {
                            Patient patient = PatientsTableView.getSelectionModel().getSelectedItem();
                            if (patient == null) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "No Selected Patient.");
                                alert.showAndWait();
                            }
                             else {
                            ModifyPatientController.selectedpatient = patient;
                            FXMLLoader fxmlload = new FXMLLoader(getClass().getResource("ModifyPatient.fxml"));
                            try {
                                root = (Parent) fxmlload.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ModifyPatientController modcon = fxmlload.getController();
                            System.out.println(ModifyPatientController.selectedpatient.toString());
                            stage = new Stage();
                            scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        }

                        });

                    }


                    HBox container = new HBox(5, vw, dl, md);


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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        if (newPatient==true || deleted ==true) {
                            if (newPatient == true) {
                                newPatient=false;
                                add.setDisable(false);
                            }
                            else  {
                                deleted=false;
                            }
                            getIt();
                        }
                    }
                };
                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                    }
                    Platform.runLater(updater);
                }
            }
        });
        searchField.setPromptText("Search ");
        
    }
}

