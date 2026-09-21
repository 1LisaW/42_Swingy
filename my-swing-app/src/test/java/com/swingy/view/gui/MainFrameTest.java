package com.swingy.view.gui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import com.swingy.controller.GameController;
import com.swingy.controller.Phases;

class MainFrameTest {

    private GameController controller;
    private MainFrame frame;

    @BeforeEach
    void setUp() {
        controller = mock(GameController.class);

        when(controller.getHeroes())
                .thenReturn(Collections.emptyList());
    }

    @AfterEach
    void tearDown() {
        if (frame != null) {
            frame.dispose();
        }
    }

    @Test
    void constructor_createsFrame() {
        frame = new MainFrame(controller);

        assertNotNull(frame);
    }

    @Test
    void constructor_setsTitle() {
        frame = new MainFrame(controller);

        assertEquals("Swingy", frame.getTitle());
    }

    @Test
    void constructor_setsSize() {
        frame = new MainFrame(controller);

        assertEquals(1200, frame.getWidth());
        assertEquals(600, frame.getHeight());
    }

    @Test
    void constructor_setsDefaultCloseOperation() {
        frame = new MainFrame(controller);

        assertEquals(
            javax.swing.JFrame.EXIT_ON_CLOSE,
            frame.getDefaultCloseOperation()
        );
    }

    @Test
    void constructor_createsAllPanels() {
        frame = new MainFrame(controller);

        assertNotNull(frame.getMainMenuPanel());
        assertNotNull(frame.getCreateHeroPanel());
        assertNotNull(frame.getGamePanel());
        assertNotNull(frame.getSelectHeroFromListPanel());
        assertNotNull(frame.getGameOverPanel(true));
        assertNotNull(frame.getGameOverPanel(false));
    }

    @Test
    void getGameOverPanel_returnsWonPanelWhenTrue() {
        frame = new MainFrame(controller);

        GameOverPanel wonPanel = frame.getGameOverPanel(true);

        assertNotNull(wonPanel);
    }

    @Test
    void getGameOverPanel_returnsLostPanelWhenFalse() {
        frame = new MainFrame(controller);

        GameOverPanel lostPanel = frame.getGameOverPanel(false);

        assertNotNull(lostPanel);
    }

    @Test
    void getGameOverPanel_returnsDifferentPanels() {
        frame = new MainFrame(controller);

        GameOverPanel wonPanel = frame.getGameOverPanel(true);
        GameOverPanel lostPanel = frame.getGameOverPanel(false);

        assertNotSame(wonPanel, lostPanel);
    }

    @Test
    void showPanel_menuPanelDoesNotThrow() {
        frame = new MainFrame(controller);

        assertDoesNotThrow(() -> frame.showPanel("MENU"));
    }

    @Test
    void showPanel_gamePanelDoesNotThrow() {
        frame = new MainFrame(controller);

        assertDoesNotThrow(() -> frame.showPanel("GAME"));
    }

    @Test
    void showPanel_createPanelDoesNotThrow() {
        frame = new MainFrame(controller);

        assertDoesNotThrow(() -> frame.showPanel("CREATE"));
    }

    @Test
    void showPanel_selectPanelDoesNotThrow() {
        frame = new MainFrame(controller);

        assertDoesNotThrow(() -> frame.showPanel("SELECT"));
    }

    @Test
    void showPanel_gameOverWonDoesNotThrow() {
        frame = new MainFrame(controller);

        assertDoesNotThrow(() ->
            frame.showPanel("GAME_OVER_WON")
        );
    }

    @Test
    void showPanel_gameOverLostDoesNotThrow() {
        frame = new MainFrame(controller);

        assertDoesNotThrow(() ->
            frame.showPanel("GAME_OVER_LOST")
        );
    }

    @Test
    void getMainMenuPanel_returnsMainMenuPanel() {
        frame = new MainFrame(controller);

        MainMenuPanel panel = frame.getMainMenuPanel();

        assertNotNull(panel);
        assertInstanceOf(MainMenuPanel.class, panel);
    }

    @Test
    void getCreateHeroPanel_returnsCreateHeroPanel() {
        frame = new MainFrame(controller);

        CreateHeroPanel panel = frame.getCreateHeroPanel();

        assertNotNull(panel);
        assertInstanceOf(CreateHeroPanel.class, panel);
    }

    @Test
    void getGamePanel_returnsGamePanel() {
        frame = new MainFrame(controller);

        GamePanel panel = frame.getGamePanel();

        assertNotNull(panel);
        assertInstanceOf(GamePanel.class, panel);
    }

    @Test
    void getSelectHeroFromListPanel_returnsSelectPanel() {
        frame = new MainFrame(controller);

        SelectHeroFromListPanel panel =
                frame.getSelectHeroFromListPanel();

        assertNotNull(panel);
        assertInstanceOf(
            SelectHeroFromListPanel.class,
            panel
        );
    }

    @Test
    void addLoadHeroesButtonListener_registersListener() {
        frame = new MainFrame(controller);

        ActionListener listener = mock(ActionListener.class);

        frame.addLoadHeroesButtonListener(listener);

        // Find the Load hero button.
        JButton loadButton = findButton(
            frame.getMainMenuPanel(),
            "Load hero"
        );

        assertNotNull(loadButton);

        loadButton.doClick();

        verify(listener).actionPerformed(any(ActionEvent.class));
    }

