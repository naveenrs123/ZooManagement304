package zoo.model;

import java.sql.Date;

public class HealthCheckupModel {
    private String CheckupID;
    private String EmployeeID;
    private String AnimalID;
    private int Weight;
    private String HealthStatus;
    private Date CheckupDate;

    public HealthCheckupModel(String checkupID, String employeeID, String animalID, int weight, String healthStatus, Date checkupDate) {
        CheckupID = checkupID;
        EmployeeID = employeeID;
        AnimalID = animalID;
        Weight = weight;
        HealthStatus = healthStatus;
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

    public Date getCheckupDate() {
        return CheckupDate;
    }
}
