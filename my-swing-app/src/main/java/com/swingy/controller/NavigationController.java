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
}
