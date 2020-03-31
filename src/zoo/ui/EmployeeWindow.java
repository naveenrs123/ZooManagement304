package zoo.ui;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilCalendarModel;
import org.jdatepicker.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Properties;

public class EmployeeWindow extends JFrame {
    ArrayList<JTextField> textFieldList = new ArrayList<>();
    ArrayList<JComboBox> comboBoxList = new ArrayList<>();
    ArrayList<JDatePicker> datePickers = new ArrayList<>();

    public EmployeeWindow() {
        super("Employee Management");
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        this.setSize(1200, 800);
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
        JButton resetView = new JButton("Reset View");
        employeeButtons.add(addEmployee);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(deleteEmployee);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(updateEmployee);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(searchEmployees);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(resetView);

        JTextPane infoPanel = new JTextPane();
        infoPanel.setOpaque(false);
        infoPanel.setEditable(false);
        StyledDocument infoText = infoPanel.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontSize(center, 16);
        infoPanel.setCharacterAttributes(center, false);
        infoText.setParagraphAttributes(0, infoText.getLength(), center, false);
        try {
            infoText.insertString(0, "Usage Guidelines\n\nTo add, update, or delete an employee, provide the values needed" +
                    " to add/update/delete in the appropriate spots, then click the button that corresponds to the action of your choice." +
                    " To search, enter the search conditions in the input boxes and then click search.", center);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        infoPanel.setMaximumSize(new Dimension(800, 200));

        JPanel inputsPane = new JPanel();
        inputsPane.setLayout(new BoxLayout(inputsPane, BoxLayout.LINE_AXIS));
        
        JPanel allEmployeesInput = new JPanel();
        allEmployeesInput.setLayout(new BoxLayout(allEmployeesInput, BoxLayout.PAGE_AXIS));
        allEmployeesInput.setMaximumSize(new Dimension(350, 400));
        allEmployeesInput.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel allEmployeesLabel = createInfoLabel("All Employees");
        JPanel employeeIDPanel = createTextInputPanel("Employee ID");
        JPanel namePanel = createTextInputPanel("Name");

        JPanel startDatePanel = createDatepickerInputPanel("Start Date");
        JComboBox<String> startDateCombo = createOperatorComboBox();
        startDatePanel.add(startDateCombo);

        JPanel endDatePanel = createDatepickerInputPanel("End Date");
        JComboBox<String> endDateCombo = createOperatorComboBox();
        endDatePanel.add(endDateCombo);

        JPanel onDutyPanel = createDropdownInputPanel("On Duty", new String[] {"","T", "F"});

        allEmployeesInput.add(allEmployeesLabel);
        allEmployeesInput.add(Box.createRigidArea(new Dimension(0, 20)));
        allEmployeesInput.add(employeeIDPanel);
        allEmployeesInput.add(namePanel);
        allEmployeesInput.add(startDatePanel);
        allEmployeesInput.add(endDatePanel);
        allEmployeesInput.add(onDutyPanel);

        JPanel vetInput = new JPanel();
        vetInput.setLayout(new BoxLayout(vetInput, BoxLayout.PAGE_AXIS));
        vetInput.setMaximumSize(new Dimension(350, 350));
        vetInput.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel vetLabel = createInfoLabel("Vets");
        JPanel onCallPanel = createDropdownInputPanel("On Call",  new String[] {"", "T", "F"});
        JPanel experiencePanel = createTextInputPanel("Experience");
        JPanel specializationPanel = createTextInputPanel("Specialization");
        JPanel phoneNumberPanel = createTextInputPanel("Phone Number");

        vetInput.add(vetLabel);
        vetInput.add(Box.createRigidArea(new Dimension(0, 20)));
        vetInput.add(onCallPanel);
        vetInput.add(experiencePanel);
        vetInput.add(specializationPanel);
        vetInput.add(phoneNumberPanel);

        JPanel keeperManagerInput = new JPanel();
        keeperManagerInput.setLayout(new BoxLayout(keeperManagerInput, BoxLayout.PAGE_AXIS));
        keeperManagerInput.setMaximumSize(new Dimension(350, 350));
        keeperManagerInput.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel zookeeperLabel = createInfoLabel("Zookeepers");
        JPanel eventDutyPanel = createDropdownInputPanel("Event Duty", new String[] {"", "T", "F"});

        JLabel managerLabel = createInfoLabel("Managers");
        JPanel inOfficePanel = createDropdownInputPanel("In Office",  new String[] {"", "T", "F"});
        JPanel officeNumberPanel = createTextInputPanel("Office Number");

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
        contentPane.add(infoPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPane.add(inputsPane);
        contentPane.add(employeeButtons);

        this.setVisible(true);
    }

    public JComboBox<String> createOperatorComboBox() {
        JComboBox panelCombo = new JComboBox(new String[] {">", ">=", "<", "<=", "=", "<>"});
        panelCombo.setMaximumSize(new Dimension(20, 20));
        panelCombo.setAlignmentX(Component.RIGHT_ALIGNMENT);
        return panelCombo;
    }

    public JPanel createTextInputPanel(String labelText) {
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

        JCheckBox panelCheckBox = new JCheckBox();
        panelCheckBox.setAlignmentX(Component.RIGHT_ALIGNMENT);

        panel.add(panelText);
        panel.add(panelField);
        panel.add(panelCheckBox);

        textFieldList.add(panelField);
        return panel;
    }

    public JPanel createDropdownInputPanel(String labelText, String[] choices) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel panelText = new JLabel(labelText);
        panelText.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelText.setBorder(new EmptyBorder(0, 0, 0, 10));

        JComboBox panelCombo = new JComboBox(choices);
        panelCombo.setMaximumSize(new Dimension(100, 16));
        panelCombo.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JCheckBox panelCheckBox = new JCheckBox();
        panelCheckBox.setAlignmentX(Component.RIGHT_ALIGNMENT);

        panel.add(panelText);
        panel.add(panelCombo);
        panel.add(panelCheckBox);

        comboBoxList.add(panelCombo);
        return panel;
    }

    public JPanel createDatepickerInputPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel panelText = new JLabel(labelText);
        panelText.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelText.setBorder(new EmptyBorder(0, 0, 0, 10));

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        UtilCalendarModel model = new UtilCalendarModel();
        JDatePicker datePicker = new JDatePicker(model);

        //datePicker.setMaximumSize(new Dimension(100, 16));
        datePicker.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JCheckBox panelCheckBox = new JCheckBox();
        panelCheckBox.setAlignmentX(Component.RIGHT_ALIGNMENT);

        panel.add(panelText);
        panel.add(datePicker);
        panel.add(panelCheckBox);

        datePickers.add(datePicker);
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
