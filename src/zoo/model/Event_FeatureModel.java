package zoo.model;

public class Event_FeatureModel {
    private String Event_ID;
    private  String Animal_ID;
    private String Employee_ID;

    public Event_FeatureModel(String event_ID, String animal_ID, String employee_ID) {
        Event_ID = event_ID;
        Animal_ID = animal_ID;
        Employee_ID = employee_ID;
    }

    public String getEvent_ID() {
        return Event_ID;
    }

    public String getAnimal_ID() {
        return Animal_ID;
    }

    public String getEmployee_ID() {
        return Employee_ID;
    }
}
