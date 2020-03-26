package zoo.model;

public class AreaModel {
    private char Area_ID;
    private int Num_Pens;
    private String Name;
    private String type;

    public AreaModel(char area_ID, int num_Pens, String name, String type) {
        Area_ID = area_ID;
        Num_Pens = num_Pens;
        Name = name;
        this.type = type;
    }

    public char getArea_ID() {
        return Area_ID;
    }

    public int getNum_Pens() {
        return Num_Pens;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return type;
    }
}
