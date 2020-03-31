package zoo.database;

import zoo.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";

	private Connection connection = null;
	private Object Character;

	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
	
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}

			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(false);

			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}

	public void insertAnimal(AnimalModel animalModel) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO animals VALUES (?,?,?,?,?,?,?,?)");
			ps.setString(1, animalModel.getAnimalID());
			ps.setString(2, animalModel.getType());
			ps.setObject(3, animalModel.getSex(), Types.CHAR);
			ps.setString(4, animalModel.getSpecies());
			ps.setInt(5, animalModel.getAge());
			ps.setString(6, animalModel.getName());
			ps.setInt(7, animalModel.getPenNumber());
			ps.setObject(8, animalModel.getAreaID(), Types.CHAR);

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void deleteAnimal(int animalID) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM animals WHERE animal_id = ?");
			ps.setInt(1, animalID);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Branch " + animalID + " does not exist!");
			}

			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public ZooEmployeeModel[] getEmployeeInfo() {
		ArrayList<ZooEmployeeModel> result = new ArrayList<ZooEmployeeModel>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ZooEmployee");
			while(rs.next()) {
				ZooEmployeeModel model = new ZooEmployeeModel
						(rs.getString("Employee_ID"),
						rs.getString("Name"),
						rs.getDate("Start_Date"), (char)rs.getObject("On_Duty"),
						rs.getString("Address")
				);
				result.add(model);
			}


			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result.toArray(new ZooEmployeeModel[result.size()]);
	}
    public AnimalModel[] getAnimalInfo() {
        ArrayList<AnimalModel> result = new ArrayList<AnimalModel>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Animal");
            while(rs.next()) {
                AnimalModel model = new AnimalModel
                        (rs.getString("animalID"),
                                rs.getString("type"),
                                 (char)rs.getObject("sex"),
                                rs.getString("species"),
                                rs.getInt("age"),
                                rs.getString("name"),
                                rs.getInt("penNumber"),
                                (char)rs.getObject("areaID")
                        );
                result.add(model);
            }


            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new AnimalModel[result.size()]);
    }


    public FoodModel[] getFoodInfo() {
        ArrayList<FoodModel> result = new ArrayList<FoodModel>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Food");
            while(rs.next()) {
                FoodModel model = new FoodModel
                        (rs.getString("food_ID"),
                                rs.getString("type"),
                                rs.getInt("inventory_Amount")
                        );
                result.add(model);
            }


            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new FoodModel[result.size()]);
    }



	public void insertEmployee(ZooEmployeeModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO ZooEmployee VALUES (?,?,?,?,?)");
			ps.setString(1, model.getEmployee_ID());
			ps.setString(2, model.getName());
			ps.setDate(3, model.getStartDate());
			ps.setObject(4, model.getOnDuty(), Types.CHAR);
			ps.setString(5, model.getAddress());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertHealthCheckup(HealthCheckupModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO HealthCheckup VALUES (?,?,?,?,?)");
			ps.setString(1, model.getCheckupID());
			ps.setString(2, model.getAnimalID());
			ps.setInt(3, model.getWeight());
			ps.setString(4, model.getHealthStatus());
			ps.setString(5, model.getMedication());
			ps.setString(6, model.getMedication());
			ps.setDate(5, model.getCheckupDate());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}
	public void InsertPenCleaning(PenCleaningModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO PenCleaning VALUES (?,?,?,?,?,?,?,?)");
			ps.setString(1, model.getEmployee_ID());
			ps.setInt(2, model.getPen_Number());
			ps.setObject(3, model.getArea_ID(), Types.CHAR);
			ps.setDate(4, model.getDate_of_cleaning());

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}


	public void InsertFoodModel(FoodModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO Food VALUES (?,?,?,?,?,?,?,?)");
			ps.setString(1, model.getFood_ID());
			ps.setString(2, model.getType());
			ps.setInt(3, model.getInventory_Amount());

			ps.executeUpdate();
			connection.commit();
			ps.close();
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
