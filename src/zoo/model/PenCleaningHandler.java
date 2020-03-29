package zoo.model;

import java.sql.Date;

public class PenCleaningHandler {
    private String Employee_ID;
    private int Pen_Number;
    private char Area_ID;
    private Date Date_of_cleaning;

    public PenCleaningHandler(String employee_ID, int pen_Number, char area_ID, Date date_of_cleaning) {
        Employee_ID = employee_ID;
        Pen_Number = pen_Number;
        Area_ID = area_ID;
        Date_of_cleaning = date_of_cleaning;
    }

    public String getEmployee_ID() {
        return Employee_ID;
    }

    public int getPen_Number() {
        return Pen_Number;
    }

    public char getArea_ID() {
        return Area_ID;
    }

    public Date getDate_of_cleaning() {
        return Date_of_cleaning;
    }
}
