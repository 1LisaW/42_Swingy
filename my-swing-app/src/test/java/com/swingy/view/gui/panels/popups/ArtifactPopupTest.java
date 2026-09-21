package com.swingy.view.gui;

import com.swingy.controller.GameController;
import com.swingy.controller.Phases;
import com.swingy.model.Hero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArtifactPopupTest {

    private GameController controller;
    private PopupManager popupManager;


    @BeforeEach
    void setUp() {
        controller = mock(GameController.class);
        popupManager = mock(PopupManager.class);
    }

    @Test
    void shouldUseArtifact() throws Exception {

        when(controller.getBattleArtifactType())
                .thenReturn("weapon");

        when(controller.getBattleArtifactName())
                .thenReturn("Sword of Power");

        when(controller.getHeroLevel())
                .thenReturn(1, 1);

        ArtifactPopup[] popup = new ArtifactPopup[1];

        SwingUtilities.invokeAndWait(() -> {
            popup[0] = new ArtifactPopup(controller, popupManager);
        });

        SwingUtilities.invokeAndWait(() -> {
            popup[0].getOptionPane().setValue("Use");
        });

        verify(controller).updateHeroArtifact();
        verify(controller).collectBattleExperience();
        verify(controller).setGamePhase(Phases.GAMEPLAY);
        verify(popupManager).checkGameOver();
        verify(popupManager).next();
    }

    @Test
    void shouldDropArtifact() throws Exception {

        when(controller.getBattleArtifactType())
                .thenReturn("weapon");

        when(controller.getBattleArtifactName())
                .thenReturn("Sword of Power");

        when(controller.getHeroLevel())
                .thenReturn(1, 1);

        ArtifactPopup[] popup = new ArtifactPopup[1];

        SwingUtilities.invokeAndWait(() -> {
            popup[0] = new ArtifactPopup(controller, popupManager);
        });

        SwingUtilities.invokeAndWait(() -> {
            popup[0].getOptionPane().setValue("Drop");
        });

        verify(controller, never()).updateHeroArtifact();

        verify(controller).collectBattleExperience();
        verify(controller).setGamePhase(Phases.GAMEPLAY);
        verify(popupManager).checkGameOver();
        verify(popupManager).next();
    }

    @Test
    void shouldSwitchToLevelUpPhaseWhenHeroLevelsUp() throws Exception {

        when(controller.getBattleArtifactType())
                .thenReturn("weapon");

        when(controller.getBattleArtifactName())
                .thenReturn("Sword of Power");

        // First call = previous level
        // Second call = level after collecting XP
        when(controller.getHeroLevel())
                .thenReturn(1, 2);

        ArtifactPopup[] popup = new ArtifactPopup[1];

        SwingUtilities.invokeAndWait(() -> {
            popup[0] = new ArtifactPopup(controller, popupManager);
        });

        SwingUtilities.invokeAndWait(() -> {
            popup[0].getOptionPane().setValue("Use");
        });

        verify(controller).updateHeroArtifact();
        verify(controller).collectBattleExperience();

        verify(controller).setGamePhase(Phases.HERO_LEVEL_UP);

        verify(popupManager, never()).checkGameOver();
        verify(popupManager).next();
    }
}
