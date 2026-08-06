package com.swingy.controller;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.Window;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;

public class ExitAction extends AbstractAction {

    private final JFrame frame;

    public ExitAction(JFrame frame) {
        super("Exit");
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispatchEvent(
            new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)
        );
    }
}
