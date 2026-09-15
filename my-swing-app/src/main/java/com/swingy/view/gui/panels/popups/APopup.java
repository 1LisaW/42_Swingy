package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;

import com.swingy.controller.GameController;


public abstract class APopup {

    protected JDialog currentDialog;
    protected GameController controller;

    // private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public APopup(GameController controller) {
        this.controller = controller;
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
}
