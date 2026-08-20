package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;

public class CreateHeroPanel extends JPanel {

    private JTextField heroNameField;
    private JLabel heroNameErrorLabel;
    private JComboBox<String> heroClassBox;
    private JButton submitButton;

    public CreateHeroPanel(Action mainMenuAction) {
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

        heroNameErrorLabel = new JLabel("Please fill in heroes name");
        heroNameErrorLabel.setForeground(Color.RED);


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

        submitButton.addActionListener(e -> {
            if (heroNameField.getText().trim().isEmpty()
                ) {
                gbc.gridx = 0;
                gbc.gridy = 1;
                add(heroNameErrorLabel, gbc);
                revalidate();
                repaint();
            }
            else {
                remove(heroNameErrorLabel);
                revalidate();
                repaint();
            }
        });

        JButton backButton = new JButton(mainMenuAction);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(backButton, gbc);
    }

    public String getHeroName() {
        return heroNameField.getText();
    }

    public String getHeroClass() {
        return (String) heroClassBox.getSelectedItem();
    }

    // public void addStartGameListener(ActionListener listener) {
    //     submitButton.addActionListener(listener);
    // }


}
