package zoo.ui;

import zoo.database.DatabaseConnectionHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AreaPensWindow extends JFrame {
    DatabaseConnectionHandler dbhandler;

    public AreaPensWindow(DatabaseConnectionHandler dbhandler) {
        super("Areas and Pens");
        this.dbhandler = dbhandler;
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
        title.setText("Manage Areas and Pens");
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentY(Component.CENTER_ALIGNMENT);
        titlePane.add(title);

        contentPane.add(title);
        this.setVisible(true);
    }
}
