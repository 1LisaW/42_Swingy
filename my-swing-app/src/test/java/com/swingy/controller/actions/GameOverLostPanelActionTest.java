package com.swingy.controller.actions;

import com.swingy.controller.NavigationController;

import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;

import static org.mockito.Mockito.*;

class GameOverLostPanelActionTest {

    @Test
    void actionPerformed_showsGameOverLostPanel() {
        NavigationController navigation = mock(NavigationController.class);
        GameOverLostPanelAction action = new GameOverLostPanelAction(navigation);

        ActionEvent event = new ActionEvent(
            this,
            ActionEvent.ACTION_PERFORMED,
            "Game over lost panel"
        );

        action.actionPerformed(event);

        verify(navigation).showGameOverPanel(false);
    }

}
