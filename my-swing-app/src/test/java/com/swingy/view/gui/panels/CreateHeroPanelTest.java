package com.swingy.view.gui;

import com.swingy.controller.GameController;
import com.swingy.model.HeroCredentials;
import com.swingy.model.Hero;
import com.swingy.model.HeroArchetype;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateHeroPanelTest {

    private GameController controller;
    private Action mainMenuAction;
    private Action gamePanelAction;
    private CreateHeroPanel panel;

    @BeforeEach
    void setUp() {
        controller = mock(GameController.class);
        mainMenuAction = mock(Action.class);
        gamePanelAction = mock(Action.class);

        // Swing components should preferably be created on the EDT.
        SwingUtilities.invokeLater(() -> {
            panel = new CreateHeroPanel(
                    mainMenuAction,
                    gamePanelAction,
                    controller
            );
        });

        // Wait until the EDT has finished creating the panel.
        try {
            SwingUtilities.invokeAndWait(() -> {});
        } catch (Exception e) {
            fail("Could not initialize Swing panel", e);
        }
    }

    @Test
    void shouldReturnEnteredHeroName() throws Exception {
        JTextField nameField = findComponent(JTextField.class);

        SwingUtilities.invokeAndWait(() ->
                nameField.setText("Gandalf")
        );

        assertEquals("Gandalf", panel.getHeroName());
    }

    @Test
    void shouldReturnSelectedHeroClass() throws Exception {
        JComboBox<?> classBox = findComponent(JComboBox.class);

        SwingUtilities.invokeAndWait(() ->
                classBox.setSelectedItem("warrior")
        );

        assertEquals("warrior", panel.getHeroClass());
    }

    @Test
    void shouldHaveExpectedHeroClasses() {
        JComboBox<?> classBox = findComponent(JComboBox.class);

        assertEquals(3, classBox.getItemCount());
        assertEquals("wizard", classBox.getItemAt(0));
        assertEquals("warrior", classBox.getItemAt(1));
        assertEquals("barbarian", classBox.getItemAt(2));
    }

    @Test
    void shouldNotStartGameWhenHeroNameIsEmpty() throws Exception {
        JButton startButton = findButton("Start");

        SwingUtilities.invokeAndWait(startButton::doClick);

        verify(controller, never()).createHero(any(HeroCredentials.class));
        verify(controller, never()).startGame(any());
        verify(gamePanelAction, never())
                .actionPerformed(any(ActionEvent.class));
    }

    @Test
    void shouldNotStartGameWhenHeroNameContainsOnlySpaces()
            throws Exception {

        JTextField nameField = findComponent(JTextField.class);
        JButton startButton = findButton("Start");

        SwingUtilities.invokeAndWait(() -> {
            nameField.setText("   ");
            startButton.doClick();
        });

        verify(controller, never()).createHero(any(HeroCredentials.class));
        verify(controller, never()).startGame(any());
        verify(gamePanelAction, never())
                .actionPerformed(any(ActionEvent.class));
    }

    @Test
    void shouldCreateHeroAndStartGameWhenInputIsValid()
            throws Exception {

        JTextField nameField = findComponent(JTextField.class);
        JComboBox<?> classBox = findComponent(JComboBox.class);
        JButton startButton = findButton("Start");

        Hero hero = new Hero("Cezar", "warrior",1,0,3,3,3);

        when(controller.createHero(any(HeroCredentials.class)))
                .thenReturn(hero);

        SwingUtilities.invokeAndWait(() -> {
            nameField.setText("Gandalf");
            classBox.setSelectedItem("wizard");

            startButton.doClick();
        });

        verify(controller).createHero(any(HeroCredentials.class));
        verify(controller).startGame(hero);
        verify(gamePanelAction).actionPerformed(any(ActionEvent.class));
    }

    @Test
    void shouldPassCorrectCredentialsToCreateHero()
            throws Exception {

        JTextField nameField = findComponent(JTextField.class);
        JComboBox<?> classBox = findComponent(JComboBox.class);
        JButton startButton = findButton("Start");

        Hero hero = new Hero("Conan", "barbarian",1,0,3,3,3);
        when(controller.createHero(any(HeroCredentials.class)))
                .thenReturn(hero);

        SwingUtilities.invokeAndWait(() -> {
            nameField.setText("Conan");
            classBox.setSelectedItem("barbarian");

            startButton.doClick();
        });

        verify(controller).createHero(argThat(credentials ->
                credentials != null
                // Adjust these getters if HeroCredentials
                // uses different method names.
                && credentials.getName().equals("Conan")
                && credentials.getHeroType() == HeroArchetype.BARBARIAN
        ));
    }

    @Test
    void shouldCallGamePanelActionOnlyAfterSuccessfulValidation()
            throws Exception {

        JTextField nameField = findComponent(JTextField.class);
        JButton startButton = findButton("Start");

        when(controller.createHero(any(HeroCredentials.class)))
                .thenReturn(new Hero("Cezar", "warrior",1,0,3,3,3));

        SwingUtilities.invokeAndWait(() -> {
            nameField.setText("Hero");
            startButton.doClick();
        });

        verify(gamePanelAction, times(1))
                .actionPerformed(any(ActionEvent.class));
    }

    private <T extends Component> T findComponent(Class<T> type) {
        for (Component component : panel.getComponents()) {
            if (type.isInstance(component)) {
                return type.cast(component);
            }
        }

        fail("Could not find component: " + type.getSimpleName());
        return null;
    }

    // private JButton findButton(String text) {
    //     for (Component component : panel.getComponents()) {
    //         if (component instanceof JButton button
    //                 && text.equals(button.getText())) {
    //             return button;
    //         }
    //     }

    //     fail("Could not find button: " + text);
    //     return null;
    // }
    private JButton findButton(String text) {
        JButton button = findButton((Container) panel, text);

        if (button == null) {
            throw new AssertionError("Could not find button: " + text);
        }

        return button;
    }

    private JButton findButton(Container container, String text) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton button
                    && text.equals(button.getText())) {
                return button;
            }

            if (component instanceof Container child) {
                JButton result = findButton(child, text);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;  // <-- important
    }

    private void printComponents(Container container, String indent) {
        for (Component component : container.getComponents()) {
            System.out.println(
                    indent
                    + component.getClass().getSimpleName()
                    + " : "
                    + (component instanceof JButton
                        ? ((JButton) component).getText()
                        : "")
            );

            if (component instanceof Container child) {
                printComponents(child, indent + "  ");
            }
        }
    }

}
