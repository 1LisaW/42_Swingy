package com.swingy.controller.actions;

import com.swingy.controller.NavigationController;

import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;

import static org.mockito.Mockito.*;

class MainMenuActionTest {

    @Test
    void actionPerformed_showsMainMenu() {
        NavigationController navigation = mock(NavigationController.class);
        MainMenuAction action = new MainMenuAction(navigation);

        ActionEvent event = new ActionEvent(
            this,
            ActionEvent.ACTION_PERFORMED,
            "Main menu"
        );

        action.actionPerformed(event);

        verify(navigation).showMainMenu();
    }

}
