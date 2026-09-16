package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;

import com.swingy.controller.GameController;
import com.swingy.view.gui.PopupManager;


public abstract class APopup {

    protected JDialog currentDialog;
    protected GameController controller;
    protected final PopupManager popupManager;

    // private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public APopup(GameController controller, PopupManager popupManager) {
        this.controller = controller;
        this.popupManager = popupManager;
    }

    // protected void finish(T result) {
    //     pcs.firePropertyChange("result", null, result);
    //     close();
    // }

    // public void addPropertyChangeListener(PropertyChangeListener listener) {
    //     pcs.addPropertyChangeListener(listener);
    // }

    // public void removePropertyChangeListener(PropertyChangeListener listener) {
    //     pcs.removePropertyChangeListener(listener);
    // }

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
