package com.swingy.controller.actions;

import com.swingy.controller.NavigationController;

import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;

import static org.mockito.Mockito.*;

class GameOverWonPanelActionTest {

    @Test
    void actionPerformed_showsGameOverWonPanel() {
        NavigationController navigation = mock(NavigationController.class);
        GameOverWonPanelAction action = new GameOverWonPanelAction(navigation);

        ActionEvent event = new ActionEvent(
            this,
            ActionEvent.ACTION_PERFORMED,
            "Game over won panel"
        );

        action.actionPerformed(event);

        verify(navigation).showGameOverPanel(true);
    }

}
