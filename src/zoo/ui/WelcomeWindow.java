package zoo.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WelcomeWindow extends JFrame implements ActionListener {

    public WelcomeWindow() {
        super("Zoo Management Portal");
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.setAlignmentY(Component.CENTER_ALIGNMENT);

        this.setSize(900, 190);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

            }
        });

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
        JButton employees = new JButton("Employees");
        JButton food = new JButton("Food and Feedings");
        JButton healthCheckups = new JButton("Health Checkups");
        JButton areasPens = new JButton("Areas and Pens");

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

    }

    public void showEmployeesPane() {

    }

    public void showFoodPane() {

    }

    public void showHealthCheckupPane() {

    }

    public void showAreaPensPane() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}