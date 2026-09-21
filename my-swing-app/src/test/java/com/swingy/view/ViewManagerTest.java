package com.swingy.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import com.swingy.controller.GameController;
import com.swingy.view.console.ConsoleView;
import com.swingy.view.gui.GuiView;

class ViewManagerTest {

    @Test
    void switchTo_hidesPreviousViewAndShowsNewView() {
        GameController controller = mock(GameController.class);

        try (MockedConstruction<ConsoleView> consoleMock =
                     mockConstruction(ConsoleView.class);
             MockedConstruction<GuiView> guiMock =
                     mockConstruction(GuiView.class)) {

            ViewManager manager = new ViewManager(controller);

            ConsoleView consoleView =
                    consoleMock.constructed().get(0);

            GuiView guiView =
                    guiMock.constructed().get(0);

            manager.switchTo(consoleView);

            verify(consoleView).show();
            verify(consoleView, never()).hide();

            manager.switchTo(guiView);

            verify(consoleView).hide();
            verify(guiView).show();
        }
    }

    @Test
    void switchToConsole_showsConsoleView() {
        GameController controller = mock(GameController.class);

        try (MockedConstruction<ConsoleView> consoleMock =
                     mockConstruction(ConsoleView.class);
             MockedConstruction<GuiView> guiMock =
                     mockConstruction(GuiView.class)) {

            ViewManager manager = new ViewManager(controller);

            ConsoleView consoleView =
                    consoleMock.constructed().get(0);

            manager.switchToConsole();

            verify(consoleView).show();
            verify(consoleView, never()).hide();
        }
    }

    @Test
    void switchToSwing_hidesConsoleAndShowsGuiView() {
        GameController controller = mock(GameController.class);

        try (MockedConstruction<ConsoleView> consoleMock =
                     mockConstruction(ConsoleView.class);
             MockedConstruction<GuiView> guiMock =
                     mockConstruction(GuiView.class)) {

            ViewManager manager = new ViewManager(controller);

            ConsoleView consoleView =
                    consoleMock.constructed().get(0);

            GuiView guiView =
                    guiMock.constructed().get(0);

            manager.switchToConsole();
            manager.switchToSwing();

            verify(consoleView).show();
            verify(consoleView).hide();
            verify(guiView).show();
        }
    }

    @Test
    void switchTo_firstView_doesNotAttemptToHideAnything() {
        GameController controller = mock(GameController.class);

        try (MockedConstruction<ConsoleView> consoleMock =
                     mockConstruction(ConsoleView.class);
             MockedConstruction<GuiView> guiMock =
                     mockConstruction(GuiView.class)) {

            ViewManager manager = new ViewManager(controller);

            ConsoleView consoleView =
                    consoleMock.constructed().get(0);

            manager.switchTo(consoleView);

            verify(consoleView).show();
            verify(consoleView, never()).hide();
        }
    }
}
