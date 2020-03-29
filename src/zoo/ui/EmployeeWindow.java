package zoo.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EmployeeWindow extends JFrame {

    public EmployeeWindow() {
        super("Employee Management");
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.setAlignmentY(Component.CENTER_ALIGNMENT);

        this.setSize(600, 600);
        this.setResizable(false);

        JPanel titlePane = new JPanel();
        titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.LINE_AXIS));
        titlePane.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = new JLabel();
        title.setPreferredSize(new Dimension(1200, 100));
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        title.setText("Employee Management");
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentY(Component.CENTER_ALIGNMENT);
        titlePane.add(title);

        contentPane.add(title);
        this.setVisible(true);
    }
}
