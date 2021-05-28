package logic;

import java.sql.Date;
import java.util.Scanner;

// a main class for testing
public class LogicMain {

    public static void main(String[] args) {
        // TODO code application logic here


        System.out.println(Appointment.search(Date.valueOf("2010-11-05"), "h"));



/*
        Scanner scan = new Scanner(System.in);
        Patient p = null ;
        int user = 1;
        while (true) {

            System.out.println("enter a command");
            String command = scan.nextLine();

            if (command.equals("quit")) {
                break;
            }
            if (command.equals("user")) {
                System.out.println("Enter user id");
                user = Integer.valueOf(scan.nextLine());
                System.out.println("user == " + user);

            }

            if (command.equals("add")) {
                System.out.println("Enter firstname");

                String fname = scan.nextLine();
                System.out.println("Enter secondName");
                String sname = scan.nextLine();
                System.out.println("Enter date");
                String dateS = scan.nextLine();
                Date date = Date.valueOf(dateS);
                p = new Patient(fname, sname, date);

                System.out.println("Enter sex");
                String sex = scan.nextLine();
                if (!sex.isEmpty()) {
                    p.setSex(sex);
                }
                System.out.println("Enter adr");
                String adr = scan.nextLine();
                if (!adr.isEmpty()) {
                    p.setAdr(adr);
                }
                System.out.println("Enter number");
                String num = scan.nextLine();
                if (!num.isEmpty()) {
                    p.setNumber(num);
                }

                p.add();
                continue;
            }

            if (command.equals("delete")) {
                System.out.println("Enter firstname");

                String fname = scan.nextLine();
                System.out.println("Enter secondName");
                String sname = scan.nextLine();
                System.out.println("Enter date");
                String dateS = scan.nextLine();
                Date date = Date.valueOf(dateS);
                p = new Patient(fname, sname, date);
                boolean deleted = p.delete();
                System.out.println("Patient has beeen deleted : " + deleted);


                continue;
            }
            if (command.equals("modify")) {

                System.out.println("Choose patient who will get modified");
                System.out.println("Enter firstname");

                String fname = scan.nextLine();
                System.out.println("Enter secondName");
                String sname = scan.nextLine();
                System.out.println("Enter date");
                String dateS = scan.nextLine();
                Date date = Date.valueOf(dateS);
                p = new Patient(fname, sname, date);
                System.out.println("Set a new firstName ");
                fname = scan.nextLine();
                if (!fname.isEmpty()) {
                    p.setFirst_name(fname);
                }
                System.out.println("Set a new SecondName ");
                sname = scan.nextLine();
                if (!sname.isEmpty()) {
                    p.setSecond_name(sname);
                }

                System.out.println("Set a new birth date ");
                String birth = scan.nextLine();
                if (!birth.isEmpty()) {
                    p.setDateOfbirth(Date.valueOf(birth));
                }

                System.out.println("Set another Sex ");
                String sex = scan.nextLine();
                if (!sex.isEmpty()) {
                    p.setSex(sex);
                }
                System.out.println("Set a new adress");
                String adr = scan.nextLine();
                if (!adr.isEmpty()) {
                    p.setAdr(adr);
                }

                System.out.println("Set a new number");
                String num = scan.nextLine();
                if (!num.isEmpty()) {
                    p.setNumber(num);
                }
                System.out.println("add associate doctor");
                int idd = Integer.valueOf(scan.nextLine());
                p.addAssociateDoctor(idd);

                p.modify();
                continue;

            }

            if (command.equals("search")) {

                System.out.println("Enter search criteria");
                String crit = scan.nextLine();
                System.out.println(Patient.search(crit, user));

                continue;
            }

        }



    }
*/
    }
}
