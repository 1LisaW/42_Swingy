package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;

import com.swingy.controller.GameController;
import com.swingy.view.gui.PopupManager;


public abstract class APopup {

    protected JDialog currentDialog;
    protected GameController controller;
    protected final PopupManager popupManager;


    public APopup(GameController controller, PopupManager popupManager) {
        this.controller = controller;
        this.popupManager = popupManager;
    }

    public void close() {
        if (currentDialog != null) {
            currentDialog.dispose();
            currentDialog = null;
        }
    }

    public void nextPopup() {
        popupManager.next();
    }
}
