package zoo.model;

import java.sql.Date;

public class ZooEmployeeModel {

    private String Employee_ID;
    private String Name;
    private Date StartDate;
    private char OnDuty;
    private String Address;

    public ZooEmployeeModel(String employee_ID, String name, Date startDate, char onDuty, String address) {
        this.Employee_ID = employee_ID;
        this.Name = name;
        this.StartDate = startDate;
        this.OnDuty = onDuty;
        this.Address = address;
    }

    public String getEmployee_ID() {
        return Employee_ID;
    }

    public String getName() {
        return Name;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public char getOnDuty() {
        return OnDuty;
    }

    public String getAddress() {
        return Address;
    }

}
