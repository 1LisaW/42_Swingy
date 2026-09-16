package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.swingy.controller.GameController;
import com.swingy.controller.Phases;

import com.swingy.view.gui.APopup;
import com.swingy.view.gui.PopupManager;

public class ArtifactPopup extends APopup {

    public ArtifactPopup(GameController controller, PopupManager popupManager) {
        super(controller, popupManager);

        ImageIcon resultIcon = getArtifactIcon(controller.getBattleArtifactType());
        Object[] options = {"Use", "Drop"};
        String message = "You have obtained an " + controller.getBattleArtifactName() + "! What would you like to do?";
        JPanel panel = new JPanel();

        JOptionPane optionPane = new JOptionPane(
            message,
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.YES_NO_OPTION,
            resultIcon,
            options,
            options[0]
        );
        currentDialog = optionPane.createDialog(panel, "Artifact Acquired");

        currentDialog.setModal(false);

        optionPane.addPropertyChangeListener(e -> {
            if (JOptionPane.VALUE_PROPERTY.equals(e.getPropertyName())) {
                Object value = optionPane.getValue();

                if (options[0].equals(value)) {
                    currentDialog.dispose();
                    controller.updateHeroArtifact();
                    onChoice();
                } else if (options[1].equals(value)) {

                    currentDialog.dispose();
                    onChoice();
                }
            }
        });

        currentDialog.setVisible(true);
    }

    private ImageIcon getArtifactIcon(String artifactType) {
        if (artifactType == null) {
            return null;
        }
        String imagePath = "/images/artifact/" + artifactType.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image scaled = icon.getImage().getScaledInstance(
            150, 150, Image.SCALE_SMOOTH
        );
        return new ImageIcon(scaled);
    }

    private void onChoice() {
        if (checkLevelUp())
            controller.setGamePhase(Phases.HERO_LEVEL_UP);
        else {
            controller.setGamePhase(Phases.GAMEPLAY);
            popupManager.checkGameOver();
        }
        popupManager.next();
    }

    private boolean checkLevelUp() {
        int prevLevel = this.controller.getHeroLevel();
        controller.collectBattleExperience();
        int nextLevel = this.controller.getHeroLevel();
        return nextLevel > prevLevel;
    }

}
