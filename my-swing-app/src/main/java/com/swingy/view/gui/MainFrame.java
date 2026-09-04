package com.swingy.view.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;

import javax.swing.UIManager;

import com.swingy.controller.GameController;
import com.swingy.controller.Phases;
import com.swingy.controller.NavigationController;
import com.swingy.controller.MainMenuAction;
import com.swingy.controller.GamePanelAction;
import com.swingy.controller.GameOverWonPanelAction;
import com.swingy.controller.GameOverLostPanelAction;
import com.swingy.controller.ExitAction;

class MainFrame extends JFrame {
    private final GameController controller;
    private CardLayout cardLayout;
    private JPanel container;
    private MainMenuPanel mainMenuPanel;
    private GamePanel gamePanel;
    private CreateHeroPanel createHeroPanel;
    private SelectHeroFromListPanel selectHeroFromListPanel;
    private GameOverPanel gameOverPanelWon;
    private GameOverPanel gameOverPanelLost;

    public MainFrame(GameController controller) {
        UIManager.put("OptionPane.background", Color.DARK_GRAY);
        UIManager.put("Panel.background", Color.DARK_GRAY);
        UIManager.put("Panel.foreground", Color.WHITE);
        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);


        this.controller = controller;
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        NavigationController navigator = new NavigationController(cardLayout, container);

        MainMenuAction mainMenuAction = new MainMenuAction(navigator);
        GamePanelAction gamePanelAction = new GamePanelAction(navigator);
        ExitAction exitAction = new ExitAction(this);
        GameOverWonPanelAction gameOverWonPanelAction = new GameOverWonPanelAction(navigator);
        GameOverLostPanelAction gameOverLostPanelAction = new GameOverLostPanelAction(navigator);

        // Create panels
        mainMenuPanel = new MainMenuPanel(exitAction);
        createHeroPanel = new CreateHeroPanel(mainMenuAction, gamePanelAction, controller);
        gamePanel = new GamePanel(gameOverWonPanelAction, gameOverLostPanelAction, controller);
        selectHeroFromListPanel = new SelectHeroFromListPanel(mainMenuAction, gamePanelAction, controller);
        gameOverPanelWon = new GameOverPanel(true, () -> showPanel("CREATE"), () -> System.exit(0));
        gameOverPanelLost = new GameOverPanel(false, () -> showPanel("CREATE"), () -> System.exit(0));

        // Register panels with names
        container.add(mainMenuPanel, "MENU");
        container.add(gamePanel, "GAME");
        container.add(createHeroPanel, "CREATE");
        container.add(selectHeroFromListPanel, "SELECT");
        container.add(gameOverPanelWon, "GAME_OVER_WON");
        container.add(gameOverPanelLost, "GAME_OVER_LOST");

        add(container);
        setTitle("Swingy");
        // setContentPane(this.mainPanel);

        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // mainMenuPanel = new MainMenuPanel();

        mainMenuPanel.addNewGameListener(e -> {
            showPanel("CREATE");
            this.controller.setGamePhase(Phases.HERO_CREATION);
        });
        mainMenuPanel.addLoadHeroesButtonListener(e -> {
            showPanel("SELECT");
            this.controller.setGamePhase(Phases.HERO_SELECTION);
        });


    }

    public void showPanel(String name) {
        cardLayout.show(container, name);
    }

    public void showGamePanelPopup() {
        gamePanel.showPopup(this.controller.getGamePhase());
    }

    public MainMenuPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    public CreateHeroPanel getCreateHeroPanel() {
        return createHeroPanel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public GameOverPanel getGameOverPanel(boolean won) {
        return won ? gameOverPanelWon : gameOverPanelLost;
    }

    public SelectHeroFromListPanel getSelectHeroFromListPanel() {
        return selectHeroFromListPanel;
    }

    public void addLoadHeroesButtonListener(ActionListener listener) {
        mainMenuPanel.addLoadHeroesButtonListener(listener);
    }
}
