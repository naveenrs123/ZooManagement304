package zoo.database;

import zoo.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

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

	public ZooEmployeeModel[] getEmployeeInfo() {
		ArrayList<ZooEmployeeModel> result = new ArrayList<ZooEmployeeModel>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ZooEmployee");
			while(rs.next()) {
				ZooEmployeeModel model = new ZooEmployeeModel
						(rs.getString("Employee_ID"),
						rs.getString("Name"),
						rs.getDate("Start_Date"),
						rs.getDate("End_Date"),
						rs.getString("On_Duty").charAt(0)
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

	public ZookeeperEmployeeModel[] getZookeeperEmployeeInfo() {
		ArrayList<ZookeeperEmployeeModel> result = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ZooEmployee INNER JOIN ZookeeperEmployee ON ZooEmployee.employee_id = ZookeeperEmployee.employee_id");
			while(rs.next()) {
				ZookeeperEmployeeModel model = new ZookeeperEmployeeModel
						(rs.getString(1),
								rs.getString("Name"),
								rs.getDate("Start_Date"),
								rs.getDate("End_Date"),
								rs.getString("On_Duty").charAt(0),
								rs.getString("Event_Duty").charAt(0)
						);
				result.add(model);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		result.sort(new Comparator<ZookeeperEmployeeModel>() {
			@Override
			public int compare(ZookeeperEmployeeModel o1, ZookeeperEmployeeModel o2) {
				int id1 = Integer.parseInt(o1.getEmployee_ID().substring(1));
				int id2 = Integer.parseInt(o2.getEmployee_ID().substring(1));
				return id1 - id2;
			}
		});
		return result.toArray(new ZookeeperEmployeeModel[result.size()]);
	}

	public VetEmployeeModel[] getVetEmployeeInfo() {
		ArrayList<VetEmployeeModel> result = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ZooEmployee INNER JOIN VetEmployee ON ZooEmployee.employee_id = VetEmployee.employee_id");
			while(rs.next()) {
				VetEmployeeModel model = new VetEmployeeModel
						(rs.getString(1),
								rs.getString("Name"),
								rs.getDate("Start_Date"),
								rs.getDate("End_Date"),
								rs.getString("On_Duty").charAt(0),
								rs.getString("On_Call").charAt(0),
								rs.getInt("Experience"),
								rs.getString("Specialization"),
								rs.getString("Phone_Number")
						);
				result.add(model);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		result.sort(new Comparator<VetEmployeeModel>() {
			@Override
			public int compare(VetEmployeeModel o1, VetEmployeeModel o2) {
				int id1 = Integer.parseInt(o1.getEmployee_ID().substring(1));
				int id2 = Integer.parseInt(o2.getEmployee_ID().substring(1));
				return id1 - id2;
			}
		});
		return result.toArray(new VetEmployeeModel[result.size()]);
	}

	public ManagerEmployeeModel[] getManagerEmployeeInfo() {
		ArrayList<ManagerEmployeeModel> result = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ZooEmployee INNER JOIN ManagerEmployee ON ZooEmployee.employee_id = ManagerEmployee.employee_id");
			while(rs.next()) {
				ManagerEmployeeModel model = new ManagerEmployeeModel
						(rs.getString(1),
								rs.getString("Name"),
								rs.getDate("Start_Date"),
								rs.getDate("End_Date"),
								rs.getString("On_Duty").charAt(0),
								rs.getString("In_Office").charAt(0),
								rs.getInt("Office_#")
						);
				result.add(model);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		result.sort(new Comparator<ManagerEmployeeModel>() {
			@Override
			public int compare(ManagerEmployeeModel o1, ManagerEmployeeModel o2) {
				int id1 = Integer.parseInt(o1.getEmployee_ID().substring(1));
				int id2 = Integer.parseInt(o2.getEmployee_ID().substring(1));
				return id1 - id2;
			}
		});
		return result.toArray(new ManagerEmployeeModel[result.size()]);
	}

	public void updateEmployee(ZooEmployeeModel employee) {
		String id = employee.getEmployee_ID();
		try {

			if (employee.getName() != null) {
				PreparedStatement ps = connection.prepareStatement("UPDATE ZooEmployee SET name = ? WHERE Employee_ID = ?");
				ps.setString(1, employee.getName());
				ps.setString(2, id);
				ps.executeUpdate();
				connection.commit();
				ps.close();

			} else if (employee.getStartDate() != null) {
				PreparedStatement ps = connection.prepareStatement("UPDATE ZooEmployee SET start_date = ? WHERE Employee_ID = ?");
				ps.setDate(1, employee.getStartDate());
				ps.setString(2, id);
				ps.executeUpdate();
				connection.commit();
				ps.close();

			} else if (employee.getOnDuty() != 'N') {
				PreparedStatement ps = connection.prepareStatement("UPDATE ZooEmployee SET on_duty = ? WHERE Employee_ID = ?");
				ps.setString(1, String.valueOf(employee.getOnDuty()));
				ps.setString(2, id);
				ps.executeUpdate();
				connection.commit();
				ps.close();
			}

		} catch (SQLException e) {
			System.out.println("error");
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
                                 (char)rs.getObject("Sex"),
                                rs.getString("Species"),
                                rs.getInt("Age"),
                                rs.getString("Name"),
                                rs.getInt("Pen_Number"),
                                (char)rs.getObject("Area_ID")
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
			ps.setDate(4, model.getEndDate());
			ps.setObject(5, model.getOnDuty(), Types.CHAR);

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
