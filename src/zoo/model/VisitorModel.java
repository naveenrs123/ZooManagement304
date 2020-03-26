package zoo.model;


import java.sql.Date;

public class VisitorModel {
   private String Visitor_ID;
   private String Name;
   private String email;
   private Date date;

    public VisitorModel(String visitor_ID, String name, String email, Date date) {
        this.Visitor_ID = visitor_ID;
        this.Name = name;
        this.email = email;
        this.date = date;
    }

    public String getVisitor_ID() {
        return Visitor_ID;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return email;
    }

    public Date getDate() {
        return date;
    }
}
