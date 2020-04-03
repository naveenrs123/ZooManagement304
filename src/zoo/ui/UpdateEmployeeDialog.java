package zoo.ui;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilCalendarModel;
import zoo.database.DatabaseConnectionHandler;
import zoo.model.ManagerEmployeeModel;
import zoo.model.VetEmployeeModel;
import zoo.model.ZooEmployeeModel;
import zoo.model.ZookeeperEmployeeModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

public class UpdateEmployeeDialog extends JFrame {
    DatabaseConnectionHandler dbhandler;
    JTable table;
    /**
     * 0: Name, 1: Experience, 2: Specialization, 3: Phone Number
     * 4: Office Number.
     */
    ArrayList<JTextField> textFieldList = new ArrayList<>();

    /**
     * 0: Employee_ID, 1: On Duty, 2: On Call, 3: Event Duty, 4: In Office
     */
    ArrayList<JComboBox> comboBoxList = new ArrayList<>();

    /**
     * 0: Start Date, 1: End Date
     */
    ArrayList<JDatePicker> datePickers = new ArrayList<>();

    public UpdateEmployeeDialog(DatabaseConnectionHandler dbhandler, JTable table) {
        super("Update Employee Information");
        this.dbhandler = dbhandler;
        this.table = table;
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        this.setSize(900, 450);
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
        JPanel employeeIDPanel = createDropdownInputPanel("Employee ID", dbhandler.getEmployeeIDs());
        JPanel namePanel = createTextInputPanel("Name");
        JPanel startDatePanel = createDatepickerInputPanel("Start Date");
        JPanel endDatePanel = createDatepickerInputPanel("End Date");
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
        vetInput.setMaximumSize(new Dimension(300, 300));
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
        keeperManagerInput.setMaximumSize(new Dimension(300, 300));
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

        inputsPane.add(Box.createRigidArea(new Dimension(50, 0)));
        inputsPane.add(allEmployeesInput);
        inputsPane.add(vetInput);
        inputsPane.add(keeperManagerInput);

        comboBoxList.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object item = comboBoxList.get(0).getSelectedItem();
                resetFields();
                comboBoxList.get(0).setSelectedItem(item);
                setFields();
            }
        });

        JPanel employeeButtons = new JPanel();
        employeeButtons.setLayout(new BoxLayout(employeeButtons, BoxLayout.LINE_AXIS));
        employeeButtons.setPreferredSize(new Dimension(800, 50));
        employeeButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton submit = new JButton("Submit");
        JButton clear = new JButton("Clear Fields");
        employeeButtons.add(submit);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(clear);

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

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
        for (JComboBox comboBox : comboBoxList) {
            comboBox.setSelectedIndex(0);
        }
        for (JDatePicker datePicker : datePickers) {
            datePicker.getFormattedTextField().setText("");
        }
    }

    private void setFields() {
        String id = (String) comboBoxList.get(0).getSelectedItem();
        ZooEmployeeModel employee = dbhandler.getOneEmployee(id);

        textFieldList.get(0).setText(employee.getName());

        setDate(employee.getStartDate(), datePickers.get(0));
        setDate(employee.getEndDate(), datePickers.get(1));

        if (employee.getOnDuty() == 'T') {
            comboBoxList.get(1).setSelectedIndex(1);
        }
        else {
            comboBoxList.get(1).setSelectedIndex(2);
        }

        VetEmployeeModel vet = dbhandler.getOneVet(id);
        ZookeeperEmployeeModel zookeeper = dbhandler.getOneZookeeper(id);
        ManagerEmployeeModel manager = dbhandler.getOneManager(id);

        if (vet != null) {
            if (vet.getOnCall() == 'T') {
                comboBoxList.get(2).setSelectedIndex(1);
            }
            else {
                comboBoxList.get(2).setSelectedIndex(2);
            }
            textFieldList.get(1).setText(Integer.toString(vet.getExperience()));
            textFieldList.get(2).setText(vet.getSpecialization());
            textFieldList.get(3).setText(vet.getPhoneNumber());
        } else if (zookeeper != null) {
            if (zookeeper.getEventDuty() == 'T') {
                comboBoxList.get(3).setSelectedIndex(1);
            }
            else {
                comboBoxList.get(3).setSelectedIndex(2);
            }
        }
        else if (manager != null) {
            if (manager.getInOffice() == 'T') {
                comboBoxList.get(4).setSelectedIndex(1);
            }
            else {
                comboBoxList.get(4).setSelectedIndex(2);
            }
            textFieldList.get(4).setText(Integer.toString(manager.getOfficeNumber()));
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
            infoText.insertString(0, "To update employee information, type in the ID of the employee along with the fields" +
                    " you want to change.\n\nWhen you are done, click the 'Submit Button'. To reset the fields, click the" +
                    " 'Clear Fields' button.", center);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        infoPanel.setMaximumSize(new Dimension(850, 120));
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

        //datePicker.setMaximumSize(new Dimension(100, 16));
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

    private void setDate(Date date, JDatePicker datePicker) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            DateModel<Calendar> dateModel = (DateModel<Calendar>) datePicker.getModel();
            dateModel.setValue(calendar);
        }
    }
}
