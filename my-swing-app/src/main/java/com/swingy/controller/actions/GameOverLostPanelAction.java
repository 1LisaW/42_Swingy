package com.swingy.controller.actions;

import java.awt.event.ActionEvent;
import javax.swing.*;

import com.swingy.controller.NavigationController;

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
