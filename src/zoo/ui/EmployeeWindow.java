package zoo.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeWindow extends JFrame {
    AddEmployeeDialog addEmployeeDialog;
    UpdateEmployeeDialog updateEmployeeDialog;

    public EmployeeWindow() {
        super("Employee Management");
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

        JScrollPane employeeScroll = new JScrollPane();
        employeeScroll.setPreferredSize(new Dimension(700, 300));
        employeeScroll.setMaximumSize(new Dimension(700, 300));
        employeeScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        JList<String> employeeList = new JList<String>();
        employeeList.setFixedCellWidth(600);
        employeeScroll.add(employeeList);

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
