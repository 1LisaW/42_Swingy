package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;

import com.swingy.controller.GameController;
import com.swingy.model.HeroCredentials;


public class CreateHeroPanel extends JPanel {

    private JTextField heroNameField;
    private JLabel heroNameErrorLabel;
    private JComboBox<String> heroClassBox;
    private JButton submitButton;

    private final GameController controller;

    public CreateHeroPanel(Action mainMenuAction, Action gamePanelAction, GameController controller) {
        this.controller = controller;
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

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        gbc.gridx = 1;
        gbc.gridy = 3;

        // Button
        submitButton = new JButton("Start");
        buttonPanel.add(submitButton);

        submitButton.addActionListener(e -> {
            if (validatePanel(gbc)) {
                this.controller.startGame(
                    this.controller.createHero(
                        new HeroCredentials(getHeroName(), getHeroClass())
                    )
                );
                gamePanelAction.actionPerformed(e);
            }

        });

        JButton backButton = new JButton(mainMenuAction);
        buttonPanel.add(backButton);
        add(buttonPanel, gbc);
    }

    private boolean validatePanel(GridBagConstraints gbc) {
        if (heroNameField.getText().trim().isEmpty()) {
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(heroNameErrorLabel, gbc);

            revalidate();
            repaint();

            return false;
        }

        remove(heroNameErrorLabel);
        revalidate();
        repaint();

        return true;
    }

    public String getHeroName() {
        return heroNameField.getText();
    }

    public String getHeroClass() {
        return (String) heroClassBox.getSelectedItem();
    }

}
