package logic;


// this class in only used to return the results of appointment search
public class AppointmentSearchResult {
    private String firstName;
    private String secondName;
    private int doctor_id;
    private String adr;
    private String num;


    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public String getAdr() {
        return adr;
    }

    public String getNum() {
        return num;
    }

    public AppointmentSearchResult(String firstName, String secondName, int doctor_id, String adr, String num) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.doctor_id = doctor_id;
        this.adr = adr;
        this.num = num;
    }

    @Override
    public String toString() {
        return
                "{firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", doctor_id=" + doctor_id +
                ", adr='" + adr + '\'' +
                ", num='" + num + "}";


    }
}
