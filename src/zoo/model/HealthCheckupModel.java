package zoo.model;

import java.sql.Date;

public class HealthCheckupModel {
    private String CheckupID;
    private String EmployeeID;
    private String AnimalID;
    private int Weight;
    private String HealthStatus;
    private String Medication;
    private String Comments;
    private Date CheckupDate;

    public HealthCheckupModel(String checkupID, String employeeID, String animalID, int weight, String healthStatus, String medication, String comments, Date checkupDate) {
        CheckupID = checkupID;
        EmployeeID = employeeID;
        AnimalID = animalID;
        Weight = weight;
        HealthStatus = healthStatus;
        Medication = medication;
        Comments = comments;
        CheckupDate = checkupDate;
    }

    public String getCheckupID() {
        return CheckupID;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public String getAnimalID() {
        return AnimalID;
    }

    public int getWeight() {
        return Weight;
    }

    public String getHealthStatus() {
        return HealthStatus;
    }

    public String getMedication() {
        return Medication;
    }

    public String getComments() {
        return Comments;
    }

    public Date getCheckupDate() {
        return CheckupDate;
    }
}
