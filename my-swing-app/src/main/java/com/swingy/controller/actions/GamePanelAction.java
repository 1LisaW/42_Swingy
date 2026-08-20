package com.swingy.controller;

import java.awt.event.ActionEvent;
import javax.swing.*;


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
