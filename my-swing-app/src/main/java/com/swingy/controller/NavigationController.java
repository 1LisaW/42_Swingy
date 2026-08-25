package com.swingy.controller;

import java.awt.*;
import javax.swing.JPanel;

public class NavigationController {
    private final CardLayout layout;
    private final JPanel cards;

    public NavigationController(CardLayout layout, JPanel cards) {
        this.layout = layout;
        this.cards = cards;
    }

    public void showMainMenu() {
        layout.show(cards, "MENU");
    }

    public void showGamePanel() {
        layout.show(cards, "GAME");
    }

    public void showGameOverPanel(boolean won) {
        if (won) {
            layout.show(cards, "GAME_OVER_WON");
        } else {
            layout.show(cards, "GAME_OVER_LOST");
        }
    }
}
