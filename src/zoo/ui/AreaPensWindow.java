package zoo.ui;

import zoo.database.DatabaseConnectionHandler;
import zoo.model.AnimalModel;
import zoo.model.AnimalTypes;
import zoo.model.PenInfoModel;
import zoo.model.ZookeeperEmployeeModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class AreaPensWindow extends JFrame {
    DatabaseConnectionHandler dbhandler;
    JTable table;
    JScrollPane penAreaScroll;
    AddPenCleaningDialog addPenCleaningDialog;
    RelocateAnimalDialog relocateAnimalDialog;
    ExtractDataDialog extractDataDialog;

    public AreaPensWindow(DatabaseConnectionHandler dbhandler) {
        super("Area and Pens Management");
        this.dbhandler = dbhandler;
        this.table = new JTable();
        this.addPenCleaningDialog = new AddPenCleaningDialog(dbhandler, table);
        this.relocateAnimalDialog = new RelocateAnimalDialog(dbhandler, table);
        this.extractDataDialog = new ExtractDataDialog(dbhandler);
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        JPanel titlePane = createTitlePane();
        sharedInfo();

        penAreaScroll = new JScrollPane(table);
        penAreaScroll.setPreferredSize(new Dimension(700, 300));
        penAreaScroll.setMaximumSize(new Dimension(700, 300));
        penAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        penAreaScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel selectOptions = new JPanel();
        selectOptions.setLayout(new BoxLayout(selectOptions, BoxLayout.LINE_AXIS));
        selectOptions.setPreferredSize(new Dimension(700, 50));
        selectOptions.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton areaA = new JButton("A: Jungle Adventure");
        JButton areaB = new JButton("B: Under the Sea");
        JButton areaC = new JButton("C: Arctic Expedition");
        JButton areaD = new JButton("D: Down on the Farm");
        JButton areaE = new JButton("E: African Safari");

        areaA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                areaInfo("A");
            }
        });
        areaB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaInfo("B");
            }
        });
        areaC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaInfo("C");
            }
        });
        areaD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaInfo("D");
            }
        });
        areaE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaInfo("E");
            }
        });

        selectOptions.add(areaA);
        selectOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        selectOptions.add(areaB);
        selectOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        selectOptions.add(areaC);
        selectOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        selectOptions.add(areaD);
        selectOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        selectOptions.add(areaE);

        JPanel animalButtons = new JPanel();
        animalButtons.setLayout(new BoxLayout(animalButtons, BoxLayout.LINE_AXIS));
        animalButtons.setPreferredSize(new Dimension(700, 50));
        animalButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addPenCleaning = new JButton("Clean a Pen");
        JButton moveAnimal = new JButton("Relocate an Animal");
        JButton extractData = new JButton("Get Log Data");
        JButton resetView = new JButton("Reset View");

        addPenCleaning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPenCleaningDialog.showFrame();
            }
        });
        moveAnimal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {relocateAnimalDialog.showFrame();}
        });
        extractData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                extractDataDialog.showFrame();
            }
        });
        resetView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sharedInfo();
            }
        });

        animalButtons.add(addPenCleaning);
        animalButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        animalButtons.add(moveAnimal);
        animalButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        animalButtons.add(extractData);
        animalButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        animalButtons.add(resetView);

        contentPane.add(titlePane);
        contentPane.add(penAreaScroll);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(selectOptions);
        contentPane.add(animalButtons);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    String pen = table.getValueAt(row, 0).toString();
                    String area = table.getValueAt(row, 1).toString();
                    PenAnimalPopUp animalPopup = new PenAnimalPopUp(dbhandler);
                    animalPopup.showFrame(Integer.parseInt(pen), area.charAt(0));
                }
            }
        });

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

    public void areaInfo(String area) {
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
        PenInfoModel[] pens = dbhandler.getAreaInfo(area);

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


    public JPanel createTitlePane() {
        JPanel panel =  new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = new JLabel();
        title.setPreferredSize(new Dimension(550, 100));
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        title.setText("Area and Pen Management");
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(title);

        return panel;
    }
}
