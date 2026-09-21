package com.swingy.controller.actions;

import java.awt.event.ActionEvent;
import javax.swing.*;

import com.swingy.controller.NavigationController;


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
