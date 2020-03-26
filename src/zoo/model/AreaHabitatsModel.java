package zoo.model;

public class AreaHabitatsModel {
    private String Area_ID;
    private int PenNumber;
    private String habitat;

    public AreaHabitatsModel(String area_ID, int penNumber, String habitat) {
        Area_ID = area_ID;
        PenNumber = penNumber;
        this.habitat = habitat;
    }

    public String getArea_ID() {
        return Area_ID;
    }

    public int getPenNumber() {
        return PenNumber;
    }

    public String getHabitat() {
        return habitat;
    }
}
