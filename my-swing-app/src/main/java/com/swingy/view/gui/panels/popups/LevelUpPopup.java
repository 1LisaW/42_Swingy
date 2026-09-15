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


public class LevelUpPopup extends APopup {

    private JDialog currentDialog;

    public LevelUpPopup(GameController controller) {
        super(controller);
        BattleResult battleResult = controller.getBattleResult();

        ImageIcon resultIcon = getBattleResultIcon(battleResult);
        JPanel panel = new JPanel();

        int heroLevel = controller.getHeroLevel();
        String message = heroLevel + " > " + (heroLevel + 1);
        JOptionPane optionPane = new JOptionPane(
            message,
            JOptionPane.INFORMATION_MESSAGE,
            JOptionPane.DEFAULT_OPTION,
            resultIcon
        );

        currentDialog = optionPane.createDialog(panel, "Level Up!");
        currentDialog.setModal(false);
        currentDialog.setVisible(true);

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
