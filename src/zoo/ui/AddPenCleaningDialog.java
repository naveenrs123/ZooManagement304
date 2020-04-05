package zoo.ui;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilCalendarModel;
import zoo.database.DatabaseConnectionHandler;
import zoo.model.AnimalModel;
import zoo.model.PenCleaningModel;
import zoo.model.PenInfoModel;

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

public class AddPenCleaningDialog extends JFrame {

    DatabaseConnectionHandler dbhandler;
    JTable table;

    /**
     * 0: ZooKeeper ID, 1: PenNum, AreaID
     */
    ArrayList<JComboBox> comboBoxList = new ArrayList<>();

    /**
     * 0: Date
     */
    ArrayList<JDatePicker> datePickers = new ArrayList<>();

    public AddPenCleaningDialog(DatabaseConnectionHandler dbhandler, JTable table) {
        super("Clean a Pen");
        this.dbhandler = dbhandler;
        this.table = table;
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

        JPanel allPenCleaningInputs = new JPanel();
        allPenCleaningInputs.setLayout(new BoxLayout(allPenCleaningInputs, BoxLayout.PAGE_AXIS));
        allPenCleaningInputs.setMaximumSize(new Dimension(300, 300));
        allPenCleaningInputs.setAlignmentY(Component.TOP_ALIGNMENT);
        allPenCleaningInputs.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel zookeeperID = createDropdownInputPanel("Zookeeper ID", dbhandler.getZookeeperIDs());
        JPanel penNumbers = createDropdownInputPanel("Area, Pen", dbhandler.getPenNumbers());
        JPanel date = createDatepickerInputPanel("Date of cleaning");


        allPenCleaningInputs.add(Box.createRigidArea(new Dimension(0, 20)));
        allPenCleaningInputs.add(zookeeperID);
        allPenCleaningInputs.add(penNumbers);
        allPenCleaningInputs.add(date);


        inputsPane.add(Box.createRigidArea(new Dimension(50, 0)));
        inputsPane.add(allPenCleaningInputs);

        JPanel penCleaningButtons = new JPanel();
        penCleaningButtons.setLayout(new BoxLayout(penCleaningButtons, BoxLayout.LINE_AXIS));
        penCleaningButtons.setPreferredSize(new Dimension(800, 50));
        penCleaningButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton submit = new JButton("Submit");
        JButton clear = new JButton("Clear Fields");

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertPenCleaning();
            }
        });

        penCleaningButtons.add(submit);
        penCleaningButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        penCleaningButtons.add(clear);

        contentPane.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPane.add(infoPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPane.add(inputsPane);
        contentPane.add(penCleaningButtons);

        this.setVisible(true);
    }

    private void insertPenCleaning() {
        String employee_id = comboBoxList.get(0).getSelectedItem().toString();
        int PenNumber = Integer.parseInt(comboBoxList.get(1).getSelectedItem().toString().split(",")[1]);
        char AreaID = comboBoxList.get(1).getSelectedItem().toString().split(",")[0].charAt(0);
        Date date = getDate(datePickers.get(0));

        if (date == null) {
            JOptionPane.showMessageDialog(null, "Please specify a date");
        } else if (employee_id.equals("")) {
            JOptionPane.showMessageDialog(null, "Please specify an employee");
        } else {
            PenCleaningModel penCleaning = new PenCleaningModel(employee_id, PenNumber, AreaID, date);
            dbhandler.InsertPenCleaning(penCleaning);
            sharedInfo();
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
            infoText.insertString(0, "To add a new pen cleaning event to the system, type in and select all the appropriate details for that " +
                    " event.\n\nWhen you are done, click the 'Submit' button. If you need to clear all fields, hit the" +
                    " 'Clear Fields' button to reset all inputs.", center);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        infoPanel.setMaximumSize(new Dimension(650, 200));
        return infoPanel;
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

    public void sharedInfo() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Vector<String> columnNames = new Vector();
        columnNames.add("Pen Number");
        columnNames.add("Area ID");
        columnNames.add("Size");
        columnNames.add("Occupancy");
        columnNames.add("Last Cleaning Date");
        tableModel.setColumnIdentifiers(columnNames);
        PenInfoModel[] pens = dbhandler.getAllAreaInfo();

        Vector<String> areaData;
        for (PenInfoModel pen: pens) {
            areaData = new Vector<>();
            areaData.add(Integer.toString(pen.getPenNumber()));
            areaData.add(Character.toString(pen.getAreaID()));
            areaData.add(Integer.toString(pen.getPenSize()));
            areaData.add(Integer.toString(pen.getOccupancy()));
            areaData.add(pen.getDateOfLastCleaning().toString());
            tableModel.addRow(areaData);
        }
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }
}