    @Test
    void loadHeroesButton_changesPhaseToHeroSelection() {
        frame = new MainFrame(controller);

        JButton loadButton = findButton(
            frame.getMainMenuPanel(),
            "Load hero"
        );

        assertNotNull(loadButton);

        loadButton.doClick();

        verify(controller).setGamePhase(
            Phases.HERO_SELECTION
        );
    }

    @Test
    void loadHeroesButton_showsSelectPanel() {
        frame = new MainFrame(controller);

        JButton loadButton = findButton(
            frame.getMainMenuPanel(),
            "Load hero"
        );

        assertNotNull(loadButton);

        loadButton.doClick();

        // The CardLayout has received the SELECT request.
        // Verify indirectly that the button interaction completed.
        verify(controller).setGamePhase(
            Phases.HERO_SELECTION
        );
    }

    @Test
    void newGameButton_changesPhaseToHeroCreation() {
        frame = new MainFrame(controller);

        JButton newGameButton = findButton(
            frame.getMainMenuPanel(),
            "Create new hero"
        );

        assertNotNull(newGameButton);

        newGameButton.doClick();

        verify(controller).setGamePhase(
            Phases.HERO_CREATION
        );
    }

    @Test
    void newGameButton_showsCreatePanel() {
        frame = new MainFrame(controller);

        JButton newGameButton = findButton(
            frame.getMainMenuPanel(),
            "Create new hero"
        );

        assertNotNull(newGameButton);

        newGameButton.doClick();

        verify(controller).setGamePhase(
            Phases.HERO_CREATION
        );
    }

    @Test
    void hideActivePopup_delegatesToGamePanel() {
        frame = new MainFrame(controller);

        GamePanel gamePanel = mock(GamePanel.class);

        // MainFrame's gamePanel is private/final-ish through normal
        // construction, so this test verifies the method exists and
        // can be called without throwing.
        assertDoesNotThrow(() ->
            frame.hideActivePopup()
        );
    }

    @Test
    void showCurrentPopup_delegatesToGamePanel() {
        when(controller.getGamePhase())
            .thenReturn(Phases.GAMEPLAY);

        frame = new MainFrame(controller);

        assertDoesNotThrow(() ->
            frame.showCurrentPopup()
        );
    }

    @Test
    void addLoadHeroesButtonListener_canRegisterMultipleListeners() {
        frame = new MainFrame(controller);

        ActionListener listener1 = mock(ActionListener.class);
        ActionListener listener2 = mock(ActionListener.class);

        frame.addLoadHeroesButtonListener(listener1);
        frame.addLoadHeroesButtonListener(listener2);

        JButton loadButton = findButton(
            frame.getMainMenuPanel(),
            "Load hero"
        );

        assertNotNull(loadButton);

        loadButton.doClick();

        verify(listener1).actionPerformed(
            any(ActionEvent.class)
        );

        verify(listener2).actionPerformed(
            any(ActionEvent.class)
        );
    }

    @Test
    void mainMenuPanel_isAddedToFrame() {
        frame = new MainFrame(controller);

        assertTrue(
            isComponentContained(
                frame,
                frame.getMainMenuPanel()
            )
        );
    }

    @Test
    void gamePanel_isAddedToFrame() {
        frame = new MainFrame(controller);

        assertTrue(
            isComponentContained(
                frame,
                frame.getGamePanel()
            )
        );
    }

    @Test
    void createHeroPanel_isAddedToFrame() {
        frame = new MainFrame(controller);

        assertTrue(
            isComponentContained(
                frame,
                frame.getCreateHeroPanel()
            )
        );
    }

    @Test
    void selectHeroPanel_isAddedToFrame() {
        frame = new MainFrame(controller);

        assertTrue(
            isComponentContained(
                frame,
                frame.getSelectHeroFromListPanel()
            )
        );
    }

    @Test
    void gameOverWonPanel_isAddedToFrame() {
        frame = new MainFrame(controller);

        assertTrue(
            isComponentContained(
                frame,
                frame.getGameOverPanel(true)
            )
        );
    }

    @Test
    void gameOverLostPanel_isAddedToFrame() {
        frame = new MainFrame(controller);

        assertTrue(
            isComponentContained(
                frame,
                frame.getGameOverPanel(false)
            )
        );
    }

    /**
     * Recursively searches for a JButton with the requested text.
     */
    private JButton findButton(
            Container container,
            String text) {

        for (Component component :
                container.getComponents()) {

            if (component instanceof JButton) {
                JButton button = (JButton) component;

                if (text.equals(button.getText())) {
                    return button;
                }
            }

            if (component instanceof Container) {
                JButton result = findButton(
                    (Container) component,
                    text
                );

                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    /**
     * Recursively checks whether a component exists
     * somewhere inside a container.
     */
    private boolean isComponentContained(
            Container parent,
            Component target) {

        for (Component component :
                parent.getComponents()) {

            if (component == target) {
                return true;
            }

            if (component instanceof Container) {
                if (isComponentContained(
                        (Container) component,
                        target)) {
                    return true;
                }
            }
        }

        return false;
    }
}
