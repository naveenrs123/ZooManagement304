package zoo.model;


import java.sql.Date;

public class AnimalRelocationModel {
    private String Relocation_ID;
    private String Employee_ID;
    private String Animal_ID;
    private String from_Pen_ID;
    private String from_Area_ID;
    private String to_Pen_ID;
    private String to_Area_ID;
    private Date RelocationDate;

    public AnimalRelocationModel(String relocation_ID, String employee_ID, String animal_ID, String from_Pen_ID, String from_Area_ID, String to_Pen_ID, String to_Area_ID, Date relocationDate) {
        this.Relocation_ID = relocation_ID;
        this.Employee_ID = employee_ID;
        this.Animal_ID = animal_ID;
        this.from_Pen_ID = from_Pen_ID;
        this.from_Area_ID = from_Area_ID;
        this.to_Pen_ID = to_Pen_ID;
        this.to_Area_ID = to_Area_ID;
        this.RelocationDate = relocationDate;
    }

    public String getRelocation_ID() {
        return Relocation_ID;
    }

    public String getEmployee_ID() {
        return Employee_ID;
    }

    public String getAnimal_ID() {
        return Animal_ID;
    }

    public String getFrom_Pen_ID() {
        return from_Pen_ID;
    }

    public String getFrom_Area_ID() {
        return from_Area_ID;
    }

    public String getTo_Pen_ID() {
        return to_Pen_ID;
    }

    public String getTo_Area_ID() {
        return to_Area_ID;
    }

    public Date getRelocationDate() {
        return RelocationDate;
    }
}
