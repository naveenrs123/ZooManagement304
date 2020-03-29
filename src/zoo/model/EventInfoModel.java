package zoo.model;

import java.sql.Date;

public class EventInfoModel {
    private String EventID;
    private String Name;
    private Date StartDate;
    private Date EndDate;
    private int Capacity;

    public EventInfoModel(String eventID, String name, Date startDate, Date endDate, int capacity) {
        EventID = eventID;
        Name = name;
        StartDate = startDate;
        EndDate = endDate;
        Capacity = capacity;
    }

    public String getEventID() {
        return EventID;
    }

    public String getName() {
        return Name;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public int getCapacity() {
        return Capacity;
    }
}
