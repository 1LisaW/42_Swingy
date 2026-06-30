package com.example;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("My Swing App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        JLabel label = new JLabel("Hello, Swing!");
        JButton button = new JButton("Click Me");

        button.addActionListener(e ->
            label.setText("Button clicked!")
        );

        panel.add(label);
        panel.add(button);

        add(panel);

        setVisible(true);
    }
}
