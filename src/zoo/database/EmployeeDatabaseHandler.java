package zoo.database;

import zoo.model.*;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class EmployeeDatabaseHandler {
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private Connection connection;
    private ArrayList<String> employeeColumns = new ArrayList<>(Arrays.asList("Employee_ID", "Name", "Start_Date", "End_Date", "On_Duty", "On_Call", "Experience", "Specialization", "Phone_Number", "Event_Duty", "In_Office", "Office_#"));
    private ArrayList<String> vetColumns = new ArrayList<>(Arrays.asList("On_Call", "Experience", "Specialization", "Phone_Number"));
    private String zookeeperColumn = "Event_Duty";
    private ArrayList<String> managerColumns = new ArrayList<>(Arrays.asList("In_Office", "Office_#"));

    private ArrayList<String> stringColumns = new ArrayList<>(Arrays.asList("Employee_ID", "Name", "Specialization", "Phone_Number"));
    private ArrayList<String> dateColumns = new ArrayList<>(Arrays.asList("Start_Date", "End_Date"));
    private ArrayList<String> intColumns = new ArrayList<>(Arrays.asList("Experience", "Office_#"));
    private ArrayList<String> charColumns = new ArrayList<>(Arrays.asList("On_Duty", "On_Call", "Event_Duty", "In_Office"));


    public EmployeeDatabaseHandler(Connection connection) {
        this.connection = connection;
    }

    public Character[] getAreaIDs() {
        ArrayList<Character> areaIDs = new ArrayList<>();
         try {
            String query = "SELECT Area_ID FROM Area";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                areaIDs.add(rs.getString("Area_ID").charAt(0));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, EXCEPTION_TAG + " " + e.getMessage());
            e.printStackTrace();
            rollbackConnection();
        }
        return areaIDs.toArray(new Character[areaIDs.size()]);
    }

    public SelectModel getZookeepersWhoCleanedAllPens(char area_ID) {
        ArrayList<String> projectionColumns = new ArrayList<>(Arrays.asList("Employee ID", "Name"));
        ArrayList<ArrayList<String>> rowData = new ArrayList<>();
        try {
            String query = "SELECT z.Employee_ID, z.Name FROM ZOOEMPLOYEE z WHERE NOT EXISTS (SELECT * FROM PenInfo p " +
                    "WHERE p.AREA_ID = ? AND NOT EXISTS (SELECT pc.Employee_ID FROM PenCleaning pc " +
                    "WHERE pc.Employee_ID = z.Employee_ID AND p.Pen_Number = pc.Pen_Number AND p.Area_ID = pc.Area_ID))";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, String.valueOf(area_ID));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                row.add(rs.getString("Employee_ID"));
                row.add(rs.getString("Name"));
                rowData.add(row);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, EXCEPTION_TAG + " " + e.getMessage());
            e.printStackTrace();
            rollbackConnection();
        }
        return new SelectModel(projectionColumns, rowData);
    }

    public SelectModel getExperiencedOnCallVet() {
        ArrayList<String> projectionColumns = new ArrayList<>(Arrays.asList("Employee ID", "Name", "Phone Number", "Experience"));
        ArrayList<ArrayList<String>> rowData = new ArrayList<>();
        try {
            String query = "SELECT z.Employee_ID, z.Name, v.Phone_Number, v.Experience " +
                    "FROM ZooEmployee z, VetEmployee v " +
                    "WHERE z.On_Duty = 'T' AND v.On_Call = 'T' AND z.Employee_ID = v.Employee_ID " +
                    "AND v.Experience = (SELECT MAX(Experience) FROM VetEmployee)";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                row.add(rs.getString("Employee_ID"));
                row.add(rs.getString("Name"));
                row.add(rs.getString("Phone_Number"));
                row.add(Integer.toString(rs.getInt("Experience")));
                rowData.add(row);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, EXCEPTION_TAG + " " + e.getMessage());
            e.printStackTrace();
            rollbackConnection();
        }
        return new SelectModel(projectionColumns, rowData);
    }

    public SelectModel searchVetEmployees(VetEmployeeModel vmodel, ArrayList<Boolean> selectedColumns, ArrayList<String> conditions) {
        SelectModel result;
        ArrayList<ArrayList<String>> rowData = new ArrayList<>();
        ArrayList<String> projectionColumns = new ArrayList<>();
        ZooEmployeeModel model = new ZooEmployeeModel(vmodel.getEmployee_ID(), vmodel.getName(), vmodel.getStartDate(), vmodel.getEndDate(), vmodel.getOnDuty());


        StringBuilder query = new StringBuilder("SELECT ");
        for (int i = 0; i < selectedColumns.size(); i++) {
            if (selectedColumns.get(i)) {
                projectionColumns.add(employeeColumns.get(i));
                if (vetColumns.contains(employeeColumns.get(i))) {
                    query.append("v.").append(employeeColumns.get(i)).append(",");
                } else {
                    query.append("z.").append(employeeColumns.get(i)).append(",");
                }
            }
        }
        query = new StringBuilder(query.substring(0, query.length() - 1));
        query.append(" FROM ZooEmployee z,VetEmployee v WHERE ");

        ArrayList<String> whereColumns = new ArrayList<>();

        if (!vmodel.getEmployee_ID().equals("")) {
            query.append("z.").append(employeeColumns.get(0)).append(" = ? AND ");
            whereColumns.add(employeeColumns.get(0));
        }

        if (!vmodel.getName().equals("")) {
            query.append("z.").append(employeeColumns.get(1)).append(" LIKE ? AND ");
            whereColumns.add(employeeColumns.get(1));
        }

        if (vmodel.getStartDate() != null) {
            query.append("z.").append(employeeColumns.get(2)).append(" ").append(conditions.get(0)).append(" ? AND ");
            whereColumns.add(employeeColumns.get(2));
        }

        boolean isEndDateSearched = vmodel.getEndDate() != null || selectedColumns.get(3);

        if (isEndDateSearched) {
            if (vmodel.getEndDate() == null) {
                query.append("z.").append(employeeColumns.get(3)).append(" IS NULL AND ");
            } else {
                query.append("z.").append(employeeColumns.get(3)).append(" ").append(conditions.get(1)).append(" ? AND ");
                whereColumns.add(employeeColumns.get(3));
            }
        }

        if (vmodel.getOnDuty() != ' ') {
            query.append("z.").append(employeeColumns.get(4)).append(" = ? AND ");
            whereColumns.add(employeeColumns.get(4));
        }

        if (vmodel.getOnCall() != ' ') {
            query.append("v.").append(employeeColumns.get(5)).append(" = ? AND ");
            whereColumns.add(employeeColumns.get(5));
        }

        if (vmodel.getExperience() != -1) {
            query.append("v.").append(employeeColumns.get(6)).append(" ").append(conditions.get(2)).append(" ? AND ");
            whereColumns.add(employeeColumns.get(6));
        }

        if (!vmodel.getSpecialization().equals("")) {
            query.append("v.").append(employeeColumns.get(7)).append(" LIKE ? AND ");
            whereColumns.add(employeeColumns.get(7));
        }

        if (!vmodel.getPhoneNumber().equals("")) {
            query.append("v.").append(employeeColumns.get(8)).append(" LIKE ? AND ");
            whereColumns.add(employeeColumns.get(8));
        }

        query.append("z.employee_id = v.employee_id");

        try {
            PreparedStatement ps = connection.prepareStatement(query.toString());
            for(int i = 0; i < whereColumns.size(); i++) {
                String column = whereColumns.get(i);
                if (stringColumns.contains(column)) {
                    ps.setString(i+1, "%" + employeeStringGetter(column, model, vmodel) + "%");
                } else if (charColumns.contains(column)) {
                    ps.setString(i+1, String.valueOf(employeeCharGetter(column, model, vmodel, null, null)));
                } else if (dateColumns.contains(column)) {
                    ps.setDate(i+1, employeeDateGetter(column, model));
                } else if (intColumns.contains(column)) {
                    ps.setInt(i+1, employeeIntegerGetter(column, vmodel, null));
                }
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                for (String column : projectionColumns) {
                    if (stringColumns.contains(column)) {
                        row.add(rs.getString(column));
                    } else if (dateColumns.contains(column)) {
                        if (rs.getDate(column) == null) {
                            row.add("Currently employed");
                        } else {
                            row.add(rs.getDate(column).toString());
                        }
                    } else if (charColumns.contains(column)) {
                        row.add(rs.getString(column));
                    } else if (intColumns.contains(column)) {
                        row.add(Integer.toString(rs.getInt(column)));
                    }
                }
                rowData.add(row);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
            e.printStackTrace();
        }

        result = new SelectModel(projectionColumns, rowData);
        return result;
    }

    public SelectModel searchZookeeperEmployees(ZookeeperEmployeeModel zmodel, ArrayList<Boolean> selectedColumns, ArrayList<String> conditions) {
        SelectModel result;
        ArrayList<ArrayList<String>> rowData = new ArrayList<>();
        ArrayList<String> projectionColumns = new ArrayList<>();
        ZooEmployeeModel model = new ZooEmployeeModel(zmodel.getEmployee_ID(), zmodel.getName(), zmodel.getStartDate(), zmodel.getEndDate(), zmodel.getOnDuty());

        StringBuilder query = new StringBuilder("SELECT ");
        for (int i = 0; i < selectedColumns.size(); i++) {
            if (selectedColumns.get(i)) {
                projectionColumns.add(employeeColumns.get(i));
                if (zookeeperColumn.equals(employeeColumns.get(i))) {
                    query.append("zk.").append(employeeColumns.get(i)).append(",");
                } else {
                    query.append("z.").append(employeeColumns.get(i)).append(",");
                }
            }
        }
        query = new StringBuilder(query.substring(0, query.length() - 1));
        query.append(" FROM ZooEmployee z,ZookeeperEmployee zk WHERE ");

        ArrayList<String> whereColumns = new ArrayList<>();

        if (!zmodel.getEmployee_ID().equals("")) {
            query.append("z.").append(employeeColumns.get(0)).append(" = ? AND ");
            whereColumns.add(employeeColumns.get(0));
        }

        if (!zmodel.getName().equals("")) {
            query.append("z.").append(employeeColumns.get(1)).append(" LIKE ? AND ");
            whereColumns.add(employeeColumns.get(1));
        }

        if (zmodel.getStartDate() != null) {
            query.append("z.").append(employeeColumns.get(2)).append(" ").append(conditions.get(0)).append(" ? AND ");
            whereColumns.add(employeeColumns.get(2));
        }

        boolean isEndDateSearched = zmodel.getEndDate() != null || selectedColumns.get(3);

        if (isEndDateSearched) {
            if (zmodel.getEndDate() == null) {
                query.append("z.").append(employeeColumns.get(3)).append(" IS NULL AND ");
            } else {
                query.append("z.").append(employeeColumns.get(3)).append(" ").append(conditions.get(1)).append(" ? AND ");
                whereColumns.add(employeeColumns.get(3));
            }
        }

        if (zmodel.getOnDuty() != ' ') {
            query.append("z.").append(employeeColumns.get(4)).append(" = ? AND ");
            whereColumns.add(employeeColumns.get(4));
        }

        if (zmodel.getEventDuty() != ' ') {
            query.append("zk.").append(employeeColumns.get(9)).append(" = ? AND ");
            whereColumns.add(employeeColumns.get(9));
        }

        query.append("z.employee_id = zk.employee_id");

        try {
            PreparedStatement ps = connection.prepareStatement(query.toString());
            for(int i = 0; i < whereColumns.size(); i++) {
                String column = whereColumns.get(i);
                if (stringColumns.contains(column)) {
                    ps.setString(i+1, "%" + employeeStringGetter(column, model, null) + "%");
                } else if (charColumns.contains(column)) {
                    ps.setString(i+1, String.valueOf(employeeCharGetter(column, model, null, zmodel, null)));
                } else if (dateColumns.contains(column)) {
                    ps.setDate(i+1, employeeDateGetter(column, model));
                } else if (intColumns.contains(column)) {
                    ps.setInt(i+1, employeeIntegerGetter(column, null, null));
                }
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                for (String column : projectionColumns) {
                    if (stringColumns.contains(column)) {
                        row.add(rs.getString(column));
                    } else if (dateColumns.contains(column)) {
                        if (rs.getDate(column) == null) {
                            row.add("Currently employed");
                        } else {
                            row.add(rs.getDate(column).toString());
                        }
                    } else if (charColumns.contains(column)) {
                        row.add(rs.getString(column));
                    } else if (intColumns.contains(column)) {
                        row.add(Integer.toString(rs.getInt(column)));
                    }
                }
                rowData.add(row);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
            e.printStackTrace();
        }

        result = new SelectModel(projectionColumns, rowData);
        return result;
    }

    public SelectModel searchManagerEmployees(ManagerEmployeeModel mmodel, ArrayList<Boolean> selectedColumns, ArrayList<String> conditions) {
        SelectModel result;
        ArrayList<ArrayList<String>> rowData = new ArrayList<>();
        ArrayList<String> projectionColumns = new ArrayList<>();
        ZooEmployeeModel model = new ZooEmployeeModel(mmodel.getEmployee_ID(), mmodel.getName(), mmodel.getStartDate(), mmodel.getEndDate(), mmodel.getOnDuty());

        StringBuilder query = new StringBuilder("SELECT ");
        for (int i = 0; i < selectedColumns.size(); i++) {
            if (selectedColumns.get(i)) {
                projectionColumns.add(employeeColumns.get(i));
                if (managerColumns.contains(employeeColumns.get(i))) {
                    query.append("m.").append(employeeColumns.get(i)).append(",");
                } else {
                    query.append("z.").append(employeeColumns.get(i)).append(",");
                }
            }
        }
        query = new StringBuilder(query.substring(0, query.length() - 1));
        query.append(" FROM ZooEmployee z,ManagerEmployee m WHERE ");

        ArrayList<String> whereColumns = new ArrayList<>();

        if (!mmodel.getEmployee_ID().equals("")) {
            query.append("z.").append(employeeColumns.get(0)).append(" = ? AND ");
            whereColumns.add(employeeColumns.get(0));
        }

        if (!mmodel.getName().equals("")) {
            query.append("z.").append(employeeColumns.get(1)).append(" LIKE ? AND ");
            whereColumns.add(employeeColumns.get(1));
        }

        if (mmodel.getStartDate() != null) {
            query.append("z.").append(employeeColumns.get(2)).append(" ").append(conditions.get(0)).append(" ? AND ");
            whereColumns.add(employeeColumns.get(2));
        }

        boolean isEndDateSearched = mmodel.getEndDate() != null || selectedColumns.get(3);

        if (isEndDateSearched) {
            if (mmodel.getEndDate() == null) {
                query.append("z.").append(employeeColumns.get(3)).append(" IS NULL AND ");
            } else {
                query.append("z.").append(employeeColumns.get(3)).append(" ").append(conditions.get(1)).append(" ? AND ");
                whereColumns.add(employeeColumns.get(3));
            }
        }

        if (mmodel.getOnDuty() != ' ') {
            query.append("z.").append(employeeColumns.get(4)).append(" = ? AND ");
            whereColumns.add(employeeColumns.get(4));
        }

        if (mmodel.getInOffice() != ' ') {
            query.append("m.").append(employeeColumns.get(10)).append(" = ? AND ");
            whereColumns.add(employeeColumns.get(10));
        }

        if (mmodel.getOfficeNumber() != -1) {
            query.append("m.").append(employeeColumns.get(11)).append(" ").append(conditions.get(2)).append(" ? AND ");
            whereColumns.add(employeeColumns.get(11));
        }

        query.append("z.employee_id = m.employee_id");

        try {
            PreparedStatement ps = connection.prepareStatement(query.toString());
            for(int i = 0; i < whereColumns.size(); i++) {
                String column = whereColumns.get(i);
                if (stringColumns.contains(column)) {
                    ps.setString(i+1, "%" + employeeStringGetter(column, model, null) + "%");
                } else if (charColumns.contains(column)) {
                    ps.setString(i+1, String.valueOf(employeeCharGetter(column, model, null, null, mmodel)));
                } else if (dateColumns.contains(column)) {
                    ps.setDate(i+1, employeeDateGetter(column, model));
                } else if (intColumns.contains(column)) {
                    ps.setInt(i+1, employeeIntegerGetter(column, null, mmodel));
                }
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                for (String column : projectionColumns) {
                    if (stringColumns.contains(column)) {
                        row.add(rs.getString(column));
                    } else if (dateColumns.contains(column)) {
                        if (rs.getDate(column) == null) {
                            row.add("Currently employed");
                        } else {
                            row.add(rs.getDate(column).toString());
                        }
                    } else if (charColumns.contains(column)) {
                        row.add(rs.getString(column));
                    } else if (intColumns.contains(column)) {
                        row.add(Integer.toString(rs.getInt(column)));
                    }
                }
                rowData.add(row);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
            e.printStackTrace();
        }

        result = new SelectModel(projectionColumns, rowData);
        return result;
    }

    public SelectModel searchEmployees(ZooEmployeeModel model, ArrayList<Boolean> selectedColumns, ArrayList<String> conditions) {
        SelectModel result;
        ArrayList<ArrayList<String>> rowData = new ArrayList<>();
        ArrayList<String> projectionColumns = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT ");
        for (int i = 0; i < 5; i++) {
            if (selectedColumns.get(i)) {
                projectionColumns.add(employeeColumns.get(i));
                query.append(employeeColumns.get(i)).append(",");
            }
        }
        query = new StringBuilder(query.substring(0, query.length() - 1));
        query.append(" FROM ZooEmployee WHERE ");

        ArrayList<String> whereColumns = new ArrayList<>();

        if (!model.getEmployee_ID().equals("")) {
            query.append(employeeColumns.get(0)).append(" LIKE").append(" ?").append(" AND ");
            whereColumns.add(employeeColumns.get(0));
        }

        if (!model.getName().equals("")) {
            query.append(employeeColumns.get(1)).append(" LIKE").append(" ?").append(" AND ");
            whereColumns.add(employeeColumns.get(1));
        }

        if (model.getStartDate() != null) {
            query.append(employeeColumns.get(2)).append(" ").append(conditions.get(0)).append(" ? AND ");
            whereColumns.add(employeeColumns.get(2));
        }

        boolean isEndDateSearched = model.getEndDate() != null || selectedColumns.get(3);

        if (isEndDateSearched) {
            if (model.getEndDate() == null) {
                query.append(employeeColumns.get(3)).append(" IS NULL AND ");
            } else {
                query.append(employeeColumns.get(3)).append(" ").append(conditions.get(1)).append(" ? AND ");
                whereColumns.add(employeeColumns.get(3));
            }
        }

        if (model.getOnDuty() != ' ') {
            query.append(employeeColumns.get(4)).append(" = ? AND ");
            whereColumns.add(employeeColumns.get(4));
        }

        query = new StringBuilder(query.substring(0, query.length() - 4));

        try {
            PreparedStatement ps = connection.prepareStatement(query.toString());
            for(int i = 0; i < whereColumns.size(); i++) {
                String column = whereColumns.get(i);
                if (stringColumns.contains(column)) {
                    ps.setString(i+1, "%" + employeeStringGetter(column, model, null) + "%");
                } else if (charColumns.contains(column)) {
                    ps.setString(i+1, String.valueOf(employeeCharGetter(column, model, null, null, null)));
                } else if (dateColumns.contains(column)) {
                    ps.setDate(i+1, employeeDateGetter(column, model));
                } else if (intColumns.contains(column)) {
                    ps.setInt(i+1, employeeIntegerGetter(column, null, null));
                }
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                for (String column : projectionColumns) {
                    if (stringColumns.contains(column)) {
                        row.add(rs.getString(column));
                    } else if (dateColumns.contains(column)) {
                        if (rs.getDate(column) == null) {
                            row.add("Currently employed");
                        } else {
                            row.add(rs.getDate(column).toString());
                        }
                    } else if (charColumns.contains(column)) {
                        row.add(rs.getString(column));
                    } else if (intColumns.contains(column)) {
                        row.add(Integer.toString(rs.getInt(column)));
                    }
                }
                rowData.add(row);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            rollbackConnection();
        }

        result = new SelectModel(projectionColumns, rowData);
        return result;
    }

    public int employeeIntegerGetter(String column, VetEmployeeModel vmodel, ManagerEmployeeModel mmodel) {
        int ret = -1;
        if (vmodel != null) {
            if (column.equals("Experience")) {
                ret = vmodel.getExperience();
            }
        } else if (mmodel != null) {
            if (column.equals("Office_#")) {
                ret = mmodel.getOfficeNumber();
            }
        }
        return ret;
    }

    public String employeeStringGetter(String column, ZooEmployeeModel model, VetEmployeeModel vmodel) {
        String ret = "";
        if (column.equals("Employee_ID")) {
            ret = model.getEmployee_ID();
        } else if (column.equals("Name")) {
            ret = model.getName();
        }

        if (vmodel != null) {
            if (column.equals("Specialization")) {
                ret = vmodel.getSpecialization();
            } else if (column.equals("Phone_Number")) {
                ret = vmodel.getPhoneNumber();
            }
        }
        return ret;
    }

    public char employeeCharGetter(String column, ZooEmployeeModel model, VetEmployeeModel vmodel, ZookeeperEmployeeModel zmodel, ManagerEmployeeModel mmodel) {
        char ret = ' ';
        if (column.equals("On_Duty")) {
            ret = model.getOnDuty();
        } else if (vmodel != null) {
            if (column.equals("On_Call")) {
                ret = vmodel.getOnCall();
            }
        } else if (zmodel != null) {
            if (column.equals("Event_Duty")) {
                ret = zmodel.getEventDuty();
            }
        } else if (mmodel != null) {
            if (column.equals("In_Office")) {
                ret = mmodel.getInOffice();
            }
        }
        return ret;
    }

    public Date employeeDateGetter(String column, ZooEmployeeModel model) {
        Date ret = null;
        if (column.equals("Start_Date")) {
            ret = model.getStartDate();
        } else if (column.equals("End_Date")) {
            ret = model.getEndDate();
        }
        return ret;
    }

    public String[] getEmployeeIDs() {
        ArrayList<String> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT EMPLOYEE_ID FROM ZooEmployee");
            while(rs.next()) {
                result.add(rs.getString("Employee_ID"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
        }
        result.sort((o1, o2) -> {
            int id1 = Integer.parseInt(o1.substring(1));
            int id2 = Integer.parseInt(o2.substring(1));
            return id1 - id2;
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
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
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
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
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
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
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
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
        }
        return managerEmployeeModel;
    }

    public ZooEmployeeModel[] getEmployeeInfo() {
        ArrayList<ZooEmployeeModel> result = new ArrayList<>();
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
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
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
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
        }
        result.sort((o1, o2) -> {
            int id1 = Integer.parseInt(o1.getEmployee_ID().substring(1));
            int id2 = Integer.parseInt(o2.getEmployee_ID().substring(1));
            return id1 - id2;
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
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
        }
        result.sort((o1, o2) -> {
            int id1 = Integer.parseInt(o1.getEmployee_ID().substring(1));
            int id2 = Integer.parseInt(o2.getEmployee_ID().substring(1));
            return id1 - id2;
        });
        return result.toArray(new VetEmployeeModel[result.size()]);
    }

    public String[] getManagerIDs() {
        ManagerEmployeeModel[] managers = getManagerEmployeeInfo();
        ArrayList<String> managersStringArray = new ArrayList<>();
        for (ManagerEmployeeModel manager: managers) {
            String ID = manager.getEmployee_ID();
            managersStringArray.add(ID);
        }
        String[] IDs = new String[managersStringArray.size()];
        IDs = managersStringArray.toArray(IDs);
        return IDs;
    }

    public String[] getZooKeeperIDs() {
        ZookeeperEmployeeModel[] zookeepers = getZookeeperEmployeeInfo();
        ArrayList<String> zookeeperStringArray = new ArrayList<>();
        for (ZookeeperEmployeeModel zookeeper: zookeepers) {
            String ID = zookeeper.getEmployee_ID();
            zookeeperStringArray.add(ID);
        }
        String[] IDs = new String[zookeeperStringArray.size()];
        IDs = zookeeperStringArray.toArray(IDs);
        return IDs;
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
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
        }
        result.sort((o1, o2) -> {
            int id1 = Integer.parseInt(o1.getEmployee_ID().substring(1));
            int id2 = Integer.parseInt(o2.getEmployee_ID().substring(1));
            return id1 - id2;
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
            JOptionPane.showMessageDialog(null, e.getMessage());
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
            JOptionPane.showMessageDialog(null, e.getMessage());
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
            JOptionPane.showMessageDialog(null, e.getMessage());
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
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
        }
    }

    public void updateEmployee(ZooEmployeeModel model) {
        String id = model.getEmployee_ID();
        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE ZooEmployee SET END_DATE = ? WHERE Employee_ID = ?");
            pstmt.setDate(1, model.getEndDate());
            pstmt.setString(2, id);
            pstmt.executeUpdate();
            connection.commit();
            pstmt.close();

            if (!model.getName().equals("")) {
                PreparedStatement ps = connection.prepareStatement("UPDATE ZooEmployee SET name = ? WHERE Employee_ID = ?");
                ps.setString(1, model.getName());
                ps.setString(2, id);
                ps.executeUpdate();
                connection.commit();
                ps.close();

            } if (model.getStartDate() != null) {
                PreparedStatement ps = connection.prepareStatement("UPDATE ZooEmployee SET start_date = ? WHERE Employee_ID = ?");
                ps.setDate(1, model.getStartDate());
                ps.setString(2, id);
                ps.executeUpdate();
                connection.commit();
                ps.close();

            } if (model.getOnDuty() != ' ') {
                PreparedStatement ps = connection.prepareStatement("UPDATE ZooEmployee SET on_duty = ? WHERE Employee_ID = ?");
                ps.setString(1, String.valueOf(model.getOnDuty()));
                ps.setString(2, id);
                ps.executeUpdate();
                connection.commit();
                ps.close();

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
        }
    }

    public void updateZooKeeper(ZookeeperEmployeeModel model) {
        String id = model.getEmployee_ID();
        updateEmployee(new ZooEmployeeModel(id, model.getName(), model.getStartDate(), model.getEndDate(), model.getOnDuty()));
        try {

            if (model.getEventDuty() != ' ') {
                PreparedStatement ps = connection.prepareStatement("UPDATE ZOOKEEPEREMPLOYEE SET EVENT_DUTY = ? WHERE Employee_ID = ?");
                ps.setString(1, String.valueOf(model.getEventDuty()));
                ps.setString(2, id);
                ps.executeUpdate();
                connection.commit();
                ps.close();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            rollbackConnection();
        }
    }

    public void updateVet(VetEmployeeModel model) {
        String id = model.getEmployee_ID();
        updateEmployee(new ZooEmployeeModel(id, model.getName(), model.getStartDate(), model.getEndDate(), model.getOnDuty()));
        try {
            if (model.getOnCall() != ' ') {
                PreparedStatement ps = connection.prepareStatement("UPDATE VETEMPLOYEE SET ON_CALL = ? WHERE Employee_ID = ?");
                ps.setString(1, String.valueOf(model.getOnCall()));
                ps.setString(2, id);
                ps.executeUpdate();
                connection.commit();
                ps.close();

            } if (model.getExperience() != -1) {
                PreparedStatement ps = connection.prepareStatement("UPDATE VETEMPLOYEE SET EXPERIENCE = ? WHERE Employee_ID = ?");
                ps.setInt(1, model.getExperience());
                ps.setString(2, id);
                ps.executeUpdate();
                connection.commit();
                ps.close();

            } if (!model.getSpecialization().equals("")) {
                PreparedStatement ps = connection.prepareStatement("UPDATE VETEMPLOYEE SET SPECIALIZATION = ? WHERE Employee_ID = ?");
                ps.setString(1, model.getSpecialization());
                ps.setString(2, id);
                ps.executeUpdate();
                connection.commit();
                ps.close();
            } if (!model.getPhoneNumber().equals("")) {
                PreparedStatement ps = connection.prepareStatement("UPDATE VETEMPLOYEE SET PHONE_NUMBER = ? WHERE Employee_ID = ?");
                ps.setString(1, model.getPhoneNumber());
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

    public void updateManager(ManagerEmployeeModel model) {
        String id = model.getEmployee_ID();
        updateEmployee(new ZooEmployeeModel(id, model.getName(), model.getStartDate(), model.getEndDate(), model.getOnDuty()));
        try {

            if (model.getInOffice() != ' ') {
                PreparedStatement ps = connection.prepareStatement("UPDATE MANAGEREMPLOYEE SET IN_OFFICE = ? WHERE Employee_ID = ?");
                ps.setString(1, String.valueOf(model.getInOffice()));
                ps.setString(2, id);
                ps.executeUpdate();
                connection.commit();
                ps.close();

            } if (model.getOfficeNumber() != -1) {
                PreparedStatement ps = connection.prepareStatement("UPDATE MANAGEREMPLOYEE SET OFFICE_# = ? WHERE Employee_ID = ?");
                ps.setInt(1, model.getOfficeNumber());
                ps.setString(2, id);
                ps.executeUpdate();
                connection.commit();
                ps.close();

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
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
