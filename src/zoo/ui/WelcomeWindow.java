package zoo.ui;

import zoo.database.DatabaseConnectionHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WelcomeWindow extends JFrame implements ActionListener {
    AnimalWindow animalWindow;
    EmployeeWindow employeeWindow;
    FoodWindow foodWindow;
    HealthCheckupWindow healthCheckupWindow;
    AreaPensWindow areaPensWindow;
    DatabaseConnectionHandler dbhandler;


    public WelcomeWindow(DatabaseConnectionHandler dbhandler) {
        super("Zoo Management Portal");
        this.dbhandler = dbhandler;
        animalWindow = new AnimalWindow(this.dbhandler);
        employeeWindow = new EmployeeWindow(this.dbhandler);
        foodWindow = new FoodWindow(this.dbhandler);
        healthCheckupWindow = new HealthCheckupWindow(this.dbhandler);
        areaPensWindow = new AreaPensWindow(this.dbhandler);
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.setAlignmentY(Component.CENTER_ALIGNMENT);

        this.setSize(900, 190);
        this.setResizable(false);

        JPanel titlePane = new JPanel();
        titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.LINE_AXIS));
        titlePane.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = new JLabel();
        title.setPreferredSize(new Dimension(1200, 100));
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        title.setText("Welcome to the Zoo Management Portal");
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentY(Component.CENTER_ALIGNMENT);
        titlePane.add(title);

        JLabel options = new JLabel();
        options.setPreferredSize(new Dimension(600, 30));
        options.setBorder(new EmptyBorder(0, 0, 20, 0));
        options.setText("What would you like to manage?");
        options.setFont(new Font("Serif", Font.BOLD, 20));
        options.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPane = new JPanel();
        buttonPane.setPreferredSize(new Dimension(1200, 20));
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        JButton animals = new JButton("Animals");
        animals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAnimalsPane();
            }
        });
        JButton employees = new JButton("Employees");
        employees.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEmployeesPane();
            }
        });
        JButton food = new JButton("Food and Feedings");
        food.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFoodPane();
            }
        });
        JButton healthCheckups = new JButton("Health Checkups");
        healthCheckups.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHealthCheckupPane();
            }
        });
        JButton areasPens = new JButton("Areas and Pens");
        areasPens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAreaPensPane();
            }
        });

        buttonPane.add(animals);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(employees);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(food);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(healthCheckups);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(areasPens);

        contentPane.add(titlePane);
        contentPane.add(options);
        contentPane.add(buttonPane);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void showAnimalsPane() {
        animalWindow.showFrame();
    }

    public void showEmployeesPane() {
        employeeWindow.showFrame();
    }

    public void showFoodPane() {
        foodWindow.showFrame();
    }

    public void showHealthCheckupPane() {
        healthCheckupWindow.showFrame();
    }

    public void showAreaPensPane() {
        areaPensWindow.showFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}