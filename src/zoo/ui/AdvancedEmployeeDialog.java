package zoo.ui;

import zoo.database.DatabaseConnectionHandler;
import zoo.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class AdvancedEmployeeDialog extends JFrame {
    DatabaseConnectionHandler dbhandler;
    JTable table;

    public AdvancedEmployeeDialog(DatabaseConnectionHandler dbhandler, JTable table) {
        super("Add Employee");
        this.dbhandler = dbhandler;
        this.table = table;
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        this.setSize(450, 300);
        this.setResizable(false);

        JTextPane infoPanel = createInfoPanel();

        JPanel inputsPane = new JPanel();
        inputsPane.setLayout(new BoxLayout(inputsPane, BoxLayout.PAGE_AXIS));
        inputsPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton query1 = new JButton("Select the On-Call Vet(s) with the Most Experience");
        query1.setMaximumSize(new Dimension(350, 50));
        query1.setAlignmentX(Component.CENTER_ALIGNMENT);

        query1.addActionListener(e -> {
            displaySelectQuery(dbhandler.getExperiencedOnCallVet());
            dispose();
        });

        JPanel query2Panel = new JPanel();
        query2Panel.setMaximumSize(new Dimension(350, 80));
        query2Panel.setLayout(new BoxLayout(query2Panel, BoxLayout.LINE_AXIS));
        query2Panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton query2 = new JButton("<html> Find the Zookeepers who have cleaned all<br> pens in the specified area.</html>");
        query2.setMaximumSize(new Dimension(280, 80));

        JPanel query2LabelPanel = new JPanel();
        query2LabelPanel.setLayout(new BoxLayout(query2LabelPanel, BoxLayout.PAGE_AXIS));
        JLabel areaLabel = new JLabel("Area ID");
        areaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComboBox areaID = new JComboBox<>(dbhandler.getAreaIDs());
        query2.addActionListener(e -> {
            displaySelectQuery(dbhandler.getZookeepersWhoCleanedAllPens((char) areaID.getSelectedItem()));
            dispose();
        });

        query2LabelPanel.add(areaLabel);
        query2LabelPanel.add(Box.createRigidArea(new Dimension(0, -10)));
        query2LabelPanel.add(areaID);

        query2Panel.add(query2);
        query2Panel.add(query2LabelPanel);

        inputsPane.add(query1);
        inputsPane.add(Box.createRigidArea(new Dimension(0, 20)));
        inputsPane.add(query2Panel);

        contentPane.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPane.add(infoPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(inputsPane);

        this.setVisible(true);
    }

    private JTextPane createInfoPanel() {
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
            infoText.insertString(0, "Select from these advanced search options and see the results displayed in the table.", center);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        infoPanel.setMaximumSize(new Dimension(350, 50));
        return infoPanel;
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
