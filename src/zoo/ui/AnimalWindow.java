package zoo.ui;

import zoo.database.DatabaseConnectionHandler;
import zoo.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class AnimalWindow extends JFrame {
    AddAnimalDialog addAnimalDialog;
    UpdateAnimalDialog updateAnimalDialog;
    DatabaseConnectionHandler dbhandler;
    JTable table;
    JScrollPane animalScroll;

    public AnimalWindow(DatabaseConnectionHandler dbhandler) {
        super("Animal Management");
        this.dbhandler = dbhandler;
        this.table = new JTable();
        addAnimalDialog = new AddAnimalDialog(dbhandler, table);
        updateAnimalDialog = new UpdateAnimalDialog(dbhandler, table);
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        JPanel titlePane = createTitlePane();
        sharedInfo();

        animalScroll = new JScrollPane(table);
        animalScroll.setPreferredSize(new Dimension(700, 300));
        animalScroll.setMaximumSize(new Dimension(700, 300));
        animalScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        animalScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel selectOptions = new JPanel();
        selectOptions.setLayout(new BoxLayout(selectOptions, BoxLayout.LINE_AXIS));
        selectOptions.setPreferredSize(new Dimension(700, 50));
        selectOptions.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton allAnimals = new JButton("Animals");
        JButton mammals = new JButton("Mammals");
        JButton avian = new JButton("Avian");
        JButton reptile = new JButton("Reptiles");
        JButton invertebrate = new JButton("Invertebrates");
        JButton aquatic = new JButton("Aquatic");

        allAnimals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sharedInfo();
            }
        });
        mammals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animalTypeInfo(AnimalTypes.Mammal);
            }
        });
        avian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animalTypeInfo(AnimalTypes.Avian);
            }
        });
        reptile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animalTypeInfo(AnimalTypes.Reptile);
            }
        });
        invertebrate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animalTypeInfo(AnimalTypes.Invertebrate);
            }
        });
        aquatic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animalTypeInfo(AnimalTypes.Aquatic);
            }
        });

        selectOptions.add(allAnimals);
        selectOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        selectOptions.add(mammals);
        selectOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        selectOptions.add(avian);
        selectOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        selectOptions.add(reptile);
        selectOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        selectOptions.add(invertebrate);
        selectOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        selectOptions.add(aquatic);

        JPanel animalButtons = new JPanel();
        animalButtons.setLayout(new BoxLayout(animalButtons, BoxLayout.LINE_AXIS));
        animalButtons.setPreferredSize(new Dimension(700, 50));
        animalButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addAnimal = new JButton("Add Animal");
        JButton updateAnimal = new JButton("Update Animal Info");
        JButton searchAnimals = new JButton("Search");
        JButton resetView = new JButton("Reset View");

        addAnimal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAnimalDialog.showFrame();
            }
        });
        updateAnimal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAnimalDialog.showFrame();
            }
        });

        animalButtons.add(addAnimal);
        animalButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        animalButtons.add(updateAnimal);
        animalButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        animalButtons.add(searchAnimals);
        animalButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        animalButtons.add(resetView);

        contentPane.add(titlePane);
        contentPane.add(animalScroll);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(selectOptions);
        contentPane.add(animalButtons);

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
        columnNames.add("Animal ID");
        columnNames.add("Type");
        columnNames.add("Sex");
        columnNames.add("Species");
        columnNames.add("Age");
        columnNames.add("Name");
        columnNames.add("Pen Number");
        columnNames.add("Area ID");
        tableModel.setColumnIdentifiers(columnNames);

        AnimalModel[] animals = dbhandler.getAnimalInfo();
        Vector<String> animalData;
        for (AnimalModel animal: animals) {
            animalData = new Vector<>();
            animalData.add(animal.getAnimalID());
            animalData.add(animal.getType());
            animalData.add(Character.toString(animal.getSex()));
            animalData.add(animal.getSpecies());
            animalData.add(Integer.toString(animal.getAge()));
            animalData.add(animal.getName());
            animalData.add(Integer.toString(animal.getPenNumber()));
            animalData.add(Character.toString(animal.getAreaID()));
            tableModel.addRow(animalData);
        }
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }

    public void animalTypeInfo(AnimalTypes type) {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Vector<String> columnNames = new Vector();
        columnNames.add("Animal ID");
        columnNames.add("Sex");
        columnNames.add("Species");
        columnNames.add("Age");
        columnNames.add("Name");
        columnNames.add("Pen Number");
        columnNames.add("Area ID");
        tableModel.setColumnIdentifiers(columnNames);
        AnimalModel[] animals = dbhandler.getAnimalTypeInfo(type);

        Vector<String> animalData;
        for (AnimalModel animal: animals) {
            animalData = new Vector<>();
            animalData.add(animal.getAnimalID());
            animalData.add(Character.toString(animal.getSex()));
            animalData.add(animal.getSpecies());
            animalData.add(Integer.toString(animal.getAge()));
            animalData.add(animal.getName());
            animalData.add(Integer.toString(animal.getPenNumber()));
            animalData.add(Character.toString(animal.getAreaID()));
            tableModel.addRow(animalData);
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
        title.setText("Animal Management");
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(title);

        return panel;
    }
}