package zoo.database;

import zoo.model.AnimalModel;
import zoo.model.PenCleaningModel;
import zoo.model.PenInfoModel;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class PenAreaDatabaseHandler {

    private Connection connection;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    public PenAreaDatabaseHandler(Connection connection) {
        this.connection = connection;
    }

    public void InsertPenCleaning(PenCleaningModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO PenCleaning VALUES (?,?,?,?)");
            ps.setString(1, model.getEmployee_ID());
            ps.setInt(2, model.getPen_Number());
            ps.setString(3, Character.toString(model.getArea_ID()));
            ps.setDate(4, model.getDate_of_cleaning());

            ps.executeUpdate();
            connection.commit();
            ps.close();

            updateLastCleaningDate(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println("yeet");
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateLastCleaningDate(PenCleaningModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT DATE_OF_LAST_CLEANING from PENINFO WHERE PEN_NUMBER = ? AND AREA_ID = ?");
            ps.setInt(1, model.getPen_Number());
            ps.setString(2, Character.toString(model.getArea_ID()));
            ResultSet rs = ps.executeQuery();
            Date date = null;
            if (rs.next()) {
                date = rs.getDate(1);
            }
            ps.close();
            if (date != null) {
                int compare = model.getDate_of_cleaning().compareTo(date);
                if (compare > 0) {
                    PreparedStatement ps2 = connection.prepareStatement("UPDATE PENINFO SET DATE_OF_LAST_CLEANING = ? WHERE PEN_NUMBER = ? AND AREA_ID = ?");
                    ps2.setDate(1, model.getDate_of_cleaning());
                    ps2.setInt(2, model.getPen_Number());
                    ps2.setString(3, Character.toString(model.getArea_ID()));
                    ps2.executeUpdate();
                    connection.commit();
                    ps2.close();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public String[] getPenNumbers() {
        ArrayList<String> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT Pen_Number, AREA_ID from PENINFO");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String boi = rs.getString("Area_ID") + "," + rs.getInt("Pen_Number");
                result.add(boi);
            }
            ps.close();
            String[] result1 = new String[result.size()];
            result1 = result.toArray(result1);
            return result1;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public String[] getPenNumbersInArea(char AreaID) {
        ArrayList<String> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT Pen_Number, AREA_ID from PENINFO WHERE AREA_ID = ?");
            ps.setString(1, Character.toString(AreaID));
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String boi = rs.getString("Area_ID") + "," + rs.getInt("Pen_Number");
                result.add(boi);
            }
            ps.close();
            String[] result1 = new String[result.size()];
            result1 = result.toArray(result1);
            return result1;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public PenInfoModel[] getAllAreaInfo() {
        ArrayList<PenInfoModel> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM PENINFO");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PenInfoModel model = new PenInfoModel(
                        rs.getInt("Pen_Number"),
                        rs.getString("Area_ID").charAt(0),
                        rs.getInt("PenSize"),
                        rs.getInt("Occupancy"),
                        rs.getDate("Date_of_Last_Cleaning"));
                result.add(model);
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return result.toArray(new PenInfoModel[result.size()]);
    }

    public PenInfoModel[] getAreaInfo(String area) {
        ArrayList<PenInfoModel> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM PENINFO WHERE AREA_ID = ?");
            ps.setString(1, area);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PenInfoModel model = new PenInfoModel(
                        rs.getInt("Pen_Number"),
                        rs.getString("Area_ID").charAt(0),
                        rs.getInt("PenSize"),
                        rs.getInt("Occupancy"),
                        rs.getDate("Date_of_Last_Cleaning"));
                result.add(model);
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
        return result.toArray(new PenInfoModel[result.size()]);
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
