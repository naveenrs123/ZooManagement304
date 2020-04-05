package zoo.ui;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilCalendarModel;
import zoo.database.DatabaseConnectionHandler;
import zoo.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;

public class SearchEmployeeDialog extends JFrame {
    DatabaseConnectionHandler dbhandler;
    JTable table;
    JCheckBox vetCheckbox;
    JCheckBox zookeeperCheckbox;
    JCheckBox managerCheckbox;

    ArrayList<String> arithmeticFields = new ArrayList<>(Arrays.asList("Start Date", "End Date", "Experience", "Office Number"));

    /**
     * 0: Employee ID, 1: Name, 2: Experience, 3: Specialization, 4: Phone Number
     * 5: Office Number.
     */
    ArrayList<JTextField> textFieldList = new ArrayList<>();

    /**
     * 0: On Duty, 1: On Call, 2: Event Duty, 3: In Office
     */
    ArrayList<JComboBox<String>> comboBoxList = new ArrayList<>();

    /**
     * 0: Start Date, 1: End Date
     */
    ArrayList<JDatePicker> datePickers = new ArrayList<>();

    /**
     * 0: Employee ID, 1: Name, 2: Start Date, 3: End Date, 4: On Duty
     * 5: On Call, 6: Experience, 7: Specialization, 8: Phone Number
     * 9: Event Duty, 10: In Office, 11: Office Number
     */
    ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

    /**
     * 0: Start Date, 1: End Date, 2: Experience, 3: Office Number
     */
    ArrayList<JComboBox<String>> conditionBoxes = new ArrayList<>();

