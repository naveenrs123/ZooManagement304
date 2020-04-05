package zoo.ui;

import zoo.database.DatabaseConnectionHandler;
import zoo.model.ManagerEmployeeModel;
import zoo.model.VetEmployeeModel;
import zoo.model.ZooEmployeeModel;
import zoo.model.ZookeeperEmployeeModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class EmployeeWindow extends JFrame {
    AddEmployeeDialog addEmployeeDialog;
    UpdateEmployeeDialog updateEmployeeDialog;
    SearchEmployeeDialog searchEmployeeDialog;
    AdvancedEmployeeDialog advancedEmployeeDialog;
    DatabaseConnectionHandler dbhandler;
    JTable table;
    JScrollPane employeeScroll;

    public EmployeeWindow(DatabaseConnectionHandler dbhandler) {
        super("Employee Management");
        this.dbhandler = dbhandler;
        this.table = new JTable();
        addEmployeeDialog = new AddEmployeeDialog(dbhandler, table);
        updateEmployeeDialog = new UpdateEmployeeDialog(dbhandler, table);
        searchEmployeeDialog = new SearchEmployeeDialog(dbhandler, table);
        advancedEmployeeDialog = new AdvancedEmployeeDialog(dbhandler, table);
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        JPanel titlePane = createTitlePane();
        sharedInfo();

        employeeScroll = new JScrollPane(table);
        employeeScroll.setPreferredSize(new Dimension(700, 300));
        employeeScroll.setMaximumSize(new Dimension(700, 300));

        JPanel selectOptions = new JPanel();
        selectOptions.setLayout(new BoxLayout(selectOptions, BoxLayout.LINE_AXIS));
        selectOptions.setPreferredSize(new Dimension(700, 50));
        selectOptions.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton allEmployees = new JButton("All Employees");
        JButton zookeepers = new JButton("Zookeepers");
        JButton vets = new JButton("Vets");
        JButton managers = new JButton("Managers");


         allEmployees.addActionListener(e -> sharedInfo());
        zookeepers.addActionListener(e -> zookeeperInfo());
        vets.addActionListener(e -> vetInfo());
        managers.addActionListener(e -> managerInfo());

        selectOptions.add(allEmployees);
        selectOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        selectOptions.add(zookeepers);
        selectOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        selectOptions.add(vets);
        selectOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        selectOptions.add(managers);
       
        JPanel employeeButtons = new JPanel();
        employeeButtons.setLayout(new BoxLayout(employeeButtons, BoxLayout.LINE_AXIS));
        employeeButtons.setPreferredSize(new Dimension(700, 50));
        employeeButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addEmployee = new JButton("Add Employee");
        JButton updateEmployee = new JButton("Update Employee Info");
        JButton searchEmployees = new JButton("Basic Search");
        JButton advanced = new JButton("Advanced Search");

        addEmployee.addActionListener(e -> addEmployeeDialog.showFrame());
        updateEmployee.addActionListener(e -> updateEmployeeDialog.showFrame());
        searchEmployees.addActionListener(e -> searchEmployeeDialog.showFrame());
        advanced.addActionListener(e -> advancedEmployeeDialog.showFrame());

        employeeButtons.add(addEmployee);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(updateEmployee);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(searchEmployees);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(advanced);

        contentPane.add(titlePane);
        contentPane.add(employeeScroll);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(selectOptions);
        contentPane.add(employeeButtons);

        this.setSize(800, 500);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void sharedInfo() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Employee ID");
        columnNames.add("Name");
        columnNames.add("Start Date");
        columnNames.add("End Date");
        columnNames.add("On Duty?");
        tableModel.setColumnIdentifiers(columnNames);

        ZooEmployeeModel[] employees = dbhandler.getEmployeeInfo();
        Vector<String> employeeData;
        for (ZooEmployeeModel employee: employees) {
            employeeData = new Vector<>();
            employeeData.add(employee.getEmployee_ID());
            employeeData.add(employee.getName());
            String enddate;
            if (employee.getEndDate() == null) {
                enddate = "Currently employed";
            } else {
                enddate = employee.getEndDate().toString();
            }
            employeeData.add(employee.getStartDate().toString());
            employeeData.add(enddate);
            employeeData.add(Character.toString(employee.getOnDuty()));
            tableModel.addRow(employeeData);
        }
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }

    public void zookeeperInfo() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Vector columnNames = new Vector<>(Arrays.asList("Employee ID","Name", "Start Date", "End Date", "On Duty?", "Event Duty?"));
        tableModel.setColumnIdentifiers(columnNames);

        ZookeeperEmployeeModel[] employees = dbhandler.getZookeeperEmployeeInfo();
        Vector<String> employeeData;
        for (ZookeeperEmployeeModel employee: employees) {
            employeeData = new Vector<>();
            employeeData.add(employee.getEmployee_ID());
            employeeData.add(employee.getName());
            String enddate;
            if (employee.getEndDate() == null) {
                enddate = "Currently employed";
            } else {
                enddate = employee.getEndDate().toString();
            }
            employeeData.add(employee.getStartDate().toString());
            employeeData.add(enddate);
            employeeData.add(Character.toString(employee.getOnDuty()));
            employeeData.add(Character.toString(employee.getEventDuty()));
            tableModel.addRow(employeeData);
        }
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }

    public void vetInfo() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Employee ID");
        columnNames.add("Name");
        columnNames.add("Start Date");
        columnNames.add("End Date");
        columnNames.add("On Duty?");
        columnNames.add("On Call?");
        columnNames.add("Experience");
        columnNames.add("Specialization");
        columnNames.add("Phone Number");
        tableModel.setColumnIdentifiers(columnNames);

        VetEmployeeModel[] vets = dbhandler.getVetEmployeeInfo();
        Vector<String> vetEmployeeData;
        for (VetEmployeeModel vet: vets) {
            vetEmployeeData = new Vector<>();
            vetEmployeeData.add(vet.getEmployee_ID());
            vetEmployeeData.add(vet.getName());
            String enddate;
            if (vet.getEndDate() == null) {
                enddate = "Currently employed";
            } else {
                enddate = vet.getEndDate().toString();
            }
            vetEmployeeData.add(vet.getStartDate().toString());
            vetEmployeeData.add(enddate);
            vetEmployeeData.add(Character.toString(vet.getOnDuty()));
            vetEmployeeData.add(Character.toString(vet.getOnCall()));
            vetEmployeeData.add(Integer.toString(vet.getExperience()));
            vetEmployeeData.add(vet.getSpecialization());
            if (vet.getPhoneNumber() == null) {
                vetEmployeeData.add("N/A");
            }
            else {
                vetEmployeeData.add(vet.getPhoneNumber());
            }
            tableModel.addRow(vetEmployeeData);
        }
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }

    public void managerInfo() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Employee ID");
        columnNames.add("Name");
        columnNames.add("Start Date");
        columnNames.add("End Date");
        columnNames.add("On Duty?");
        columnNames.add("In Office?");
        columnNames.add("Office Number");
        tableModel.setColumnIdentifiers(columnNames);

        ManagerEmployeeModel[] employees = dbhandler.getManagerEmployeeInfo();
        Vector<String> employeeData;
        for (ManagerEmployeeModel employee: employees) {
            employeeData = new Vector<>();
            employeeData.add(employee.getEmployee_ID());
            employeeData.add(employee.getName());
            String enddate;
            if (employee.getEndDate() == null) {
                enddate = "Currently employed";
            } else {
                enddate = employee.getEndDate().toString();
            }
            employeeData.add(employee.getStartDate().toString());
            employeeData.add(enddate);
            employeeData.add(Character.toString(employee.getOnDuty()));
            employeeData.add(Character.toString(employee.getInOffice()));
            employeeData.add(Integer.toString(employee.getOfficeNumber()));
            tableModel.addRow(employeeData);
        }
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }

    public JPanel createTitlePane() {
        JPanel panel =  new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = new JLabel();
        title.setPreferredSize(new Dimension(550, 100));
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        title.setText("Employee Management");
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(title);

        return panel;
    }
}
