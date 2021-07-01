package logic;

import dao.CnxWithDB;
import gui.MedicamentsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consultation {
    static private Connection cnx = CnxWithDB.getConnection();
    static public Statement statement;


    public int consultation_id;
    public int patient_id;
    private int height;
    private int weight;
    private int blood_pressure;
    private double blood_glu;

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setBlood_pressure(int blood_pressure) {
        this.blood_pressure = blood_pressure;
    }

    public void setBlood_glu(double blood_glu) {
        this.blood_glu = blood_glu;
    }

    public void setTomperature(double tomperature) {
        this.tomperature = tomperature;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private double tomperature;
    private Date date;

    public Consultation(int patient_id, int height, int weight, int blood_pressure, double blood_glu, double tomperature, Date date) {
        this.patient_id = patient_id;
        this.height = height;
        this.weight = weight;
        this.blood_pressure = blood_pressure;
        this.blood_glu = blood_glu;
        this.tomperature = tomperature;
        this.date = date;
        this.consultation_id = getConsultationId();





    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getBlood_pressure() {
        return blood_pressure;
    }

    public double getBlood_glu() {
        return blood_glu;
    }

    public double getTomperature() {
        return tomperature;
    }

    public Date getDate() {
        return date;
    }



    private void updateConsultationId(){
        String query ="SELECT id FROM consultation WHERE patient_id = " + this.patient_id + " date = "+ this.date;

        try {
            Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(query);
             if(rs.next()){

                this.consultation_id = rs.getInt("id");

             }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
         this.consultation_id =0;

    }

    public void addWithoutConsultationInfos() {

        try {

            System.out.println(this);
            String sql = "INSERT INTO `consultation`( `patient_id` ,`date`) VALUES (?,?)";
          PreparedStatement st =  cnx.prepareStatement(sql);

           st.setInt(1,this.patient_id);
           st.setDate(2,date);
           st.executeUpdate();
        //     updateConsultationId();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void addConsultationInfos(){
        try {
            String sql = "UPDATE `consultation` SET `height`= " + this.height + " , " +
                    " `weight`= "+ this.weight + " ,`blood_pre` = " + this.blood_pressure+ " ,`blood_glu` = " +this.blood_glu+" ," +
                    " `temp`= " + this.tomperature + " WHERE id = " + this.consultation_id;



              Statement st = cnx.createStatement();
              st.executeUpdate(sql);


        } catch (SQLException e) {
            e.printStackTrace();
        }



    }




    static public void deleteConsultation(int i ){

        String sql = "DELETE FROM consultation WHERE id = " + i;
         String sql2 = "DELETE FROM prescription WHERE consult_id = " + i;
        try {

            Statement st = cnx.createStatement();
   st.executeUpdate(sql);
 st.executeUpdate(sql2);

        System.out.println("deleted both from consultation and prescription");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }





    static public ObservableList<Consultation> history(int patient_id) {
        ObservableList<Consultation> ob = FXCollections.observableArrayList();

        try {

            Connection cnx = CnxWithDB.getConnection();
            ResultSet rs = cnx.createStatement().executeQuery("select * from consultation where patient_id =" + patient_id);
            while (rs.next()) {
                ob.add(new Consultation(rs.getInt("patient_id"),
                        rs.getInt("height"),
                        rs.getInt("weight"),
                        rs.getInt("blood_pre"),
                        rs.getDouble("blood_glu"),
                        rs.getDouble("temp"),
                        rs.getDate("date")));
            }

        } catch (SQLException e) {
            Logger.getLogger(MedicamentsController.class.getName()).log(Level.SEVERE, null, e);
        }


        return ob;
    }


    private int getConsultationId() {
        String query = "SELECT id FROM consultation WHERE patient_id = ? AND date = ? ";

        try ( PreparedStatement st = cnx.prepareStatement(query)) {

            st.setInt(1, this.patient_id);
            st.setDate(2,this.date);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                return rs.getInt("id");

            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;

    }


    public static void clear(){
        try {
            name_and_age_fill("","","","");
            medication_fill("","",false,false,false,1);

            medication_fill("","",false,false,false,2);

            medication_fill("","",false,false,false,3);

            medication_fill("","",false,false,false,4);

            medication_fill("","",false,false,false,5);

            medication_fill("","",false,false,false,6);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // Method to open the prescription template from path
    public static void launch_template() {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("C:\\prescription_final.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }




    public static void name_and_age_fill(String nom , String prenom , String age,String ageFormat) throws  IOException, RuntimeException,  NoClassDefFoundError {


        PDDocument.load(new File("C:\\prescription_final.pdf"), (String) null);
        PDDocument pdfDocument;//change the location

        File pdfFile = new File("C:\\prescription_final.pdf");//change the path here
        pdfDocument = PDDocument.load(pdfFile);


        PDDocumentCatalog pdCatalog = pdfDocument.getDocumentCatalog();
        PDAcroForm pdAcroForm = pdCatalog.getAcroForm();
        // Text fields filling
        PDField Nom = pdAcroForm.getField("Nom");
        Nom.setValue(nom+" "+ prenom);

        PDField Date = pdAcroForm.getField("Date");
        Date.setValue(LocalDate.now().toString());
        //Date.setReadonly();

        PDField Age = pdAcroForm.getField("Age");

        Age.setValue(age + ageFormat);
        // Age.setReadonly(true);
        pdfDocument.save("C:\\prescription_final.pdf");//change the path here
        pdfDocument.close();
    }


    private static String generateMedicationField(String medicationName, String dose, boolean matin
            , boolean midi, boolean soir){


        String returned = medicationName + " " + dose;
        if(matin){
            returned += " Matin ";
        }
        if(midi){

            returned += " Midi ";
        }
        if(soir){

            returned += " Soir ";
        }
        return returned;

    }



    static public void medication_fill(String medicationName, String dose, boolean matin
            , boolean midi, boolean soir,int id) throws IOException, RuntimeException,  NoClassDefFoundError {


        PDDocument.load(new File("C:\\prescription_final.pdf"), (String) null);
        PDDocument pdfDocument;//change the location

        File pdfFile = new File("C:\\prescription_final.pdf");//change the path here
        pdfDocument = PDDocument.load(pdfFile);


        PDDocumentCatalog pdCatalog = pdfDocument.getDocumentCatalog();
        PDAcroForm pdAcroForm = pdCatalog.getAcroForm();


        PDField medication = (PDTextField) pdAcroForm.getField(("Medicament"+String.valueOf(id)));
        if (medication != null){
            medication.setValue(generateMedicationField( medicationName, dose,matin
                    ,midi, soir));
        }
        else System.out.println("it is null ya hmara");
        //medication.setReadonly(true);

        pdfDocument.save("C:\\prescription_final.pdf");//change the path here
        pdfDocument.close();

    }


    @Override
    public String toString() {
        return "Consultation{" +
                "consultation_id=" + consultation_id +
                ", patient_id=" + patient_id +
                ", height=" + height +
                ", weight=" + weight +
                ", blood_pressure=" + blood_pressure +
                ", blood_glu=" + blood_glu +
                ", tomperature=" + tomperature +
                ", date='" + date + '\'' +
                '}';
    }
}
