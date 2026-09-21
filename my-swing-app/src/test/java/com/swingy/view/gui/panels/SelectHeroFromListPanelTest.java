package com.swingy.view.gui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


import org.junit.jupiter.api.Test;

import com.swingy.controller.GameController;
import com.swingy.controller.Phases;
import com.swingy.model.Hero;

class SelectHeroFromListPanelTest {

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    @Test
    void constructor_createsPanel() throws Exception {

        Action mainMenuAction = mock(Action.class);
        Action gamePanelAction = mock(Action.class);
        GameController controller = mock(GameController.class);

        SelectHeroFromListPanel panel =
            createPanel(
                mainMenuAction,
                gamePanelAction,
                controller
            );

        assertNotNull(panel);
    }

    @Test
    void constructor_createsStartButton() throws Exception {

        SelectHeroFromListPanel panel =
            createPanel();

        JButton startButton =
            findButton(panel, "Start");

        assertNotNull(startButton);
        assertEquals("Start", startButton.getText());
    }

    // ============================================================
    // UPDATE HERO LIST
    // ============================================================

    @Test
    void updateHeroList_createsComboBox()
        throws Exception {

        SelectHeroFromListPanel panel =
            createPanel();

        Hero hero1 = mock(Hero.class);
        Hero hero2 = mock(Hero.class);

        when(hero1.getName())
            .thenReturn("Aragorn");

        when(hero1.getArchetype())
            .thenReturn("Warrior");

        when(hero1.getLevel())
            .thenReturn(5);

        when(hero2.getName())
            .thenReturn("Gandalf");

        when(hero2.getArchetype())
            .thenReturn("Mage");

        when(hero2.getLevel())
            .thenReturn(7);

        List<Hero> heroes =
            List.of(hero1, hero2);

        panel.updateHeroList(heroes);

        JComboBox<Hero> comboBox =
            findComboBox(panel);

        assertNotNull(comboBox);

        assertEquals(
            2,
            comboBox.getItemCount()
        );

        assertSame(
            hero1,
            comboBox.getItemAt(0)
        );

        assertSame(
            hero2,
            comboBox.getItemAt(1)
        );
    }

    @Test
    void updateHeroList_selectsFirstHeroByDefault()
        throws Exception {

        SelectHeroFromListPanel panel =
            createPanel();

        Hero hero1 = mock(Hero.class);
        Hero hero2 = mock(Hero.class);

        panel.updateHeroList(
            List.of(hero1, hero2)
        );

        JComboBox<Hero> comboBox =
            findComboBox(panel);

        assertNotNull(comboBox);

        assertSame(
            hero1,
            comboBox.getSelectedItem()
        );
    }

    // ============================================================
    // HERO INFORMATION
    // ============================================================

    @Test
    void updateHeroList_displaysHeroInformation()
        throws Exception {

        SelectHeroFromListPanel panel =
            createPanel();

        Hero hero = mock(Hero.class);

        when(hero.getExperience())
            .thenReturn(150);

        when(hero.getHitPoints())
            .thenReturn(100);

        when(hero.getAttack())
            .thenReturn(25);

        when(hero.getDefense())
            .thenReturn(15);

        panel.updateHeroList(
            List.of(hero)
        );

        JLabel infoLabel =
            findLabel(
                panel,
                "EXP: 150 | HP: 100 | ATK: 25 | DEF: 15"
            );

        assertNotNull(infoLabel);

        assertEquals(
            "EXP: 150 | HP: 100 | ATK: 25 | DEF: 15",
            infoLabel.getText()
        );
    }

    @Test
    void updateHeroList_emptyList_createsEmptyComboBox()
        throws Exception {

        SelectHeroFromListPanel panel =
            createPanel();

        panel.updateHeroList(
            new ArrayList<>()
        );

        JComboBox<Hero> comboBox =
            findComboBox(panel);

        assertNotNull(comboBox);

        assertEquals(
            0,
            comboBox.getItemCount()
        );
    }

    // ============================================================
    // HERO RENDERER
    // ============================================================

    @Test
    void heroRenderer_displaysHeroNameArchetypeAndLevel()
        throws Exception {
        SelectHeroFromListPanel panel = createPanel();

        Hero hero = mock(Hero.class);

        when(hero.getName()).thenReturn("Aragorn");
        when(hero.getArchetype()).thenReturn("Warrior");
        when(hero.getLevel()).thenReturn(5);

        panel.updateHeroList(List.of(hero));

        JComboBox<Hero> comboBox = findComboBox(panel);

        assertNotNull(comboBox);

        ListCellRenderer<? super Hero> renderer =
            comboBox.getRenderer();

        JList<Hero> list = new JList<>();

        Component component =
            renderer.getListCellRendererComponent(
                list,
                hero,
                0,
                false,
                false
            );

        assertInstanceOf(JLabel.class, component);

        JLabel label = (JLabel) component;

        assertEquals(
            "Aragorn (Warrior) | level: 5",
            label.getText()
        );
    }

    // ============================================================
    // SELECTING ANOTHER HERO
    // ============================================================

