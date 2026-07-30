package com.swingy.view.gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GamePanel extends JPanel {

    public GamePanel() {
    // (GuiView view) {

        setLayout(new BorderLayout());

        JLabel game = new JLabel("Game Screen", SwingConstants.CENTER);

        JButton back = new JButton("Back to Menu");

        // back.addActionListener(e -> view.showScreen("MENU"));

        add(game, BorderLayout.CENTER);
        add(back, BorderLayout.SOUTH);
    }
}
