package com.swingy.view.gui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.Test;

import com.swingy.controller.actions.ExitAction;

class MainMenuPanelTest {

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    @Test
    void constructor_createsPanel() throws Exception {

        ExitAction exitAction = createExitAction();

        MainMenuPanel panel = createPanel(exitAction);

        assertNotNull(panel);
    }

    @Test
    void constructor_createsThreeButtons() throws Exception {

        ExitAction exitAction = createExitAction();

        MainMenuPanel panel = createPanel(exitAction);

        List<JButton> buttons = findButtons(panel);

        assertEquals(3, buttons.size());
    }

    // ============================================================
    // NEW GAME BUTTON
    // ============================================================

    @Test
    void newGameButton_hasCorrectText() throws Exception {

        ExitAction exitAction = createExitAction();

        MainMenuPanel panel = createPanel(exitAction);

        JButton button =
            findButton(panel, "Create new hero");

        assertNotNull(button);

        assertEquals(
            "Create new hero",
            button.getText()
        );
    }

    // ============================================================
    // LOAD HERO BUTTON
    // ============================================================

    @Test
    void loadHeroesButton_hasCorrectText() throws Exception {

        ExitAction exitAction = createExitAction();

        MainMenuPanel panel = createPanel(exitAction);

        JButton button =
            findButton(panel, "Load hero");

        assertNotNull(button);

        assertEquals(
            "Load hero",
            button.getText()
        );
    }

    // ============================================================
    // EXIT BUTTON
    // ============================================================

    @Test
    void exitButton_usesProvidedExitAction()
        throws Exception {

        ExitAction exitAction =
            createExitAction();

        MainMenuPanel panel =
            createPanel(exitAction);

        JButton exitButton =
            findButtonWithAction(
                panel,
                exitAction
            );

        assertNotNull(exitButton);

        assertSame(
            exitAction,
            exitButton.getAction()
        );
    }

    @Test
    void exitButton_hasCorrectText()
        throws Exception {

        ExitAction exitAction =
            createExitAction();

        MainMenuPanel panel =
            createPanel(exitAction);

        JButton exitButton =
            findButtonWithAction(
                panel,
                exitAction
            );

        assertNotNull(exitButton);

        /*
         * The button text comes from Action.NAME.
         */
        assertEquals(
            "Exit",
            exitButton.getText()
        );
    }

    // ============================================================
    // NEW GAME LISTENER
    // ============================================================

    @Test
    void addNewGameListener_listenerIsCalled()
        throws Exception {

        ExitAction exitAction =
            createExitAction();

        MainMenuPanel panel =
            createPanel(exitAction);

        ActionListener listener =
            mock(ActionListener.class);

        panel.addNewGameListener(listener);

        JButton newGameButton =
            findButton(
                panel,
                "Create new hero"
            );

        assertNotNull(newGameButton);

        SwingUtilities.invokeAndWait(
            newGameButton::doClick
        );

        verify(listener).actionPerformed(
            any(ActionEvent.class)
        );
    }

    // ============================================================
    // LOAD HERO LISTENER
    // ============================================================

    @Test
    void addLoadHeroesButtonListener_listenerIsCalled()
        throws Exception {

        ExitAction exitAction =
            createExitAction();

        MainMenuPanel panel =
            createPanel(exitAction);

        ActionListener listener =
            mock(ActionListener.class);

        panel.addLoadHeroesButtonListener(listener);

        JButton loadButton =
            findButton(
                panel,
                "Load hero"
            );

        assertNotNull(loadButton);

        SwingUtilities.invokeAndWait(
            loadButton::doClick
        );

        verify(listener).actionPerformed(
            any(ActionEvent.class)
        );
    }

    // ============================================================
    // EXIT LISTENER
    // ============================================================

