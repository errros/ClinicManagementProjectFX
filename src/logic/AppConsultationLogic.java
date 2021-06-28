package logic;


import java.sql.Date;
import java.time.LocalDate;

public class AppConsultationLogic {
    public static LocalDate localDate = LocalDate.of(2015, 12, 31);
    public static Date date =  Date.valueOf(localDate);
    public static Patient person = new Patient("ayoub","bousnane",date);
}
