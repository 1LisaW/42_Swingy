package com.swingy.controller.actions;

import java.awt.event.ActionEvent;
import javax.swing.*;

import com.swingy.controller.NavigationController;


public class GamePanelAction extends AbstractAction {
    private final NavigationController navigation;

    public GamePanelAction(NavigationController navigation) {
        super("Game panel");
        this.navigation = navigation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        navigation.showGamePanel();
    }
}
