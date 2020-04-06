package zoo.ui;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilCalendarModel;
import zoo.database.DatabaseConnectionHandler;
import zoo.model.AnimalModel;
import zoo.model.HealthCheckupModel;

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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

public class AddHealthCheckupDialog extends JFrame {
    DatabaseConnectionHandler dbhandler;
    JTable table;

    /**
     * 0: Weight
     */
    ArrayList<JTextField> textFieldList = new ArrayList<>();

    /**
     * 0: Vet ID, 1: Animal ID, 2: Health status
     */
    ArrayList<JComboBox> comboBoxList = new ArrayList<>();

    /**
     * 0: Date of checkup
     */
    ArrayList<JDatePicker> datePickers = new ArrayList<>();

    public AddHealthCheckupDialog(DatabaseConnectionHandler dbhandler, JTable table) {
        super("Add Health Checkup");
        this.dbhandler = dbhandler;
        this.table = table;
    }

    private void clear() {
        comboBoxList.clear();
        datePickers.clear();
        textFieldList.clear();
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        this.setSize(700, 400);
        this.setResizable(false);

        JTextPane infoPanel = createInfoPanel();

        JPanel inputsPane = new JPanel();
        inputsPane.setLayout(new BoxLayout(inputsPane, BoxLayout.LINE_AXIS));
        inputsPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel allCheckupInput = new JPanel();
        allCheckupInput.setLayout(new BoxLayout(allCheckupInput, BoxLayout.PAGE_AXIS));
        allCheckupInput.setMaximumSize(new Dimension(300, 300));
        allCheckupInput.setAlignmentY(Component.TOP_ALIGNMENT);
        allCheckupInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] statuses = {"Great", "Good", "Poor", "Moderate"};

        JPanel vetIDs = createDropdownInputPanel("Vet", dbhandler.getVetIDs());
        JPanel animalIDs = createDropdownInputPanel("Animal ID", dbhandler.getAnimalIDs());
        JPanel weight = createTextInputPanel("Weight");
        JPanel status = createDropdownInputPanel("Health Status", statuses);
        JPanel date = createDatepickerInputPanel("Date of Checkup");

        allCheckupInput.add(Box.createRigidArea(new Dimension(0, 20)));
        allCheckupInput.add(vetIDs);
        allCheckupInput.add(animalIDs);
        allCheckupInput.add(weight);
        allCheckupInput.add(status);
        allCheckupInput.add(date);

        JPanel animals2 = new JPanel();
        animals2.setLayout(new BoxLayout(animals2, BoxLayout.PAGE_AXIS));
        animals2.setMaximumSize(new Dimension(300, 300));
        animals2.setAlignmentY(Component.TOP_ALIGNMENT);

        inputsPane.add(Box.createRigidArea(new Dimension(50, 0)));
        inputsPane.add(allCheckupInput);

        JPanel animalButtons = new JPanel();
        animalButtons.setLayout(new BoxLayout(animalButtons, BoxLayout.LINE_AXIS));
        animalButtons.setPreferredSize(new Dimension(800, 50));
        animalButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton submit = new JButton("Submit");
        JButton clear = new JButton("Clear Fields");

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertHealthCheckup();
                clear();
                dispose();
            }
        });

        animalButtons.add(submit);
        animalButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        animalButtons.add(clear);

        contentPane.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPane.add(infoPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPane.add(inputsPane);
        contentPane.add(animalButtons);

        this.setVisible(true);
    }

    private void insertHealthCheckup() {
        if (comboBoxList.isEmpty() || datePickers.isEmpty() || textFieldList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "invalid input");
            return;
        } else if (comboBoxList.get(0).getSelectedItem() == null || comboBoxList.get(1).getSelectedItem() == null || comboBoxList.get(2).getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "invalid input");
            return;
        }
        String checkup_id ="CH" + dbhandler.getNextCheckupNumber();
        String vet_id = comboBoxList.get(0).getSelectedItem().toString();
        String animal_ID = comboBoxList.get(1).getSelectedItem().toString();
        if (vet_id.equals("") || animal_ID.equals("")) {
            JOptionPane.showMessageDialog(null, "invalid input");
            return;
        }
        int weight;
        try {
            weight = Integer.parseInt(textFieldList.get(0).getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Please enter a number for the weight");
            return;
        }
        String healthStatus = comboBoxList.get(2).getSelectedItem().toString();
        Date dateOfCheckup = getDate(datePickers.get(0));
        HealthCheckupModel checkup = new HealthCheckupModel(checkup_id, vet_id, animal_ID, weight, healthStatus, dateOfCheckup);
        dbhandler.insertHealthCheckup(checkup);
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
            infoText.insertString(0, "To log a health checkup, type in and select all the appropriate details.\n\nWhen you are done, click the 'Submit' button. If you need to clear all fields, hit the" +
                    " 'Clear Fields' button to reset all inputs.", center);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        infoPanel.setMaximumSize(new Dimension(650, 200));
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
        panelField.setMaximumSize(new Dimension(150, 30));
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
        JComboBox combobox = new JComboBox();
        combobox.insertItemAt("", 0);

        for (String str: choices) {
            combobox.addItem(str);
        }

        combobox.setMaximumSize(new Dimension(150, 30));
        combobox.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(panelText);
        panel.add(combobox);

        comboBoxList.add(combobox);
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

    public void sharedInfo() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Checkup ID");
        columnNames.add("Employee ID");
        columnNames.add("Animal ID");
        columnNames.add("Weight");
        columnNames.add("Health Status");
        columnNames.add("Checkup Date");
        tableModel.setColumnIdentifiers(columnNames);

        HealthCheckupModel[] checkups = dbhandler.getHealthCheckups();

        Vector<String> checkupData;
        for (HealthCheckupModel checkup: checkups) {
            checkupData = new Vector<>();
            checkupData.add(checkup.getCheckupID());
            checkupData.add(checkup.getEmployeeID());
            checkupData.add(checkup.getAnimalID());
            checkupData.add(Integer.toString(checkup.getWeight()));
            checkupData.add(checkup.getHealthStatus());
            checkupData.add(checkup.getCheckupDate().toString());
            tableModel.addRow(checkupData);
        }
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }

}
