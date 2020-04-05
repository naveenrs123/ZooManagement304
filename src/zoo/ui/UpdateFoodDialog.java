package zoo.ui;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilCalendarModel;
import zoo.database.DatabaseConnectionHandler;
import zoo.model.FeedingModel;
import zoo.model.FoodModel;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.Vector;

public class UpdateFoodDialog extends JFrame {
    DatabaseConnectionHandler dbhandler;
    JTable table;
    /**
     * 0: Name, 1: Experience, 2: Specialization, 3: Phone Number
     * 4: Office Number.
     */
    ArrayList<JTextField> textFieldList = new ArrayList<>();

    /**
     * 0: Food_ID, 1: On Duty, 2: On Call, 3: Event Duty, 4: In Office
     */
    ArrayList<JComboBox> comboBoxList = new ArrayList<>();

    /**
     * 0: Start Date, 1: End Date
     */
    ArrayList<JDatePicker> datePickers = new ArrayList<>();

    public UpdateFoodDialog(DatabaseConnectionHandler dbhandler, JTable table) {
        super("Update Food Information");
        this.dbhandler = dbhandler;
        this.table = table;
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        this.setSize(900, 450);
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

        JLabel allFoodsLabel = createInfoLabel("Food");
        JPanel FoodIDPanel = createDropdownInputPanel("Food ID", dbhandler.getFoodIDs());
        JPanel typePanel = createTextInputPanel("Type");
        JPanel amountPanel = createTextInputPanel("Amount");

        allFoodsInput.add(allFoodsLabel);
        allFoodsInput.add(Box.createRigidArea(new Dimension(0, 20)));
        allFoodsInput.add(FoodIDPanel);
        allFoodsInput.add(typePanel);
        allFoodsInput.add(amountPanel);

        JPanel foodInput = new JPanel();
        foodInput.setLayout(new BoxLayout(foodInput, BoxLayout.PAGE_AXIS));
        foodInput.setMaximumSize(new Dimension(300, 300));
        foodInput.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel feedLabel = createInfoLabel("Feedings");
        JPanel foodID2Panel = createDropdownInputPanel("Food ID", dbhandler.getfeedFoodIDs());
        JPanel animalIDPanel = createTextInputPanel("Type");
        JPanel Employee_IDPanel = createTextInputPanel("Type");
        JPanel amount2Panel = createTextInputPanel("Amount");
        JPanel feedDatePanel = createDatepickerInputPanel("Date Of Feeding");;

        foodInput.add(feedLabel);
        foodInput.add(Box.createRigidArea(new Dimension(0, 20)));
        foodInput.add(foodID2Panel);
        foodInput.add(animalIDPanel);
        foodInput.add(Employee_IDPanel);
        foodInput.add(amount2Panel);
        foodInput.add(feedDatePanel);


        inputsPane.add(Box.createRigidArea(new Dimension(50, 0)));
        inputsPane.add(allFoodsInput);
        inputsPane.add(foodInput);

        comboBoxList.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object item = comboBoxList.get(0).getSelectedItem();
                resetFields();
                comboBoxList.get(0).setSelectedItem(item);
                setFields();
            }
        });

        JPanel FoodButtons = new JPanel();
        FoodButtons.setLayout(new BoxLayout(FoodButtons, BoxLayout.LINE_AXIS));
        FoodButtons.setPreferredSize(new Dimension(800, 50));
        FoodButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton submit = new JButton("Submit");
        JButton clear = new JButton("Clear Fields");
        FoodButtons.add(submit);
        FoodButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        FoodButtons.add(clear);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFood();
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        contentPane.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPane.add(infoPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPane.add(inputsPane);
        contentPane.add(FoodButtons);

        this.setVisible(true);
    }

    private void resetFields() {
        for (JTextField field : textFieldList) {
            field.setText("");
        }
        for (JComboBox comboBox : comboBoxList) {
            comboBox.setSelectedIndex(0);
        }
        for (JDatePicker datePicker : datePickers) {
            datePicker.getFormattedTextField().setText("");
        }
    }

    private void setFields() {
        String id1 = (String) comboBoxList.get(0).getSelectedItem();
        FoodModel Food = dbhandler.getOneFood(id1);

        textFieldList.get(0).setText(Food.getType());
        textFieldList.get(1).setText(Food.getInventory_Amount()+"");

        String id2 = (String) comboBoxList.get(1).getSelectedItem();
        FeedingModel feed2 = dbhandler.getOneFeed(id2);
        textFieldList.get(2).setText(feed2.getAnimal_ID());
        textFieldList.get(3).setText(feed2.getAmount()+"");
        setDate(feed2.getDate_Of_Feeding(),datePickers.get(0));

    }

    private void updateFood() {
        //0: Food ID, 1: Type, 2: Amount, 3: Type, Number foodid animal id employee id
        String FoodID = (String) comboBoxList.get(0).getSelectedItem();
        String type = textFieldList.get(0).getText().trim();
        String amt = textFieldList.get(1).getText().trim();
        int foodAmt1 = -1;
        if (!amt.equals("")) {
            foodAmt1 = Integer.parseInt(amt);
        }

        //Insert feeding
        String FoodID2 = (String) comboBoxList.get(1).getSelectedItem();
        String animalID = textFieldList.get(2).getText().trim();
        String employeeID = textFieldList.get(3).getText().trim();
        String amt2 = textFieldList.get(4).getText().trim();
        Date date = getDate(datePickers.get(0));
        int foodAmt2 = -1;
        if (!amt2.equals("")) {
            foodAmt2 = Integer.parseInt(amt2);
        }

        if (FoodID != "") {
            FoodModel foodModel = new FoodModel(FoodID, type, foodAmt1);
            dbhandler.updateFood(foodModel);
        }
        else if (FoodID2 != "") {
            FeedingModel feedinModel = new FeedingModel(FoodID, animalID, employeeID, foodAmt2, date);
            dbhandler.updateFeed(feedinModel);
        }
        else {
           //Do nothing
        }

        sharedInfo();

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
            infoText.insertString(0, "To update Food information, type in the ID of the Food along with the fields" +
                    " you want to change.\n\nWhen you are done, click the 'Submit Button'. To reset the fields, click the" +
                    " 'Clear Fields' button.", center);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        infoPanel.setMaximumSize(new Dimension(850, 120));
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

        //datePicker.setMaximumSize(new Dimension(100, 16));
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

    private void setDate(Date date, JDatePicker datePicker) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            DateModel<Calendar> dateModel = (DateModel<Calendar>) datePicker.getModel();
            dateModel.setValue(calendar);
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
