package logic;


import javafx.scene.control.Button;

// this class in only used to return the results of appointment search
public class AppointmentSearchResult {
    private int rdv_id;
    private String firstName;
    private String secondName;
    private String doctor;
    private String adr;
    private String num;

    public int getRdv_id() {
        return rdv_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getAdr() {
        return adr;
    }

    public String getNum() {
        return num;
    }

    public AppointmentSearchResult(String firstName, String secondName, String doctor, String adr, String num) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.doctor = doctor;
        this.adr = adr;
        this.num = num;
    }


    @Override
    public String toString() {
        return
                "{firstName='" + firstName + '\'' +
                        ", secondName='" + secondName + '\'' +
                        ", doctor=" + doctor +
                        ", adr='" + adr + '\'' +
                        ", num='" + num + "}";


    }
}
