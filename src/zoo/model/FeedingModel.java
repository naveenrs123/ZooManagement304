package zoo.model;

import java.sql.Date;

public class FeedingModel {

    private String Food_ID;
    private String Animal_ID;
    private String Employee_ID;
    private int Amount;
    private Date Date_Of_Feeding;

    public FeedingModel(String food_ID, String animal_ID, String employee_ID, int amount, Date date_Of_Feeding) {
        Food_ID = food_ID;
        Animal_ID = animal_ID;
        Employee_ID = employee_ID;
        Amount = amount;
        Date_Of_Feeding = date_Of_Feeding;
    }

    public String getFood_ID() {
        return Food_ID;
    }

    public String getAnimal_ID() {
        return Animal_ID;
    }

    public String getEmployee_ID() {
        return Employee_ID;
    }

    public int getAmount() {
        return Amount;
    }

    public Date getDate_Of_Feeding() {
        return Date_Of_Feeding;
    }
}
