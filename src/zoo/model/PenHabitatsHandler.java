package zoo.model;

public class PenHabitatsHandler {
    private char Area_ID;
    private int Pen_Number;
    private String Habitat;

    public PenHabitatsHandler(char area_ID, int pen_Number, String habitat) {
        Area_ID = area_ID;
        Pen_Number = pen_Number;
        Habitat = habitat;
    }

    public char getArea_ID() {
        return Area_ID;
    }

    public int getPen_Number() {
        return Pen_Number;
    }

    public String getHabitat() {
        return Habitat;
    }
}
