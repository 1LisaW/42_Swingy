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


public class BattleRunResultPopup extends APopup {

    // private JDialog currentDialog;

    public BattleRunResultPopup(GameController controller, PopupManager popupManager) {
        super(controller, popupManager);

        Boolean escapedBattle = this.controller.getCurrentBattleSimulator() == null;
        String message = escapedBattle
            ? "You successfully ran away!"
            : "You failed to run away! Prepare to fight!";

        JPanel panel = new JPanel();
        Object[] options = {"Accept"};

        JOptionPane optionPane = new JOptionPane(
            message,
            JOptionPane.QUESTION_MESSAGE,
            JOptionPane.DEFAULT_OPTION,
            null,
            options,
            options[0]
        );
        currentDialog = optionPane.createDialog(panel, "Run results");

        currentDialog.setModal(false);

        optionPane.addPropertyChangeListener(e -> {
            if (JOptionPane.VALUE_PROPERTY.equals(e.getPropertyName())) {
                Object value = optionPane.getValue();

                if (options[0].equals(value)) {
                    currentDialog.dispose();
                    onAccept();
                }
            }
        });

        currentDialog.setVisible(true);

    }

    private void onAccept() {
        if (this.controller.getCurrentBattleSimulator() == null) {
                controller.setGamePhase(Phases.GAMEPLAY);
                return ;
        }
        this.controller.setGamePhase(Phases.BATTLE_RESULT);
        this.controller.simulateBattle();
        popupManager.next();
    }


}
