package zoo.model;

public class Event_AttendanceModel {
   private String Visitor_ID ;
   private String  Event_ID  ;

    public Event_AttendanceModel(String visitor_ID, String event_ID) {
        Visitor_ID = visitor_ID;
        Event_ID = event_ID;
    }

    public String getVisitor_ID() {
        return Visitor_ID;
    }

    public String getEvent_ID() {
        return Event_ID;
    }
}
