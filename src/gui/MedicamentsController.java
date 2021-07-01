package gui;

import dao.CnxWithDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.Medicament;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicamentsController implements Initializable {


    @FXML
    private TableView<Medicament> table1;

    @FXML
    private TableColumn<Medicament, String> nom_marque_1;

    @FXML
    private TableColumn<Medicament, String> fome_1;

    @FXML
    private TableColumn<Medicament, String> dosage_1;

    @FXML
    private TextField recherche_field;

    @FXML
    private Button Done_table;

    @FXML
    private TableView<Medicament> table2;

    @FXML
    private TableColumn<Medicament, String> nom_marque_2;

    @FXML
    private TableColumn<Medicament, String> forme_2;

    @FXML
    private TableColumn<Medicament, String> dosage_2;

    @FXML
    private TableColumn<Medicament, String> matin;

    @FXML
    private TableColumn<Medicament, String> midi;

    @FXML
    private TableColumn<Medicament, String> soir;

    @FXML
    private Button Addtable;
    static public int id;
// i made this list static to ensure that not every time the list get retrieved again from the db
// so it will be initialized once , after the login
    public static ObservableList<Medicament> oblist = FXCollections.observableArrayList();
    private ObservableList<Medicament> medocs = FXCollections.observableArrayList();
    private FilteredList<Medicament> searchResultList;

    public void addItem() {
        Medicament medoc = table1.getSelectionModel().getSelectedItem();
        medocs.add(medoc);
        table2.setItems(medocs);
    }



    public void addT2todb(ActionEvent event){
        for (Medicament medicament : medocs){
            System.out.println(AppConsController.oblist.get(0).consultation_id);

     medicament.add(AppConsController.oblist.get(0).consultation_id);
        }
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();



    }

    static void getAllMedicamentsFromDB(){


        try {

            Connection cnx = CnxWithDB.getConnection();
            ResultSet rs = cnx.createStatement().executeQuery("select * from medicaments ");
            while (rs.next()) {
                oblist.add(new Medicament(rs.getString("name"), rs.getString("form"), rs.getString("dosage")));
            }

        } catch (SQLException e) {
            Logger.getLogger(MedicamentsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        searchResultList = new FilteredList<>(oblist, b -> true);
        recherche_field.textProperty().addListener((Observable, oldValue, newValue) -> {
            searchResultList.setPredicate(patient -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (patient.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else return false;

            });
        });

        nom_marque_1.setCellValueFactory(new PropertyValueFactory<>("name"));
        fome_1.setCellValueFactory(new PropertyValueFactory<>("form"));
        dosage_1.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        table1.setItems(searchResultList);
        nom_marque_2.setCellValueFactory(new PropertyValueFactory<>("name"));
        forme_2.setCellValueFactory(new PropertyValueFactory<>("form"));
        dosage_2.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        matin.setCellValueFactory(new PropertyValueFactory<>("checkBox_matin"));
        midi.setCellValueFactory(new PropertyValueFactory<>("checkBox_midi"));
        soir.setCellValueFactory(new PropertyValueFactory<>("checkBox_soir"));


    }
}
