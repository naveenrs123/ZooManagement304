package zoo.ui;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilCalendarModel;
import zoo.database.DatabaseConnectionHandler;
import zoo.model.ManagerEmployeeModel;
import zoo.model.VetEmployeeModel;
import zoo.model.ZooEmployeeModel;
import zoo.model.ZookeeperEmployeeModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

public class AddEmployeeDialog extends JFrame {
    DatabaseConnectionHandler dbhandler;
    JTable table;

    /**
     * 0: Employee ID, 1: Name, 2: Experience, 3: Specialization, 4: Phone Number
     * 5: Office Number.
     */
    ArrayList<JTextField> textFieldList = new ArrayList<>();

    /**
     * 0: On Duty, 1: On Call, 2: Event Duty, 3: In Office
     */
    ArrayList<JComboBox> comboBoxList = new ArrayList<>();

    /**
     * 0: Start Date, 1: End Date
     */
    ArrayList<JDatePicker> datePickers = new ArrayList<>();

    public AddEmployeeDialog(DatabaseConnectionHandler dbhandler, JTable table) {
        super("Add Employee");
        this.dbhandler = dbhandler;
        this.table = table;
    }

    private void clear() {
        textFieldList.clear();
        comboBoxList.clear();
        datePickers.clear();
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        this.setSize(900, 500);
        this.setResizable(false);

        JTextPane infoPanel = createInfoPanel();

        JPanel inputsPane = new JPanel();
        inputsPane.setLayout(new BoxLayout(inputsPane, BoxLayout.LINE_AXIS));
        inputsPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel allEmployeesInput = new JPanel();
        allEmployeesInput.setLayout(new BoxLayout(allEmployeesInput, BoxLayout.PAGE_AXIS));
        allEmployeesInput.setMaximumSize(new Dimension(300, 300));
        allEmployeesInput.setAlignmentY(Component.TOP_ALIGNMENT);
        allEmployeesInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel allEmployeesLabel = createInfoLabel("All Employees");
        JPanel employeeIDPanel = createTextInputPanel("Employee ID");
        JPanel namePanel = createTextInputPanel("Name");
        JPanel startDatePanel = createDatepickerInputPanel("Start Date");
        JPanel endDatePanel = createDatepickerInputPanel("End Date");
        JPanel onDutyPanel = createDropdownInputPanel("On Duty", new String[] {" ","T", "F"});

        allEmployeesInput.add(allEmployeesLabel);
        allEmployeesInput.add(Box.createRigidArea(new Dimension(0, 20)));
        allEmployeesInput.add(employeeIDPanel);
        allEmployeesInput.add(namePanel);
        allEmployeesInput.add(startDatePanel);
        allEmployeesInput.add(endDatePanel);
        allEmployeesInput.add(onDutyPanel);

        JPanel vetInput = new JPanel();
        vetInput.setLayout(new BoxLayout(vetInput, BoxLayout.PAGE_AXIS));
        vetInput.setMaximumSize(new Dimension(300, 300));
        vetInput.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel vetLabel = createInfoLabel("Vets");
        JPanel onCallPanel = createDropdownInputPanel("On Call",  new String[] {" ", "T", "F"});
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
        keeperManagerInput.setMaximumSize(new Dimension(300, 300));
        keeperManagerInput.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel zookeeperLabel = createInfoLabel("Zookeepers");
        JPanel eventDutyPanel = createDropdownInputPanel("Event Duty", new String[] {" ", "T", "F"});

        JLabel managerLabel = createInfoLabel("Managers");
        JPanel inOfficePanel = createDropdownInputPanel("In Office",  new String[] {" ", "T", "F"});
        JPanel officeNumberPanel = createTextInputPanel("Office Number");

        keeperManagerInput.add(zookeeperLabel);
        keeperManagerInput.add(Box.createRigidArea(new Dimension(0, 20)));
        keeperManagerInput.add(eventDutyPanel);
        keeperManagerInput.add(Box.createRigidArea(new Dimension(0, 10)));
        keeperManagerInput.add(managerLabel);
        keeperManagerInput.add(Box.createRigidArea(new Dimension(0, 20)));
        keeperManagerInput.add(inOfficePanel);
        keeperManagerInput.add(officeNumberPanel);

        inputsPane.add(Box.createRigidArea(new Dimension(50, 0)));
        inputsPane.add(allEmployeesInput);
        inputsPane.add(vetInput);
        inputsPane.add(keeperManagerInput);

        JPanel employeeButtons = new JPanel();
        employeeButtons.setLayout(new BoxLayout(employeeButtons, BoxLayout.LINE_AXIS));
        employeeButtons.setPreferredSize(new Dimension(800, 50));
        employeeButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton submit = new JButton("Submit");
        JButton clear = new JButton("Clear Fields");

        submit.addActionListener(e -> {
            insertEmployee();
            clear();
            dispose();
        });

        employeeButtons.add(submit);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(clear);

        contentPane.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPane.add(infoPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPane.add(inputsPane);
        contentPane.add(employeeButtons);

        this.setVisible(true);
    }

    private void insertEmployee() {
        String employeeID = textFieldList.get(0).getText().trim();
        String name = textFieldList.get(1).getText().trim();
        Date startDate = getDate(datePickers.get(0));
        Date endDate = getDate(datePickers.get(1));
        char onDuty = ((String) comboBoxList.get(0).getSelectedItem()).charAt(0);

        char onCall = ((String) comboBoxList.get(1).getSelectedItem()).charAt(0);

        String experienceString = textFieldList.get(2).getText().trim();
        int experience = -1;
        if (!experienceString.equals("")) {
            experience = Integer.parseInt(experienceString);
        }

        String specialization = textFieldList.get(3).getText().trim();
        String phoneNumber = textFieldList.get(4).getText().trim();

        char eventDuty = ((String) comboBoxList.get(2).getSelectedItem()).charAt(0);

        char inOffice = ((String) comboBoxList.get(3).getSelectedItem()).charAt(0);

        String officeNumberString = textFieldList.get(5).getText().trim();
        int officeNumber = -1;
        if (!officeNumberString.equals("")) {
            officeNumber = Integer.parseInt(officeNumberString);
        }

        if (onCall != ' ') {
            VetEmployeeModel vetEmployeeModel = new VetEmployeeModel(employeeID, name, startDate, endDate, onDuty, onCall, experience, specialization, phoneNumber);
            dbhandler.insertVetEmployee(vetEmployeeModel);
        }
        else if (eventDuty != ' ') {
            ZookeeperEmployeeModel zookeeperEmployeeModel = new ZookeeperEmployeeModel(employeeID, name, startDate, endDate, onDuty, eventDuty);
            dbhandler.insertZookeeperEmployee(zookeeperEmployeeModel);
        }
        else if (inOffice != ' ') {
            ManagerEmployeeModel managerEmployeeModel = new ManagerEmployeeModel(employeeID, name, startDate, endDate, onDuty, inOffice, officeNumber);
            dbhandler.insertManagerEmployee(managerEmployeeModel);
        }
        else {
            ZooEmployeeModel zooEmployeeModel = new ZooEmployeeModel(employeeID, name, startDate, endDate, onDuty);
            dbhandler.insertEmployee(zooEmployeeModel);
        }
        sharedInfo();
    }

    private Date getDate(JDatePicker datePicker) {
        if (!datePicker.getModel().isSelected()) {
            return null;
        }
        else {
            int day = datePicker.getModel().getDay();
            int month = datePicker.getModel().getMonth() + 1;
            int year = datePicker.getModel().getYear();

            return Date.valueOf(year + "-" + month + "-" + day);
        }
    }

    private JTextPane createInfoPanel() {
        JTextPane infoPanel = new JTextPane();
        infoPanel.setOpaque(false);
        infoPanel.setEditable(false);
        StyledDocument infoText = infoPanel.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
        StyleConstants.setFontSize(center, 16);
        infoPanel.setCharacterAttributes(center, false);
        infoText.setParagraphAttributes(0, infoText.getLength(), center, false);
        try {
            infoText.insertString(0, "To add a new employee to the system, type in all the appropriate details for that " +
                    " employee.\n\nFor each type of employee, fill in the fields for all employees as well as the fields for the type of" +
                    " employee being added.\n\nWhen you are done, click the 'Submit' button. If you need to clear all fields, hit the" +
                    " 'Clear Fields' button to reset all inputs.", center);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        infoPanel.setMaximumSize(new Dimension(850, 200));
        return infoPanel;
    }

    public JPanel createTextInputPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel panelText = new JLabel(labelText);
        panelText.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelText.setBorder(new EmptyBorder(0, 0, 0, 10));

        JTextField panelField = new JTextField();
        panelField.setMaximumSize(new Dimension(100, 30));
        panelField.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(panelText);
        panel.add(panelField);

        textFieldList.add(panelField);
        return panel;
    }

    public JPanel createDropdownInputPanel(String labelText, String[] choices) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel panelText = new JLabel(labelText);
        panelText.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelText.setBorder(new EmptyBorder(0, 0, 0, 10));

        JComboBox panelCombo = new JComboBox(choices);
        panelCombo.setMaximumSize(new Dimension(100, 30));
        panelCombo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(panelText);
        panel.add(panelCombo);

        comboBoxList.add(panelCombo);
        return panel;
    }

    public JPanel createDatepickerInputPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel panelText = new JLabel(labelText);
        panelText.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelText.setBorder(new EmptyBorder(0, 0, 0, 10));

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        UtilCalendarModel model = new UtilCalendarModel();
        JDatePicker datePicker = new JDatePicker(model);
        datePicker.setMaximumSize(new Dimension(150, 30));

        datePicker.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(panelText);
        panel.add(datePicker);

        datePickers.add(datePicker);
        return panel;
    }

    public JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(0, 0, 0, 5));
        label.setFont(new Font("Sans Serif", Font.BOLD, 16));
        return label;
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
}
