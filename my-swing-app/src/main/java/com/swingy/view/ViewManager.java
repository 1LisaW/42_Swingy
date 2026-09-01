package com.swingy.view;

import javax.swing.SwingUtilities;

import com.swingy.controller.GameController;
import com.swingy.view.View;
import com.swingy.view.console.ConsoleView;
import com.swingy.view.gui.GuiView;

public class ViewManager {
    private final ConsoleView consoleView;
    private final GuiView guiView;

    private View currentView;

    public ViewManager(GameController controller) {
        this.consoleView = new ConsoleView(controller, this);
        this.guiView = new GuiView(controller, this);
    }

    public void switchTo(View view) {
        if (currentView != null) {
            currentView.hide();
        }

        currentView = view;
        currentView.show();
    }

    public void switchToConsole() {
        switchTo(consoleView);
    }

    public void switchToSwing() {
        switchTo(guiView);
    }
}