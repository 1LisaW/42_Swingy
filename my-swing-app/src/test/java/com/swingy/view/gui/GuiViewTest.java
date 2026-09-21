package com.swingy.view.gui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.awt.KeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import com.swingy.controller.GameController;
import com.swingy.controller.Phases;
import com.swingy.model.Hero;
import com.swingy.model.HeroCredentials;
import com.swingy.view.ViewManager;



class GuiViewTest {

    // ============================================================
    // HELPER
    // ============================================================

    private List<Hero> emptyHeroes() {
        return new ArrayList<>();
    }

    /**
     * Creates a mocked MainFrame and makes sure that
     * getSelectHeroFromListPanel() returns a real/mock panel
     * instead of null.
     */
    private SelectHeroFromListPanel mockSelectPanel(
            MainFrame frame) {

        SelectHeroFromListPanel selectPanel =
            mock(SelectHeroFromListPanel.class);

        when(frame.getSelectHeroFromListPanel())
            .thenReturn(selectPanel);

        return selectPanel;
    }

    private void waitForSwing() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            // Wait until the EDT has processed pending events.
        });
    }

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    @Test
    void constructor_createsMainFrame()
        throws Exception {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        List<Hero> heroes =
            emptyHeroes();

        when(controller.getHeroes())
            .thenReturn(heroes);

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(MainFrame.class)) {

            MainFrame frame =
                mocked.constructed().isEmpty()
                    ? null
                    : mocked.constructed().get(0);

            /*
             * We need the mock configuration to exist before
             * GuiView constructor calls getSelectHeroFromListPanel().
             *
             * Therefore create the GuiView after configuring
             * the constructed MainFrame.
             */
            GuiView view;

            /*
             * mockConstruction can configure every newly
             * constructed MainFrame using a MockInitializer.
             *
             * This test is therefore handled below with the
             * dedicated initializer version.
             */
        }
    }

    @Test
    void constructor_loadsHeroesIntoSelectPanel() {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        List<Hero> heroes =
            new ArrayList<>();

        Hero hero =
            mock(Hero.class);

        heroes.add(hero);

        when(controller.getHeroes())
            .thenReturn(heroes);

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        SelectHeroFromListPanel selectPanel =
                            mock(
                                SelectHeroFromListPanel.class
                            );

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(selectPanel);
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            assertNotNull(view);

            assertEquals(
                1,
                mocked.constructed().size()
            );

            MainFrame frame =
                mocked.constructed().get(0);

            SelectHeroFromListPanel selectPanel =
                frame.getSelectHeroFromListPanel();

            verify(selectPanel)
                .updateHeroList(heroes);
        }
    }

    // ============================================================
    // START
    // ============================================================

    @Test
    void start_makesFrameVisible() {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            MainFrame frame =
                mocked.constructed().get(0);

            view.start();

            verify(frame)
                .setVisible(true);
        }
    }

    // ============================================================
    // DISPLAY MAIN MENU
    // ============================================================

    @Test
    void displayMainMenu_showsMenuPanel() {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            MainFrame frame =
                mocked.constructed().get(0);

            view.displayMainMenu();

            verify(frame)
                .showPanel("MENU");
        }
    }

    // ============================================================
    // DISPLAY HERO LIST
    // ============================================================

    @Test
    void displayChooseHeroFromList_updatesHeroList() {
    GameController gameController = mock(GameController.class);
    ViewManager viewManager = mock(ViewManager.class);

    List<Hero> heroes = new ArrayList<>();

    SelectHeroFromListPanel selectPanel =
            mock(SelectHeroFromListPanel.class);

    when(gameController.getHeroes()).thenReturn(heroes);

    try (MockedConstruction<MainFrame> mocked =
            mockConstruction(
                MainFrame.class,
                (mainFrame, context) -> {
                    when(mainFrame.getSelectHeroFromListPanel())
                            .thenReturn(selectPanel);
                })) {

            GuiView view = new GuiView(gameController, viewManager);

            // Ignore calls made by GuiView's constructor.
            clearInvocations(gameController, selectPanel);

            view.displayChooseHeroFromList();

            verify(gameController, times(1)).getHeroes();
            verify(selectPanel, times(1)).updateHeroList(heroes);
        }
    }

    // ============================================================
    // START GAME
    // ============================================================

    @Test
    void startGame_delegatesToController() {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        Hero hero =
            mock(Hero.class);

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            view.startGame(hero);

            verify(controller)
                .startGame(hero);
        }
    }

    // ============================================================
    // HERO CREDENTIALS
    // ============================================================

    @Test
    void createHeroCredentials_returnsNewCredentials() {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            HeroCredentials credentials =
                view.createHeroCredentials();

            assertNotNull(credentials);
            assertInstanceOf(
                HeroCredentials.class,
                credentials
            );
        }
    }

    // ============================================================
    // PROMPT METHODS
    // ============================================================

    @Test
    void promptMainMenu_returnsOne() {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            assertEquals(
                1,
                view.promptMainMenu()
            );
        }
    }

    @Test
    void promptBattleFightOrRun_returnsZero() {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            assertEquals(
                0,
                view.promptBattleFightOrRun()
            );
        }
    }

    @Test
    void promptChooseHeroFromList_returnsZero() {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            assertEquals(
                0,
                view.promptChooseHeroFromList(10)
            );
        }
    }

    @Test
    void promptUseArtifact_returnsZero() {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            assertEquals(
                0,
                view.promptUseArtifact()
            );
        }
    }

    @Test
    void getUserInput_returnsEmptyString() {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            assertEquals(
                "",
                view.getUserInput("Name")
            );
        }
    }

    @Test
    void promptHeroMove_returnsEmptyString() {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            assertEquals(
                "",
                view.promptHeroMove()
            );
        }
    }

    // ============================================================
    // SHOW - MAIN MENU
    // ============================================================

    @Test
    void show_mainMenu_showsMenuPanel()
        throws Exception {

        testShowPhase(
            Phases.MAIN_MENU,
            "MENU"
        );
    }

    // ============================================================
    // SHOW - HERO CREATION
    // ============================================================

    @Test
    void show_heroCreation_showsCreatePanel()
        throws Exception {

        testShowPhase(
            Phases.HERO_CREATION,
            "CREATE"
        );
    }

    // ============================================================
    // SHOW - HERO SELECTION
    // ============================================================

    @Test
    void show_heroSelection_showsSelectPanel()
        throws Exception {

        testShowPhase(
            Phases.HERO_SELECTION,
            "SELECT"
        );
    }

    // ============================================================
    // SHOW - GAMEPLAY
    // ============================================================

    @Test
    void show_gameplay_showsGamePanel()
        throws Exception {

        testShowPhase(
            Phases.GAMEPLAY,
            "GAME"
        );
    }

    // ============================================================
    // SHOW - BATTLE PHASES
    // ============================================================

    @Test
    void show_battleRunOrFight_showsGamePanel()
        throws Exception {

        testShowPhase(
            Phases.BATTLE_RUN_OR_FIGHT,
            "GAME"
        );
    }

    @Test
    void show_battleRunResult_showsGamePanel()
        throws Exception {

        testShowPhase(
            Phases.BATTLE_RUN_RESULT,
            "GAME"
        );
    }

    @Test
    void show_battleResult_showsGamePanel()
        throws Exception {

        testShowPhase(
            Phases.BATTLE_RESULT,
            "GAME"
        );
    }

    @Test
    void show_battleArtifact_showsGamePanel()
        throws Exception {

        testShowPhase(
            Phases.BATTLE_ARTIFACT,
            "GAME"
        );
    }

    @Test
    void show_heroLevelUp_showsGamePanel()
        throws Exception {

        testShowPhase(
            Phases.HERO_LEVEL_UP,
            "GAME"
        );
    }

    // ============================================================
    // SHOW - GAME OVER WON
    // ============================================================

    @Test
    void show_gameOverWon_showsWonPanel()
        throws Exception {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        when(controller.getGamePhase())
            .thenReturn(Phases.GAME_OVER);

        when(controller.levelCleared())
            .thenReturn(true);

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            MainFrame frame =
                mocked.constructed().get(0);

            view.show();

            waitForSwing();

            verify(frame)
                .showPanel("GAME_OVER_WON");

            verify(frame)
                .setVisible(true);

            verify(frame)
                .requestFocus();

            verify(frame)
                .showCurrentPopup();
        }
    }

    // ============================================================
    // SHOW - GAME OVER LOST
    // ============================================================

    @Test
    void show_gameOverLost_showsLostPanel()
        throws Exception {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        when(controller.getGamePhase())
            .thenReturn(Phases.GAME_OVER);

        when(controller.levelCleared())
            .thenReturn(false);

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            MainFrame frame =
                mocked.constructed().get(0);

            view.show();

            waitForSwing();

            verify(frame)
                .showPanel("GAME_OVER_LOST");

            verify(frame)
                .setVisible(true);

            verify(frame)
                .requestFocus();

            verify(frame)
                .showCurrentPopup();
        }
    }

    // ============================================================
    // SHOW - DEFAULT
    // ============================================================

    @Test
    void show_nullPhase_showsMenuPanel() throws Exception {
        GameController gameController = mock(GameController.class);
        ViewManager viewManager = mock(ViewManager.class);

        SelectHeroFromListPanel selectPanel =
                mock(SelectHeroFromListPanel.class);

        when(gameController.getHeroes())
                .thenReturn(Collections.emptyList());

        when(gameController.getGamePhase())
                .thenReturn(null);

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (mainFrame, context) -> {
                        when(mainFrame.getSelectHeroFromListPanel())
                                .thenReturn(selectPanel);
                    })) {

            GuiView view = new GuiView(gameController, viewManager);

            view.show();

            // Wait for Swing's EDT.
            SwingUtilities.invokeAndWait(() -> {});

            MainFrame mainFrame = mocked.constructed().get(0);

            verify(mainFrame).showPanel("MENU");
            verify(mainFrame).setVisible(true);
            verify(mainFrame).requestFocus();
            verify(mainFrame).showCurrentPopup();
        }
    }


    // ============================================================
    // HIDE
    // ============================================================

    @Test
    void hide_hidesFrameAndPopup()
        throws Exception {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            MainFrame frame =
                mocked.constructed().get(0);

            view.hide();

            waitForSwing();

            verify(frame)
                .hideActivePopup();

            verify(frame)
                .setVisible(false);
        }
    }

    // ============================================================
    // CTRL + C
    // ============================================================

    @Test
    void ctrlC_nonTextComponent_switchesToConsole() {
        GameController gameController = mock(GameController.class);
        ViewManager viewManager = mock(ViewManager.class);

        SelectHeroFromListPanel selectPanel =
                mock(SelectHeroFromListPanel.class);

        when(gameController.getHeroes())
                .thenReturn(Collections.emptyList());

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (mainFrame, context) -> {
                        when(mainFrame.getSelectHeroFromListPanel())
                                .thenReturn(selectPanel);
                    })) {

            GuiView view = new GuiView(gameController, viewManager);

            KeyEventDispatcher dispatcher =
                    view.getGlobalKeyDispatcherForTest();

            JPanel source = new JPanel();

            KeyEvent event = new KeyEvent(
                    source,
                    KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(),
                    InputEvent.CTRL_DOWN_MASK,
                    KeyEvent.VK_C,
                    'C'
            );

            boolean consumed = dispatcher.dispatchKeyEvent(event);

            assertTrue(consumed);

            verify(viewManager).switchToConsole();
        }
    }


    @Test
    void ctrlC_textComponent_doesNotSwitchToConsole()
        throws Exception {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            JTextField textField =
                new JTextField();

            KeyEvent event =
                new KeyEvent(
                    textField,
                    KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(),
                    InputEvent.CTRL_DOWN_MASK,
                    KeyEvent.VK_C,
                    'C'
                );

            dispatchKeyEvent(event);

            verify(
                viewManager,
                never()
            ).switchToConsole();
        }
    }

    @Test
    void nonCtrlC_doesNotSwitchToConsole()
        throws Exception {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            JButton button =
                new JButton();

            KeyEvent event =
                new KeyEvent(
                    button,
                    KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(),
                    0,
                    KeyEvent.VK_C,
                    'C'
                );

            dispatchKeyEvent(event);

            verify(
                viewManager,
                never()
            ).switchToConsole();
        }
    }

    // ============================================================
    // HELPERS
    // ============================================================

    private void testShowPhase(
        Phases phase,
        String expectedPanel
    ) throws Exception {

        GameController controller =
            mock(GameController.class);

        ViewManager viewManager =
            mock(ViewManager.class);

        when(controller.getHeroes())
            .thenReturn(emptyHeroes());

        when(controller.getGamePhase())
            .thenReturn(phase);

        try (MockedConstruction<MainFrame> mocked =
                mockConstruction(
                    MainFrame.class,
                    (frame, context) -> {

                        when(frame
                            .getSelectHeroFromListPanel())
                            .thenReturn(
                                mock(
                                    SelectHeroFromListPanel.class
                                )
                            );
                    })) {

            GuiView view =
                new GuiView(
                    controller,
                    viewManager
                );

            MainFrame frame =
                mocked.constructed().get(0);

            view.show();

            waitForSwing();

            verify(frame)
                .showPanel(expectedPanel);

            verify(frame)
                .setVisible(true);

            verify(frame)
                .requestFocus();

            verify(frame)
                .showCurrentPopup();
        }
    }

    private void dispatchKeyEvent(
        KeyEvent event
    ) {

        KeyboardFocusManager manager =
            KeyboardFocusManager
                .getCurrentKeyboardFocusManager();

        manager.dispatchKeyEvent(event);
    }
}
