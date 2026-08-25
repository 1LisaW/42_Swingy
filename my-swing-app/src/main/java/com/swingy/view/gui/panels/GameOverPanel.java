package com.swingy.view.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class GameOverPanel extends JPanel {

    private final Color background = new Color(18, 20, 30);
    private final Color cardColor = new Color(30, 34, 48);

    public GameOverPanel(boolean won, Runnable onRestart, Runnable onExit) {
        setLayout(new GridBagLayout());
        setBackground(background);

        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(cardColor);
                g2.fill(new RoundRectangle2D.Float(
                    0, 0, getWidth(), getHeight(), 30, 30
                ));

                g2.dispose();
            }
        };

        card.setOpaque(false);
        card.setPreferredSize(new Dimension(450, 350));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(35, 45, 35, 45));

        // Icon
        JLabel icon = new JLabel(won ? "🏆" : "💀");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 55));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel title = new JLabel(won ? "YOU WIN!" : "GAME OVER");
        title.setFont(new Font("SansSerif", Font.BOLD, 38));
        title.setForeground(won
            ? new Color(80, 220, 140)
            : new Color(255, 90, 100)
        );
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Message
        JLabel message = new JLabel(
            won ? "Congratulations! You did it!"
                 : "Better luck next time!"
        );
        message.setFont(new Font("SansSerif", Font.PLAIN, 17));
        message.setForeground(new Color(190, 195, 210));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JButton restartButton = createButton(
            "Play Again",
            new Color(70, 130, 255)
        );

        JButton exitButton = createButton(
            "Exit",
            new Color(65, 70, 85)
        );

        restartButton.addActionListener(e -> {
            if (onRestart != null) {
                onRestart.run();
            }
        });

        exitButton.addActionListener(e -> {
            if (onExit != null) {
                onExit.run();
            }
        });

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttons.setOpaque(false);
        buttons.add(restartButton);
        buttons.add(exitButton);

        // Layout
        card.add(icon);
        card.add(Box.createVerticalStrut(10));
        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(message);
        card.add(Box.createVerticalGlue());
        card.add(buttons);

        add(card);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(color);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(130, 45));

        return button;
    }
}