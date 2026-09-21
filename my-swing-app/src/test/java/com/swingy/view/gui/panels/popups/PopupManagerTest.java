package com.swingy.view.gui.panels.popups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import com.swingy.controller.GameController;
import com.swingy.controller.Phases;
import com.swingy.view.gui.APopup;
import com.swingy.view.gui.ArtifactPopup;
import com.swingy.view.gui.BattleResultPopup;
import com.swingy.view.gui.BattleRunOrFightPopup;
import com.swingy.view.gui.BattleRunResultPopup;
import com.swingy.view.gui.GamePanel;
import com.swingy.view.gui.LevelUpPopup;
import com.swingy.view.gui.PopupManager;

@ExtendWith(MockitoExtension.class)
class PopupManagerTest {

    @Mock
    private GameController controller;

    @Mock
    private GamePanel gamePanel;

    private PopupManager popupManager;

    @BeforeEach
    void setUp() {
        popupManager = new PopupManager(
            controller,
            gamePanel
        );
    }

    // ============================================================
    // closeCurrentPopup()
    // ============================================================

    @Test
    void closeCurrentPopup_whenNoPopupExists_doesNothing() {

        popupManager.closeCurrentPopup();

        verifyNoInteractions(gamePanel);
        verifyNoInteractions(controller);
    }

    @Test
    void closeCurrentPopup_closesCurrentPopup() throws Exception {

        APopup popup = mock(APopup.class);

        Field field =
            PopupManager.class.getDeclaredField("currentPopup");

        field.setAccessible(true);
        field.set(popupManager, popup);

        popupManager.closeCurrentPopup();

        verify(popup).close();

        assertNull(field.get(popupManager));
    }

    // ============================================================
    // checkGameOver()
    // ============================================================

    @Test
    void checkGameOver_callsGamePanel() {

        popupManager.checkGameOver();

        verify(gamePanel).checkGameOver();
    }

    // ============================================================
    // next() - GAMEPLAY
    // ============================================================

    @Test
    void next_gameplay_repaintsGamePanel() {

        when(controller.getGamePhase())
            .thenReturn(Phases.GAMEPLAY);

        popupManager.next();

        verify(gamePanel).repaint();

        verify(gamePanel, never()).checkGameOver();
    }

    // ============================================================
    // next() - BATTLE_RUN_OR_FIGHT
    // ============================================================

    @Test
    void next_battleRunOrFight_createsPopup() {

        when(controller.getGamePhase())
            .thenReturn(Phases.BATTLE_RUN_OR_FIGHT);

        try (MockedConstruction<BattleRunOrFightPopup> mocked =
                 mockConstruction(BattleRunOrFightPopup.class)) {

            popupManager.next();

            assertEquals(
                1,
                mocked.constructed().size()
            );
        }
    }

    // ============================================================
    // next() - BATTLE_RUN_RESULT
    // ============================================================

    @Test
    void next_battleRunResult_createsPopupAndRepaints() {

        when(controller.getGamePhase())
            .thenReturn(Phases.BATTLE_RUN_RESULT);

        try (MockedConstruction<BattleRunResultPopup> mocked =
                 mockConstruction(BattleRunResultPopup.class)) {

            popupManager.next();

            assertEquals(
                1,
                mocked.constructed().size()
            );

            verify(gamePanel).repaint();
        }
    }

    // ============================================================
    // next() - BATTLE_RESULT
    // ============================================================

    @Test
    void next_battleResult_createsPopupAndRepaints() {

        when(controller.getGamePhase())
            .thenReturn(Phases.BATTLE_RESULT);

        try (MockedConstruction<BattleResultPopup> mocked =
                 mockConstruction(BattleResultPopup.class)) {

            popupManager.next();

            assertEquals(
                1,
                mocked.constructed().size()
            );

            verify(gamePanel).repaint();
        }
    }

    // ============================================================
    // next() - BATTLE_ARTIFACT
    // ============================================================

    @Test
    void next_battleArtifact_createsPopupAndRepaints() {

        when(controller.getGamePhase())
            .thenReturn(Phases.BATTLE_ARTIFACT);

        try (MockedConstruction<ArtifactPopup> mocked =
                 mockConstruction(ArtifactPopup.class)) {

            popupManager.next();

            assertEquals(
                1,
                mocked.constructed().size()
            );

            verify(gamePanel).repaint();
        }
    }

    // ============================================================
    // next() - HERO_LEVEL_UP
    // ============================================================

    @Test
    void nextHeroLevelUp_createsPopupAndRepaints() {

        when(controller.getGamePhase())
            .thenReturn(Phases.HERO_LEVEL_UP);

        try (MockedConstruction<LevelUpPopup> mocked =
                 mockConstruction(LevelUpPopup.class)) {

            popupManager.next();

            assertEquals(
                1,
                mocked.constructed().size()
            );

            verify(gamePanel).repaint();
        }
    }
}
