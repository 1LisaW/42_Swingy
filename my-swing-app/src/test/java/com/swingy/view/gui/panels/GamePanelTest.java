package com.swingy.view.gui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.swingy.controller.GameController;
import com.swingy.controller.Phases;
import com.swingy.controller.actions.GameOverLostPanelAction;
import com.swingy.controller.actions.GameOverWonPanelAction;
import com.swingy.model.BattleResult;

@ExtendWith(MockitoExtension.class)
class GamePanelTest {

    @Mock
    private GameController controller;

    @Mock
    private GameOverWonPanelAction gameOverWonPanelAction;

    @Mock
    private GameOverLostPanelAction gameOverLostPanelAction;

    private GamePanel gamePanel;

    @BeforeEach
    void setUp() throws Exception {

        /*
         * GamePanel loads image resources in its field initializers,
         * so make sure the corresponding resources exist in:
         *
         * src/main/resources/images/villain/
         *
         * goblin.png
         * orc.png
         * golem.png
         */
        gamePanel = createGamePanel();
    }

    /**
     * Creates GamePanel on Swing's Event Dispatch Thread.
     */
    private GamePanel createGamePanel() throws Exception {

        GamePanel[] result = new GamePanel[1];

        SwingUtilities.invokeAndWait(() -> {
            result[0] = new GamePanel(
                gameOverWonPanelAction,
                gameOverLostPanelAction,
                controller
            );
        });

        return result[0];
    }

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    @Test
    void constructor_createsPanel() {

        assertNotNull(gamePanel);
    }

    @Test
    void constructor_panelIsFocusable() {

        assertTrue(gamePanel.isFocusable());
    }

    @Test
    void constructor_hasCorrectLayout() {

        assertTrue(
            gamePanel.getLayout()
                instanceof java.awt.BorderLayout
        );
    }

    // ============================================================
    // checkGameOver() - WIN
    // ============================================================

    @Test
    void checkGameOver_whenWon_setsGameOverPhase()
        throws Exception {

        when(controller.isGameOver())
            .thenReturn(true);

        when(controller.getBattleResult())
            .thenReturn(BattleResult.WIN);

        gamePanel.checkGameOver();

        verify(controller).setGamePhase(
            Phases.GAME_OVER
        );
    }

    @Test
    void checkGameOver_whenWon_callsWonAction()
        throws Exception {

        when(controller.isGameOver())
            .thenReturn(true);

        when(controller.getBattleResult())
            .thenReturn(BattleResult.WIN);

        gamePanel.checkGameOver();

        verify(gameOverWonPanelAction)
            .actionPerformed(null);

        verify(gameOverLostPanelAction, never())
            .actionPerformed(any());
    }

    // ============================================================
    // checkGameOver() - LOSE
    // ============================================================

    @Test
    void checkGameOver_whenLost_setsGameOverPhase()
        throws Exception {

        when(controller.isGameOver())
            .thenReturn(true);

        when(controller.getBattleResult())
            .thenReturn(BattleResult.LOSE);

        gamePanel.checkGameOver();

        verify(controller).setGamePhase(
            Phases.GAME_OVER
        );
    }

    @Test
    void checkGameOver_whenLost_callsLostAction()
        throws Exception {

        when(controller.isGameOver())
            .thenReturn(true);

        when(controller.getBattleResult())
            .thenReturn(BattleResult.LOSE);

        gamePanel.checkGameOver();

        verify(gameOverLostPanelAction)
            .actionPerformed(null);

        verify(gameOverWonPanelAction, never())
            .actionPerformed(any());
    }

    // ============================================================
    // checkGameOver() - GAME NOT OVER
    // ============================================================

    @Test
    void checkGameOver_whenGameIsNotOver_doesNothing() {

        when(controller.isGameOver())
            .thenReturn(false);

        gamePanel.checkGameOver();

        verify(controller, never())
            .setGamePhase(any());

        verify(gameOverWonPanelAction, never())
            .actionPerformed(any());

        verify(gameOverLostPanelAction, never())
            .actionPerformed(any());
    }

    // ============================================================
    // checkGameOver() - DRAW
    // ============================================================

