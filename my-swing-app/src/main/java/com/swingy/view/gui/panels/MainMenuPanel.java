package com.swingy.view.gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import com.swingy.controller.actions.ExitAction;

public class MainMenuPanel extends JPanel {

    private final JButton newGameButton = new JButton("Create new hero");
    private final JButton loadHeroesButton = new JButton("Load hero");
    private JButton exitButton;

    public MainMenuPanel(ExitAction exitAction) {
        exitButton = new JButton(exitAction);
        setLayout(new GridBagLayout());

        JLabel title = new JLabel("Swingy", SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        Dimension buttonSize = new Dimension(240, 40);

        newGameButton.setMaximumSize(buttonSize);
        newGameButton.setPreferredSize(buttonSize);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadHeroesButton.setMaximumSize(buttonSize);
        loadHeroesButton.setPreferredSize(buttonSize);
        loadHeroesButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        exitButton.setMaximumSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(loadHeroesButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(exitButton);
        buttonPanel.setOpaque(false);

        add(buttonPanel);
    }

    public void addNewGameListener(ActionListener listener) {
        newGameButton.addActionListener(listener);
    }

    public void addLoadHeroesButtonListener(ActionListener listener) {
        loadHeroesButton.addActionListener(listener);
    }

    public void addExitListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

}
