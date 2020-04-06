package zoo.ui;

import zoo.database.DatabaseConnectionHandler;
import zoo.model.HealthCheckupModel;
import zoo.model.ZooEmployeeModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Vector;

public class HealthCheckupWindow extends JFrame {

    AddHealthCheckupDialog addHealthCheckupDialog;
    DatabaseConnectionHandler dbhandler;
    JScrollPane healthScroll;
    JTable table;

    public HealthCheckupWindow(DatabaseConnectionHandler dbhandler) {
        super("Health Checkups");
        this.dbhandler = dbhandler;
        this.table = new JTable();
        this.addHealthCheckupDialog = new AddHealthCheckupDialog(dbhandler, table);
    }


    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        JPanel titlePane = createTitlePane();
        sharedInfo();

        healthScroll = new JScrollPane(table);
        healthScroll.setPreferredSize(new Dimension(700, 300));
        healthScroll.setMaximumSize(new Dimension(700, 300));

        JPanel selectOptions = new JPanel();
        selectOptions.setLayout(new BoxLayout(selectOptions, BoxLayout.LINE_AXIS));
        selectOptions.setPreferredSize(new Dimension(700, 50));
        selectOptions.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel healthCheckupButtons = new JPanel();
        healthCheckupButtons.setLayout(new BoxLayout(healthCheckupButtons, BoxLayout.LINE_AXIS));
        healthCheckupButtons.setPreferredSize(new Dimension(700, 50));
        healthCheckupButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addCheckup = new JButton("Log Health Checkup");
        JButton resetView = new JButton("Reset View");

        addCheckup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addHealthCheckupDialog.showFrame();
            }
        });
        resetView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sharedInfo();
            }
        });

        healthCheckupButtons.add(addCheckup);
        healthCheckupButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        healthCheckupButtons.add(resetView);

        contentPane.add(titlePane);
        contentPane.add(healthScroll);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(selectOptions);
        contentPane.add(healthCheckupButtons);

        this.setSize(800, 500);
        this.setResizable(false);
        this.setVisible(true);
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

    public JPanel createTitlePane() {
        JPanel panel =  new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = new JLabel();
        title.setPreferredSize(new Dimension(550, 100));
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        title.setText("Health Checkup Records Management");
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(title);

        return panel;
    }
}
