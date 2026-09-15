package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.swingy.controller.GameController;
import com.swingy.controller.Phases;
import com.swingy.model.BattleResult;

import com.swingy.view.gui.APopup;


public class BattleRunOrFightPopup extends APopup {

    private JDialog currentDialog;

    public BattleRunOrFightPopup(GameController controller) {
        super(controller);
        Object[] options = {"Run", "Fight"};
            JPanel panel = new JPanel();
            int result = JOptionPane.showOptionDialog(
                panel,
                "You met a villain! What do you want to do?",
                "Battle!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );

            if (result == 0) {
                // showBattleRunResultPopup();
                // Run
            } else if (result == 1) {
                // runBattle();
                // Fight
            }
    }


}
