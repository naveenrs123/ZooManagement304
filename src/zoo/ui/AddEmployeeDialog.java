package zoo.ui;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilCalendarModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.Properties;

public class AddEmployeeDialog extends JFrame {
    ArrayList<JTextField> textFieldList = new ArrayList<>();
    ArrayList<JComboBox> comboBoxList = new ArrayList<>();
    ArrayList<JDatePicker> datePickers = new ArrayList<>();

    public AddEmployeeDialog() {
        super("Add Employee");
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        this.setSize(900, 500);
        this.setResizable(false);

        JPanel employeeButtons = new JPanel();
        employeeButtons.setLayout(new BoxLayout(employeeButtons, BoxLayout.LINE_AXIS));
        employeeButtons.setPreferredSize(new Dimension(800, 50));
        employeeButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton submit = new JButton("Submit");
        JButton clear = new JButton("Clear Fields");
        employeeButtons.add(submit);
        employeeButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        employeeButtons.add(clear);

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
        panelCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panelCombo;
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
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(0, 0, 0, 5));
        label.setFont(new Font("Sans Serif", Font.BOLD, 16));
        return label;
    }
}
