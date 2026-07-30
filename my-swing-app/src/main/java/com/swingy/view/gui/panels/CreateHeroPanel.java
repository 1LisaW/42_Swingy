package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;

public class CreateHeroPanel extends JPanel {

    private JTextField heroNameField;
    private JComboBox<String> heroClassBox;
    private JButton submitButton;

    public CreateHeroPanel() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Hero's name:"), gbc);

        heroNameField = new JTextField(15);
        gbc.gridx = 1;
        add(heroNameField, gbc);


        // Hero class
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Hero's class:"), gbc);

        heroClassBox = new JComboBox<>(
                new String[]{"wizard", "warrior", "barbarian"}
        );
        gbc.gridx = 1;
        add(heroClassBox, gbc);

        // Button
        submitButton = new JButton("Start");
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(submitButton, gbc);
    }
}