    @Test
    void checkGameOver_whenBattleResultIsDraw_doesNothing() {

        when(controller.isGameOver())
            .thenReturn(true);

        when(controller.getBattleResult())
            .thenReturn(BattleResult.DRAW);

        gamePanel.checkGameOver();

        verify(controller, never())
            .setGamePhase(any());

        verify(gameOverWonPanelAction, never())
            .actionPerformed(any());

        verify(gameOverLostPanelAction, never())
            .actionPerformed(any());
    }

    // ============================================================
    // onHide()
    // ============================================================

    @Test
    void onHide_closesCurrentPopup() {

        /*
         * We cannot directly access popupManager because it is
         * private, so replace it with a mock.
         */
        PopupManager popupManager =
            mock(PopupManager.class);

        setField(
            gamePanel,
            "popupManager",
            popupManager
        );

        gamePanel.onHide();

        verify(popupManager)
            .closeCurrentPopup();
    }

    // ============================================================
    // showCurrentPopup()
    // ============================================================

    @Test
    void showCurrentPopup_callsPopupManagerNext() {

        PopupManager popupManager =
            mock(PopupManager.class);

        setField(
            gamePanel,
            "popupManager",
            popupManager
        );

        gamePanel.showCurrentPopup();

        verify(popupManager).next();
    }

    // ============================================================
    // KEY BINDINGS
    // ============================================================

    @Test
    void keyBindings_containsMovementKeys() {

        InputMap inputMap =
            gamePanel.getInputMap(
                JComponent.WHEN_IN_FOCUSED_WINDOW
            );

        assertEquals(
            "moveUp",
            inputMap.get(
                KeyStroke.getKeyStroke("W")
            )
        );

        assertEquals(
            "moveLeft",
            inputMap.get(
                KeyStroke.getKeyStroke("A")
            )
        );

        assertEquals(
            "moveDown",
            inputMap.get(
                KeyStroke.getKeyStroke("S")
            )
        );

        assertEquals(
            "moveRight",
            inputMap.get(
                KeyStroke.getKeyStroke("D")
            )
        );
    }

    // ============================================================
    // MOVEMENT ACTIONS
    // ============================================================

    @Test
    void moveUpAction_callsController() {

        invokeMovementAction("W");

        verify(controller).moveHero("up");
    }

    @Test
    void moveLeftAction_callsController() {

        invokeMovementAction("A");

        verify(controller).moveHero("left");
    }

    @Test
    void moveDownAction_callsController() {

        invokeMovementAction("S");

        verify(controller).moveHero("down");
    }

    @Test
    void moveRightAction_callsController() {

        invokeMovementAction("D");

        verify(controller).moveHero("right");
    }

    /**
     * Executes one of the W/A/S/D keyboard actions.
     */
    private void invokeMovementAction(String key) {

        InputMap inputMap =
            gamePanel.getInputMap(
                JComponent.WHEN_IN_FOCUSED_WINDOW
            );

        Object actionKey =
            inputMap.get(
                KeyStroke.getKeyStroke(key)
            );

        assertNotNull(actionKey);

        Action action =
            gamePanel.getActionMap()
                .get(actionKey);

        assertNotNull(action);

        ActionEvent event =
            new ActionEvent(
                gamePanel,
                ActionEvent.ACTION_PERFORMED,
                key
            );

        action.actionPerformed(event);
    }

    // ============================================================
    // REFLECTION HELPER
    // ============================================================

    /**
     * Changes a private field.
     *
     * Used only for replacing PopupManager with a mock.
     */
    private void setField(
        Object object,
        String fieldName,
        Object value
    ) {

        try {

            Class<?> currentClass =
                object.getClass();

            while (currentClass != null) {

                try {

                    Field field =
                        currentClass.getDeclaredField(
                            fieldName
                        );

                    field.setAccessible(true);
                    field.set(object, value);

                    return;

                } catch (NoSuchFieldException e) {

                    currentClass =
                        currentClass.getSuperclass();
                }
            }

            throw new RuntimeException(
                "Field not found: " + fieldName
            );

        } catch (Exception e) {

            throw new RuntimeException(
                "Could not set field: " + fieldName,
                e
            );
        }
    }
}
