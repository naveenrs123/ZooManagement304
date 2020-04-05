package zoo.ui;

import zoo.database.DatabaseConnectionHandler;
import zoo.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class FoodWindow extends JFrame {
    DatabaseConnectionHandler dbhandler;
    JTable table;
    AddFoodDialog addFoodDialog;
    UpdateFoodDialog updateFoodDialog;
    JScrollPane foodScroll;

    public FoodWindow(DatabaseConnectionHandler dbhandler) {
        super("Food Management");
        this.dbhandler = dbhandler;
        this.table = new JTable();
        this.addFoodDialog = new AddFoodDialog(dbhandler,table);
        this.updateFoodDialog = new UpdateFoodDialog(dbhandler,table);
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        JPanel titlePane = createTitlePane();
        sharedInfo();

        foodScroll = new JScrollPane(table);
        foodScroll.setPreferredSize(new Dimension(700, 300));
        foodScroll.setMaximumSize(new Dimension(700, 300));
        foodScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        foodScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel selectOptions = new JPanel();
        selectOptions.setLayout(new BoxLayout(selectOptions, BoxLayout.LINE_AXIS));
        selectOptions.setPreferredSize(new Dimension(700, 50));
        selectOptions.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton foods = new JButton("Food Inventory");
        JButton foodPrefs = new JButton("Food Preferences");
        JButton feeding = new JButton("Feeding Schedule");

        foods.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sharedInfo();
            }
        });
        foodPrefs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                foodPrefs();
            }
        });
        feeding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                feeding();
            }
        });

        selectOptions.add(foods);
        selectOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        selectOptions.add(foodPrefs);
        selectOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        selectOptions.add(feeding);
        selectOptions.add(Box.createRigidArea(new Dimension(10, 0)));

        JPanel foodButtons = new JPanel();
        foodButtons.setLayout(new BoxLayout(foodButtons, BoxLayout.LINE_AXIS));
        foodButtons.setPreferredSize(new Dimension(700, 50));
        foodButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addfood = new JButton("Add Food/Feeding/Preference");
        JButton updatefood = new JButton("Update Food/Feeding/Preference");
        JButton searchfoods = new JButton("Search");
        JButton resetView = new JButton("Reset View");

        addfood.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFoodDialog.showFrame();
            }
        });
        updatefood.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               updateFoodDialog.showFrame();
            }
        });

        foodButtons.add(addfood);
        foodButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        foodButtons.add(updatefood);
        foodButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        foodButtons.add(searchfoods);
        foodButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        foodButtons.add(resetView);

        contentPane.add(titlePane);
        contentPane.add(foodScroll);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(selectOptions);
        contentPane.add(foodButtons);

        this.setSize(800, 500);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void feeding() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Vector<String> columnNames = new Vector<>();

        columnNames.add("Food ID");
        columnNames.add("Animal ID");
        columnNames.add("Employee ID");
        columnNames.add("Amount");
        columnNames.add("Date_Of_Feeding");
        tableModel.setColumnIdentifiers(columnNames);
        FeedingModel[] foods = dbhandler.getFeedingInfo();
        Vector<String> foodData;
        for (FeedingModel food: foods) {
            foodData = new Vector<>();
            foodData.add(food.getFood_ID());
            foodData.add(food.getAnimal_ID());
            foodData.add(food.getEmployee_ID());
            foodData.add(food.getAmount()+"");
            foodData.add(food.getDate_Of_Feeding()+"");
            tableModel.addRow(foodData);
        }
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }

    private void foodPrefs() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Species");
        columnNames.add("Food Type");
        tableModel.setColumnIdentifiers(columnNames);
        FoodPreferencesModel[] foods = dbhandler.getFoodPreferences();
        Vector<String> foodData;
        for (FoodPreferencesModel food: foods) {
            foodData = new Vector<>();
            foodData.add(food.getFood_Type());
            foodData.add(food.getSpecies()+"");
            tableModel.addRow(foodData);
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
        columnNames.add("Food ID");
        columnNames.add("Type");
        columnNames.add("Inventory Amount");
        tableModel.setColumnIdentifiers(columnNames);
        FoodModel[] foods = dbhandler.getFoodInfo();
        Vector<String> foodData;
        for (FoodModel food: foods) {
            foodData = new Vector<>();
            foodData.add(food.getFood_ID());
            foodData.add(food.getType());
            foodData.add(Integer.toString(food.getInventory_Amount()));
            tableModel.addRow(foodData);
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
        title.setText("Food Management");
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(title);

        return panel;
    }
}