    public SearchEmployeeDialog(DatabaseConnectionHandler dbhandler, JTable table) {
        super("Search Employees");
        this.dbhandler = dbhandler;
        this.table = table;
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        this.setSize(1100, 500);
        this.setResizable(false);

        JTextPane infoPanel = createInfoPanel();

        JPanel inputsPane = new JPanel();
        inputsPane.setLayout(new BoxLayout(inputsPane, BoxLayout.LINE_AXIS));
        inputsPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel allEmployeesInput = new JPanel();
        allEmployeesInput.setLayout(new BoxLayout(allEmployeesInput, BoxLayout.PAGE_AXIS));
        allEmployeesInput.setMaximumSize(new Dimension(400, 300));
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
        vetInput.setMaximumSize(new Dimension(380, 300));
        vetInput.setAlignmentY(Component.TOP_ALIGNMENT);

        JPanel vetLabelPanel = new JPanel();
        vetLabelPanel.setLayout(new BoxLayout(vetLabelPanel, BoxLayout.LINE_AXIS));
        vetLabelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        vetCheckbox = new JCheckBox();
        JLabel vetLabel = createInfoLabel("Vets");
        vetLabelPanel.add(vetCheckbox);
        vetLabelPanel.add(vetLabel);

        JPanel onCallPanel = createDropdownInputPanel("On Call",  new String[] {" ", "T", "F"});
        JPanel experiencePanel = createTextInputPanel("Experience");
        JPanel specializationPanel = createTextInputPanel("Specialization");
        JPanel phoneNumberPanel = createTextInputPanel("Phone Number");

        vetInput.add(vetLabelPanel);
        vetInput.add(Box.createRigidArea(new Dimension(0, 20)));
        vetInput.add(onCallPanel);
        vetInput.add(experiencePanel);
        vetInput.add(specializationPanel);
        vetInput.add(phoneNumberPanel);

        JPanel keeperManagerInput = new JPanel();
        keeperManagerInput.setLayout(new BoxLayout(keeperManagerInput, BoxLayout.PAGE_AXIS));
        keeperManagerInput.setMaximumSize(new Dimension(380, 300));
        keeperManagerInput.setAlignmentY(Component.TOP_ALIGNMENT);
        
        JPanel zookeeperLabelPanel = new JPanel();
        zookeeperLabelPanel.setLayout(new BoxLayout(zookeeperLabelPanel, BoxLayout.LINE_AXIS));
        zookeeperLabelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        zookeeperCheckbox = new JCheckBox();
        JLabel zookeeperLabel = createInfoLabel("Zookeepers");
        zookeeperLabelPanel.add(zookeeperCheckbox);
        zookeeperLabelPanel.add(zookeeperLabel);
        
        
        JPanel eventDutyPanel = createDropdownInputPanel("Event Duty", new String[] {" ", "T", "F"});

        JPanel managerLabelPanel = new JPanel();
        managerLabelPanel.setLayout(new BoxLayout(managerLabelPanel, BoxLayout.LINE_AXIS));
        managerLabelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        managerCheckbox = new JCheckBox();
        JLabel managerLabel = createInfoLabel("Managers");
        managerLabelPanel.add(managerCheckbox);
        managerLabelPanel.add(managerLabel);
        
        JPanel inOfficePanel = createDropdownInputPanel("In Office",  new String[] {" ", "T", "F"});
        JPanel officeNumberPanel = createTextInputPanel("Office Number");

        keeperManagerInput.add(zookeeperLabelPanel);
        keeperManagerInput.add(Box.createRigidArea(new Dimension(0, 20)));
        keeperManagerInput.add(eventDutyPanel);
        keeperManagerInput.add(Box.createRigidArea(new Dimension(0, 10)));
        keeperManagerInput.add(managerLabelPanel);
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
        JButton advanced = new JButton("Advanced");
        JButton submit = new JButton("Submit");
        JButton clear = new JButton("Clear Fields");


        submit.addActionListener(e -> searchEmployees());
        clear.addActionListener(e -> resetFields());

        employeeButtons.add(advanced);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
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

    private void resetFields() {
        for (JTextField field : textFieldList) {
            field.setText("");
        }
        for (JComboBox<String> comboBox : comboBoxList) {
            comboBox.setSelectedIndex(0);
        }
        for (JDatePicker datePicker : datePickers) {
            datePicker.getFormattedTextField().setText("");
        }
        for (JComboBox<String> comboBox : conditionBoxes) {
            comboBox.setSelectedIndex(0);
        }
        for (int i = 1; i < checkBoxes.size(); i++) {
            checkBoxes.get(i).setSelected(false);
        }
        checkBoxes.get(0).setSelected(true);

        vetCheckbox.setSelected(false);
        zookeeperCheckbox.setSelected(false);
        managerCheckbox.setSelected(false);
    }

    private void searchEmployees() {
        // General
        ArrayList<Boolean> selectedColumns = new ArrayList<>();
        ArrayList<String> conditions = new ArrayList<>();

        String employeeID = textFieldList.get(0).getText().trim();
        selectedColumns.add(checkBoxes.get(0).isSelected());

        String name = textFieldList.get(1).getText().trim();
        selectedColumns.add(checkBoxes.get(1).isSelected());

        Date startDate = getDate(datePickers.get(0));
        selectedColumns.add(checkBoxes.get(2).isSelected());
        
        String startDateCondition = (String) conditionBoxes.get(0).getSelectedItem();
        if (!Objects.equals(startDateCondition, " ")) {
            conditions.add(startDateCondition);
        }

        Date endDate = getDate(datePickers.get(1));
        selectedColumns.add(checkBoxes.get(3).isSelected());
        String endDateCondition = (String) conditionBoxes.get(0).getSelectedItem();
        if (!Objects.equals(endDateCondition, " ")) {
            conditions.add(endDateCondition);
        }

        char onDuty = ((String) Objects.requireNonNull(comboBoxList.get(0).getSelectedItem())).charAt(0);
        selectedColumns.add(checkBoxes.get(4).isSelected());

        // Vet
        char onCall = ((String) Objects.requireNonNull(comboBoxList.get(1).getSelectedItem())).charAt(0);
        selectedColumns.add(checkBoxes.get(5).isSelected());

        String experienceString = textFieldList.get(2).getText().trim();
        int experience = -1;
        if (!experienceString.equals("")) {
            experience = Integer.parseInt(experienceString);
        }
        selectedColumns.add(checkBoxes.get(6).isSelected());
        String experienceCondition = (String) conditionBoxes.get(0).getSelectedItem();
        if (!Objects.equals(experienceCondition, " ")) {
            conditions.add(experienceCondition);
        }

        String specialization = textFieldList.get(3).getText().trim();
        selectedColumns.add(checkBoxes.get(7).isSelected());

        String phoneNumber = textFieldList.get(4).getText().trim();
        selectedColumns.add(checkBoxes.get(8).isSelected());

        // Zookeeper
        char eventDuty = ((String) Objects.requireNonNull(comboBoxList.get(2).getSelectedItem())).charAt(0);
        selectedColumns.add(checkBoxes.get(9).isSelected());

        // Manager
        char inOffice = ((String) Objects.requireNonNull(comboBoxList.get(3).getSelectedItem())).charAt(0);
        selectedColumns.add(checkBoxes.get(10).isSelected());

        String officeNumberString = textFieldList.get(5).getText().trim();
        int officeNumber = -1;
        if (!officeNumberString.equals("")) {
            officeNumber = Integer.parseInt(officeNumberString);
        }
        selectedColumns.add(checkBoxes.get(11).isSelected());
        String officeNumberCondition = (String) conditionBoxes.get(0).getSelectedItem();
        if (!Objects.equals(officeNumberCondition, " ")) {
            conditions.add(officeNumberCondition);
        }

        if (vetCheckbox.isSelected()) {
            VetEmployeeModel vetEmployeeModel = new VetEmployeeModel(employeeID, name, startDate, endDate, onDuty, onCall, experience, specialization, phoneNumber);
            displaySelectQuery(dbhandler.searchVetEmployees(vetEmployeeModel, selectedColumns, conditions));

        }
        else if (zookeeperCheckbox.isSelected()) {
            ZookeeperEmployeeModel zookeeperEmployeeModel = new ZookeeperEmployeeModel(employeeID, name, startDate, endDate, onDuty, eventDuty);
            displaySelectQuery(dbhandler.searchZookeeperEmployees(zookeeperEmployeeModel, selectedColumns, conditions));
        }
        else if (managerCheckbox.isSelected()) {
            ManagerEmployeeModel managerEmployeeModel = new ManagerEmployeeModel(employeeID, name, startDate, endDate, onDuty, inOffice, officeNumber);
            displaySelectQuery(dbhandler.searchManagerEmployees(managerEmployeeModel, selectedColumns, conditions));
        }
        else {
            ZooEmployeeModel zooEmployeeModel = new ZooEmployeeModel(employeeID, name, startDate, endDate, onDuty);
            displaySelectQuery(dbhandler.searchEmployees(zooEmployeeModel, selectedColumns, conditions));
        }
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
            infoText.insertString(0, "Search for employees that meet the conditions you specify. You can select the columns to return" +
                    " with the checkbox beside each name (note that Employee_ID must be selected). Some fields allow you to select a comparison using a dropdown.\n\n" +
                    "To search for only vets, zookeepers, or managers, select the checkbox beside the label. If no checkbox is selected, only fields common to all employees can be searched.", center);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        infoPanel.setMaximumSize(new Dimension(1000, 200));
        return infoPanel;
    }

    public JPanel createTextInputPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JCheckBox checkBox = new JCheckBox();
        if (labelText.equals("Employee ID")) {
            checkBox.setSelected(true);
            checkBox.setEnabled(false);
        }

        JLabel panelText = new JLabel(labelText);
        panelText.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelText.setBorder(new EmptyBorder(0, 0, 0, 10));

        JTextField panelField = new JTextField();
        panelField.setMaximumSize(new Dimension(100, 30));
        panelField.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(checkBox);
        panel.add(panelText);
        panel.add(panelField);

        JComboBox<String> comboBox;
        if (arithmeticFields.contains(labelText)) {
            comboBox = new JComboBox<>(new String[]{" ", ">", ">=", "<", "<=", "==", "<>"});
            comboBox.setMaximumSize(new Dimension(70, 30));
            panel.add(comboBox);
            conditionBoxes.add(comboBox);
        }

        textFieldList.add(panelField);
        checkBoxes.add(checkBox);

        return panel;
    }

