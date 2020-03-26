package zoo.model;

import java.util.Date;

public class ZookeeperEmployeeModel extends ZooEmployeeModel {

    private char EventDuty;

    public ZookeeperEmployeeModel(String employee_ID, String name, Date startDate, char onDuty, String address, char eventDuty) {
        super(employee_ID, name, startDate, onDuty, address);
        this.EventDuty = eventDuty;
    }

    public char getEventDuty() {
        return EventDuty;
    }
}
