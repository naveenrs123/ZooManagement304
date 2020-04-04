package zoo.ui;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

public class AddAnimalDialog extends JFrame{
    DatabaseConnectionHandler dbhandler;
    JTable table;

    /**
     * 0: Animal ID, 1: Age, 2: Name
     */
    ArrayList<JTextField> textFieldList = new ArrayList<>();

    /**
     * 0: Type, 1: Sex, 2: Species, 3: Area_ID,Pen_Number, 4: Approval Manager
     */
    ArrayList<JComboBox> comboBoxList = new ArrayList<>();

    /**
     * 0: Start Date, 1: End Date
     */
    ArrayList<JDatePicker> datePickers = new ArrayList<>();

    public AddAnimalDialog(DatabaseConnectionHandler dbhandler, JTable table) {
        super("Add Animal");
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

        JPanel allAnimalsInput = new JPanel();
        allAnimalsInput.setLayout(new BoxLayout(allAnimalsInput, BoxLayout.PAGE_AXIS));
        allAnimalsInput.setMaximumSize(new Dimension(300, 300));
        allAnimalsInput.setAlignmentY(Component.TOP_ALIGNMENT);
        allAnimalsInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] sex = {"F", "M"};
        String[] species = getAnimalSpecies();
        String[] types = getAnimalTypes();
        String[] areaPens = dbhandler.getPenNumbers();

        JPanel animalIDPanel = createTextInputPanel("Animal ID");
        JPanel animalTypes = createDropdownInputPanel("Type", types);
        JPanel animalSex = createDropdownInputPanel("Sex", sex);


        allAnimalsInput.add(Box.createRigidArea(new Dimension(0, 20)));
        allAnimalsInput.add(animalIDPanel);
        allAnimalsInput.add(animalTypes);
        allAnimalsInput.add(animalSex);

        JPanel animals2 = new JPanel();
        animals2.setLayout(new BoxLayout(animals2, BoxLayout.PAGE_AXIS));
        animals2.setMaximumSize(new Dimension(300, 300));
        animals2.setAlignmentY(Component.TOP_ALIGNMENT);

        String[] managerIDStrings = getManagerIDs();

        JPanel animalSpecies = createDropdownInputPanel("Species", species);
        JPanel agePanel = createTextInputPanel("Age");
        JPanel namePanel = createTextInputPanel("Name");
        JPanel penNumbers = createDropdownInputPanel("Area, Pen", areaPens);
        JPanel managerIDs = createDropdownInputPanel("Approval Manager", managerIDStrings);
        animals2.add(animalSpecies);
        animals2.add(agePanel);
        animals2.add(namePanel);
        animals2.add(penNumbers);
        animals2.add(managerIDs);


        inputsPane.add(Box.createRigidArea(new Dimension(50, 0)));
        inputsPane.add(allAnimalsInput);
        inputsPane.add(animals2);

        JPanel animalButtons = new JPanel();
        animalButtons.setLayout(new BoxLayout(animalButtons, BoxLayout.LINE_AXIS));
        animalButtons.setPreferredSize(new Dimension(800, 50));
        animalButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton submit = new JButton("Submit");
        JButton clear = new JButton("Clear Fields");

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertAnimal();
            }
        });

        animalButtons.add(submit);
        animalButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        animalButtons.add(clear);

        contentPane.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPane.add(infoPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPane.add(inputsPane);
        contentPane.add(animalButtons);

        this.setVisible(true);
    }

    private String[] getManagerIDs() {
        ManagerEmployeeModel[] managers = dbhandler.getManagerEmployeeInfo();
        ArrayList<String> managersStringArray = new ArrayList<>();
        for (ManagerEmployeeModel manager: managers) {
            String ID = manager.getEmployee_ID();
            managersStringArray.add(ID);
        }
        String[] IDs = new String[managersStringArray.size()];
        IDs = managersStringArray.toArray(IDs);
        return IDs;
    }

    private String[] getAnimalTypes() {
        AnimalTypes[] animalTypesArray = AnimalTypes.class.getEnumConstants();
        ArrayList<String> animalTypesStringArray = new ArrayList<>();
        for (AnimalTypes at: animalTypesArray) {
            String atstr = at.toString();
            animalTypesStringArray.add(atstr);
            //  atstr = atstr.substring(0,1) +  atstr.substring(1).toLowerCase();
        }
        String[] types = new String[animalTypesStringArray.size()];
        types = animalTypesStringArray.toArray(types);
        return types;
    }

    private String[] getAnimalSpecies() {
        Species[] speciesArray = Species.class.getEnumConstants();
        ArrayList<String> speciesArrayString = new ArrayList<>();
        for (Species sp: speciesArray) {
            String spstr = sp.toString();
            speciesArrayString.add(spstr);
            //  atstr = atstr.substring(0,1) +  atstr.substring(1).toLowerCase();
        }
        String[] species = new String[speciesArrayString.size()];
        species = speciesArrayString.toArray(species);
        return species;
    }

    private void insertAnimal() {
        String animalID = textFieldList.get(0).getText().trim();
        String age = textFieldList.get(1).getText().trim();
        String name = textFieldList.get(2).getText().trim();
        String type = comboBoxList.get(0).getSelectedItem().toString();
        String sex = comboBoxList.get(1).getSelectedItem().toString();
        String speciesString = comboBoxList.get(2).getSelectedItem().toString();
        String species;
        if (speciesString.contains("_")) {
            String[] speciesSplit = comboBoxList.get(2).getSelectedItem().toString().split("_",2);
            species = speciesSplit[0] + " " + speciesSplit[1];
        } else {
            species = speciesString;
        }
        String[] area_pen = comboBoxList.get(3).getSelectedItem().toString().split(",", 2);
        String area_id = area_pen[0];
        String pen_number = area_pen[1];
        String managerID = comboBoxList.get(4).getSelectedItem().toString();

        boolean requirement = (!animalID.equals("")) && (!age.equals("")) && (!name.equals(" ")) && (!type.equals("")) && (!species.equals("")) && (!area_id.equals("")) && (!pen_number.equals(""));
        if (requirement) {
            AnimalModel animal = new AnimalModel(animalID, type, sex.charAt(0), species, Integer.parseInt(age), name, Integer.parseInt(pen_number), area_id.charAt(0));
            dbhandler.insertAnimal(animal, managerID);
            sharedInfo();
        } else {
            System.out.println("error");
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
            infoText.insertString(0, "To add a new animal to the system, type in and select all the appropriate details for that " +
                    " animal.\n\nWhen you are done, click the 'Submit' button. If you need to clear all fields, hit the" +
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
