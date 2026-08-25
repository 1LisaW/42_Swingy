package com.swingy.controller;

import java.awt.event.ActionEvent;
import javax.swing.*;


public class GameOverWonPanelAction extends AbstractAction {
    private final NavigationController navigation;

    public GameOverWonPanelAction(NavigationController navigation) {
        super("Game over won panel");
        this.navigation = navigation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        navigation.showGameOverPanel(true);
    }
}
