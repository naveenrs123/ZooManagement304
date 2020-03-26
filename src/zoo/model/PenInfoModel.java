package zoo.model;

import java.sql.Date;

public class PenInfoModel {
    private int PenNumber;
    private char AreaID;
    private String Description;
    private int PenSize;
    private int Occupancy;
    private Date DateOfLastCleaning;

    public PenInfoModel(int penNumber, char areaID, String description, int penSize, int occupancy, Date dateOfLastCleaning) {
        PenNumber = penNumber;
        AreaID = areaID;
        Description = description;
        PenSize = penSize;
        Occupancy = occupancy;
        DateOfLastCleaning = dateOfLastCleaning;
    }

    public int getPenNumber() {
        return PenNumber;
    }

    public char getAreaID() {
        return AreaID;
    }

    public String getDescription() {
        return Description;
    }

    public int getPenSize() {
        return PenSize;
    }

    public int getOccupancy() {
        return Occupancy;
    }

    public Date getDateOfLastCleaning() {
        return DateOfLastCleaning;
    }
}
