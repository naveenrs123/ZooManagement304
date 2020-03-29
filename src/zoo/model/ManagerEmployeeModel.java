package zoo.model;

import java.sql.Date;

public class ManagerEmployeeModel extends ZooEmployeeModel {

    private char InOffice;
    private int OfficeNumber;

    public ManagerEmployeeModel(String employee_ID, String name, Date startDate, char onDuty, String address, char inOffice, int officeNumber) {
        super(employee_ID, name, startDate, onDuty, address);
        this.InOffice = inOffice;
        this.OfficeNumber = officeNumber;
    }

    public char getInOffice() {
        return InOffice;
    }

    public int getOfficeNumber() {
        return OfficeNumber;
    }
}
