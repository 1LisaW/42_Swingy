package com.swingy.view.gui;

import java.util.List;

import javax.swing.*;
import java.awt.*;

import com.swingy.model.Hero;

class SelectHeroFromListPanel extends JPanel {
    private JPanel contentPanel;
    private JPanel buttonPanel;

    public SelectHeroFromListPanel() {
        setLayout(new BorderLayout());

        contentPanel = new JPanel();
        // contentPanel.add(new JLabel("TEST TEXT"));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setPreferredSize(new Dimension(300, 400));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        Dimension buttonSize = new Dimension(240, 40);

        JButton backButton = new JButton("Back to main menu");
        backButton.setMaximumSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

   public void updateHeroList(List<Hero> heroes) {
        System.out.println("Heroes count: " + heroes.size());

        contentPanel.removeAll();

        contentPanel.add(new JLabel("TEST TEXT"));

        contentPanel.add(new JLabel("Heroes:"));
        contentPanel.add(Box.createVerticalStrut(10));

        if (heroes.size() == 0) {
            JLabel label = new JLabel("No data to show");
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(label);
            contentPanel.add(Box.createVerticalStrut(5));
        }
        for (Hero hero : heroes) {
            JLabel label = new JLabel("Hero " + hero.getName());
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(label);
            contentPanel.add(Box.createVerticalStrut(5));
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}