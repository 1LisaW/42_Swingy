package com.swingy.view.gui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.Test;

class GameOverPanelTest {

    // ============================================================
    // WIN STATE
    // ============================================================

    @Test
    void winPanel_displaysCorrectTitle() throws Exception {

        GameOverPanel panel = createPanel(
            true,
            null,
            null,
            null,
            null
        );

        JLabel title = findLabel(panel, "YOU WIN!");

        assertNotNull(title);
        assertEquals("YOU WIN!", title.getText());
    }

    @Test
    void winPanel_displaysCorrectMessage() throws Exception {

        GameOverPanel panel = createPanel(
            true,
            null,
            null,
            null,
            null
        );

        JLabel message =
            findLabel(panel, "Congratulations! You did it!");

        assertNotNull(message);
        assertEquals(
            "Congratulations! You did it!",
            message.getText()
        );
    }

    @Test
    void winPanel_containsFourButtons() throws Exception {

        GameOverPanel panel = createPanel(
            true,
            null,
            null,
            null,
            null
        );

        List<JButton> buttons = findButtons(panel);

        assertEquals(4, buttons.size());

        assertTrue(hasButton(buttons, "Replay"));
        assertTrue(hasButton(buttons, "Continue"));
        assertTrue(hasButton(buttons, "Save & Continue"));
        assertTrue(hasButton(buttons, "Exit"));
    }

    // ============================================================
    // LOSS STATE
    // ============================================================

    @Test
    void lossPanel_displaysCorrectTitle() throws Exception {

        GameOverPanel panel = createPanel(
            false,
            null,
            null,
            null,
            null
        );

        JLabel title = findLabel(panel, "GAME OVER");

        assertNotNull(title);
        assertEquals("GAME OVER", title.getText());
    }

    @Test
    void lossPanel_displaysCorrectMessage() throws Exception {

        GameOverPanel panel = createPanel(
            false,
            null,
            null,
            null,
            null
        );

        JLabel message =
            findLabel(panel, "Better luck next time!");

        assertNotNull(message);
        assertEquals(
            "Better luck next time!",
            message.getText()
        );
    }

    @Test
    void lossPanel_containsOnlyReplayAndExitButtons()
        throws Exception {

        GameOverPanel panel = createPanel(
            false,
            null,
            null,
            null,
            null
        );

        List<JButton> buttons = findButtons(panel);

        assertEquals(2, buttons.size());

        assertTrue(hasButton(buttons, "Replay"));
        assertTrue(hasButton(buttons, "Exit"));

        assertFalse(hasButton(buttons, "Continue"));
        assertFalse(hasButton(buttons, "Save & Continue"));
    }

    // ============================================================
    // REPLAY BUTTON
    // ============================================================

    @Test
    void replayButton_callsOnRestart() throws Exception {

        Runnable onRestart = mock(Runnable.class);

        GameOverPanel panel = createPanel(
            true,
            onRestart,
            null,
            null,
            null
        );

        JButton replayButton =
            findButton(panel, "Replay");

        assertNotNull(replayButton);

        SwingUtilities.invokeAndWait(
            replayButton::doClick
        );

        verify(onRestart).run();
    }

    // ============================================================
    // CONTINUE BUTTON
    // ============================================================

    @Test
    void continueButton_callsOnContinue() throws Exception {

        Runnable onContinue = mock(Runnable.class);

        GameOverPanel panel = createPanel(
            true,
            null,
            onContinue,
            null,
            null
        );

        JButton continueButton =
            findButton(panel, "Continue");

        assertNotNull(continueButton);

        SwingUtilities.invokeAndWait(
            continueButton::doClick
        );

        verify(onContinue).run();
    }

    // ============================================================
    // SAVE & CONTINUE BUTTON
    // ============================================================

    @Test
    void saveAndContinueButton_callsCorrectRunnable()
        throws Exception {

        Runnable onSaveAndContinue =
            mock(Runnable.class);

        GameOverPanel panel = createPanel(
            true,
            null,
            null,
            onSaveAndContinue,
            null
        );

        JButton saveButton =
            findButton(panel, "Save & Continue");

        assertNotNull(saveButton);

        SwingUtilities.invokeAndWait(
            saveButton::doClick
        );

        verify(onSaveAndContinue).run();
    }

    // ============================================================
    // EXIT BUTTON
    // ============================================================

