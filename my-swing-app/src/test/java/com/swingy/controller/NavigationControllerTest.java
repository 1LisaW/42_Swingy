package com.swingy.controller;

import org.junit.jupiter.api.Test;

import javax.swing.JPanel;
import java.awt.CardLayout;

import static org.mockito.Mockito.*;

class NavigationControllerTest {

    @Test
    void showMainMenu_showsMenuCard() {
        CardLayout layout = mock(CardLayout.class);
        JPanel cards = new JPanel();

        NavigationController navigation =
            new NavigationController(layout, cards);

        navigation.showMainMenu();

        verify(layout).show(cards, "MENU");
    }

    @Test
    void showGamePanel_showsGameCard() {
        CardLayout layout = mock(CardLayout.class);
        JPanel cards = new JPanel();

        NavigationController navigation =
            new NavigationController(layout, cards);

        navigation.showGamePanel();

        verify(layout).show(cards, "GAME");
    }

    @Test
    void showGameOverPanel_whenWon_showsWonCard() {
        CardLayout layout = mock(CardLayout.class);
        JPanel cards = new JPanel();

        NavigationController navigation =
            new NavigationController(layout, cards);

        navigation.showGameOverPanel(true);

        verify(layout).show(cards, "GAME_OVER_WON");
    }

    @Test
    void showGameOverPanel_whenLost_showsLostCard() {
        CardLayout layout = mock(CardLayout.class);
        JPanel cards = new JPanel();

        NavigationController navigation =
            new NavigationController(layout, cards);

        navigation.showGameOverPanel(false);

        verify(layout).show(cards, "GAME_OVER_LOST");
    }

}
