package zoo.ui;

import zoo.database.DatabaseConnectionHandler;
import zoo.model.ZooEmployeeModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class EmployeeWindow extends JFrame {
    AddEmployeeDialog addEmployeeDialog;
    UpdateEmployeeDialog updateEmployeeDialog;
    DatabaseConnectionHandler dbhandler;

    public EmployeeWindow(DatabaseConnectionHandler dbhandler) {
        super("Employee Management");
        this.dbhandler = dbhandler;
        addEmployeeDialog = new AddEmployeeDialog();
        updateEmployeeDialog = new UpdateEmployeeDialog();
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        this.setSize(800, 450);
        this.setResizable(false);

        JPanel titlePane = createTitlePane();
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Employee ID");
        columnNames.add("Name");
        columnNames.add("Start Date");
        columnNames.add("End Date");
        columnNames.add("On Duty?");

        Vector<Vector<String>> rowData = new Vector();
        ZooEmployeeModel[] employees = dbhandler.getEmployeeInfo();
        System.out.println(employees.length);
        Vector<String> employeeData = new Vector<>();
        System.out.println("hooooo");
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
            rowData.add(employeeData);
        }
        JTable table = new JTable(rowData, columnNames);
        JList<String> employeeList = new JList<String>();
        employeeList.setFixedCellWidth(600);
        JScrollPane employeeScroll = new JScrollPane(table);
        employeeScroll.setPreferredSize(new Dimension(700, 300));
        employeeScroll.setMaximumSize(new Dimension(700, 300));
        employeeScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel employeeButtons = new JPanel();
        employeeButtons.setLayout(new BoxLayout(employeeButtons, BoxLayout.LINE_AXIS));
        employeeButtons.setPreferredSize(new Dimension(700, 50));
        employeeButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addEmployee = new JButton("Add Employee");
        JButton updateEmployee = new JButton("Update Employee Info");
        JButton searchEmployees = new JButton("Search");
        JButton resetView = new JButton("Reset View");

        addEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployeeDialog.showFrame();
            }
        });
        updateEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployeeDialog.showFrame();
            }
        });

        employeeButtons.add(addEmployee);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(updateEmployee);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(searchEmployees);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(resetView);


        contentPane.add(titlePane);
        contentPane.add(employeeScroll);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(employeeButtons);

        this.setVisible(true);
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
