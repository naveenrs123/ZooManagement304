package zoo.database;

import zoo.model.*;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AnimalDatabaseHandler {
    private Connection connection;
    private ArrayList<String> animalColumns = new ArrayList<>(Arrays.asList("Animal_ID", "Type", "Sex", "Species", "Age", "Name", "Pen_Number", "Area_ID"));


    public AnimalDatabaseHandler(Connection connection) {
        this.connection = connection;
    }

    public SelectModel animalCountByType() {
        ArrayList<String> projectionColumns = new ArrayList<>(Arrays.asList("Type", "Count"));
        ArrayList<ArrayList<String>> rowData = new ArrayList<>();
        try {
            String query = "SELECT Type, Count(Type) FROM Animals GROUP BY Type";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                row.add(rs.getString("Type"));
                row.add(Integer.toString(rs.getInt(2)));
                rowData.add(row);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
        }
        return new SelectModel(projectionColumns, rowData);
    }

    public void insertAnimal(AnimalModel animalModel, String managerID) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO animals VALUES (?,?,?,?,?,?,?,?)");
            ps.setString(1, animalModel.getAnimalID());
            ps.setString(2, animalModel.getType());
            ps.setString(3, String.valueOf(animalModel.getSex()));
            ps.setString(4, animalModel.getSpecies());
            ps.setInt(5, animalModel.getAge());
            ps.setString(6, animalModel.getName());
            ps.setInt(7, animalModel.getPenNumber());
            ps.setString(8, String.valueOf(animalModel.getAreaID()));

            ps.executeUpdate();
            connection.commit();
            long millis=System.currentTimeMillis();
            Date date=new java.sql.Date(millis);
            String relocationID = "R" + getNextRelocationNumber();
            AnimalRelocationModel relocation = new AnimalRelocationModel(relocationID, managerID, animalModel.getAnimalID(), null, null, Integer.toString(animalModel.getPenNumber()), Character.toString(animalModel.getAreaID()), date);
            insertAnimalRelocation(relocation);
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
        }
    }

    public String[] getAnimalIDs() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT ANIMAL_ID from ANIMALS");
            ResultSet rs = ps.executeQuery();
            ArrayList<String> idsString = new ArrayList<>();
            while(rs.next()) {
                idsString.add(rs.getString(1));
            }
            return idsString.toArray(new String[idsString.size()]);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public String[] getAnimalTypes() {
        AnimalTypes[] animalTypesArray = AnimalTypes.class.getEnumConstants();
        ArrayList<String> animalTypesStringArray = new ArrayList<>();
        for (AnimalTypes at: animalTypesArray) {
            String atstr = at.toString();
            animalTypesStringArray.add(atstr);
            //  atstr = atstr.substring(0,1) +  atstr.substring(1).toLowerCase();
        }
        String[] types = new String[animalTypesStringArray.size()];
        types = animalTypesStringArray.toArray(types);
        return types;
    }

    public String[] getAnimalSpecies() {
        Species[] speciesArray = Species.class.getEnumConstants();
        ArrayList<String> speciesArrayString = new ArrayList<>();
        for (Species sp: speciesArray) {
            String spstr = sp.toString();
            speciesArrayString.add(spstr);
            //  atstr = atstr.substring(0,1) +  atstr.substring(1).toLowerCase();
        }
        String[] species = new String[speciesArrayString.size()];
        species = speciesArrayString.toArray(species);
        return species;
    }

    public AnimalModel getOneAnimal(String id) {
        AnimalModel animal = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ANIMALS WHERE ANIMAL_ID = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                animal = new AnimalModel (
                        rs.getString("Animal_ID"),
                        rs.getString("Type"),
                        rs.getString("Sex").charAt(0),
                        rs.getString("Species"),
                        rs.getInt("Age"),
                        rs.getString("Name"),
                        rs.getInt("Pen_Number"),
                        rs.getString("Area_ID").charAt(0)
                );
            }
            rs.close();
            ps.close();
            return animal;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
        }
        return animal;
    }

    public void insertAnimalRelocation(AnimalRelocationModel relocation) {
        try {
            determinePenAvailability(relocation.getTo_Pen_ID(), relocation.getTo_Area_ID(), relocation.getTo_Pen_ID(), relocation.getTo_Area_ID());
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ANIMALRELOCATION VALUES(?,?,?,?,?,?,?,?)");
            ps.setString(1, relocation.getRelocation_ID());
            ps.setString(2, relocation.getEmployee_ID());
            ps.setString(3, relocation.getAnimal_ID());
            if (relocation.getFrom_Pen_ID() == null || relocation.getFrom_Area_ID() == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
                ps.setString(5, null);
            } else {
                ps.setInt(4, Integer.parseInt(relocation.getFrom_Pen_ID()));
                ps.setString(5, relocation.getFrom_Area_ID());
            }
            if (relocation.getTo_Pen_ID() == null || relocation.getTo_Area_ID() == null) {
                ps.setNull(6, Types.INTEGER);
                ps.setString(7, null);
            } else {
                ps.setInt(6, Integer.parseInt(relocation.getTo_Pen_ID()));
                ps.setString(7, relocation.getTo_Area_ID());
            }
            ps.setDate(8, relocation.getRelocationDate());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
    }

    public void determinePenAvailability(String to_PenNumber, String to_AreaID, String from_PenNumber, String from_AreaID) throws Exception {
        try {
            if ((to_PenNumber != null ) && (to_AreaID != null))  {
                PreparedStatement ps = connection.prepareStatement("SELECT PENSIZE, OCCUPANCY from PENINFO WHERE PEN_NUMBER = ? AND AREA_ID = ?");
                ps.setInt(1, Integer.parseInt(to_PenNumber));
                ps.setString(2, to_AreaID);
                ResultSet rs = ps.executeQuery();
                int newOccupancy;
                int occupancy = 0;
                int size = 0;
                while(rs.next()) {
                    size = rs.getInt(1);
                    occupancy = rs.getInt(2);
                }
                ps.close();
                if (occupancy + 1 > size) {
                    throw new Exception();
                }
                newOccupancy = occupancy + 1;
                PreparedStatement ps2 = connection.prepareStatement("UPDATE PENINFO SET OCCUPANCY = ? WHERE PEN_NUMBER = ? AND AREA_ID = ?");
                ps2.setInt(1, newOccupancy);
                ps2.setInt(2, Integer.parseInt(to_PenNumber));
                ps2.setString(3, to_AreaID);
                ps2.executeUpdate();
                connection.commit();
                ps2.close();
            }

            if ((from_PenNumber != null) && (from_AreaID != null)) {
                PreparedStatement ps = connection.prepareStatement("SELECT OCCUPANCY from PENINFO WHERE PEN_NUMBER = ? AND AREA_ID = ?");
                ps.setInt(1, Integer.parseInt(from_PenNumber));
                ps.setString(2, from_AreaID);
                ResultSet rs = ps.executeQuery();
                int occupancy = 0;
                while(rs.next()) {
                    occupancy = rs.getInt(1);
                }
                ps.close();
                int newOccupancy = occupancy - 1;
                PreparedStatement ps3 = connection.prepareStatement("UPDATE PENINFO SET OCCUPANCY = ? WHERE PEN_NUMBER = ? AND AREA_ID = ?");
                ps3.setInt(1, newOccupancy);
                ps3.setInt(2, Integer.parseInt(from_PenNumber));
                ps3.setString(3, from_AreaID);
                ps3.executeUpdate();
                connection.commit();
                ps3.close();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
    }

    public int getNextRelocationNumber() {
        try {
            PreparedStatement ps = connection.prepareStatement("Select RELOCATION_ID from ANIMALRELOCATION");
            ResultSet rs = ps.executeQuery();
            int max = -1;
            while (rs.next()) {
                int value = Integer.parseInt(rs.getString(1).substring(1));
                if (value > max) {
                    max = value;
                }
            }
            return max+1;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return 0;
        }
    }

    public AnimalModel[] getAnimalTypeInfo(AnimalTypes type) {
        ArrayList<AnimalModel> result = new ArrayList<>();
        String animalType;
        if (type == AnimalTypes.Mammal) {
            animalType = "Mammal";
        } else if (type == AnimalTypes.Aquatic) {
            animalType = "Aquatic";
        } else if (type == AnimalTypes.Avian) {
            animalType = "Avian";
        } else if (type == AnimalTypes.Invertebrate) {
            animalType = "Invertebrate";
        } else {
            animalType = "Reptile";
        }
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT Animal_ID, Sex, Species, Age, Name, Pen_Number, Area_ID FROM Animals WHERE Type = ?");
            ps.setString(1, animalType);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                AnimalModel model = new AnimalModel
                        (rs.getString("Animal_ID"),
                                null,
                                rs.getString("Sex").charAt(0),
                                rs.getString("Species"),
                                rs.getInt("Age"),
                                rs.getString("Name"),
                                rs.getInt("Pen_Number"),
                                rs.getString("Area_ID").charAt(0)
                        );
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return result.toArray(new AnimalModel[result.size()]);

    }

    public void updateAnimal(AnimalModel animal) {
        String id = animal.getAnimalID();
        try {
            if (!animal.getName().equals("")) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE Animals SET Name = ? WHERE ANIMAL_ID = ?");
                pstmt.setString(1, animal.getName());
                pstmt.setString(2, id);
                pstmt.executeUpdate();
                connection.commit();
                pstmt.close();
            }
            if (!animal.getSpecies().equals("")) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE Animals SET Species = ? WHERE ANIMAL_ID = ?");
                pstmt.setString(1, animal.getSpecies());
                pstmt.setString(2, id);
                pstmt.executeUpdate();
                connection.commit();
                pstmt.close();
            }
            if (!animal.getType().equals("")) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE Animals SET Type = ? WHERE ANIMAL_ID = ?");
                pstmt.setString(1, animal.getType());
                pstmt.setString(2, id);
                pstmt.executeUpdate();
                connection.commit();
                pstmt.close();
            }
            if (animal.getSex() != ' '){
                PreparedStatement pstmt = connection.prepareStatement("UPDATE Animals SET Sex = ? WHERE ANIMAL_ID = ?");
                pstmt.setString(1, Character.toString(animal.getSex()));
                pstmt.setString(2, id);
                pstmt.executeUpdate();
                connection.commit();
                pstmt.close();
            }
            PreparedStatement pstmt = connection.prepareStatement("UPDATE Animals SET PEN_NUMBER= ? WHERE ANIMAL_ID = ?");
            pstmt.setInt(1, animal.getPenNumber());
            pstmt.setString(2, id);
            pstmt.executeUpdate();
            connection.commit();
            pstmt.close();

            PreparedStatement pstmt2 = connection.prepareStatement("UPDATE Animals SET AREA_ID= ? WHERE ANIMAL_ID = ?");
            pstmt.setString(1, Character.toString(animal.getAreaID()));
            pstmt.setString(2, id);
            pstmt.executeUpdate();
            connection.commit();
            pstmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }

    }


    public void deleteAnimal(String animalID) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM animals WHERE animal_id LIKE ?");
            ps.setString(1, animalID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println( animalID + " does not exist!");
            }

            connection.commit();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
        }
    }

    public AnimalModel[] getAnimalInfo() {
        ArrayList<AnimalModel> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Animals");
            while(rs.next()) {
                AnimalModel model = new AnimalModel
                        (rs.getString("Animal_ID"),
                                rs.getString("Type"),
                                rs.getString("Sex").charAt(0),
                                rs.getString("Species"),
                                rs.getInt("Age"),
                                rs.getString("Name"),
                                rs.getInt("Pen_Number"),
                                rs.getString("Area_ID").charAt(0)
                        );
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return result.toArray(new AnimalModel[result.size()]);
    }

    public AnimalModel[] getAnimalsInPenArea(int PenNumber, char AreaID) {
        ArrayList<AnimalModel> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from ANIMALS WHERE PEN_NUMBER = ? AND AREA_ID = ?");
            ps.setInt(1, PenNumber);
            ps.setString(2, Character.toString(AreaID));
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                AnimalModel model = new AnimalModel
                        (rs.getString("Animal_ID"),
                                rs.getString("Type"),
                                rs.getString("Sex").charAt(0),
                                rs.getString("Species"),
                                rs.getInt("Age"),
                                rs.getString("Name"),
                                rs.getInt("Pen_Number"),
                                rs.getString("Area_ID").charAt(0)
                        );
                result.add(model);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return result.toArray(new AnimalModel[result.size()]);
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

}
