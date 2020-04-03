package zoo.database;

import oracle.jdbc.proxy.annotation.Pre;
import zoo.model.ManagerEmployeeModel;
import zoo.model.VetEmployeeModel;
import zoo.model.ZooEmployeeModel;
import zoo.model.ZookeeperEmployeeModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

public class EmployeeDatabaseHandler {
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private Connection connection;


    public EmployeeDatabaseHandler(Connection  connection) {
        this.connection = connection;
    }

    public String[] getEmployeeIDs() {
        ArrayList<String> result = new ArrayList<String>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT EMPLOYEE_ID FROM ZooEmployee");
            while(rs.next()) {
                result.add(rs.getString("Employee_ID"));
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

    public ZooEmployeeModel getOneEmployee(String id) {
        ZooEmployeeModel zooEmployeeModel = null;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ZooEmployee WHERE ZOOEMPLOYEE.EMPLOYEE_ID = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                zooEmployeeModel = new ZooEmployeeModel
                        (rs.getString(1),
                                rs.getString("Name"),
                                rs.getDate("Start_Date"),
                                rs.getDate("End_Date"),
                                rs.getString("On_Duty").charAt(0)
                        );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return zooEmployeeModel;
    }

    public ZookeeperEmployeeModel getOneZookeeper(String id) {
        ZookeeperEmployeeModel zookeeperEmployeeModel = null;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ZooEmployee, ZookeeperEmployee WHERE ZooEmployee.employee_id = ZookeeperEmployee.employee_id AND ZOOKEEPEREMPLOYEE.EMPLOYEE_ID = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                zookeeperEmployeeModel = new ZookeeperEmployeeModel
                        (rs.getString(1),
                                rs.getString("Name"),
                                rs.getDate("Start_Date"),
                                rs.getDate("End_Date"),
                                rs.getString("On_Duty").charAt(0),
                                rs.getString("Event_Duty").charAt(0)
                        );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return zookeeperEmployeeModel;
    }

    public VetEmployeeModel getOneVet(String id) {
        VetEmployeeModel vetEmployeeModel = null;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ZooEmployee, VetEmployee WHERE ZooEmployee.employee_id = VetEmployee.employee_id AND VETEMPLOYEE.EMPLOYEE_ID = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                vetEmployeeModel = new VetEmployeeModel
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
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return vetEmployeeModel;
    }

    public ManagerEmployeeModel getOneManager(String id) {
        ManagerEmployeeModel managerEmployeeModel = null;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ZooEmployee, ManagerEmployee WHERE ZooEmployee.employee_id = ManagerEmployee.employee_id AND MANAGEREMPLOYEE.EMPLOYEE_ID = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                managerEmployeeModel = new ManagerEmployeeModel
                        (rs.getString(1),
                                rs.getString("Name"),
                                rs.getDate("Start_Date"),
                                rs.getDate("End_Date"),
                                rs.getString("On_Duty").charAt(0),
                                rs.getString("In_Office").charAt(0),
                                rs.getInt("Office_#")
                        );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return managerEmployeeModel;
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

    public void insertVetEmployee(VetEmployeeModel model) {
        ZooEmployeeModel zooEmployeeModel = new ZooEmployeeModel(
                model.getEmployee_ID(),
                model.getName(),
                model.getStartDate(),
                model.getEndDate(),
                model.getOnDuty()
        );
        insertEmployee(zooEmployeeModel);
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO VetEmployee VALUES (?,?,?,?,?)");
            ps.setString(1, model.getEmployee_ID());
            ps.setString(2, String.valueOf(model.getOnCall()));
            ps.setInt(3, model.getExperience());
            ps.setString(4, model.getSpecialization());
            ps.setString(5, model.getPhoneNumber());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertZookeeperEmployee(ZookeeperEmployeeModel model) {
        ZooEmployeeModel zooEmployeeModel = new ZooEmployeeModel(
                model.getEmployee_ID(),
                model.getName(),
                model.getStartDate(),
                model.getEndDate(),
                model.getOnDuty()
        );
        insertEmployee(zooEmployeeModel);
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ZookeeperEmployee VALUES (?,?)");
            ps.setString(1, model.getEmployee_ID());
            ps.setString(2, String.valueOf(model.getEventDuty()));
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertManagerEmployee(ManagerEmployeeModel model) {
        ZooEmployeeModel zooEmployeeModel = new ZooEmployeeModel(
                model.getEmployee_ID(),
                model.getName(),
                model.getStartDate(),
                model.getEndDate(),
                model.getOnDuty()
        );
        insertEmployee(zooEmployeeModel);
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ManagerEmployee VALUES (?,?,?)");
            ps.setString(1, model.getEmployee_ID());
            ps.setString(2, String.valueOf(model.getInOffice()));
            ps.setInt(3, model.getOfficeNumber());
            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertEmployee(ZooEmployeeModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ZooEmployee VALUES (?,?,?,?,?)");
            ps.setString(1, model.getEmployee_ID());
            ps.setString(2, model.getName());
            ps.setDate(3, model.getStartDate());
            ps.setDate(4, model.getEndDate());
            ps.setString(5, String.valueOf(model.getOnDuty()));

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateEmployee(ZooEmployeeModel model) {
        String id = model.getEmployee_ID();
        try {

            if (model.getName() != null) {
                PreparedStatement ps = connection.prepareStatement("UPDATE ZooEmployee SET name = ? WHERE Employee_ID = ?");
                ps.setString(1, model.getName());
                ps.setString(2, id);
                ps.executeUpdate();
                connection.commit();
                ps.close();

            } else if (model.getStartDate() != null) {
                PreparedStatement ps = connection.prepareStatement("UPDATE ZooEmployee SET start_date = ? WHERE Employee_ID = ?");
                ps.setDate(1, model.getStartDate());
                ps.setString(2, id);
                ps.executeUpdate();
                connection.commit();
                ps.close();

            } else if (model.getOnDuty() != ' ') {
                PreparedStatement ps = connection.prepareStatement("UPDATE ZooEmployee SET on_duty = ? WHERE Employee_ID = ?");
                ps.setString(1, String.valueOf(model.getOnDuty()));
                ps.setString(2, id);
                ps.executeUpdate();
                connection.commit();
                ps.close();
            }

        } catch (SQLException e) {
            System.out.println("error");
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
