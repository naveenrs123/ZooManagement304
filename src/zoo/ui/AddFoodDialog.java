package zoo.ui;

import org.jdatepicker.JDatePicker;
import zoo.database.DatabaseConnectionHandler;
import zoo.model.FoodModel;
import zoo.model.FeedingModel;
import zoo.model.FoodPreferencesModel;
import zoo.model.Species;

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
import java.util.*;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilCalendarModel;
import zoo.database.DatabaseConnectionHandler;

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
import java.util.Vector;

public class AddFoodDialog extends JFrame {
    DatabaseConnectionHandler dbhandler;
    JTable table;

    /**
     * 0: Food ID, 1: Type, 2: Amount, 3: Type, Number foodid animal id employee id
     * 5: Office Number.
     */
    ArrayList<JTextField> textFieldList = new ArrayList<>();

    /**
     * 0: species, 1: On Call, 2: Event Duty, 3: In Office
     */
    ArrayList<JComboBox> comboBoxList = new ArrayList<>();

    /**
     * 0: Start Date, 1: End Date
     */
    ArrayList<JDatePicker> datePickers = new ArrayList<>();

    public AddFoodDialog(DatabaseConnectionHandler dbhandler, JTable table) {
        super("Add Food");
        this.dbhandler = dbhandler;
        this.table = table;
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        this.setSize(900, 500);
        this.setResizable(false);

        JTextPane infoPanel = createInfoPanel();

        JPanel inputsPane = new JPanel();
        inputsPane.setLayout(new BoxLayout(inputsPane, BoxLayout.LINE_AXIS));
        inputsPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel allFoodsInput = new JPanel();
        allFoodsInput.setLayout(new BoxLayout(allFoodsInput, BoxLayout.PAGE_AXIS));
        allFoodsInput.setMaximumSize(new Dimension(300, 300));
        allFoodsInput.setAlignmentY(Component.TOP_ALIGNMENT);
        allFoodsInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel allFoodsLabel = createInfoLabel("Food ");
        JPanel FoodIDPanel = createTextInputPanel("Food ID");
        JPanel typePanel = createTextInputPanel("Type");
        JPanel amtPanel = createTextInputPanel("Inventory Amount");

        allFoodsInput.add(allFoodsLabel);
        allFoodsInput.add(Box.createRigidArea(new Dimension(0, 20)));
        allFoodsInput.add(FoodIDPanel);
        allFoodsInput.add(typePanel);
        allFoodsInput.add(amtPanel);

        JPanel prefInput = new JPanel();
        prefInput.setLayout(new BoxLayout(prefInput, BoxLayout.PAGE_AXIS));
        prefInput.setMaximumSize(new Dimension(300, 300));
        prefInput.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel prefLabel = createInfoLabel("Food Preferences");

        JPanel speciesPanel = createDropdownInputPanel("Species", dbhandler.getAnimalSpecies());
        JPanel typePanel2 = createTextInputPanel("Food Type");

        prefInput.add(prefLabel);
        prefInput.add(Box.createRigidArea(new Dimension(0, 20)));
        prefInput.add(speciesPanel);
        prefInput.add(typePanel2);

        JPanel feedingInput = new JPanel();
        feedingInput.setLayout(new BoxLayout(feedingInput, BoxLayout.PAGE_AXIS));
        feedingInput.setMaximumSize(new Dimension(300, 300));
        feedingInput.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel zookeeperLabel = createInfoLabel("Feeding");
        JPanel foodidPanel = createTextInputPanel("Food ID");
        JPanel animalidPanel = createTextInputPanel("Animal ID");
        JPanel employeeidPanel = createTextInputPanel("Employee ID");
        JPanel amPanel = createTextInputPanel("Amount");
        JPanel dateofFeedingPanel = createDatepickerInputPanel("Date Of Feeding");

        feedingInput.add(zookeeperLabel);
        feedingInput.add(Box.createRigidArea(new Dimension(0, 20)));
        feedingInput.add(foodidPanel);
        feedingInput.add(animalidPanel);
        feedingInput.add(employeeidPanel);
        feedingInput.add(amPanel);
        feedingInput.add(dateofFeedingPanel);
        feedingInput.add(Box.createRigidArea(new Dimension(0, 10)));

        inputsPane.add(Box.createRigidArea(new Dimension(50, 0)));
        inputsPane.add(allFoodsInput);
        inputsPane.add(prefInput);
        inputsPane.add(feedingInput);

        JPanel FoodButtons = new JPanel();
        FoodButtons.setLayout(new BoxLayout(FoodButtons, BoxLayout.LINE_AXIS));
        FoodButtons.setPreferredSize(new Dimension(800, 50));
        FoodButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton submit = new JButton("Submit");
        JButton clear = new JButton("Clear Fields");

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertFood();
            }
        });

        FoodButtons.add(submit);
        FoodButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        FoodButtons.add(clear);

        contentPane.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPane.add(infoPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPane.add(inputsPane);
        contentPane.add(FoodButtons);

        this.setVisible(true);
    }

    private void insertFood() {
        //0: Food ID, 1: Type, 2: Amount, 3: Type, Number foodid animal id employee id
        String FoodID = textFieldList.get(0).getText().trim();
        String type = textFieldList.get(1).getText().trim();
        String amt = textFieldList.get(2).getText().trim();

        //Insert preference
        String type2 = textFieldList.get(3).getText().trim();
        String s = (String)comboBoxList.get(0).getSelectedItem();

        //Insert feeding
        String FoodID2 = textFieldList.get(4).getText().trim();
        String animalID = textFieldList.get(5).getText().trim();
        String employeeID = textFieldList.get(6).getText().trim();
        String amt2 = textFieldList.get(7).getText().trim();
        int foodAmt2 = -1;
        if (!amt2.equals("")) {
            foodAmt2 = Integer.parseInt(amt2);
        }
        Date date = getDate(datePickers.get(0));



        int foodAmt = -1;
        if (!amt.equals("")) {
            foodAmt = Integer.parseInt(amt);
        }


        if (!type.equals("")) {
            FoodModel FoodModel = new FoodModel(FoodID, type, foodAmt);
            dbhandler.insertFood(FoodModel);
        }
         if (!type2.equals("")) {
            FoodPreferencesModel prefModel = new FoodPreferencesModel(type2, s);
            dbhandler.insertPreference(prefModel);
        }
         if (foodAmt2 != -1) {
            FeedingModel feed = new FeedingModel(FoodID2, animalID, employeeID, foodAmt2, date);
            dbhandler.insertFeeding(feed);
        }
        else {

        }
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
            infoText.insertString(0, "To add a new Food to the system, type in all the appropriate details for that " +
                    " Food.\n\nFor each table related to food, fill in the fields as well as the fields for the type of" +
                    " data being added.\n\nWhen you are done, click the 'Submit' button. If you need to clear all fields, hit the" +
                    " 'Clear Fields' button to reset all inputs.", center);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        infoPanel.setMaximumSize(new Dimension(850, 200));
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

    public String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
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
}

