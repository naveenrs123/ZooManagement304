package zoo.model;

import java.sql.Date;

public class ZookeeperEmployeeModel extends ZooEmployeeModel {

    private char EventDuty;

    public ZookeeperEmployeeModel(String employee_ID, String name, Date startDate, Date endDate, char onDuty, char eventDuty) {
        super(employee_ID, name, startDate, endDate, onDuty);
        this.EventDuty = eventDuty;
    }

    public char getEventDuty() {
        return EventDuty;
    }
}
