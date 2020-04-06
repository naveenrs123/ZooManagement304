package zoo.database;

import oracle.jdbc.proxy.annotation.Pre;
import zoo.model.*;
import zoo.ui.HealthCheckupWindow;

import javax.swing.*;
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
	private AnimalDatabaseHandler animalDatabaseHandler;
	private FoodDatabaseHandler foodDatabaseHandler;
	private PenAreaDatabaseHandler penAreaDatabaseHandler;
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
			animalDatabaseHandler = new AnimalDatabaseHandler(connection);
			foodDatabaseHandler = new FoodDatabaseHandler(connection);
			penAreaDatabaseHandler = new PenAreaDatabaseHandler(connection);

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
	public void updateFood(FoodModel model){foodDatabaseHandler.updateFood(model);}
	public ZookeeperEmployeeModel getOneZookeeper(String id) {
		return employeeDatabaseHandler.getOneZookeeper(id);
	}
	public String[] getFoodIDs(){return foodDatabaseHandler.getFoodIDs();}
	public String[] getfeedFoodIDs(){return foodDatabaseHandler.feedFoodIDs();}
	public FoodModel getOneFood(String id){return foodDatabaseHandler.getOneFood(id);}
	public FeedingModel getOneFeed(String id){return foodDatabaseHandler.getOneFeed(id);}
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

	public String[] getManageIDs() {return employeeDatabaseHandler.getManagerIDs();}

	public String[] getVetIDs() {return employeeDatabaseHandler.getVetIDs();}

	public String[] getZookeeperIDs() {return employeeDatabaseHandler.getZooKeeperIDs();}

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

	public SelectModel searchEmployees(ZooEmployeeModel model, ArrayList<Boolean> selectedColumns, ArrayList<String> conditions) {
		return employeeDatabaseHandler.searchEmployees(model, selectedColumns, conditions);
	}

	public SelectModel searchVetEmployees(VetEmployeeModel vmodel, ArrayList<Boolean> selectedColumns, ArrayList<String> conditions) {
		return employeeDatabaseHandler.searchVetEmployees(vmodel, selectedColumns, conditions);
	}

	public SelectModel searchZookeeperEmployees(ZookeeperEmployeeModel zmodel, ArrayList<Boolean> selectedColumns, ArrayList<String> conditions) {
		return employeeDatabaseHandler.searchZookeeperEmployees(zmodel, selectedColumns, conditions);
	}

	public SelectModel searchManagerEmployees(ManagerEmployeeModel mmodel, ArrayList<Boolean> selectedColumns, ArrayList<String> conditions) {
		return employeeDatabaseHandler.searchManagerEmployees(mmodel, selectedColumns, conditions);
	}

	public SelectModel getExperiencedOnCallVet() {
		return employeeDatabaseHandler.getExperiencedOnCallVet();
	}

	public SelectModel getZookeepersWhoCleanedAllPens(char area_ID) {
		return employeeDatabaseHandler.getZookeepersWhoCleanedAllPens(area_ID);
	}

	public Character[] getAreaIDs() {
		return employeeDatabaseHandler.getAreaIDs();
	}

	public SelectModel animalCountByType() {
		return animalDatabaseHandler.animalCountByType();
	}

	public String[] getAnimalIDs() {
		return animalDatabaseHandler.getAnimalIDs();
	}

	public String[] getAnimalTypes() {
		return animalDatabaseHandler.getAnimalTypes();
	}

	public String[] getAnimalSpecies() {
		return animalDatabaseHandler.getAnimalSpecies();
	}

	public AnimalModel getOneAnimal(String id) {return animalDatabaseHandler.getOneAnimal(id);}

	public void insertAnimal(AnimalModel animalModel, String managerID) {
		animalDatabaseHandler.insertAnimal(animalModel, managerID);
	}
	public void updateAnimal(AnimalModel animalModel) {
		animalDatabaseHandler.updateAnimal(animalModel);
	}

	public AnimalModel[] getAnimalsInPenArea(int PenNumber, char AreaID) { return animalDatabaseHandler.getAnimalsInPenArea(PenNumber, AreaID);}

	public void insertAnimalRelocation(AnimalRelocationModel relocation) {
		animalDatabaseHandler.insertAnimalRelocation(relocation);
	}

	public void determinePenAvailability(String to_PenNumber, String to_AreaID, String from_PenNumber, String from_AreaID) throws Exception {
		animalDatabaseHandler.determinePenAvailability(to_PenNumber, to_AreaID, from_PenNumber, from_AreaID);
	}

	public int getNextCheckupNumber() {return animalDatabaseHandler.getNextCheckupNumber();}

	public int getNextRelocationNumber() {
		return animalDatabaseHandler.getNextRelocationNumber();
	}

	public FeedingModel[] getFeedingInfo(){
		ArrayList<FeedingModel> result = new ArrayList<FeedingModel>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM FEEDING");
			while(rs.next()) {
				FeedingModel model = new FeedingModel
						(rs.getString("Food_ID"),
								rs.getString("Animal_ID"),
								rs.getString("Employee_ID"),
								rs.getInt("Amount"),
								rs.getDate("Date_Of_Feeding")
						);
				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		FeedingModel[] r = result.toArray(new FeedingModel[result.size()]);
		return r;
	}
	public FoodPreferencesModel[] getFoodPreferences(){
		ArrayList<FoodPreferencesModel> result = new ArrayList<FoodPreferencesModel>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM FOODPREFERENCES");
			while(rs.next()) {
				FoodPreferencesModel model = new FoodPreferencesModel
						(rs.getString("Food_Type"), rs.getString("Species")
						);
				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		FoodPreferencesModel[] r = result.toArray(new FoodPreferencesModel[result.size()]);
		return r;
	}
	public void insertFood(FoodModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO FOOD VALUES (?,?,?)");
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

	public String[] getPenNumbers() {
		return penAreaDatabaseHandler.getPenNumbers();
	}

	public String[] getPenNumbersInArea(char area) {return penAreaDatabaseHandler.getPenNumbersInArea(area);}

	public PenCleaningModel[] getPenCleaningsFromTo(Date from, Date to) {return penAreaDatabaseHandler.getPenCleaningsFromTo(from, to);}

	public AnimalRelocationModel[] getAnimalRelocationsFromTo(Date from, Date to) {return penAreaDatabaseHandler.getAnimalRelocationsFromTo(from, to);}

	public void deleteAnimal(String animalID) {
		animalDatabaseHandler.deleteAnimal(animalID);
	}

    public AnimalModel[] getAnimalInfo() {
		return animalDatabaseHandler.getAnimalInfo();
    }

    public AnimalModel[] getAnimalTypeInfo(AnimalTypes type) {
		return animalDatabaseHandler.getAnimalTypeInfo(type);
	}


    public FoodModel[] getFoodInfo() {
        ArrayList<FoodModel> result = new ArrayList<>();
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
        	JOptionPane.showMessageDialog(null, e.getMessage());
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
			ps.setString(5, model.getHealthStatus());
			ps.setDate(6, model.getCheckupDate());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			rollbackConnection();
		}
	}
	public void InsertPenCleaning(PenCleaningModel model) {
		penAreaDatabaseHandler.InsertPenCleaning(model);
	}
	public PenInfoModel[] getAreaInfo(String area) { return penAreaDatabaseHandler.getAreaInfo(area); }
	public PenInfoModel[] getAllAreaInfo() { return penAreaDatabaseHandler.getAllAreaInfo(); }

	public void insertPreference(FoodPreferencesModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO FOODPREFERENCES VALUES (?,?)");
			ps.setString(1, model.getFood_Type());
			ps.setString(2, ""+model.getSpecies());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}
	public void insertFeeding(FeedingModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO FEEDING VALUES (?,?,?,?,?)");
			ps.setString(1, model.getFood_ID());
			ps.setString(2, model.getAnimal_ID());
			ps.setString(3, model.getEmployee_ID());
			ps.setInt(4, model.getAmount());
			ps.setDate(5,model.getDate_Of_Feeding());

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
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public HealthCheckupModel[] getHealthCheckups() {
		ArrayList<HealthCheckupModel> result = new ArrayList<>();
		try{
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM HEALTHCHECKUP");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				HealthCheckupModel checkup = new HealthCheckupModel(
						rs.getString("Checkup_ID"),
						rs.getString("Employee_ID"),
						rs.getString("Animal_ID"),
						rs.getInt("Weight"),
						rs.getString("Health_Status"),
						rs.getDate("CheckupDate"));
				result.add(checkup);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			rollbackConnection();

		}
		return result.toArray(new HealthCheckupModel[result.size()]);
	}


	private void rollbackConnection() {
		try  {
			connection.rollback();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
}
