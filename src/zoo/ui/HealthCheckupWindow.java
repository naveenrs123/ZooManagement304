package zoo.ui;

import zoo.database.DatabaseConnectionHandler;
import zoo.model.ZooEmployeeModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class HealthCheckupWindow extends JFrame {

    AddHealthCheckup addHealthCheckupDialog;
    DatabaseConnectionHandler dbhandler;
    JScrollPane healthScroll;
    JTable table;

    public HealthCheckupWindow(DatabaseConnectionHandler dbhandler) {
        super("Health Checkups");
        this.dbhandler = dbhandler;
        this.table = new JTable();
        this.addHealthCheckupDialog = new AddHealthCheckup();
    }


    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        JPanel titlePane = createTitlePane();
        sharedInfo();

        healthScroll = new JScrollPane(table);
        healthScroll.setPreferredSize(new Dimension(700, 300));
        healthScroll.setMaximumSize(new Dimension(700, 300));

        JPanel selectOptions = new JPanel();
        selectOptions.setLayout(new BoxLayout(selectOptions, BoxLayout.LINE_AXIS));
        selectOptions.setPreferredSize(new Dimension(700, 50));
        selectOptions.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel healthCheckupButtons = new JPanel();
        healthCheckupButtons.setLayout(new BoxLayout(healthCheckupButtons, BoxLayout.LINE_AXIS));
        healthCheckupButtons.setPreferredSize(new Dimension(700, 50));
        healthCheckupButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addCheckup = new JButton("Log Health Checkup");
        JButton searchCheckups = new JButton("Search");
        JButton resetView = new JButton("Reset View");

        addCheckup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           //     addEmployeeDialog.showFrame();
            }
        });
        resetView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sharedInfo();
            }
        });

        healthCheckupButtons.add(addCheckup);
        healthCheckupButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        healthCheckupButtons.add(searchCheckups);
        healthCheckupButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        healthCheckupButtons.add(resetView);

        contentPane.add(titlePane);
        contentPane.add(healthScroll);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(selectOptions);
        contentPane.add(healthCheckupButtons);

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

    public JPanel createTitlePane() {
        JPanel panel =  new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = new JLabel();
        title.setPreferredSize(new Dimension(550, 100));
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        title.setText("Health Checkup Records Management");
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(title);

        return panel;
    }
}
