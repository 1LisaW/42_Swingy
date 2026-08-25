package com.swingy.controller;

import java.awt.event.ActionEvent;
import javax.swing.*;


public class GameOverLostPanelAction extends AbstractAction {
    private final NavigationController navigation;

    public GameOverLostPanelAction(NavigationController navigation) {
        super("Game over lost panel");
        this.navigation = navigation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        navigation.showGameOverPanel(false);
    }
}
