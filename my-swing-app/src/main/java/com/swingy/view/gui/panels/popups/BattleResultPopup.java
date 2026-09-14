package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.swingy.controller.GameController;
import com.swingy.controller.Phases;
import com.swingy.model.BattleResult;


public class BattleResultPopup {
    JPanel panel = new JPanel();

    public BattleResultPopup(GameController controller) {
        BattleResult battleResult = controller.getBattleResult();

        ImageIcon resultIcon = getBattleResultIcon(battleResult);
        JOptionPane.showMessageDialog(
            this.panel,
            controller.getBattleLog().stream().reduce("", (acc, line) -> acc + line + "\n"),
            "Battle Results",
            JOptionPane.INFORMATION_MESSAGE,
            resultIcon
        );

        if (battleResult == BattleResult.WIN) {
            if (controller.isBattleProduceArtifact()) {
                controller.setGamePhase(Phases.BATTLE_ARTIFACT);
                ArtifactPopup artifactPopup = new ArtifactPopup(controller);
            }
            controller.collectBattleExperience();
        }
    }

    private ImageIcon getBattleResultIcon(BattleResult battleResult) {
        ImageIcon icon = null;
        if (battleResult == BattleResult.WIN) {
            icon = new ImageIcon(getClass().getResource("/images/battle_won.png"));
        } else if (battleResult == BattleResult.LOSE) {
            icon = new ImageIcon(getClass().getResource("/images/battle_lost.png"));
        }
        Image scaled = icon.getImage().getScaledInstance(
            150, 150, Image.SCALE_SMOOTH
        );
        return new ImageIcon(scaled);
    }
}
