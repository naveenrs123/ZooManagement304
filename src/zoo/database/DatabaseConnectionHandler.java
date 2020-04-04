package zoo.database;

import zoo.model.*;

import java.sql.*;
import java.sql.Types;
import java.util.ArrayList;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	private EmployeeDatabaseHandler employeeDatabaseHandler;

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
			employeeDatabaseHandler = new EmployeeDatabaseHandler(connection);

			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}

	public String[] getEmployeeIDs() {
		return employeeDatabaseHandler.getEmployeeIDs();
	}

	public ZooEmployeeModel getOneEmployee(String id) {
		return employeeDatabaseHandler.getOneEmployee(id);
	}

	public ZookeeperEmployeeModel getOneZookeeper(String id) {
		return employeeDatabaseHandler.getOneZookeeper(id);
	}

	public VetEmployeeModel getOneVet(String id) {
		return employeeDatabaseHandler.getOneVet(id);
	}

	public ManagerEmployeeModel getOneManager(String id) {
		return employeeDatabaseHandler.getOneManager(id);
	}

	public ZooEmployeeModel[] getEmployeeInfo() { return employeeDatabaseHandler.getEmployeeInfo(); }

	public ZookeeperEmployeeModel[] getZookeeperEmployeeInfo() { return employeeDatabaseHandler.getZookeeperEmployeeInfo(); }

	public VetEmployeeModel[] getVetEmployeeInfo() {
		return employeeDatabaseHandler.getVetEmployeeInfo();
	}

	public ManagerEmployeeModel[] getManagerEmployeeInfo() {
		return employeeDatabaseHandler.getManagerEmployeeInfo();
	}

	public void insertVetEmployee(VetEmployeeModel model) {
		employeeDatabaseHandler.insertVetEmployee(model);
	}

	public void insertZookeeperEmployee(ZookeeperEmployeeModel model) { employeeDatabaseHandler.insertZookeeperEmployee(model); }

	public void insertManagerEmployee(ManagerEmployeeModel model) {
		employeeDatabaseHandler.insertManagerEmployee(model);
	}

	public void insertEmployee(ZooEmployeeModel model) {
		employeeDatabaseHandler.insertEmployee(model);
	}

	public void updateEmployee(ZooEmployeeModel model) {
		employeeDatabaseHandler.updateEmployee(model);
	}

	public void updateZookeeper(ZookeeperEmployeeModel model) {
		employeeDatabaseHandler.updateZooKeeper(model);
	}

	public void updateVet(VetEmployeeModel model) {
		employeeDatabaseHandler.updateVet(model);
	}

	public void updateManager(ManagerEmployeeModel model) { employeeDatabaseHandler.updateManager(model); }

	public void insertAnimal(AnimalModel animalModel) {
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
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
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
			String[] result1 = new String[result.size()];
			result1 = result.toArray(result1);
			return result1;
		} catch (SQLException e) {
			return null;
		}
	}

	public void deleteAnimal(String animalID) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM animals WHERE animal_id LIKE ?");
			ps.setString(1, animalID);

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

    public AnimalModel[] getAnimalInfo() {
        ArrayList<AnimalModel> result = new ArrayList<AnimalModel>();
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
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        AnimalModel[] r = result.toArray(new AnimalModel[result.size()]);
		return r;
    }

    public AnimalModel[] getAnimalTypeInfo(AnimalTypes type) {
		ArrayList<AnimalModel> result = new ArrayList<AnimalModel>();
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
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		AnimalModel[] r = result.toArray(new AnimalModel[result.size()]);
		return r;

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

	public void insertHealthCheckup(HealthCheckupModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO HealthCheckup VALUES (?,?,?,?,?,?)");
			ps.setString(1, model.getCheckupID());
			ps.setString(2, model.getEmployeeID());
			ps.setString(3, model.getAnimalID());
			ps.setInt(4, model.getWeight());
			ps.setString(4, model.getHealthStatus());
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
