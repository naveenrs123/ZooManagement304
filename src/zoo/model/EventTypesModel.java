package zoo.model;

public class EventTypesModel {
    private String Name;
    private String Type;

    public EventTypesModel(String name, String type) {
        Name = name;
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }
}
