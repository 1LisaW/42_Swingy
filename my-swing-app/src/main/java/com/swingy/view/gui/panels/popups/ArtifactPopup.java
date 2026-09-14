package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.swingy.controller.GameController;
import com.swingy.controller.Phases;

public class ArtifactPopup {

    public ArtifactPopup(GameController controller) {
        ImageIcon resultIcon = getArtifactIcon(controller.getBattleArtifactType());
        Object[] options = {"Use", "Drop"};
        JPanel panel = new JPanel();
        int choice = JOptionPane.showOptionDialog(
            panel,
            "You have obtained an " + controller.getBattleArtifactName() + "! What would you like to do?",
            "Artifact Acquired",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            resultIcon,
            options,
            options[0]
        );

        if (choice == JOptionPane.YES_OPTION) {
            controller.updateHeroArtifact();
            onChoice(controller);
            // Use the artifact
        } else {
            // Drop
            onChoice(controller);
        }
    }

    private void onChoice(GameController controller) {
        controller.setGamePhase(Phases.GAMEPLAY);
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

}
