package com.swingy.controller;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class MainMenuAction extends AbstractAction {
    private final NavigationController navigation;

    public MainMenuAction(NavigationController navigation) {
        super("Main menu");
        this.navigation = navigation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        navigation.showMainMenu();
    }
}