    @Test
    void addExitListener_registersListener()
        throws Exception {

        ExitAction exitAction =
            createExitAction();

        MainMenuPanel panel =
            createPanel(exitAction);

        ActionListener listener =
            mock(ActionListener.class);

        panel.addExitListener(listener);

        JButton exitButton =
            findButtonWithAction(
                panel,
                exitAction
            );

        assertNotNull(exitButton);

        ActionListener[] listeners =
            exitButton.getActionListeners();

        assertTrue(
            containsListener(listeners, listener)
        );
    }

    // ============================================================
    // BUTTON SIZE
    // ============================================================

    @Test
    void buttons_haveExpectedPreferredSize()
        throws Exception {

        ExitAction exitAction =
            createExitAction();

        MainMenuPanel panel =
            createPanel(exitAction);

        List<JButton> buttons =
            findButtons(panel);

        assertEquals(3, buttons.size());

        for (JButton button : buttons) {

            Dimension size =
                button.getPreferredSize();

            assertEquals(
                240,
                size.width
            );

            assertEquals(
                40,
                size.height
            );
        }
    }

    // ============================================================
    // BUTTON ALIGNMENT
    // ============================================================

    @Test
    void buttons_areCentered()
        throws Exception {

        ExitAction exitAction =
            createExitAction();

        MainMenuPanel panel =
            createPanel(exitAction);

        List<JButton> buttons =
            findButtons(panel);

        assertEquals(3, buttons.size());

        for (JButton button : buttons) {

            assertEquals(
                Component.CENTER_ALIGNMENT,
                button.getAlignmentX(),
                0.001
            );
        }
    }

    // ============================================================
    // HELPER: CREATE EXIT ACTION
    // ============================================================

    private ExitAction createExitAction() {

        ExitAction exitAction =
            mock(ExitAction.class);

        /*
         * JButton(ExitAction) uses Action.NAME
         * as the button's text.
         */
        when(
            exitAction.getValue(Action.NAME)
        ).thenReturn("Exit");

        return exitAction;
    }

    // ============================================================
    // HELPER: CREATE PANEL
    // ============================================================

    private MainMenuPanel createPanel(
        ExitAction exitAction
    ) throws Exception {

        MainMenuPanel[] result =
            new MainMenuPanel[1];

        SwingUtilities.invokeAndWait(() -> {

            result[0] =
                new MainMenuPanel(exitAction);
        });

        return result[0];
    }

    // ============================================================
    // HELPER: FIND BUTTONS
    // ============================================================

    private List<JButton> findButtons(
        Container container
    ) {

        List<JButton> buttons =
            new ArrayList<>();

        for (Component component :
                container.getComponents()) {

            if (component instanceof JButton) {

                buttons.add(
                    (JButton) component
                );
            }

            if (component instanceof Container) {

                buttons.addAll(
                    findButtons(
                        (Container) component
                    )
                );
            }
        }

        return buttons;
    }

    // ============================================================
    // HELPER: FIND BUTTON BY TEXT
    // ============================================================

    private JButton findButton(
        Container container,
        String text
    ) {

        for (Component component :
                container.getComponents()) {

            if (component instanceof JButton) {

                JButton button =
                    (JButton) component;

                if (text.equals(button.getText())) {
                    return button;
                }
            }

            if (component instanceof Container) {

                JButton button =
                    findButton(
                        (Container) component,
                        text
                    );

                if (button != null) {
                    return button;
                }
            }
        }

        return null;
    }

    // ============================================================
    // HELPER: FIND BUTTON BY ACTION
    // ============================================================

    private JButton findButtonWithAction(
        Container container,
        Action action
    ) {

        for (Component component :
                container.getComponents()) {

            if (component instanceof JButton) {

                JButton button =
                    (JButton) component;

                if (button.getAction() == action) {
                    return button;
                }
            }

            if (component instanceof Container) {

                JButton button =
                    findButtonWithAction(
                        (Container) component,
                        action
                    );

                if (button != null) {
                    return button;
                }
            }
        }

        return null;
    }

    private boolean containsListener(
    ActionListener[] listeners,
    ActionListener expected
    ) {
        for (ActionListener listener : listeners) {
            if (listener == expected) {
                return true;
            }
        }

        return false;
    }
}
