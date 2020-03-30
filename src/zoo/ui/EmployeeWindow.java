package zoo.ui;

import javafx.scene.shape.Line;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class EmployeeWindow extends JFrame {
    ArrayList<JTextField> textFieldList = new ArrayList<JTextField>();

    public EmployeeWindow() {
        super("Employee Management");
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        this.setSize(1000, 600);
        this.setResizable(false);

        JPanel titlePane = createTitlePane();

        JScrollPane employeeScroll = new JScrollPane();
        employeeScroll.setPreferredSize(new Dimension(900, 200));
        employeeScroll.setMaximumSize(new Dimension(900, 200));
        employeeScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        JList<String> employeeList = new JList<String>();
        employeeList.setFixedCellWidth(900);
        employeeScroll.add(employeeList);

        JPanel employeeButtons = new JPanel();
        employeeButtons.setLayout(new BoxLayout(employeeButtons, BoxLayout.LINE_AXIS));
        employeeButtons.setPreferredSize(new Dimension(900, 50));
        employeeButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton addEmployee = new JButton("Add");
        JButton deleteEmployee = new JButton("Delete");
        JButton updateEmployee = new JButton("Update");
        JButton searchEmployees = new JButton("Search");
        employeeButtons.add(addEmployee);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(deleteEmployee);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(updateEmployee);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(searchEmployees);

        JPanel inputsPane = new JPanel();
        inputsPane.setLayout(new BoxLayout(inputsPane, BoxLayout.LINE_AXIS));
        
        JPanel allEmployeesInput = new JPanel();
        allEmployeesInput.setLayout(new BoxLayout(allEmployeesInput, BoxLayout.PAGE_AXIS));
        allEmployeesInput.setMaximumSize(new Dimension(250, 350));
        allEmployeesInput.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel allEmployeesLabel = createInfoLabel("All Employees");
        JPanel employeeIDPanel = createInputPanel("Employee ID");
        JPanel namePanel = createInputPanel("Name");
        JPanel startDatePanel = createInputPanel("Start Date");
        JPanel endDatePanel = createInputPanel("End Date");
        JPanel onDutyPanel = createInputPanel("On Duty");

        allEmployeesInput.add(allEmployeesLabel);
        allEmployeesInput.add(Box.createRigidArea(new Dimension(0, 20)));
        allEmployeesInput.add(employeeIDPanel);
        allEmployeesInput.add(namePanel);
        allEmployeesInput.add(startDatePanel);
        allEmployeesInput.add(endDatePanel);
        allEmployeesInput.add(onDutyPanel);

        JPanel vetInput = new JPanel();
        vetInput.setLayout(new BoxLayout(vetInput, BoxLayout.PAGE_AXIS));
        vetInput.setMaximumSize(new Dimension(250, 350));
        vetInput.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel vetLabel = createInfoLabel("Vets");
        JPanel onCallPanel = createInputPanel("On Call");
        JPanel experiencePanel = createInputPanel("Experience");
        JPanel specializationPanel = createInputPanel("Specialization");
        JPanel phoneNumberPanel = createInputPanel("Phone Number");

        vetInput.add(vetLabel);
        vetInput.add(Box.createRigidArea(new Dimension(0, 20)));
        vetInput.add(onCallPanel);
        vetInput.add(experiencePanel);
        vetInput.add(specializationPanel);
        vetInput.add(phoneNumberPanel);

        JPanel keeperManagerInput = new JPanel();
        keeperManagerInput.setLayout(new BoxLayout(keeperManagerInput, BoxLayout.PAGE_AXIS));
        keeperManagerInput.setMaximumSize(new Dimension(250, 350));
        keeperManagerInput.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel zookeeperLabel = createInfoLabel("Zookeepers");
        JPanel eventDutyPanel = createInputPanel("Event Duty");

        JLabel managerLabel = createInfoLabel("Managers");
        JPanel inOfficePanel = createInputPanel("In Office");
        JPanel officeNumberPanel = createInputPanel("Office Number");

        keeperManagerInput.add(zookeeperLabel);
        keeperManagerInput.add(Box.createRigidArea(new Dimension(0, 20)));
        keeperManagerInput.add(eventDutyPanel);
        keeperManagerInput.add(Box.createRigidArea(new Dimension(0, 10)));
        keeperManagerInput.add(managerLabel);
        keeperManagerInput.add(Box.createRigidArea(new Dimension(0, 20)));
        keeperManagerInput.add(inOfficePanel);
        keeperManagerInput.add(officeNumberPanel);

        inputsPane.add(allEmployeesInput);
        inputsPane.add(vetInput);
        inputsPane.add(keeperManagerInput);

        contentPane.add(titlePane);
        contentPane.add(employeeScroll);
        contentPane.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPane.add(inputsPane);
        contentPane.add(employeeButtons);

        this.setVisible(true);
    }

    public JPanel createInputPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JLabel panelText = new JLabel(labelText);
        panelText.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelText.setBorder(new EmptyBorder(0, 0, 0, 10));
        JTextField panelField = new JTextField();
        panelField.setMaximumSize(new Dimension(100, 16));
        panelField.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(panelText);
        panel.add(panelField);

        textFieldList.add(panelField);
        return panel;
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

    public JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.RIGHT_ALIGNMENT);
        label.setBorder(new EmptyBorder(0, 0, 0, 5));
        label.setFont(new Font("Sans Serif", Font.BOLD, 16));
        return label;
    }
}