    @Test
    void selectingAnotherHero_updatesChosenHeroInformation()
        throws Exception {

        SelectHeroFromListPanel panel =
            createPanel();

        Hero hero1 = mock(Hero.class);
        Hero hero2 = mock(Hero.class);

        when(hero1.getExperience())
            .thenReturn(100);

        when(hero1.getHitPoints())
            .thenReturn(80);

        when(hero1.getAttack())
            .thenReturn(20);

        when(hero1.getDefense())
            .thenReturn(10);

        when(hero2.getExperience())
            .thenReturn(250);

        when(hero2.getHitPoints())
            .thenReturn(150);

        when(hero2.getAttack())
            .thenReturn(35);

        when(hero2.getDefense())
            .thenReturn(25);

        panel.updateHeroList(
            List.of(hero1, hero2)
        );

        JComboBox<Hero> comboBox =
            findComboBox(panel);

        assertNotNull(comboBox);

        SwingUtilities.invokeAndWait(() -> {
            comboBox.setSelectedIndex(1);
        });

        JLabel infoLabel =
            findLabel(
                panel,
                "EXP: 250 | HP: 150 | ATK: 35 | DEF: 25"
            );

        assertNotNull(infoLabel);

        assertEquals(
            hero2,
            comboBox.getSelectedItem()
        );
    }

    // ============================================================
    // START BUTTON
    // ============================================================

    @Test
    void startButton_startsGameWithChosenHero()
        throws Exception {

        Action mainMenuAction =
            mock(Action.class);

        Action gamePanelAction =
            mock(Action.class);

        GameController controller =
            mock(GameController.class);

        SelectHeroFromListPanel panel =
            createPanel(
                mainMenuAction,
                gamePanelAction,
                controller
            );

        Hero hero = mock(Hero.class);

        panel.updateHeroList(
            List.of(hero)
        );

        JButton startButton =
            findButton(panel, "Start");

        assertNotNull(startButton);

        SwingUtilities.invokeAndWait(
            startButton::doClick
        );

        verify(controller).setGamePhase(
            Phases.GAMEPLAY
        );

        verify(controller).startGame(
            hero
        );

        verify(gamePanelAction)
            .actionPerformed(
                any(ActionEvent.class)
            );
    }

    // ============================================================
    // START WITHOUT HERO
    // ============================================================

    @Test
    void startButton_withEmptyHeroList_doesNothing()
        throws Exception {

        Action mainMenuAction =
            mock(Action.class);

        Action gamePanelAction =
            mock(Action.class);

        GameController controller =
            mock(GameController.class);

        SelectHeroFromListPanel panel =
            createPanel(
                mainMenuAction,
                gamePanelAction,
                controller
            );

        panel.updateHeroList(
            new ArrayList<>()
        );

        JButton startButton =
            findButton(panel, "Start");

        assertNotNull(startButton);

        SwingUtilities.invokeAndWait(
            startButton::doClick
        );

        verify(controller, never())
            .setGamePhase(any());

        verify(controller, never())
            .startGame(any());

        verifyNoInteractions(gamePanelAction);
    }

    // ============================================================
    // BACK BUTTON
    // ============================================================

    @Test
    void constructor_backButton_usesMainMenuAction()
        throws Exception {

        Action mainMenuAction =
            mock(Action.class);

        Action gamePanelAction =
            mock(Action.class);

        GameController controller =
            mock(GameController.class);

        SelectHeroFromListPanel panel =
            createPanel(
                mainMenuAction,
                gamePanelAction,
                controller
            );

        JButton backButton =
            findButtonWithAction(
                panel,
                mainMenuAction
            );

        assertNotNull(backButton);

        assertSame(
            mainMenuAction,
            backButton.getAction()
        );
    }


    // ============================================================
    // HELPER: CREATE PANEL
    // ============================================================

    private SelectHeroFromListPanel createPanel()
        throws Exception {

        return createPanel(
            mock(Action.class),
            mock(Action.class),
            mock(GameController.class)
        );
    }

    private SelectHeroFromListPanel createPanel(
        Action mainMenuAction,
        Action gamePanelAction,
        GameController controller
    ) throws Exception {

        SelectHeroFromListPanel[] result =
            new SelectHeroFromListPanel[1];

        SwingUtilities.invokeAndWait(() -> {

            result[0] =
                new SelectHeroFromListPanel(
                    mainMenuAction,
                    gamePanelAction,
                    controller
                );
        });

        return result[0];
    }

    // ============================================================
    // HELPER: FIND BUTTON
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

    // ============================================================
    // HELPER: FIND COMBO BOX
    // ============================================================

    private JComboBox<Hero> findComboBox(
        Container container
    ) {

        for (Component component :
                container.getComponents()) {

            if (component instanceof JComboBox) {

                /*
                 * The production code creates:
                 *
                 * JComboBox<Hero>
                 *
                 * The cast is safe here because this test is
                 * specifically testing SelectHeroFromListPanel.
                 */
                @SuppressWarnings("unchecked")
                JComboBox<Hero> comboBox =
                    (JComboBox<Hero>) component;

                return comboBox;
            }

            if (component instanceof Container) {

                JComboBox<Hero> comboBox =
                    findComboBox(
                        (Container) component
                    );

                if (comboBox != null) {
                    return comboBox;
                }
            }
        }

        return null;
    }

    // ============================================================
    // HELPER: FIND LABEL
    // ============================================================

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
}
