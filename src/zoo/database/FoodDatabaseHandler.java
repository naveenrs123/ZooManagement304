package zoo.database;

import zoo.model.FeedingModel;
import zoo.model.FoodModel;
import zoo.model.ZookeeperEmployeeModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

public class FoodDatabaseHandler {
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private Connection connection;


    public FoodDatabaseHandler(Connection  connection) {
        this.connection = connection;
    }
    public String[] getFoodIDs(){
        ArrayList<String> result = new ArrayList<String>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT FOOD_ID FROM FOOD");
            while(rs.next()) {
                result.add(rs.getString("Food_ID"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        result.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int id1 = Integer.parseInt(o1.substring(1));
                int id2 = Integer.parseInt(o2.substring(1));
                return id1 - id2;
            }
        });
        return result.toArray(new String[result.size()]);
    }
    public String[] feedFoodIDs(){
        ArrayList<String> result = new ArrayList<String>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT FOOD_ID FROM FEEDING");
            while(rs.next()) {
                result.add(rs.getString("Food_ID"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        result.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int id1 = Integer.parseInt(o1.substring(1));
                int id2 = Integer.parseInt(o2.substring(1));
                return id1 - id2;
            }
        });
        return result.toArray(new String[result.size()]);
    }
    public FoodModel getOneFood(String id){
        FoodModel foodModelX = null;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM FOOD WHERE FOOD.FOOD_ID = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                foodModelX = new FoodModel(rs.getString("Food_ID"),
                        rs.getString("Type"),
                        rs.getInt("Inventory_Amount")
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return foodModelX;
    }
    public FeedingModel getOneFeed(String id){
        FeedingModel feedModelX = null;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM FEEDING WHERE FEEDING.FOOD_ID = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                feedModelX = new FeedingModel(rs.getString(1),
                        rs.getString("Animal_ID"),
                        rs.getString("Employee_ID"),
                        rs.getInt("Amount"),
                        rs.getDate("Date_Of_Feeding")
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return feedModelX;
    }
    public void updateFood(FoodModel model){
        String id = model.getFood_ID();
        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE FOOD SET TYPE = ? WHERE FOOD_ID = ?");
            pstmt.setString(1, model.getType());
            pstmt.setString(2, id);
            pstmt.executeUpdate();
            connection.commit();
            pstmt.close();

            if (model.getInventory_Amount()!=-1) {
                PreparedStatement ps = connection.prepareStatement("UPDATE FOOD SET INVENTORY_AMOUNT = ? WHERE FOOD_ID = ?");
                ps.setInt(1, model.getInventory_Amount());
                ps.setString(2, id);
                ps.executeUpdate();
                connection.commit();
                ps.close();

            }

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
