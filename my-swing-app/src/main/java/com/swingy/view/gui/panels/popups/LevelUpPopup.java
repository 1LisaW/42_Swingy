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

    private static final ImageIcon LEVEL_UP_ICON = createLevelUpIcon();

    private static ImageIcon createLevelUpIcon() {
        ImageIcon icon = new ImageIcon(
            LevelUpPopup.class.getResource("/images/level_up.png")
        );

        Image scaled = icon.getImage().getScaledInstance(
            150, 150, Image.SCALE_SMOOTH
        );

        return new ImageIcon(scaled);
    }

    public LevelUpPopup(GameController controller, PopupManager popupManager) {
        super(controller, popupManager);

        // ImageIcon resultIcon = getLevelUpIcon();
        JPanel panel = new JPanel();

        int heroLevel = controller.getHeroLevel();
        Object[] options = {"OK"};
        String message = heroLevel - 1 + " > " + (heroLevel);

        JOptionPane optionPane = new JOptionPane(
            message,
            JOptionPane.INFORMATION_MESSAGE,
            JOptionPane.DEFAULT_OPTION,
            LEVEL_UP_ICON,
            options,
            options[0]
        );

        currentDialog = optionPane.createDialog(panel, "Level Up!");
        currentDialog.setModal(false);

        optionPane.addPropertyChangeListener(e -> {

            if (e.getPropertyName().equals(JOptionPane.VALUE_PROPERTY)) {
                Object value = optionPane.getValue();
                if (options[0].equals(value)) {
                    currentDialog.dispose();
                    onAccept();
                }
            }
        });
        currentDialog.setVisible(true);
    }

    private ImageIcon getLevelUpIcon() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/level_up.png"));
        Image scaled = icon.getImage().getScaledInstance(
            150, 150, Image.SCALE_SMOOTH
        );
        return new ImageIcon(scaled);
    }

    private void onAccept() {
        controller.setGamePhase(Phases.GAMEPLAY);
        popupManager.checkGameOver();
    }

}
