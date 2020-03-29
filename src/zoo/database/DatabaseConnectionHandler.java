package zoo.database;

import zoo.model.AnimalModel;
import zoo.model.FoodModel;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";

	private Connection connection = null;
	
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

	public void updateAnimal() {
		JPanel panel = new JPanel(new GridLayout(0,1));
		panel.add(new JLabel("Which animal would you like to modify?"));
		JComboBox boi = new JComboBox();
		panel.add(boi);
	}

	// called when a user wants to "delete" an employee, by supplying the date they left the zoo
	public void deleteZooEmployee(String ID, Date endDate) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE zooemployee SET END_DATE = ? WHERE employee_id = ?");
			ps.setDate(1, endDate);
			ps.setString(2, ID);
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

	// INSERT new food into food table
	public void insertFood(FoodModel food) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO food VALUES (?, ?, ?)");
			ps.setString(1, food.getFood_ID());
			ps.setString(2, food.getType());
			ps.setInt(3, food.getInventory_Amount());
			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	// update food inventory amount
	public void updateFood(String id, int amount) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE food SET inventory_amount = ? WHERE food_id = ?");
			ps.setInt(1, amount);
			ps.setString(2, id);
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

	private void getAnimalTuples(){

	}
}
