package zoo.model;

import java.util.Date;

public class VetEmployeeModel extends ZooEmployeeModel {

    private char OnCall;
    private int Experience;
    private String Specialization;
    private int Phone_Number;


    public VetEmployeeModel(String employee_ID, String name, Date startDate, char onDuty, String address, char onCall, int experience, String specialization, int phone_number) {
        super(employee_ID, name, startDate, onDuty, address);
        this.OnCall = onCall;
        this.Experience = experience;
        this.Specialization = specialization;
        this.Phone_Number = phone_number;
    }

    public char getOnCall() {
        return OnCall;
    }

    public int getExperience() {
        return Experience;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public int getPhone_Number() {
        return Phone_Number;
    }
}
