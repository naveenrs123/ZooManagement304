package zoo.model;

import java.sql.Date;

public class ZooEmployeeModel {

    private String Employee_ID;
    private String Name;
    private Date StartDate;
    private Date EndDate;
    private char OnDuty;

    public ZooEmployeeModel(String employee_ID, String name, Date startDate, Date endDate, char onDuty) {
        this.Employee_ID = employee_ID;
        this.Name = name;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.OnDuty = onDuty;
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

    public Date getEndDate() { return EndDate; }

    public char getOnDuty() {
        return OnDuty;
    }


}
