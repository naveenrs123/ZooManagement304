package zoo.model;

public class EventPricesModel {
    private String Type;
    private double Ticket_Price;

    public EventPricesModel(String type, double ticket_Price) {
        Type = type;
        Ticket_Price = ticket_Price;
    }

    public String getType() {
        return Type;
    }

    public double getTicket_Price() {
        return Ticket_Price;
    }
}
