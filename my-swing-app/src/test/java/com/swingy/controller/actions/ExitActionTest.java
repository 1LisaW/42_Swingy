package com.swingy.controller.actions;
import org.junit.jupiter.api.Test;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExitActionTest {
    @Test
    void actionPerformed_dispatchesWindowClosingEvent() {
        JFrame frame = new JFrame();
        AtomicBoolean closingReceived = new AtomicBoolean(false);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closingReceived.set(true);
            }
        });

        ExitAction action = new ExitAction(frame);

        action.actionPerformed(
            new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Exit")
        );

        assertTrue(closingReceived.get());

        frame.dispose();
    }
}
