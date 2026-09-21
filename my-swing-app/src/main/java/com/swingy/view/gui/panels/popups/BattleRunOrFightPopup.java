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
import com.swingy.view.gui.PopupManager;


public class BattleRunOrFightPopup extends APopup {

    // private JDialog currentDialog;

    public BattleRunOrFightPopup(GameController controller, PopupManager popupManager) {
        super(controller, popupManager);

        Object[] options = {"Run", "Fight"};
        String message = "You met a " + controller.getBattleVillainData() + "! What do you want to do?";

        JPanel panel = new JPanel();
        JOptionPane optionPane = new JOptionPane(
            message,
            JOptionPane.QUESTION_MESSAGE,
            JOptionPane.YES_NO_OPTION,
            null,
            options,
            options[0]
        );
        currentDialog = optionPane.createDialog(panel, "Battle!");

        currentDialog.setModal(false);

        optionPane.addPropertyChangeListener(e -> {
            if (JOptionPane.VALUE_PROPERTY.equals(e.getPropertyName())) {
                Object value = optionPane.getValue();

                if (options[0].equals(value)) {
                    currentDialog.dispose();
                    onChoiceToRun();
                    // showNextPopup();
                } else if (options[1].equals(value)) {

                    currentDialog.dispose();
                    onChoiceToFight();
                    // controller.getGamePhase();
                    // Do something else
                }
            }
        });

        currentDialog.setVisible(true);
    }

    private void onChoiceToRun() {
        this.controller.runFromBattle();
        this.controller.setGamePhase(Phases.BATTLE_RUN_RESULT);
        popupManager.next();

    }

    private void onChoiceToFight() {
        this.controller.setGamePhase(Phases.BATTLE_RESULT);
        this.controller.simulateBattle();
        popupManager.next();
    }

}
