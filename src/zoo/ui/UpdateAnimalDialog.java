package zoo.ui;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilCalendarModel;
import zoo.database.DatabaseConnectionHandler;
import zoo.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.sql.Date;
import java.util.*;

public class UpdateAnimalDialog extends JFrame{
    DatabaseConnectionHandler dbhandler;
    JTable table;
    /**
     * 0: Age, 1: Name
     */
    ArrayList<JTextField> textFieldList = new ArrayList<>();

    /**
     * 0: Animal_ID 1: Type, 2: Sex, 3: Species, 4: Area_ID,Pen_Number, 5: ManagerID
     */
    ArrayList<JComboBox<String>> comboBoxList = new ArrayList<>();

    /**
     * 0: Start Date, 1: End Date
     */
    ArrayList<JDatePicker> datePickers = new ArrayList<>();

    public UpdateAnimalDialog(DatabaseConnectionHandler dbhandler, JTable table) {
        super("Update Animal Information");
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

        JPanel allAnimalsInput = new JPanel();
        allAnimalsInput.setLayout(new BoxLayout(allAnimalsInput, BoxLayout.PAGE_AXIS));
        allAnimalsInput.setMaximumSize(new Dimension(300, 300));
        allAnimalsInput.setAlignmentY(Component.TOP_ALIGNMENT);
        allAnimalsInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        String[] sex = {"F", "M"};

        JPanel animalIDPanel = createDropdownInputPanel("Animal IDs", dbhandler.getAnimalIDs());
        JPanel animalTypes = createDropdownInputPanel("Type", dbhandler.getAnimalTypes());
        JPanel animalSex = createDropdownInputPanel("Sex", sex);


        allAnimalsInput.add(Box.createRigidArea(new Dimension(0, 20)));
        allAnimalsInput.add(animalIDPanel);
        allAnimalsInput.add(animalTypes);
        allAnimalsInput.add(animalSex);

        JPanel animals2 = new JPanel();
        animals2.setLayout(new BoxLayout(animals2, BoxLayout.PAGE_AXIS));
        animals2.setMaximumSize(new Dimension(300, 300));
        animals2.setAlignmentY(Component.TOP_ALIGNMENT);

        String[] managerIDStrings = dbhandler.getManageIDs();

        JPanel animalSpecies = createDropdownInputPanel("Species", dbhandler.getAnimalSpecies());
        JPanel agePanel = createTextInputPanel("Age");
        JPanel namePanel = createTextInputPanel("Name");
        JPanel penNumbers = createDropdownInputPanel("Area, Pen", dbhandler.getPenNumbers());
        JPanel managerIDs = createDropdownInputPanel("Approval Manager", managerIDStrings);
        animals2.add(animalSpecies);
        animals2.add(agePanel);
        animals2.add(namePanel);
        animals2.add(penNumbers);
        animals2.add(managerIDs);

        comboBoxList.get(0).addActionListener(e -> {
            Object item = comboBoxList.get(0).getSelectedItem();
            resetFields();
            comboBoxList.get(0).setSelectedItem(item);
            setFields();
        });

        JPanel animalButtons = new JPanel();
        animalButtons.setLayout(new BoxLayout(animalButtons, BoxLayout.LINE_AXIS));
        animalButtons.setPreferredSize(new Dimension(800, 50));
        animalButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton submit = new JButton("Submit");
        JButton clear = new JButton("Clear Fields");
        JButton delete = new JButton("Delete Selected Animal");
        animalButtons.add(submit);
        animalButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        animalButtons.add(clear);
        animalButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        animalButtons.add(delete);

        submit.addActionListener(e -> updateAnimal());

        clear.addActionListener(e -> resetFields());

        delete.addActionListener(e -> deleteAnimal());

        inputsPane.add(allAnimalsInput);
        inputsPane.add(animals2);

        contentPane.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPane.add(infoPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPane.add(inputsPane);
        contentPane.add(animalButtons);

        this.setVisible(true);
    }

    private void resetFields() {
        for (JTextField field : textFieldList) {
            field.setText("");
        }
        for (JComboBox<String> comboBox : comboBoxList) {
            comboBox.setSelectedIndex(0);
        }
        for (JDatePicker datePicker : datePickers) {
            datePicker.getFormattedTextField().setText("");
        }
    }

    private void setFields() {
        String id = (String) comboBoxList.get(0).getSelectedItem();
        AnimalModel animal = dbhandler.getOneAnimal(id);
        textFieldList.get(0).setText(Integer.toString(animal.getAge()));
        textFieldList.get(1).setText(animal.getName());

        String type = animal.getType();
        comboBoxList.get(1).setSelectedItem(type);

        String sex = Character.toString(animal.getSex());
        comboBoxList.get(2).setSelectedItem(sex);

        String species = animal.getSpecies();
        comboBoxList.get(3).setSelectedItem(species);

        String penArea = animal.getAreaID() + "," + Integer.toString(animal.getPenNumber());
        comboBoxList.get(4).setSelectedItem(penArea);
    }

    private void deleteAnimal() {
        String id = comboBoxList.get(0).getSelectedItem().toString();
        dbhandler.deleteAnimal(id);
        sharedInfo();
        resetFields();
    }

    private void updateAnimal() {
        String animalID = ((String) comboBoxList.get(0).getSelectedItem());
        int age = Integer.parseInt(textFieldList.get(0).getText().trim());
        String name = textFieldList.get(1).getText().trim();
        String type = comboBoxList.get(1).getSelectedItem().toString();
        char sex = comboBoxList.get(2).getSelectedItem().toString().charAt(0);
        String species = comboBoxList.get(3).getSelectedItem().toString();
        String penArea = comboBoxList.get(4).getSelectedItem().toString();
        int penNumber = Integer.parseInt(penArea.split(",")[1]);
        char areaID = penArea.split(",")[0].charAt(0);
        String managerID = comboBoxList.get(5).getSelectedItem().toString();

        AnimalModel animal = new AnimalModel(animalID, type, sex, species, age, name, penNumber, areaID);
        AnimalModel animalOld = dbhandler.getOneAnimal(animalID);
        if (animal.getPenNumber() != animalOld.getPenNumber() || animal.getAreaID() != animalOld.getAreaID()) {
            String r_ID = "R" + Integer.toString(dbhandler.getNextRelocationNumber());
            long millis=System.currentTimeMillis();
            Date date=new java.sql.Date(millis);
            AnimalRelocationModel relocation = new AnimalRelocationModel(r_ID, managerID, animalID, Integer.toString(animalOld.getPenNumber()), Character.toString(animalOld.getAreaID()), Integer.toString(animal.getPenNumber()), Character.toString(animal.getAreaID()), date);
            dbhandler.insertAnimalRelocation(relocation);
        }
        dbhandler.updateAnimal(animal);
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
            infoText.insertString(0, "To update animal information, select the ID of the animal and update the fields" +
                    " you want to change.\n\nWhen you are done, click the 'Submit Button'. To reset the fields, click the" +
                    " 'Clear Fields' button. To instead delete the animal with the ID you selected, click the 'Delete Selected Animal' button.", center);
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

        JComboBox<String> panelCombo = new JComboBox<>(choices);
        panelCombo.setMaximumSize(new Dimension(100, 30));
        panelCombo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(panelText);
        panel.add(panelCombo);

        comboBoxList.add(panelCombo);
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

}