    public JPanel createDropdownInputPanel(String labelText, String[] choices) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JCheckBox checkBox = new JCheckBox();

        JLabel panelText = new JLabel(labelText);
        panelText.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelText.setBorder(new EmptyBorder(0, 0, 0, 10));

        JComboBox<String> panelCombo = new JComboBox<>(choices);
        panelCombo.setMaximumSize(new Dimension(100, 30));
        panelCombo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(checkBox);
        panel.add(panelText);
        panel.add(panelCombo);

        comboBoxList.add(panelCombo);
        checkBoxes.add(checkBox);
        return panel;
    }

    public JPanel createDatepickerInputPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JCheckBox checkBox = new JCheckBox();

        JLabel panelText = new JLabel(labelText);
        panelText.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelText.setBorder(new EmptyBorder(0, 0, 0, 10));

        UtilCalendarModel model = new UtilCalendarModel();
        JDatePicker datePicker = new JDatePicker(model);
        datePicker.setMaximumSize(new Dimension(150, 30));

        datePicker.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(checkBox);
        panel.add(panelText);
        panel.add(datePicker);

        JComboBox<String> comboBox = new JComboBox<>(new String[]{" ", ">", ">=", "<", "<=", "==", "<>"});
        comboBox.setMaximumSize(new Dimension(70, 30));
        panel.add(comboBox);

        conditionBoxes.add(comboBox);

        datePickers.add(datePicker);
        checkBoxes.add(checkBox);

        return panel;
    }

    public JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(0, 0, 0, 5));
        label.setFont(new Font("Sans Serif", Font.BOLD, 16));
        return label;
    }

    public void displaySelectQuery(SelectModel model) {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Vector<String> columnNames = new Vector<>(model.getSelectedColumns());
        tableModel.setColumnIdentifiers(columnNames);

        ArrayList<ArrayList<String>> rowdata = model.getRowData();
        for (ArrayList<String> row : rowdata) {
            Vector<String> singleRow = new Vector<>(row);
            tableModel.addRow(singleRow);
        }
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }
}
