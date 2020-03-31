package zoo.model;

import java.sql.Date;

public class ManagerEmployeeModel extends ZooEmployeeModel {

    private char InOffice;
    private int OfficeNumber;

    public ManagerEmployeeModel(String employee_ID, String name, Date startDate, Date endDate, char onDuty, char inOffice, int officeNumber) {
        super(employee_ID, name, startDate, endDate, onDuty);
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