    @Test
    void exitButton_callsOnExit() throws Exception {

        Runnable onExit = mock(Runnable.class);

        GameOverPanel panel = createPanel(
            true,
            null,
            null,
            null,
            onExit
        );

        JButton exitButton =
            findButton(panel, "Exit");

        assertNotNull(exitButton);

        SwingUtilities.invokeAndWait(
            exitButton::doClick
        );

        verify(onExit).run();
    }

    // ============================================================
    // LOSS REPLAY BUTTON
    // ============================================================

    @Test
    void lossReplayButton_callsOnRestart()
        throws Exception {

        Runnable onRestart = mock(Runnable.class);

        GameOverPanel panel = createPanel(
            false,
            onRestart,
            null,
            null,
            null
        );

        JButton replayButton =
            findButton(panel, "Replay");

        assertNotNull(replayButton);

        SwingUtilities.invokeAndWait(
            replayButton::doClick
        );

        verify(onRestart).run();
    }

    // ============================================================
    // LOSS EXIT BUTTON
    // ============================================================

    @Test
    void lossExitButton_callsOnExit()
        throws Exception {

        Runnable onExit = mock(Runnable.class);

        GameOverPanel panel = createPanel(
            false,
            null,
            null,
            null,
            onExit
        );

        JButton exitButton =
            findButton(panel, "Exit");

        assertNotNull(exitButton);

        SwingUtilities.invokeAndWait(
            exitButton::doClick
        );

        verify(onExit).run();
    }

    // ============================================================
    // NULL CALLBACKS
    // ============================================================

    @Test
    void winPanel_nullCallbacks_doNotThrow()
        throws Exception {

        GameOverPanel panel = createPanel(
            true,
            null,
            null,
            null,
            null
        );

        JButton replay =
            findButton(panel, "Replay");

        JButton continueButton =
            findButton(panel, "Continue");

        JButton save =
            findButton(panel, "Save & Continue");

        JButton exit =
            findButton(panel, "Exit");

        assertDoesNotThrow(() -> {
            SwingUtilities.invokeAndWait(replay::doClick);
            SwingUtilities.invokeAndWait(
                continueButton::doClick
            );
            SwingUtilities.invokeAndWait(save::doClick);
            SwingUtilities.invokeAndWait(exit::doClick);
        });
    }

    @Test
    void lossPanel_nullCallbacks_doNotThrow()
        throws Exception {

        GameOverPanel panel = createPanel(
            false,
            null,
            null,
            null,
            null
        );

        JButton replay =
            findButton(panel, "Replay");

        JButton exit =
            findButton(panel, "Exit");

        assertDoesNotThrow(() -> {
            SwingUtilities.invokeAndWait(replay::doClick);
            SwingUtilities.invokeAndWait(exit::doClick);
        });
    }

    // ============================================================
    // HELPER METHODS
    // ============================================================

    /**
     * Creates the GameOverPanel on the Swing Event Dispatch Thread.
     */
    private GameOverPanel createPanel(
        boolean won,
        Runnable onRestart,
        Runnable onContinue,
        Runnable onSaveAndContinue,
        Runnable onExit
    ) throws Exception {

        final GameOverPanel[] result =
            new GameOverPanel[1];

        SwingUtilities.invokeAndWait(() -> {
            result[0] = new GameOverPanel(
                won,
                onRestart,
                onContinue,
                onSaveAndContinue,
                onExit
            );
        });

        return result[0];
    }

    /**
     * Finds all JButton components recursively.
     */
    private List<JButton> findButtons(
        Container container
    ) {

        List<JButton> buttons =
            new ArrayList<>();

        for (Component component :
                container.getComponents()) {

            if (component instanceof JButton) {
                buttons.add((JButton) component);
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

    /**
     * Finds a JButton by its text.
     */
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

    /**
     * Finds a JLabel by its text.
     */
    private JLabel findLabel(
        Container container,
        String text
    ) {

        for (Component component :
                container.getComponents()) {

            if (component instanceof JLabel) {

                JLabel label =
                    (JLabel) component;

                if (text.equals(label.getText())) {
                    return label;
                }
            }

            if (component instanceof Container) {

                JLabel label =
                    findLabel(
                        (Container) component,
                        text
                    );

                if (label != null) {
                    return label;
                }
            }
        }

        return null;
    }

    /**
     * Checks whether a button with the specified
     * text exists.
     */
    private boolean hasButton(
        List<JButton> buttons,
        String text
    ) {

        for (JButton button : buttons) {
            if (text.equals(button.getText())) {
                return true;
            }
        }

        return false;
    }
}
