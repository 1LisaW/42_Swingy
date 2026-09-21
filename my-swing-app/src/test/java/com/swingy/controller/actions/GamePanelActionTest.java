package com.swingy.controller.actions;

import com.swingy.controller.NavigationController;

import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;

import static org.mockito.Mockito.*;

class GamePanelActionTest {

    @Test
    void actionPerformed_showsGamePanel() {
        NavigationController navigation = mock(NavigationController.class);
        GamePanelAction action = new GamePanelAction(navigation);

        ActionEvent event = new ActionEvent(
            this,
            ActionEvent.ACTION_PERFORMED,
            "Game panel"
        );

        action.actionPerformed(event);

        verify(navigation).showGamePanel();
    }

}
