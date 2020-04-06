package zoo.ui;

import zoo.database.DatabaseConnectionHandler;
import zoo.model.AnimalRelocationModel;
import zoo.model.PenCleaningModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.Vector;

public class DataPopUp extends JFrame{

    private DatabaseConnectionHandler dbhandler;
    private JTable table;
    private JScrollPane dataScroll;

    public DataPopUp(DatabaseConnectionHandler dbhandler) {
        this.dbhandler = dbhandler;
        table = new JTable();
    }

    public void showFrame(String type, Date from, Date to) {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        JPanel titlePane = createTitlePane(type, from ,to);

        info(type, from, to);

        dataScroll = new JScrollPane(table);
        dataScroll.setPreferredSize(new Dimension(700, 300));
        dataScroll.setMaximumSize(new Dimension(700, 300));
        dataScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        dataScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel selectOptions = new JPanel();
        selectOptions.setLayout(new BoxLayout(selectOptions, BoxLayout.LINE_AXIS));
        selectOptions.setPreferredSize(new Dimension(700, 50));
        selectOptions.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPane.add(titlePane);
        contentPane.add(dataScroll);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));

        this.setSize(800, 500);
        this.setResizable(false);
        this.setVisible(true);
    }



    public JPanel createTitlePane(String type, Date from, Date to) {
        JPanel panel =  new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = new JLabel();
        title.setPreferredSize(new Dimension(550, 100));
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        title.setText(type + "s from " + from.toString() + " to " + to.toString());
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(title);

        return panel;
    }

    public void info(String type, Date from, Date to) {
        if (type.equals("Pen Cleaning")) {
            DefaultTableModel tableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            Vector<String> columnNames = new Vector<>();
            columnNames.add("Employee ID");
            columnNames.add("Pen Number");
            columnNames.add("Area ID");
            columnNames.add("Date of Cleaning");
            tableModel.setColumnIdentifiers(columnNames);

            PenCleaningModel[] cleanings = dbhandler.getPenCleaningsFromTo(from, to);
            Vector<String> cleaningData;
            for (PenCleaningModel cleaning: cleanings) {
                cleaningData = new Vector<>();
                cleaningData.add(cleaning.getEmployee_ID());
                cleaningData.add(Integer.toString(cleaning.getPen_Number()));
                cleaningData.add(Character.toString(cleaning.getArea_ID()));
                cleaningData.add(cleaning.getDate_of_cleaning().toString());
                tableModel.addRow(cleaningData);
            }
            table.setModel(tableModel);
            tableModel.fireTableDataChanged();
        } else if (type.equals("Animal Relocation")) {
            DefaultTableModel tableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            Vector<String> columnNames = new Vector<>();
            columnNames.add("Relocation ID");
            columnNames.add("Employee ID");
            columnNames.add("Animal ID");
            columnNames.add("from_Pen_ID");
            columnNames.add("from_Area_ID");
            columnNames.add("to_Pen_ID");
            columnNames.add("to_Area_ID");
            columnNames.add("Date of Relocation");
            tableModel.setColumnIdentifiers(columnNames);

            AnimalRelocationModel[] relocations = dbhandler.getAnimalRelocationsFromTo(from, to);
            Vector<String> relocationData;
            for (AnimalRelocationModel relocation: relocations) {
                relocationData = new Vector<>();
                relocationData.add(relocation.getRelocation_ID());
                relocationData.add(relocation.getEmployee_ID());
                relocationData.add(relocation.getAnimal_ID());
                relocationData.add(relocation.getFrom_Pen_ID());
                relocationData.add(relocation.getTo_Pen_ID());
                relocationData.add(relocation.getTo_Area_ID());
                relocationData.add(relocation.getRelocationDate().toString());
                tableModel.addRow(relocationData);
            }
            table.setModel(tableModel);
            tableModel.fireTableDataChanged();
        }

    }

}
