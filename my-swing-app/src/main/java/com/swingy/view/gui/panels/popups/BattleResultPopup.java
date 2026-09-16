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

public class BattleResultPopup extends APopup {

    public BattleResultPopup(GameController controller, PopupManager popupManager) {
        super(controller, popupManager);

        BattleResult battleResult = controller.getBattleResult();

        ImageIcon resultIcon = getBattleResultIcon(battleResult);
        JPanel panel = new JPanel();

        Object[] options = {"OK"};
        String message = controller.getBattleLog().stream().reduce("", (acc, line) -> acc + line + "\n");

        JOptionPane optionPane = new JOptionPane(
            message,
            JOptionPane.INFORMATION_MESSAGE,
            JOptionPane.DEFAULT_OPTION,
            resultIcon,
            options,
            options[0]
        );
        currentDialog = optionPane.createDialog(panel, "Battle Results");

        currentDialog.setModal(false);

        optionPane.addPropertyChangeListener(e -> {
            if (JOptionPane.VALUE_PROPERTY.equals(e.getPropertyName())) {
                Object value = optionPane.getValue();
                System.out.println("We got today "+ value);

                if (options[0].equals(value)) {
                    currentDialog.dispose();
                    onAccept();
                }
            }
        });

        currentDialog.setVisible(true);


    }

    private ImageIcon getBattleResultIcon(BattleResult battleResult) {
        ImageIcon icon = null;
        if (battleResult == BattleResult.WIN) {
            icon = new ImageIcon(getClass().getResource("/images/battle_won.png"));
        } else if (battleResult == BattleResult.LOSE) {
            icon = new ImageIcon(getClass().getResource("/images/battle_lost.png"));
        } else {
            icon = new ImageIcon(getClass().getResource("/images/battle_draw.png"));
        }
        Image scaled = icon.getImage().getScaledInstance(
            150, 150, Image.SCALE_SMOOTH
        );
        return new ImageIcon(scaled);
    }

    private void onAccept() {
        BattleResult battleResult = controller.getBattleResult();
        if (battleResult == BattleResult.WIN) {
            if (controller.isBattleProduceArtifact()) {
                controller.setGamePhase(Phases.BATTLE_ARTIFACT);
                popupManager.next();
                // this.
                // ArtifactPopup artifactPopup = new ArtifactPopup(controller);
            } else if (checkLevelUp()) {
                controller.setGamePhase(Phases.HERO_LEVEL_UP);
                popupManager.next();
            } else {
                controller.setGamePhase(Phases.GAMEPLAY);
                popupManager.checkGameOver();
            }
        } else {
            popupManager.checkGameOver();
        }

    }

    private boolean checkLevelUp() {
         int prevLevel = controller.getHeroLevel();
        controller.collectBattleExperience();
        int nextLevel = controller.getHeroLevel();
        return nextLevel > prevLevel;
    }
}
