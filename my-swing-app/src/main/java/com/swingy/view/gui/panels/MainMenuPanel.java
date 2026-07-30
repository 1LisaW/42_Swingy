package com.swingy.view.gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    private Image background;

    private final JButton newGameButton = new JButton("Create new hero");
    private final JButton loadHeroesButton = new JButton("Load hero");
    private final JButton exitButton = new JButton("Exit");

    public MainMenuPanel() {
    // (GuiView view) {

        setLayout(new GridBagLayout());
        // setLayout(new BorderLayout());

        JLabel title = new JLabel("Swingy", SwingConstants.CENTER);

        // JButton play = new JButton("Start Game");
        // JPanel root = new JPanel(new GridBagLayout());
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

        // play.addActionListener(e -> view.showScreen("GAME"));

        // add(title, BorderLayout.CENTER);
        add(buttonPanel);
        // add(play, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        RadialGradientPaint paint = new RadialGradientPaint(
                getWidth() / 2f,
                getHeight() / 2f,
                getWidth() / 2f,
                new float[]{0f, 1f},
                new Color[]{
                        new Color(255, 255, 255, 0),
                        new Color(0, 0, 0, 180)
                });

        g2.setPaint(paint);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.dispose();
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
